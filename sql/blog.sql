DROP DATABASE IF EXISTS blogDB;
CREATE DATABASE blogDB;

USE blogDB;

DROP TABLE IF EXISTS article;
CREATE TABLE article (
    articleID INT AUTO_INCREMENT,
    articleTitle VARCHAR(100) NOT NULL,
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


DROP TABLE IF EXISTS user;
CREATE TABLE user(
    userID INT AUTO_INCREMENT,
    userName VARCHAR (20) NOT NULL,
    userPassword VARCHAR (20) NOT NULL,
    userRole INT NOT NULL,
    CONSTRAINT pk_user
        PRIMARY KEY (userID)
);


DROP TABLE IF EXISTS article_tag;
CREATE TABLE article_tag(
    articleID INT,
    tagID INT,
    CONSTRAINT pk_article_tag
        PRIMARY KEY (articleID, tagID),
    CONSTRAINT fk_article_tag_article
        FOREIGN KEY (articleID)
        REFERENCES article (articleID),
    CONSTRAINT fk_article_tag_tag
        FOREIGN KEY (tagID)
        REFERENCES tag (tagID)
);