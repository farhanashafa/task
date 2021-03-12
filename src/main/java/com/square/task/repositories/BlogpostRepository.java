package com.square.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.square.task.entity.Blogpost;
import com.square.task.entity.Users;

@Repository
public interface BlogpostRepository extends JpaRepository<Blogpost, Long> {
	@Query("select bp from Blogpost bp, Blogger b where b.user_id=bp.blogger_uid and b.status=1 and bp.status=:status")
	List<Blogpost> getBlogpostListBystatus(@Param("status") Integer status);
}
