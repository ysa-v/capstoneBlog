package com.capstoneblog.capstoneblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*********LOGINS*********
 * 
 * Username: Admin
 * Password: password
 * 
 * Username: Writer
 * Password: password
 * 
 * **********************/

@Controller
public class AuthController {
    @GetMapping("/login")
    public String logIn()
    {
        return "login";
    }
    
    //@GetMapping("/logout")
    //public String logout()
    //{
    //    //
    //}
}
