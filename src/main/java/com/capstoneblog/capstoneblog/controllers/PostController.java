package com.capstoneblog.capstoneblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstoneblog.capstoneblog.dao.ArticleDaoDB;
import com.capstoneblog.capstoneblog.model.Article;

@Controller
@RequestMapping("/Post")
public class PostController
{
    @Autowired
    private ArticleDaoDB articleDao;

    @GetMapping("/New")
    public String newPost(Model model)
    {
        Article post = new Article();
        post.setArticleID(0);

        model.addAttribute("action", "New");
        model.addAttribute("post", post);
        model.addAttribute("postExpires", post.getTimeExpires() != null);

        return "editor";
    }

    @GetMapping("/Edit/{postID}")
    public String editPost(Model model, @PathVariable int postID)
    {
        // get post ID preferably not from URL

        Article post = articleDao.getArticleByID(postID);

        model.addAttribute("action", "Edit");
        model.addAttribute("post", post);
        model.addAttribute("postExpires", post.getTimeExpires() != null);

        return "editor";
    }

    @GetMapping("/{postID}")
    public String viewPost(Model model, @PathVariable int postID)
    {
        model.addAttribute("post", articleDao.getArticleByID(postID));

        return "post";
    }
}
