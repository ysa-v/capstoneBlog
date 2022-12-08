package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleDaoDBTest {

    @Autowired
    ArticleDao aDao;
    @Autowired
    TagDao tDao;

    @BeforeEach
    void setUp() {
        List<Article> articles = aDao.getAllArticles();
        for (Article article: articles) {
            aDao.deleteArticleByID(article.getArticleID());
        }

        List<Tag> tags = tDao.getAllTags();
        for (Tag tag: tags) {
            tDao.deleteTagByID(tag.getTagID());
        }
    }

    @Test
    void testAddAndGetArticle() {
        Article article = new Article();
        article.setArticleTitle("Test Title");
        article.setArticleContent("Test content.");
        article.setArticleDisplay(0);
        article = aDao.addArticle(article);

        Article fromDao = aDao.getArticleByID(article.getArticleID());
        assertEquals(article, fromDao);
    }

    @Test
    void testGetAllArticles() {
        Article article = new Article();
        article.setArticleTitle("Test Title");
        article.setArticleContent("Test content.");
        article.setArticleDisplay(0);
        article = aDao.addArticle(article);

        Article article2 = new Article();
        article2.setArticleTitle("Test Title 2");
        article2.setArticleContent("Test content 2.");
        article2.setArticleDisplay(0);
        article2 = aDao.addArticle(article2);

        List<Article> articles = new ArrayList<>();
        articles.add(article);
        articles.add(article2);

        List<Article> fromDao = aDao.getAllArticles();
        assertEquals(2, fromDao.size());
        assertTrue(fromDao.containsAll(articles));
    }

    @Test
    void testUpdateArticle() {
        Article article = new Article();
        article.setArticleTitle("Test Title");
        article.setArticleContent("Test content.");
        article.setArticleDisplay(0);
        article = aDao.addArticle(article);

        Article fromDao = aDao.getArticleByID(article.getArticleID());
        assertEquals(article, fromDao);

        article.setTimeExpires(ZonedDateTime.of(
                2023,1,1,0,0,0,0,
                ZoneId.of("America/Chicago")).truncatedTo(ChronoUnit.SECONDS));

        fromDao = aDao.getArticleByID(article.getArticleID());
        assertNotEquals(article, fromDao);

        aDao.updateArticle(article);

        fromDao = aDao.getArticleByID(article.getArticleID());
        assertEquals(article, fromDao);
    }

    @Test
    void testDeleteArticleByID() {
        Article article = new Article();
        article.setArticleTitle("Test Title");
        article.setArticleContent("Test content.");
        article.setArticleDisplay(0);
        article = aDao.addArticle(article);

        Article fromDao = aDao.getArticleByID(article.getArticleID());
        assertEquals(article, fromDao);

        aDao.deleteArticleByID(article.getArticleID());

        fromDao = aDao.getArticleByID(article.getArticleID());
        assertNull(fromDao);
    }

    @Test
    void testAddAndGetTagsForArticle() {
        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag = tDao.addTag(tag);

        Tag tag2 = new Tag();
        tag2.setTagName("Test tag 2");
        tag2 = tDao.addTag(tag2);

        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);

        Article article = new Article();
        article.setArticleTitle("Test Title");
        article.setArticleContent("Test content.");
        article.setArticleDisplay(0);
        article.setTagsOnArticle(tags);
        article = aDao.addArticle(article);

        Article fromDao = aDao.getArticleByID(article.getArticleID());
        assertEquals(article, fromDao);

        List<Tag> fromDaoTags = fromDao.getTagsOnArticle();
        assertEquals(2, fromDaoTags.size());
        assertTrue(fromDaoTags.containsAll(tags));
    }
}