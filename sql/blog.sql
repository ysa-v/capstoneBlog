DROP DATABASE IF EXISTS blogDB;
CREATE DATABASE blogDB;

USE blogDB;

DROP TABLE IF EXISTS article;
CREATE TABLE article (
    articleID INT AUTO_INCREMENT,
    articleTitle VARCHAR(20) NOT NULL,
    articleContent MEDIUMTEXT NOT NULL,
    articleIsApproved BIT NOT NULL,
    articleCreateDate DATETIME NOT NULL,
    articleUpdateDate DATETIME,
    articleExpire DATETIME,
    CONSTRAINT pk_article
        PRIMARY KEY (articleID)
);


DROP TABLE IF EXISTS tag;
CREATE TABLE tag(
    tagID INT AUTO_INCREMENT,
    hashtag VARCHAR(15) NOT NULL,
    CONSTRAINT pk_tag
        PRIMARY KEY (tagID)
);


DROP TABLE IF EXISTS blogger;
CREATE TABLE blogger(
    bloggerID INT AUTO_INCREMENT,
    bloggerName VARCHAR (20) NOT NULL,
    bloggerPassword VARCHAR (20) NOT NULL,
    bloggerRole INT NOT NULL,
    CONSTRAINT pk_blogger
        PRIMARY KEY (bloggerID)
); 


DROP TABLE IF EXISTS article_tag;
CREATE TABLE article_tag(
    article_tagID INT AUTO_INCREMENT,
    articleID INT NOT NULL,
    tagID INT NOT NULL,
    CONSTRAINT pk_article_tag
        PRIMARY KEY (article_tagID),
    CONSTRAINT fk_article_tag_article
        FOREIGN KEY (articleID)
        REFERENCES article (articleID),
    CONSTRAINT fk_article_tag_tag
        FOREIGN KEY (tagID)
        REFERENCES tag (tagID)
);