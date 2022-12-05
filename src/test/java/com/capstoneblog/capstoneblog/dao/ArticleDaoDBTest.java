package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleDaoDBTest {

    @Autowired
    ArticleDao aDao;
    @Autowired
    TagDao tDao;

    @Test
    void testAddAndGetArticle() {
        Article article = new Article();
        article.setArticleTitle("Test Title");
        article.setArticleContent("Test content.");
        article.setArticleDisplay(0);

        article = aDao.addArticle(article);

        Article fromDao = aDao.getArticleByID(article.getArticleID());

        System.out.println(article.getTimeCreated());
        System.out.println(fromDao.getTimeCreated());

        assertEquals(article, fromDao);
    }

    @Test
    void getAllArticles() {
    }

    @Test
    void addArticle() {
    }

    @Test
    void updateArticle() {
    }

    @Test
    void deleteArticleByID() {
    }

    @Test
    void getTagsForArticle() {
    }

    @Test
    void addTagsToArticles() {
    }
}