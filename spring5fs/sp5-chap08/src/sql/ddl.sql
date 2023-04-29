create user 'spring5'@'localhost' identified by 'spring5';

create database spring5fs character set=utf8;

grant all privileges on spring5fs.* to 'spring5'@'localhost';

CREATE TABLE `spring5fs`.`MEMBER` (
  `ID` INT AUTO_INCREMENT PRIMARY KEY,
  `EMAIL` VARCHAR(255),
  `PASSWORD` VARCHAR(100),
  `NAME` VARCHAR(100),
  `REGDATE` DATETIME,
  UNIQUE KEY (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
