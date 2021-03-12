package com.square.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.square.task.entity.Users;
import com.square.task.service.MainService;


@RestController
@RequestMapping("/rest")
public class MainRestController {
    private MainService mainService;

    @Autowired
    public MainRestController(MainService mainService) {
        this.mainService = mainService;
    }


	@GetMapping("/duplicatechk")
	@ResponseBody
	public String isDulicateUsernam(@RequestParam("uname") String username) {
		System.out.println(">>"+ username);
		List<Users> userList = mainService.getUsersListByUsername(username);
		System.out.println(">>"+ userList.size());
        if (userList.size()>0) {
        	return "YES";
        }
        else {
        	return "NO";
        }
	}

}