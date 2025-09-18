package com.example.socialdata.repository;

import com.example.socialdata.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByAuthor_IdNot(Long authorId);
    List<PostEntity> findByAuthor_Id(Long authorId);
}
