package com.capstoneblog.capstoneblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstoneblog.capstoneblog.dao.ArticleDaoDB;

@Controller
@RequestMapping("/Post")
public class PostController
{
    @Autowired
    private ArticleDaoDB articleDao;

    @GetMapping("/New")
    public String newPost(Model model)
    {
        // Post post = new Post();
        // post.setId(0);

        model.addAttribute("action", "New");
        model.addAttribute("postcontent", "");
        // model.addAttribute("post", post);

        return "editor";
    }

    @GetMapping("/Edit")
    public String editPost(Model model)
    {
        // get post ID preferably not from URL

        // find post by post ID

        model.addAttribute("action", "Edit");
        model.addAttribute("postcontent", "Sample content to simulate editing an existing post.");
        // model.addAttribute("post", post);

        return "editor";
    }

    @GetMapping("/{postID}")
    public String viewPost(Model model, @PathVariable int postID)
    {
        model.addAttribute("post", articleDao.getArticleByID(postID));

        return "post";
    }
}
