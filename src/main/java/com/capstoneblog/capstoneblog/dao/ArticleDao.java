package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;

import java.util.List;

public interface ArticleDao {

    Article getArticleByID(int ID);
    List<Article> getAllArticles();
    Article addArticle(Article article);
    int updateArticle(Article article);
    void deleteArticleByID(int ID);
    List<Tag> getTagsForArticle(Article article);

    void addTagsToArticles(List<Article> articles);

}
