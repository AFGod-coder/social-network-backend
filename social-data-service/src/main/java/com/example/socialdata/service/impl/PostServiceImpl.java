package com.example.socialdata.service.impl;

import com.example.socialdata.dto.request.CreatePostRequest;
import com.example.socialdata.dto.response.PostResponse;
import com.example.socialdata.entity.PostEntity;
import com.example.socialdata.entity.UserEntity;
import com.example.socialdata.exception.PostException.PostNotFoundException;
import com.example.socialdata.exception.UserException.UserNotFoundException;
import com.example.socialdata.exception.PostException.InvalidPostMessageException;
import com.example.socialdata.repository.PostRepository;
import com.example.socialdata.repository.UserRepository;
import com.example.socialdata.service.PostService;
import com.example.socialdata.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeService likeService;

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        // Validación simple del mensaje
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new InvalidPostMessageException("El mensaje no puede estar vacío");
        }

        // Verificar existencia del autor
        UserEntity author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new UserNotFoundException(request.getAuthorId()));

        PostEntity post = PostEntity.builder()
                .message(request.getMessage())
                .author(author)
                .build();

        postRepository.save(post);
        return map(post);
    }

    @Override
    public PostResponse getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::map)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::map)
                .toList();
    }

    @Override
    public void deletePost(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        postRepository.delete(post);
    }

    private PostResponse map(PostEntity p) {
        try {
            Integer likesCount = likeService.countLikes(p.getId());
            return new PostResponse(
                    p.getId(),
                    p.getMessage(),
                    p.getAuthor().getId(),
                    p.getAuthor().getAlias(),
                    p.getCreatedAt(),
                    likesCount
            );
        } catch (Exception e) {
            // Si hay error al obtener el conteo de likes, devolver 0
            return new PostResponse(
                    p.getId(),
                    p.getMessage(),
                    p.getAuthor().getId(),
                    p.getAuthor().getAlias(),
                    p.getCreatedAt(),
                    0
            );
        }
    }
}
