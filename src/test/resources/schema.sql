CREATE TABLE authors (  id int, name varchar(255), age int );
CREATE TABLE bookEntity (isbn text, title varchar(255), author_id int, FOREIGN KEY (author_id) references authors(id) );