package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;

import java.util.List;

public interface TagDao {

    public Tag getTagByID(int ID);
    public List<Tag> getAllTags();
    public Tag addTag(Tag tag);
    public void updateTag(Tag tag);
    public void deleteTagByID(int ID);
    public List<Article> getArticlesForTag(Tag tag);
}
