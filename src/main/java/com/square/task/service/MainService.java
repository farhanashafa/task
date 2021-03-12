package com.square.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.square.task.entity.Blogger;
import com.square.task.entity.Blogpost;
import com.square.task.entity.Users;

@Service
public interface MainService {
	Users findUserByUsernameAndPass (String username, String password);
	Users create(Users user);
	List<Users> getUsersListByType(String user_type);
	List<Users> getUsersListByUsername(String username);
	Blogger create(Blogger user);
	List<Blogger> getBloggerListByStatus(Integer status);
	Blogger getBloggerById(Long blogger_id);
	boolean changeBloggerStatus(Blogger blogger, Users admin);
	Blogger getBloggerByUserId(Users user_id);
	Blogpost create(Blogpost blogpost);
	List<Blogpost> getBlogpostListBystatus(Integer status);
}