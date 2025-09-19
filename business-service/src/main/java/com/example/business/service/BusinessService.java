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
            if (socialUser != null) {
                if (socialUser.getAlias() != null) {
                    authUser.setAlias(socialUser.getAlias());
                }
                if (socialUser.getDateOfBirth() != null) {
                    authUser.setDateOfBirth(socialUser.getDateOfBirth());
                }
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
            
            // Agregar conteo de likes a cada post
            for (PostDto post : posts) {
                try {
                    Integer likesCount = socialDataClient.countLikes(post.getId());
                    post.setLikesCount(likesCount);
                } catch (Exception e) {
                    log.warn("No se pudo obtener el conteo de likes para el post {}: {}", post.getId(), e.getMessage());
                    post.setLikesCount(0);
                }
            }
            
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

    public List<PostDto> getAllPosts() {
        try {
            log.info("Obteniendo todas las publicaciones");
            List<PostDto> posts = socialDataClient.getAllPosts();
            
            // Agregar conteo de likes a cada post
            for (PostDto post : posts) {
                try {
                    Integer likesCount = socialDataClient.countLikes(post.getId());
                    post.setLikesCount(likesCount);
                } catch (Exception e) {
                    log.warn("No se pudo obtener el conteo de likes para el post {}: {}", post.getId(), e.getMessage());
                    post.setLikesCount(0);
                }
            }
            
            return posts;
        } catch (FeignException e) {
            log.error("Error al obtener publicaciones, status: {}", e.status());
            throw new BusinessClientException("Error al obtener publicaciones: " + e.getMessage());
        }
    }

    public PostDto getPostById(Long id) {
        try {
            log.info("Obteniendo post con ID: {}", id);
            PostDto post = socialDataClient.getPostById(id);
            
            // Agregar conteo de likes al post
            try {
                Integer likesCount = socialDataClient.countLikes(post.getId());
                post.setLikesCount(likesCount);
            } catch (Exception e) {
                log.warn("No se pudo obtener el conteo de likes para el post {}: {}", post.getId(), e.getMessage());
                post.setLikesCount(0);
            }
            
            return post;
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + id);
        } catch (FeignException e) {
            log.error("Error al obtener post con ID: {}, status: {}", id, e.status());
            throw new BusinessClientException("Error al obtener post: " + e.getMessage());
        }
    }

    public void deletePost(Long id) {
        try {
            log.info("Eliminando post con ID: {}", id);
            socialDataClient.deletePost(id);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + id);
        } catch (FeignException e) {
            log.error("Error al eliminar post con ID: {}, status: {}", id, e.status());
            throw new BusinessClientException("Error al eliminar post: " + e.getMessage());
        }
    }

    public List<LikeDto> getLikes(Long postId) {
        try {
            log.info("Obteniendo likes del post: {}", postId);
            return socialDataClient.getLikes(postId);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", postId);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + postId);
        } catch (FeignException e) {
            log.error("Error al obtener likes del post: {}, status: {}", postId, e.status());
            throw new BusinessClientException("Error al obtener likes: " + e.getMessage());
        }
    }

    public void removeLike(Long postId, Long likeId) {
        try {
            log.info("Eliminando like con ID: {} del post: {}", likeId, postId);
            socialDataClient.removeLike(postId, likeId);
        } catch (FeignException.NotFound e) {
            log.error("Like no encontrado con ID: {}", likeId);
            throw new ResourceNotFoundException("Like no encontrado con ID: " + likeId);
        } catch (FeignException e) {
            log.error("Error al eliminar like con ID: {}, status: {}", likeId, e.status());
            throw new BusinessClientException("Error al eliminar like: " + e.getMessage());
        }
    }

    public List<UserDto> getAllUsers() {
        try {
            log.info("Obteniendo todos los usuarios");
            return socialDataClient.getAllUsers();
        } catch (FeignException e) {
            log.error("Error al obtener usuarios, status: {}", e.status());
            throw new BusinessClientException("Error al obtener usuarios: " + e.getMessage());
        }
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        try {
            log.info("Actualizando usuario con ID: {}", id);
            return socialDataClient.updateUser(id, request);
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al actualizar usuario: {}", e.getMessage());
            throw new BusinessClientException("Error de validación: " + e.getMessage());
        } catch (FeignException e) {
            log.error("Error al actualizar usuario con ID: {}, status: {}", id, e.status());
            throw new BusinessClientException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            log.info("Eliminando usuario con ID: {}", id);
            socialDataClient.deleteUser(id);
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException e) {
            log.error("Error al eliminar usuario con ID: {}, status: {}", id, e.status());
            throw new BusinessClientException("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
