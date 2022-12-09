package com.capstoneblog.capstoneblog.model;

import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Article {

    private int articleID;
    private String articleTitle;
    private String articleContent;
    // 0 = not displayed, 1 = displayed
    private int articleDisplay;
    private ZonedDateTime timeCreated;
    private ZonedDateTime timeUpdated;
    @Nullable
    private ZonedDateTime timeExpires;
    private List<Tag> tagsOnArticle = new ArrayList<>();

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public int getArticleDisplay() {
        return articleDisplay;
    }

    public void setArticleDisplay(int articleDisplay) {
        this.articleDisplay = articleDisplay;
    }

    public ZonedDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(ZonedDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public ZonedDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(ZonedDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public ZonedDateTime getTimeExpires() {
        return timeExpires;
    }

    public void setTimeExpires(ZonedDateTime timeExpires) {
        this.timeExpires = timeExpires;
    }

    public void setTagsOnArticle(List<Tag> tagsOnArticle) {

        if (tagsOnArticle != null) {
            this.tagsOnArticle = tagsOnArticle;
            for (Tag tag : tagsOnArticle) {
                List<Article> articlesWithTag = tag.getArticlesWithTag();
                if (!articlesWithTag.contains(this)) {
                    articlesWithTag.add(this);
                    tag.setArticlesWithTag(articlesWithTag);
                }
            }
        }
    }

    public List<Tag> getTagsOnArticle() {
        return tagsOnArticle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getArticleID() == article.getArticleID()
                && getArticleDisplay() == article.getArticleDisplay()
                && Objects.equals(getArticleTitle(), article.getArticleTitle())
                && Objects.equals(getArticleContent(), article.getArticleContent())
                && Objects.equals(getTimeCreated(), article.getTimeCreated())
                && Objects.equals(getTimeUpdated(), article.getTimeUpdated())
                && Objects.equals(getTimeExpires(), article.getTimeExpires());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleID(), getArticleTitle(), getArticleContent(), getArticleDisplay(), getTimeCreated(), getTimeUpdated(), getTimeExpires(), getTagsOnArticle());
    }
}
