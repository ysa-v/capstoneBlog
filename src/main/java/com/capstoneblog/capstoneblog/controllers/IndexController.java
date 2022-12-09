package com.capstoneblog.capstoneblog.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capstoneblog.capstoneblog.dao.ArticleDaoDB;
import com.capstoneblog.capstoneblog.model.Article;

@Controller
public class IndexController
{
    @Autowired
    private ArticleDaoDB articleDao;
    // ArticleDao articleDao = new ArticleDaoDB();

    @RequestMapping("/")
    public String index(Model model)
    {
        // get post titles, previews, dates, and IDs

        // model.addAttribute("posttitle", "Title");
        // model.addAttribute("postsummary", "Sample summary");
        // model.addAttribute("postdate", "Nov 20, 2022");

        List<Article> allPosts = getVisibleArticles(articleDao.getAllArticles());

        List<Article> posts = new ArrayList<Article>();

        for (Article article : allPosts)
        {
            if (article.getTimeExpires() == null
                    || article.getTimeExpires().toLocalDateTime().isAfter(LocalDateTime.now()))
            {
                posts.add(article);
            }
        }

        model.addAttribute("posts", posts);

        return "index";
    }

    private List<Article> getVisibleArticles(List<Article> allArticles)
    {
        List<Article> visibleArticles = new ArrayList<>();

        for (Article article : allArticles)
        {
            if (article.getArticleDisplay() == 1)
            {
                visibleArticles.add(article);
            }
        }
        return visibleArticles;
    }
}
