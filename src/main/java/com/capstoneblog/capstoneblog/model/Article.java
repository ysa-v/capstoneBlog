package com.capstoneblog.capstoneblog.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Article {

    private int articleID;
    private String articleTitle;
    private String articleContent;
    // 0 = not displayed, 1 = displayed
    private int articleDisplay;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private LocalDateTime timeExpires;
    private List<Tag> tagsOnArticle;

    public List<Tag> getTagsOnArticle() {
        return tagsOnArticle;
    }

    public void setTagsOnArticle(List<Tag> tagsOnArticle) {
        this.tagsOnArticle = tagsOnArticle;
    }

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

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public LocalDateTime getTimeExpires() {
        return timeExpires;
    }

    public void setTimeExpires(LocalDateTime timeExpires) {
        this.timeExpires = timeExpires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getArticleID() == article.getArticleID() && getArticleDisplay() == article.getArticleDisplay() && Objects.equals(getArticleContent(), article.getArticleContent()) && Objects.equals(getTimeCreated(), article.getTimeCreated()) && Objects.equals(getTimeUpdated(), article.getTimeUpdated()) && Objects.equals(getTimeExpires(), article.getTimeExpires());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticleID(), getArticleContent(), getArticleDisplay(), getTimeCreated(), getTimeUpdated(), getTimeExpires());
    }
}
