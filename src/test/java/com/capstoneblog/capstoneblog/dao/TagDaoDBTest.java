package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagDaoDBTest {

    @Autowired
    TagDao tDao;

    @Autowired
    ArticleDao aDao;

    @BeforeEach
    public void setUp() {
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
    void testAddAndGetTag() {
        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag = tDao.addTag(tag);

        Tag fromDao = tDao.getTagByID(tag.getTagID());
        assertEquals(tag, fromDao);
    }

    @Test
    void testGetAllTags() {
        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag = tDao.addTag(tag);

        Tag tag2 = new Tag();
        tag2.setTagName("Test tag 2");
        tag2 = tDao.addTag(tag2);

        List<Tag> tags = tDao.getAllTags();
        assertEquals(2, tags.size());
        assertTrue(tags.contains(tag));
        assertTrue(tags.contains(tag2));
    }

    @Test
    void testUpdateTag() {
        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag = tDao.addTag(tag);

        Tag fromDao = tDao.getTagByID(tag.getTagID());
        assertEquals(tag, fromDao);

        tag.setTagName("New Test Name");
        assertNotEquals(tag, fromDao);

        tDao.updateTag(tag);
        fromDao = tDao.getTagByID(tag.getTagID());
        assertEquals(tag, fromDao);
    }

    @Test
    void testDeleteTagByID() {
        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag = tDao.addTag(tag);

        Tag fromDao = tDao.getTagByID(tag.getTagID());
        assertEquals(tag, fromDao);

        tDao.deleteTagByID(tag.getTagID());
        fromDao = tDao.getTagByID(tag.getTagID());

        assertNull(fromDao);
    }

    @Test
    void testGetArticlesForTag() {
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

        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag.setArticlesWithTag(articles);
        tag = tDao.addTag(tag);

        Tag fromDao = tDao.getTagByID(tag.getTagID());
        assertEquals(tag, fromDao);

        System.out.println(tag.getArticlesWithTag());

//        List<Article> fromDaoArticles = fromDao.getArticlesWithTag();
//        assertEquals(2, fromDaoArticles.size());
//        assertTrue(fromDaoArticles.containsAll(articles));
    }
}