package com.example.socialdata.repository;

import com.example.socialdata.entity.LikeEntity;
import com.example.socialdata.entity.PostEntity;
import com.example.socialdata.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);
    Long countByPost(PostEntity post);

    List<LikeEntity> findByPostId(Long postId);
    List<LikeEntity> findByPost(PostEntity post);
}
