package com.square.task.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.square.task.entity.Blogger;
import com.square.task.entity.Blogpost;
import com.square.task.entity.Users;
import com.square.task.service.MainService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private MainService mainService;

    @Autowired
    public AdminController(MainService mainService) {
        this.mainService = mainService;
    }
    
    @RequestMapping(value = "/addadmin", method = RequestMethod.GET) 
    public String createAdminForm(Model model) {
        model.addAttribute("user_new", new Users());
        return "create_admin";
    }

    @RequestMapping(value = "/addadmin", method = RequestMethod.POST) 
    public String createAdmin(@Valid Users user, BindingResult result, Model model, HttpSession session) {
    	if (result.hasErrors()) {
            model.addAttribute("incomplete", "Please fill up all neccessary fields");
            model.addAttribute("user_new", user);
            return "create_admin";
        }
    	Users loggedin_user = (Users)session.getAttribute("user");
    	user.setCreate_by(loggedin_user.getId());
    	user.setCreation_date(new java.util.Date());
    	user.setUser_type("Admin");
    	user = mainService.create(user);
        model.addAttribute("successMsg", "Admin created successfully");
        model.addAttribute("user_new", new Users());
        return "create_admin";
    }   
	
    @RequestMapping(value = "/pendingreq", method = RequestMethod.GET) 
    public String listBloggerPending(Model model) {
        List<Blogger> bloggerList = mainService.getBloggerListByStatus(0);
        model.addAttribute("status", 0);
        model.addAttribute("bloggerList", bloggerList);
        return "list_blogger";
    }
    
    @RequestMapping(value = "/listblogger", method = RequestMethod.GET) 
    public String listBloggerActive(Model model) {
        List<Blogger> bloggerList = mainService.getBloggerListByStatus(1);
        model.addAttribute("status", 1);
        model.addAttribute("bloggerList", bloggerList);
        return "list_blogger";
    }
    
    @RequestMapping(value = "/listblogger", method = RequestMethod.POST) 
    public String listBlogger(Model model, HttpServletRequest request) {
        Integer status = 1; //default active
        System.out.println("--->" + request.getParameter("status"));
        try { status = Integer.parseInt(request.getParameter("status")); } catch (Exception e) {}
        
        List<Blogger> bloggerList = mainService.getBloggerListByStatus(status);
        model.addAttribute("status", status);
        model.addAttribute("bloggerList", bloggerList);
        return "list_blogger";
    }
    
    @RequestMapping(value = "/changeStatus/{bid}", method = RequestMethod.GET) 
    public String changeBloggerStatus(@PathVariable("bid") Long blogger_id, Model model, HttpSession session) {
    	Users loggedin_user = (Users)session.getAttribute("user");
    	if (loggedin_user==null) {
    		//model.addAttribute("user", new Users());
    		return "redirect:/index";    	
    	}
    	
        Blogger blogger = mainService.getBloggerById(blogger_id);
        if (blogger.getId()==blogger_id) {
        	if (mainService.changeBloggerStatus(blogger, loggedin_user)) {
        		model.addAttribute("msg", "Status Change Successfull");
        	} 
        	else {
        		model.addAttribute("msg", "Status Change Failed");
        	}
        }
        return "redirect:/admin/listblogger";
    }

    @RequestMapping(value = "/pengingpost", method = RequestMethod.GET) 
    public String listBlogpostPending(Model model) {
        List<Blogpost> bloggerList = mainService.getBlogpostListBystatus(0);
        System.out.println(bloggerList.size());
        model.addAttribute("status", 0);
        model.addAttribute("blogpostList", bloggerList);
        return "list_blogpost";
    }
    
}