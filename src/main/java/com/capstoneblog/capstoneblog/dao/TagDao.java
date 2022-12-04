package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;

import java.util.List;

public interface TagDao {

    Tag getTagByID(int ID);
    List<Tag> getAllTags();
    Tag addTag(Tag tag);
    void updateTag(Tag tag);
    void deleteTagByID(int ID);
    List<Article> getArticlesForTag(Tag tag);
    void addArticleToTag(Tag tag);
}
