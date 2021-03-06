package com.emeka.blogspringboot.repositories;

import com.emeka.blogspringboot.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(int postId);
    String deleteByPostId(int postId);
}
