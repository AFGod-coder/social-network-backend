package com.example.bff.service;

import com.example.bff.client.AuthClient;
import com.example.bff.client.BusinessClient;
import com.example.bff.dto.*;
import com.example.bff.exception.ExternalServiceException;
import com.example.bff.exception.ResourceNotFoundException;
import com.example.bff.exception.UnauthorizedException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BffService {

    private final AuthClient authClient;
    private final BusinessClient businessClient;

    public BffService(AuthClient authClient, BusinessClient businessClient) {
        this.authClient = authClient;
        this.businessClient = businessClient;
    }

    // Auth
    public AuthResponse login(AuthRequest request) {
        try {
            log.info("Intentando login para usuario: {}", request.email());
            return authClient.login(request);
        } catch (FeignException.Unauthorized e) {
            log.error("Credenciales inválidas para usuario: {}", request.email());
            throw new UnauthorizedException("Credenciales inválidas");
        } catch (FeignException e) {
            log.error("Error en servicio de autenticación, status: {}", e.status());
            throw new ExternalServiceException("Error en servicio de autenticación: " + e.getMessage());
        }
    }

    public AuthResponse register(RegisterRequest request) {
        try {
            log.info("Registrando nuevo usuario: {}", request.email());
            return authClient.register(request);
        } catch (FeignException.Conflict e) {
            log.error("Usuario ya existe: {}", e.getMessage());
            throw new ExternalServiceException("Error en servicio de autenticación: " + e.getMessage());
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación en registro: {}", e.getMessage());
            throw new ExternalServiceException("Error en servicio de autenticación: " + e.getMessage());
        } catch (FeignException e) {
            log.error("Error en servicio de autenticación durante registro, status: {}", e.status());
            throw new ExternalServiceException("Error en servicio de autenticación: " + e.getMessage());
        }
    }

    public UserDto getAuthUser(Long id) {
        try {
            log.info("Obteniendo usuario de autenticación con ID: {}", id);
            return authClient.getUserById(id);
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException e) {
            log.error("Error al obtener usuario de autenticación con ID: {}, status: {}", id, e.status());
            throw new ExternalServiceException("Error al obtener usuario: " + e.getMessage());
        }
    }

    // Business
    public UserDto getUser(Long id) {
        try {
            log.info("Obteniendo usuario completo con ID: {}", id);
            return businessClient.getUserById(id);
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para obtener usuario con ID: {}", id);
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al obtener usuario con ID: {}, status: {}", id, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public List<PostDto> getFeed(Long userId) {
        try {
            log.info("Obteniendo feed para usuario: {}", userId);
            List<PostDto> posts = businessClient.getFeed(userId);
            
            // Agregar conteo de likes a cada post
            for (PostDto post : posts) {
                try {
                    Integer likesCount = businessClient.countLikes(post.getId());
                    post.setLikesCount(likesCount);
                } catch (Exception e) {
                    log.warn("No se pudo obtener el conteo de likes para el post {}: {}", post.getId(), e.getMessage());
                    post.setLikesCount(0);
                }
            }
            
            return posts;
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para obtener feed del usuario: {}", userId);
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al obtener feed para usuario: {}, status: {}", userId, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public PostDto createPost(CreatePostRequest request) {
        try {
            log.info("Creando post para usuario: {}", request.getAuthorId());
            return businessClient.createPost(request);
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al crear post: {}", e.getMessage());
            throw new ExternalServiceException("Error de validación: " + e.getMessage());
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para crear post");
            throw new UnauthorizedException("No autorizado para crear posts");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al crear post, status: {}", e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public LikeDto addLike(Long postId, CreateLikeRequest request) {
        try {
            log.info("Agregando like al post: {} por usuario: {}", postId, request.getUserId());
            return businessClient.addLike(postId, request);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", postId);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + postId);
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al agregar like: {}", e.getMessage());
            throw new ExternalServiceException("Error de validación: " + e.getMessage());
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para agregar like al post: {}", postId);
            throw new UnauthorizedException("No autorizado para agregar likes");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al agregar like al post: {}, status: {}", postId, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public Integer countLikes(Long postId) {
        try {
            log.info("Contando likes del post: {}", postId);
            return businessClient.countLikes(postId);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", postId);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + postId);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para contar likes del post: {}", postId);
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al contar likes del post: {}, status: {}", postId, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        try {
            log.info("Renovando token de acceso");
            return authClient.refreshToken(request);
        } catch (FeignException.Unauthorized e) {
            log.error("Refresh token inválido o expirado");
            throw new UnauthorizedException("Refresh token inválido o expirado");
        } catch (FeignException e) {
            log.error("Error en servicio de autenticación al renovar token, status: {}", e.status());
            throw new ExternalServiceException("Error en servicio de autenticación: " + e.getMessage());
        }
    }

    public PostDto getPost(Long id) {
        try {
            log.info("Obteniendo post con ID: {}", id);
            PostDto post = businessClient.getPostById(id);
            
            // Agregar conteo de likes al post
            try {
                Integer likesCount = businessClient.countLikes(post.getId());
                post.setLikesCount(likesCount);
            } catch (Exception e) {
                log.warn("No se pudo obtener el conteo de likes para el post {}: {}", post.getId(), e.getMessage());
                post.setLikesCount(0);
            }
            
            return post;
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + id);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para obtener post con ID: {}", id);
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al obtener post con ID: {}, status: {}", id, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public List<PostDto> getAllPosts() {
        try {
            log.info("Obteniendo todas las publicaciones");
            List<PostDto> posts = businessClient.getAllPosts();
            
            // Agregar conteo de likes a cada post
            for (PostDto post : posts) {
                try {
                    Integer likesCount = businessClient.countLikes(post.getId());
                    post.setLikesCount(likesCount);
                } catch (Exception e) {
                    log.warn("No se pudo obtener el conteo de likes para el post {}: {}", post.getId(), e.getMessage());
                    post.setLikesCount(0);
                }
            }
            
            return posts;
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para obtener publicaciones");
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al obtener publicaciones, status: {}", e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public void deletePost(Long id) {
        try {
            log.info("Eliminando post con ID: {}", id);
            businessClient.deletePost(id);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + id);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para eliminar post con ID: {}", id);
            throw new UnauthorizedException("No autorizado para eliminar este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al eliminar post con ID: {}, status: {}", id, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public List<LikeDto> getLikes(Long postId) {
        try {
            log.info("Obteniendo likes del post: {}", postId);
            return businessClient.getLikesByPost(postId);
        } catch (FeignException.NotFound e) {
            log.error("Post no encontrado con ID: {}", postId);
            throw new ResourceNotFoundException("Post no encontrado con ID: " + postId);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para obtener likes del post: {}", postId);
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al obtener likes del post: {}, status: {}", postId, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public void removeLike(Long postId, Long likeId) {
        try {
            log.info("Eliminando like con ID: {} del post: {}", likeId, postId);
            businessClient.removeLike(postId, likeId);
        } catch (FeignException.NotFound e) {
            log.error("Like no encontrado con ID: {}", likeId);
            throw new ResourceNotFoundException("Like no encontrado con ID: " + likeId);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para eliminar like con ID: {}", likeId);
            throw new UnauthorizedException("No autorizado para eliminar este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al eliminar like con ID: {}, status: {}", likeId, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public List<UserDto> getAllUsers() {
        try {
            log.info("Obteniendo todos los usuarios");
            return businessClient.getAllUsers();
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para obtener usuarios");
            throw new UnauthorizedException("No autorizado para acceder a este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al obtener usuarios, status: {}", e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        try {
            log.info("Actualizando usuario con ID: {}", id);
            return businessClient.updateUser(id, request);
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException.BadRequest e) {
            log.error("Error de validación al actualizar usuario: {}", e.getMessage());
            throw new ExternalServiceException("Error de validación: " + e.getMessage());
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para actualizar usuario con ID: {}", id);
            throw new UnauthorizedException("No autorizado para actualizar este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al actualizar usuario con ID: {}, status: {}", id, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            log.info("Eliminando usuario con ID: {}", id);
            businessClient.deleteUser(id);
        } catch (FeignException.NotFound e) {
            log.error("Usuario no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + id);
        } catch (FeignException.Unauthorized e) {
            log.error("No autorizado para eliminar usuario con ID: {}", id);
            throw new UnauthorizedException("No autorizado para eliminar este recurso");
        } catch (FeignException e) {
            log.error("Error en servicio de negocio al eliminar usuario con ID: {}, status: {}", id, e.status());
            throw new ExternalServiceException("Error en servicio de negocio: " + e.getMessage());
        }
    }
}
