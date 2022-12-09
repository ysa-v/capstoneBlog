package com.capstoneblog.capstoneblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*********************
 * LOGINS
 * 
 * Username: Admin
 * Password: password
 * 
 * Username: Writer
 * Password: password
 * 
 **********************/

@Controller
public class AuthController
{
    @GetMapping("/login")
    public String logIn(Model model)
    {
        return "login";
    }

    @GetMapping("/Dashboard")
    public String dashboard(Model model)
    {
        return "adminDashboard";
    }
}
