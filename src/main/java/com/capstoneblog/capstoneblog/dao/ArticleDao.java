package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;

import java.util.List;

public interface ArticleDao {

    public Article getArticleByID(int ID);
    public List<Article> getAllArticles();
    public Article addArticle(Article article);
    public void updateArticle(Article article);
    public void deleteArticleByID(int ID);
    public List<Tag> getTagsForArticle(Article article);

}
