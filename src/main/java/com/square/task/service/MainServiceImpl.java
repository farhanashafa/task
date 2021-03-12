package com.square.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.square.task.entity.Blogger;
import com.square.task.entity.Blogpost;
import com.square.task.entity.Users;
import com.square.task.repositories.BloggerRepository;
import com.square.task.repositories.BlogpostRepository;
import com.square.task.repositories.UsersRepository;
import com.square.task.utils.SuperAdmin;

@Service
public class MainServiceImpl implements MainService {
    private UsersRepository usersRepository;
    private BloggerRepository bloggerRepository;
    private BlogpostRepository blogpostRepository;

    @Autowired
    public MainServiceImpl(UsersRepository usersRepository, BloggerRepository bloggerRepository, BlogpostRepository blogpostRepository) {
        this.usersRepository = usersRepository;
        this.bloggerRepository = bloggerRepository;
        this.blogpostRepository = blogpostRepository;
    }

	public Users findUserByUsernameAndPass (String username, String password) {
		Users user = usersRepository.findUserByUsernameAndPassword(username, password);
		if (user==null) {
			SuperAdmin superAdmin = new SuperAdmin();
			if (superAdmin.getUsername().equals(username)==true && superAdmin.getPassword().equals(password)==true) {
				user = new Users();
				user.setUsername(username);
				user.setPassword(password);
				user.setCreation_date(new java.util.Date());
				user.setUser_type("Admin");
				user = create(user);
			}
		}
		return user;
	}

	public Users create(Users user) {
		System.out.println("user created-->" + user.getUser_type());
		user = usersRepository.save(user);
		if (user.getUser_type().equals("Blogger")==true) {
			Blogger blogger = new Blogger();
			blogger.setStatus(0);
			blogger.setUser_id(user);
			create(blogger);
		}
		return user;
	}

	public List<Users> getUsersListByType(String user_type) {
		return usersRepository.getUsersListByType(user_type);
	}

	public List<Users> getUsersListByUsername(String username) {
		return usersRepository.getUsersListByUsername(username);
	}

	public Blogger create(Blogger blogger) {
		System.out.println("blogger created: " + blogger.getUser_id().getId());
		return bloggerRepository.save(blogger);
	}

	public List<Blogger> getBloggerListByStatus(Integer status) {
		return bloggerRepository.getBloggerListByStatus(status);//0=not approved; 1=approved & active; 2=inactive
	}

	public Blogger getBloggerById(Long blogger_id) {
		Blogger blogger = bloggerRepository.findById(blogger_id).orElse(new Blogger());
		return blogger;
	}

	public boolean changeBloggerStatus(Blogger blogger, Users admin) {
		try {
			if (blogger.getStatus()==0) {
				blogger.setStatus(1);
				blogger.setApproved_by(admin);
				blogger.setApprove_time(new java.util.Date());
			} 
			else if(blogger.getStatus()==1) {
				blogger.setStatus(2);
			} 
			else if(blogger.getStatus()==2) {
				blogger.setStatus(1);
			}
			create(blogger);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Blogger getBloggerByUserId(Users user_id) {
		return bloggerRepository.findByUser_id(user_id);
	}

	public Blogpost create(Blogpost blogpost) {
		return blogpostRepository.save(blogpost);
	}

	public List<Blogpost> getBlogpostListBystatus(Integer status) {
		return blogpostRepository.getBlogpostListBystatus(status);
	}
}