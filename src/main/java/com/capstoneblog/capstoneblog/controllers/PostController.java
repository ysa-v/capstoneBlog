package com.capstoneblog.capstoneblog.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/update")
    public ResponseEntity updatePost(Article requestArticle, @RequestParam("ifExpires") boolean ifExpires,
            @RequestParam("expireDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate expireDate)
    {
        Article article = new Article();

        int requestID = requestArticle.getArticleID();
        String requestTitle = requestArticle.getArticleTitle();
        String requestContent = requestArticle.getArticleContent();
        ZonedDateTime requestPostCreated = requestID==0?ZonedDateTime.now():articleDao.getArticleByID(requestID).getTimeCreated();

        article.setArticleID(requestID);
        article.setArticleTitle(requestTitle != null ? requestTitle : "");
        article.setArticleContent(requestContent != null ? requestContent : "");
        // if writer, mark as not visible
        article.setArticleDisplay(1);
        article.setTimeCreated(requestPostCreated);
        article.setTimeUpdated(ZonedDateTime.now());
        // article.setExpireDate(ifExpires ?
        // expireDate.atStartOfDay(ZoneId.systemDefault()) : null);
        // tags

        try
        {
            if (requestID == 0)
            {
                requestID = articleDao.addArticle(article).getArticleID();
            }
            else
            {
                if (articleDao.updateArticle(article) != 1)
                {
                    return ResponseEntity.notFound().build();
                }
            }

            // return ResponseEntity.ok().build();
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/Post/" + requestID)).build();
        }
        catch (NullPointerException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
