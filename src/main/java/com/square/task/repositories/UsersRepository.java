package com.square.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.square.task.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	@Query("select u from Users u where u.username=:uname and u.password=:pass")
	Users findUserByUsernameAndPassword(@Param("uname") String username, @Param("pass") String password);
	
	@Query("select u from Users u where u.user_type=:utype")
	List<Users> getUsersListByType(@Param("utype") String user_type);

	@Query("select u from Users u where u.username=:uname")
	List<Users> getUsersListByUsername(@Param("uname") String username);
}
