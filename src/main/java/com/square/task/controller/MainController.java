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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.square.task.entity.Blogger;
import com.square.task.entity.Users;
import com.square.task.service.MainService;

@Controller
public class MainController {

    private MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET) 
    public String showLoginForm(Model model) {
        System.out.println("main controller login");
        List<Users> userList = mainService.getUsersListByType("Admin");
        System.out.println("total_admin:" + userList.size());
        model.addAttribute("total_admin", userList.size());
        model.addAttribute("user", new Users());
        return "index";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET) 
    public String homePage(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST) 
    public String validateUser(@ModelAttribute Users user, Model model, HttpSession session) {
        System.out.println("main controller user validation" + user.getPassword());
        Users verified_user = mainService.findUserByUsernameAndPass(user.getUsername(), user.getPassword());

        if (verified_user!=null && verified_user.getId()!=null && verified_user.getId()>0) {
            if (verified_user.getUser_type().equals("Admin")) {
                session.setAttribute("user_type", verified_user.getUser_type());
                session.setAttribute("user", verified_user);
                return "welcome";
            }
            else if (verified_user.getUser_type().equals("Blogger")) {
            	Blogger blogger = mainService.getBloggerByUserId(verified_user);
            	if (blogger==null) {
            		model.addAttribute("msg", "User Not Found.");
            	}
            	else if (blogger.getStatus()==0) {
                    model.addAttribute("msg", "Your account has not been activated yet.");
                }
                else if (blogger.getStatus()==2) {
                    model.addAttribute("msg", "Your account has been deactivated.");
                }
                else {
                	session.setAttribute("user_type", verified_user.getUser_type());
                    session.setAttribute("blogger", blogger);
                    return "welcome";
                }
            }
            else {
                model.addAttribute("msg", "User Not Found");
            } 
        } else {
            model.addAttribute("msg", "Authentication Failed");
        }
        model.addAttribute("user", new Users());
        return "index";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET) 
    public String logout(Model model, HttpSession session) {
        System.out.println("main controller logout");
        if (session!=null) {
            session.invalidate();
        }
        model.addAttribute("user", new Users());
        return "redirect:/";
    }
	
}