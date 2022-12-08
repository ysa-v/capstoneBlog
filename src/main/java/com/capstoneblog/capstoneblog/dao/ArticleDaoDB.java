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
import java.time.Clock;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.time.ZonedDateTime;

@Repository
public class ArticleDaoDB implements ArticleDao{

    @Autowired
    JdbcTemplate jdbc;

    Clock clock = Clock.system(ZoneId.of("America/Chicago"));

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
            if (rs.getTimestamp("articleCreateDate") != null)
            {
                article.setTimeCreated(
                        rs.getTimestamp("articleCreateDate").toLocalDateTime().atZone(ZoneId.of("America/Chicago")));
            }
            if (rs.getTimestamp("articleUpdateDate") != null)
            {
                article.setTimeUpdated(
                        rs.getTimestamp("articleUpdateDate").toLocalDateTime().atZone(ZoneId.of("America/Chicago")));
            }

            String expiration = rs.getString("articleExpire");

            if (expiration == null) {
                return article;
            } else {
                article.setTimeExpires(rs.getTimestamp("articleExpire").toLocalDateTime().atZone(ZoneId.of("America/Chicago")));
            }
            return article;
        }
    }

    @Override
    public Article getArticleByID(int ID) {
        try {
            final String GET_ARTICLE_BY_ID = "SELECT * FROM article WHERE articleID = ?";
            Article article = jdbc.queryForObject(GET_ARTICLE_BY_ID, new ArticleMapper(), ID);
            article.setTagsOnArticle(getTagsForArticle(article));
            return article;}
        catch (DataAccessException e)
            {System.out.println("Data access issue");
                return null;}
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
    @Transactional
    public Article addArticle(Article article) {
        article.setTimeCreated(ZonedDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS));
        article.setTimeUpdated(ZonedDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS));
        final String INSERT_ARTICLE = "INSERT INTO article(articleTitle, articleContent, articleCreateDate, " +
                "articleIsApproved, articleUpdateDate, articleExpire) VALUES (?, ?, ?, ?, ?, ?)";
        jdbc.update(INSERT_ARTICLE,
                article.getArticleTitle(),
                article.getArticleContent(),
                article.getTimeCreated(),
                article.getArticleDisplay(),
                article.getTimeUpdated(),
                article.getTimeExpires());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        article.setArticleID(newID);
        return article;
    }

    @Override
    public void updateArticle(Article article) {
        article.setTimeUpdated(ZonedDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS));
        final String UPDATE_ARTICLE = "UPDATE article SET articleTitle = ?, articleContent = ?, articleCreateDate = ?, " +
                "articleIsApproved = ?, articleUpdateDate = ?, articleExpire = ? WHERE articleID = ?";
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
        final String DELETE_FROM_ARTICLE_TAGS = "DELETE at.* FROM article_tag at WHERE articleID = ?";
        jdbc.update(DELETE_FROM_ARTICLE_TAGS, id);
        final String DELETE_FROM_ARTICLE = "DELETE a.* FROM article a WHERE articleID = ?";
        jdbc.update(DELETE_FROM_ARTICLE, id);
    }

    @Override
    public List<Tag> getTagsForArticle(Article article) {
        try {
            final String SELECT_TAGS_FOR_ARTICLE = "SELECT * FROM tag " +
                    "JOIN article_tag ON tag.tagID = article_tag.tagID WHERE article_tag.articleID = ?";
            return jdbc.query(SELECT_TAGS_FOR_ARTICLE, new TagDaoDB.TagMapper(), article.getArticleID());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void addTagsToArticles(List<Article> articles) {
        for(Article article : articles){
            article.setTagsOnArticle(getTagsForArticle(article));
        }
    }
}
