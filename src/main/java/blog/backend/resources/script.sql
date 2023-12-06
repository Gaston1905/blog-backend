CREATE TABLE _user (
  id INT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,    
)

CREATE TABLE categories (
  category_id INT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL
)

CREATE TABLE post (
  post_id INT PRIMARY KEY,
  post_title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  added_date TIMESTAMP,
  category_id INT,
  user_id INT,
  like_counts BIGINT NOT NULL,
  dislike_counts BIGINT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
);