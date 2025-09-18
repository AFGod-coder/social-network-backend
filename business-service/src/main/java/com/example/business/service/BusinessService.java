package com.example.business.service;

import com.example.business.client.AuthClient;
import com.example.business.client.SocialDataClient;
import com.example.business.dto.*;
import com.example.business.exception.BusinessClientException;
import com.example.business.exception.ResourceNotFoundException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BusinessService {

    private final AuthClient authClient;
    private final SocialDataClient socialDataClient;

    public BusinessService(AuthClient authClient, SocialDataClient socialDataClient) {
        this.authClient = authClient;
        this.socialDataClient = socialDataClient;
    }

    public UserDto getUserById(Long id) {
        try {
            log.info("Obteniendo usuario con ID: {}", id);
            UserDto authUser = authClient.getUserById(id);
            UserDto socialUser = socialDataClient.getUserById(id);

            // Combinar información de ambos servicios
            if (socialUser != null && socialUser.getAlias() != null) {
                authUser.setAlias(socialUser.getAlias());
            }
            return authUser;
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException e) {
            log.error("Error al obtener usuario con ID: {}, status: {}", id, e.status());
            throw new BusinessClientException("Error al obtener usuario: " + e.getMessage());
        }
    }

    public List<PostDto> getFeed(Long userId) {
        try {
            log.info("Obteniendo feed para usuario: {}", userId);
            List<PostDto> posts = socialDataClient.getAllPosts();
            // Filtrar posts del propio usuario (feed de otros usuarios)
            posts.removeIf(post -> post.getAuthorId().equals(userId));
            return posts;
        } catch (FeignException e) {
            log.error("Error al obtener feed para usuario: {}, status: {}", userId, e.status());
            throw new BusinessClientException("Error al obtener feed: " + e.getMessage());
        }
    }

    public PostDto createPost(CreatePostRequest request) {
        try {
            log.info("Creando post para usuario: {}", request.getAuthorId());
            return socialDataClient.createPost(request);
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al crear post: {}", e.getMessage());
            throw new BusinessClientException("Error de validación: " + e.getMessage());
        } catch (FeignException e) {
            log.error("Error al crear post, status: {}", e.status());
            throw new BusinessClientException("Error al crear post: " + e.getMessage());
        }
    }

    public LikeDto addLike(Long postId, CreateLikeRequest request) {
        try {
            log.info("Agregando like al post: {} por usuario: {}", postId, request.getUserId());
            return socialDataClient.addLike(postId, request);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", postId);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + postId);
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al agregar like: {}", e.getMessage());
            throw new BusinessClientException("Error de validación: " + e.getMessage());
        } catch (FeignException e) {
            log.error("Error al agregar like al post: {}, status: {}", postId, e.status());
            throw new BusinessClientException("Error al agregar like: " + e.getMessage());
        }
    }

    public Integer countLikes(Long postId) {
        try {
            log.info("Contando likes del post: {}", postId);
            return socialDataClient.countLikes(postId);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", postId);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + postId);
        } catch (FeignException e) {
            log.error("Error al contar likes del post: {}, status: {}", postId, e.status());
            throw new BusinessClientException("Error al contar likes: " + e.getMessage());
        }
    }
}
