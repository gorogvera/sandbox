-- I ha ve created a user called amin and I used it for mysql connection in java

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost'
WITH GRANT OPTION;

CREATE USER 'admin'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%'
WITH GRANT OPTION;
