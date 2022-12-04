package com.capstoneblog.capstoneblog.dao;

import com.capstoneblog.capstoneblog.model.Article;
import com.capstoneblog.capstoneblog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
            article.setTimeCreated(rs.getTimestamp("articleCreateDate").toLocalDateTime());
            article.setTimeUpdated(rs.getTimestamp("articleCreateDate").toLocalDateTime());
            article.setTimeExpires(rs.getTimestamp("articleExpire").toLocalDateTime());
            return article;
        }
    }

    @Override
    public Article getArticleByID(int ID) {
        try {
            final String GET_ARTICLE_BY_IO = "SELECT * FROM article WHERE articleID =?";
            Article article = jdbc.queryForObject(GET_ARTICLE_BY_IO, new ArticleMapper(), ID);
            article.setTagsOnArticle(getTagsForArticle(article));
            return article;}
        catch (DataAccessException e)
            {return null;}
    }

    @Override
    public List<Article> getAllArticles() {
        try {
            final String GET_ALL_ARTICLES = "SELECT * FROM article";
            List<Article> articles = jdbc.query(GET_ALL_ARTICLES, new ArticleMapper());
            addTagsToArticles(articles);
            return articles;
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Article addArticle(Article article) {
        return null;
    }

    @Override
    public void updateArticle(Article article) {
        article.setTimeUpdated(LocalDateTime.now());
        final String UPDATE_ARTICLE = "UPDATE article SET articleTitle =?, articleContent =?, articleCreateDate =? " +
                "articleIsApproved=? articleUpdateDate =?,articleExpire = ? WHERE articleID =?";
        jdbc.update(UPDATE_ARTICLE,
                article.getArticleTitle(),
                article.getArticleContent(),
                article.getTimeCreated(),
                article.getArticleDisplay(),
                article.getTimeUpdated(),
                article.getTimeExpires(),
                article.getArticleID());

    }

    @Override
    @Transactional
    public void deleteArticleByID(int id) {
        final String DELETE_FROM_ARTICLE_TAGS = "DELETE * FROM article_tag WHERE articleID = ?";
        jdbc.update(DELETE_FROM_ARTICLE_TAGS, id);
        final String DELETE_FROM_ARTICLE = "DELETE * FROM article WHERE articleID =?";
        jdbc.update(DELETE_FROM_ARTICLE, id);

    }

    @Override
    public List<Tag> getTagsForArticle(Article article) {
        final String SELECT_TAGS_FOR_ARTICLE = "SELECT t* FROM tag t " +
                "JOIN article_tag at on t.articleID = at.articleID WHERE at.articleID =?";
        return jdbc.query(SELECT_TAGS_FOR_ARTICLE, new TagDaoDB.TagMapper(), article.getArticleID());
    }

    @Override
    public void addTagsToArticles(List<Article> articles) {
        for(Article article : articles){
            article.setTagsOnArticle(getTagsForArticle(article));
        }

    }

}
