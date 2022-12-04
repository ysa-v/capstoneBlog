package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;

import java.util.List;

public class TagDaoDB implements TagDao{
    @Override
    public Tag getTagByID(int ID) {
        return null;
    }

    @Override
    public List<Tag> getAllTags() {
        return null;
    }

    @Override
    public Tag addTag(Tag tag) {
        return null;
    }

    @Override
    public void updateTag(Tag tag) {

    }

    @Override
    public void deleteTagByID(int ID) {

    }

    @Override
    public List<Article> getArticlesForTag(Tag tag) {
        return null;
    }

    @Override
    public void addArticleToTag(Tag tag) {

    }
}
