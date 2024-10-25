CREATE TABLE kwex_users
(
    id        int         NOT NULL AUTO_INCREMENT,
    firstName varchar(50) NOT NULL,
    lastName varchar(50) NOT NULL,
    PRIMARY KEY (id)
);
create table kwex_relationships
(
    id        int         NOT NULL AUTO_INCREMENT,
    senderId int not null,
    receiverId int not null,
    status varchar(20) not null,
    PRIMARY KEY (id)
);
CREATE TABLE kwex_posts
(
    id        int         NOT NULL AUTO_INCREMENT,
    text varchar(500) NOT NULL,
    createdAt DATE NOT NULL,
    userId int,
    isBlocked bool,
    PRIMARY KEY (id)
);