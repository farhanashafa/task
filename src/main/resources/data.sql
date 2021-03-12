DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(200) NOT NULL,
  full_name VARCHAR(250) DEFAULT NULL,
  user_type VARCHAR(250) DEFAULT NULL,
  email VARCHAR(250) DEFAULT NULL,
  creation_date timestamp DEFAULT NULL,
  create_by INT
);

CREATE TABLE blogger (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  status int DEFAULT 0,
  approved_by INT,
  approve_time timestamp DEFAULT NULL,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES USERS(id)
);


CREATE TABLE blogpost (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  blogger_uid INT NOT NULL,
  title VARCHAR(500) NOT NULL,
  body VARCHAR(3000) NOT NULL,
  creation_time timestamp DEFAULT NULL,
  status int NOT NULL,
  approved_by int NOT NULL,
  approve_time timestamp,
  FOREIGN KEY (approved_by) REFERENCES USERS(id),
  FOREIGN KEY (blogger_uid) REFERENCES USERS(id)
);

CREATE TABLE postcomment (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  comment VARCHAR(500) NOT NULL,
  comment_time timestamp DEFAULT NULL,
  blogpost_id INT NOT NULL,
  commented_by INT NOT NULL,
  FOREIGN KEY (blogpost_id) REFERENCES blogpost(id),
  FOREIGN KEY (commented_by) REFERENCES USERS(id)
);