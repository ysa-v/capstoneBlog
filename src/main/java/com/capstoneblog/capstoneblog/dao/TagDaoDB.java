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
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagDaoDB implements TagDao{

    @Autowired
    JdbcTemplate jdbc;

    public static final class TagMapper implements RowMapper<Tag>{
        @Override
        public Tag mapRow(ResultSet rs, int index) throws SQLException{
            Tag tag = new Tag();
            tag.setTagID(rs.getInt("tagID"));
            tag.setTagName(rs.getString("hashtag"));
            return tag;
        }
    }

    @Override
    public Tag getTagByID(int ID) {
        try{
            final String SELECT_TAG_BY_ID = "SELECT * FROM tag WHERE tagID =?";
            Tag tag = jdbc.queryForObject(SELECT_TAG_BY_ID, new TagMapper(), ID);
            tag.setArticlesWithTag(getArticlesForTag(tag));
            return tag;
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        try{
            final String GET_ALL_TAGS = "SELECT * FROM tag";
            List<Tag> tags = jdbc.query(GET_ALL_TAGS, new TagMapper());
            for(Tag tag : tags){
                tag.setArticlesWithTag(getArticlesForTag(tag));
            }
            return tags;
        } catch (DataAccessException ex){
            return null;
        }
    }

    @Override
    public Tag addTag(Tag tag) {
        final String INSERT_TAG = "INSERT INTO tag(hashtag) VALUES(?)";
        jdbc.update(INSERT_TAG, tag.getTagName());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        tag.setTagID(newID);
        insertArticleTagTable(tag);
        return tag;
    }

    @Override
    public void updateTag(Tag tag) {
        final String UPDATE_TAG = "UPDATE tag SET hashtag = ? WHERE tagID =?";
        jdbc.update(UPDATE_TAG, tag.getTagName(), tag.getTagID());

    }

    @Override
    @Transactional
    public void deleteTagByID(int ID) {
        final String DELETE_ARTICLE_TAG = "DELETE at.* FROM article_tag at WHERE tagID = ?";
        jdbc.update(DELETE_ARTICLE_TAG, ID);
        final String DELETE_TAG = "DELETE t.* FROM tag t WHERE tagID = ?";
        jdbc.update(DELETE_TAG, ID);

    }

    @Override
    public List<Article> getArticlesForTag(Tag tag) {
        try {
            final String SELECT_ARTICLES_FOR_TAG = "SELECT * FROM article a " +
                    "JOIN article_tag at ON a.articleID = at.articleID WHERE at.tagID =?";
            return jdbc.query(SELECT_ARTICLES_FOR_TAG, new ArticleDaoDB.ArticleMapper(), tag.getTagID());
        } catch (DataAccessException e) {
            return new ArrayList<>();
        }
    }


    private void insertArticleTagTable(Tag tag){
        final String INSERT_ARTICLE_TAG = "INSERT INTO article_tag(articleID, tagID) VALUES (?,?)";
        List<Article> articles = tag.getArticlesWithTag();
        if (!articles.isEmpty()) {
            for(Article article: articles) {
                try {
                    jdbc.update(INSERT_ARTICLE_TAG, article.getArticleID(), tag.getTagID());
                } catch (DataAccessException e) {
                    System.out.println("Duplicate entry prevented");
                }
            }
        }
    }
}
