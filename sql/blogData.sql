INSERT INTO article(
    articleTitle, articleContent, articleIsApproved, articleCreateDate) VALUES
    ('Happy Holidays', 'holiday cookies, trees, presents, snow', 1, '2020-12-25 10:34:09'),
    ('Summer Days', 'Beach, vacation, weddings, sun, suntan', 0, '2022-05-01 10:00:01'),
    ('Fall Themes', 'colors, trees, cooling down', 1, '2022-09-15 13:34:34');

INSERT INTO tag(hashtag) VALUES
    ('snow'),
    ('fall'),
    ('winter'),
    ('summer'),
    ('sun');

INSERT INTO user(userName, userPassword, userRole) VALUES
    ('Test Admin', 'SuperSecret123', 1),
    ('Test worker', 'ExtraSecure', 2),
    ('Test Guest', 'password', 0);

INSERT INTO article_tag (articleID, tagID) VALUES 
    (1, 1),
    (1, 3),
    (2, 4),
    (2, 5),
    (3, 3);

