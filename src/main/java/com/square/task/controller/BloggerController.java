package com.square.task.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.square.task.entity.Blogger;
import com.square.task.entity.Blogpost;
import com.square.task.entity.Users;
import com.square.task.service.MainService;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

    private MainService mainService;

    @Autowired
    public BloggerController(MainService mainService) {
        this.mainService = mainService;
    }
    
    @RequestMapping(value = "/addaccount", method = RequestMethod.GET) 
    public String createAdminForm(Model model) {
        model.addAttribute("user_new", new Users());
        return "create_blogger";
    }

    @RequestMapping(value = "/addaccount", method = RequestMethod.POST) 
    public String createAdmin(@Valid Users user, BindingResult result, Model model, HttpSession session) {
    	if (result.hasErrors() || user.getEmail()==null) {
            model.addAttribute("incomplete", "Please fill up all neccessary fields");
            model.addAttribute("user_new", user);
            return "create_blogger";
        }
    	user.setCreation_date(new java.util.Date());
    	user.setUser_type("Blogger");
    	user = mainService.create(user);
        model.addAttribute("msg", "Account created successfully. Account will be activated after admin approval. Activation email will be sent.");
        model.addAttribute("user", new Users());
        return "index";
    }   

    @RequestMapping(value = "/createpost", method = RequestMethod.GET) 
    public String createPost(Model model, HttpSession session) {
    	if ((Blogger)session.getAttribute("blogger")==null) {
    		model.addAttribute("user", new Users());
            return "index";
    	}
        model.addAttribute("blogpost", new Blogpost());
        return "create_post";
    }

    
    @RequestMapping(value = "/createpost", method = RequestMethod.POST) 
    public String createPostConfirm(@Valid Blogpost post, BindingResult result, Model model, HttpSession session) {
    	if (result.hasErrors()) {
            model.addAttribute("msg", "Please fill up all neccessary fields");
            model.addAttribute("blogpost", post);
            return "create_post";
        }
    	Blogger blogger = (Blogger)session.getAttribute("blogger");
    	System.out.println(blogger.getUser_id().getId());
    	post.setBlogger_uid(blogger.getUser_id());
    	post.setCreation_time(new java.util.Date());
    	post.setStatus(0);
    	mainService.create(post);
        model.addAttribute("msg", "Blog created and will be posted after admin approval.");
        model.addAttribute("blogpost", new Blogpost());
        return "create_post";
    }   

}