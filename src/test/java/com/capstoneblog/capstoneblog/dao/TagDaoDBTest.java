package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TagDaoDBTest {

    @Autowired
    TagDao tDao;

    @Test
    void testAddAndGetTag() {
        Tag tag = new Tag();
        tag.setTagName("Test tag");
        tag = tDao.addTag(tag);

        Tag fromDao = tDao.getTagByID(tag.getTagID());
        assertEquals(tag, fromDao);
    }

    @Test
    void getAllTags() {
    }

    @Test
    void addTag() {
    }

    @Test
    void updateTag() {
    }

    @Test
    void deleteTagByID() {
    }

    @Test
    void getArticlesForTag() {
    }
}