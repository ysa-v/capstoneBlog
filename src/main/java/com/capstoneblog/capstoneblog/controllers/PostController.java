package com.capstoneblog.capstoneblog.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capstoneblog.capstoneblog.dao.ArticleDaoDB;
import com.capstoneblog.capstoneblog.dao.TagDaoDB;
import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;

@Controller
@RequestMapping("/Post")
public class PostController
{
    @Autowired
    private ArticleDaoDB articleDao;

    @Autowired
    private TagDaoDB tagDaoDB;

    @GetMapping("/New")
    public String newPost(Model model)
    {
        Article post = new Article();
        post.setArticleID(0);

        model.addAttribute("tagList", tagsSortedAlphabetically());
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

        model.addAttribute("tagList", tagsSortedAlphabetically());
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
            @RequestParam("expireDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate expireDate,
            @RequestParam int[] tagID,
            @RequestParam boolean needsApproval)
    {
        Article article = new Article();

        int requestID = requestArticle.getArticleID();
        String requestTitle = requestArticle.getArticleTitle();
        String requestContent = requestArticle.getArticleContent();
        ZonedDateTime requestPostCreated = requestID == 0 ? ZonedDateTime.now()
                : articleDao.getArticleByID(requestID).getTimeCreated();
        List<Tag> requestTags = new ArrayList<>();

        for (int id : tagID)
        {
            Tag tag = new Tag();

            tag.setTagID(id);

            requestTags.add(tag);
        }

        article.setArticleID(requestID);
        article.setArticleTitle(requestTitle != null ? requestTitle : "");
        article.setArticleContent(requestContent != null ? requestContent : "");
        article.setArticleDisplay(needsApproval ? 0 : 1);
        article.setArticleDisplay(1);
        article.setTimeCreated(requestPostCreated);
        article.setTimeUpdated(ZonedDateTime.now());
        // article.setExpireDate(ifExpires ?
        // expireDate.atStartOfDay(ZoneId.systemDefault()) : null);
        article.setTagsOnArticle(requestTags);

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

    @PostMapping("/delete/{articleID}")
    private ResponseEntity deletePost(@PathVariable int articleID)
    {
        try
        {
            articleDao.deleteArticleByID(articleID);

            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/")).build();
        }

        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }
    }

    private List<Tag> tagsSortedPopularity()
    {
        List<Tag> sortedTags = new ArrayList<>();
        List<Tag> unsortedTags = tagDaoDB.getAllTags();

        sortedTags.add(unsortedTags.get(0));
        unsortedTags.remove(0);

        for (Tag tag : unsortedTags)
        {
            int articleCount = tag.getArticlesWithTag().size();
            int sTagsLength = sortedTags.size();

            for (int i = 0; i < sTagsLength; ++i)
            {
                if (sortedTags.get(i).getArticlesWithTag().size() > articleCount)
                {
                    sortedTags.add(tag);
                    break;
                }
            }
        }

        return sortedTags;
    }

    private List<Tag> tagsSortedAlphabetically()
    {
        List<Tag> sortedTags = new ArrayList<>();
        List<Tag> unsortedTags = tagDaoDB.getAllTags();

        sortedTags.add(unsortedTags.get(0));
        unsortedTags.remove(0);

        for (Tag tag : unsortedTags)
        {
            String tagName = tag.getTagName();
            int sTagsLength = sortedTags.size();

            for (int i = 0; i < sTagsLength; ++i)
            {
                int comparedTagNames = sortedTags.get(i).getTagName().compareToIgnoreCase(tagName);
                if (comparedTagNames < 0)
                {
                    sortedTags.add(tag);
                    break;
                }
                else if (comparedTagNames == 0)
                {
                    break;
                }
            }
        }

        return sortedTags;
    }

    @GetMapping("/Tags")
    public String allTags(Model model)
    {
        model.addAttribute("tags", tagsSortedPopularity());

        return "allTags";
    }

    @GetMapping("/Tag/{tagID}")
    public String tagPage(Model model, @PathVariable int tagID)
    {
        model.addAttribute("tags", tagsSortedPopularity());
        model.addAttribute("tag", tagDaoDB.getTagByID(tagID));

        return "tag";
    }

    @GetMapping("/tagsSortedAlphabetically")
    public List<Tag> tagsSortAlpha()
    {
        return tagsSortedAlphabetically();
    }

    @PutMapping("/addTag")
    public void addTag(@RequestBody String tagName)
    {
        Tag tag = new Tag();

        tag.setTagName(tagName);

        tagDaoDB.addTag(tag);
    }
}
