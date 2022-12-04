package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDaoDB implements ArticleDao{

    @Autowired
    JdbcTemplate jdbc;

    public static final class ArticleMapper implements RowMapper<Article>
    {
        @Override
        public Article mapRow(ResultSet rs, int index) throws SQLException
        {
            Article article = new Article();
            article.setArticleID(rs.getInt("articleID"));
            article.setArticleTitle(rs.getString("articleTitle"));
            article.setArticleContent(rs.getString("articleContent"));
            article.setArticleDisplay(rs.getInt("articleIsApproved"));
            article.setTimeCreated(rs.getTime("articleCreateDate"));
            article.setTimeUpdated(rs.getTime("articleCreateDate"));
            article.setTimeExpires(rs.getTime("articleExpire"));
            return article;
        }
    }

    @Override
    public Article getArticleByID(int ID) {
        try {

            return article;}
        catch (DataAccessException e)
            {return null;}
    }

    @Override
    public List<Article> getAllArticles() {
        return null;
    }

    @Override
    public Article addArticle(Article article) {
        return null;
    }

    @Override
    public void updateArticle(Article article) {

    }

    @Override
    public void deleteArticleByID(int ID) {

    }

    @Override
    public List<Tag> getTagsForArticle(Article article) {
        return null;
    }

    @Override
    public void addTagToArticle(Article article) {

    }
}
