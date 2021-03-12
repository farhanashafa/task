package com.square.task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.square.task.entity.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
	
}

