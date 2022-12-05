package com.capstoneblog.capstoneblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(Model model)
    {
        //get post titles, previews, dates, and IDs

        model.addAttribute("posttitle", "Title");
        model.addAttribute("postsummary", "Sample summary");
        model.addAttribute("postdate", "Nov 20, 2022");

        return "index";
    }
}
