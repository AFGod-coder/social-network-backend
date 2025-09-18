package com.example.socialdata.service.impl;

import com.example.socialdata.dto.request.CreateLikeRequest;
import com.example.socialdata.dto.response.LikeResponse;
import com.example.socialdata.entity.LikeEntity;
import com.example.socialdata.entity.PostEntity;
import com.example.socialdata.entity.UserEntity;
import com.example.socialdata.exception.PostException.PostNotFoundException;
import com.example.socialdata.exception.UserException.UserNotFoundException;
import com.example.socialdata.exception.LikeException.LikeAlreadyExistsException;
import com.example.socialdata.exception.LikeException.LikeNotFoundException;
import com.example.socialdata.repository.LikeRepository;
import com.example.socialdata.repository.PostRepository;
import com.example.socialdata.repository.UserRepository;
import com.example.socialdata.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public LikeResponse addLike(Long postId, CreateLikeRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (likeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            throw new LikeAlreadyExistsException(user.getId(), postId);
        }

        LikeEntity like = LikeEntity.builder().user(user).post(post).build();
        likeRepository.save(like);
        return map(like);
    }

    @Override
    public void removeLike(Long id) {
        LikeEntity like = likeRepository.findById(id)
                .orElseThrow(() -> new LikeNotFoundException(id));
        likeRepository.delete(like);
    }

    @Override
    public List<LikeResponse> getLikesByPost(Long postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return likeRepository.findByPostId(postId).stream()
                .map(this::map)
                .toList();
    }

    @Override
    public Integer countLikes(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return (int) likeRepository.countByPostId(postId);
    }

    private LikeResponse map(LikeEntity l) {
        return new LikeResponse(
                l.getId(),
                l.getUser().getId(),
                l.getPost().getId(),
                LocalDateTime.ofInstant(l.getCreatedAt(), ZoneId.systemDefault())
        );
    }
}
