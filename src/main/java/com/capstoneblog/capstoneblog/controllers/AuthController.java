package com.capstoneblog.capstoneblog.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.capstoneblog.capstoneblog.dao.ArticleDaoDB;
import com.capstoneblog.capstoneblog.model.Article;

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
    @Autowired
    ArticleDaoDB articleDaoDB;

    @GetMapping("/login")
    public String logIn(Model model)
    {
        return "login";
    }

    @GetMapping("/Dashboard")
    public String dashboard(Model model)
    {
        model.addAttribute("posts", getUnaprovedArticles());

        return "adminDashboard";
    }

    private List<Article> getUnaprovedArticles()
    {
        List<Article> articles = new ArrayList<>();

        for (Article article : articleDaoDB.getAllArticles())
        {
            if (article.getArticleDisplay() == 0)
            {
                articles.add(article);
            }
        }
        return articles;
    }
}
