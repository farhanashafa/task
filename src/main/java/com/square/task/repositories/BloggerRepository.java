package com.square.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.square.task.entity.Blogger;
import com.square.task.entity.Users;

@Repository
public interface BloggerRepository extends JpaRepository<Blogger, Long> {

	@Query("select b from Blogger b where b.status=:status")
	List<Blogger> getBloggerListByStatus(@Param("status") Integer status);
	
	@Query("select b from Blogger b where b.user_id=:user_id")
	Blogger findByUser_id(@Param("user_id") Users user_id);

}
