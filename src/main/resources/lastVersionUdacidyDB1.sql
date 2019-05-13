-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema udacidy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema udacidy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `udacidy` DEFAULT CHARACTER SET utf8 ;
USE `udacidy` ;

-- -----------------------------------------------------
-- Table `udacidy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(450) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 37
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`auth_tokens`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`auth_tokens` (
  `token` VARCHAR(45) NULL DEFAULT NULL,
  `expiration_date` DATE NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_auth_tokens_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_auth_tokens_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`content` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `content_type` ENUM('IMAGE', 'TEXT') NOT NULL,
  `content` VARCHAR(455) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`conversation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`conversation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date_creation` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`course`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`course` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `content_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `content_id`),
  INDEX `fk_course_content1_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `fk_course_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `udacidy`.`content` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`message` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(250) NOT NULL,
  `creator_id` INT(11) NOT NULL,
  `create_date` DATE NULL DEFAULT NULL,
  `conversation_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`creator_id` ASC) VISIBLE,
  INDEX `fk_message_conversation1_idx` (`conversation_id` ASC) VISIBLE,
  CONSTRAINT `fk_message_conversation1`
    FOREIGN KEY (`conversation_id`)
    REFERENCES `udacidy`.`conversation` (`id`),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`creator_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`section` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `course_id` INT(11) NOT NULL,
  `content_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `course_id`, `content_id`),
  INDEX `fk_section_course1_idx` (`course_id` ASC) VISIBLE,
  INDEX `fk_section_content1_idx` (`content_id` ASC) VISIBLE,
  CONSTRAINT `fk_section_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `udacidy`.`content` (`id`),
  CONSTRAINT `fk_section_course1`
    FOREIGN KEY (`course_id`)
    REFERENCES `udacidy`.`course` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`request` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(45) NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  `section_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_request_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_request_section1_idx` (`section_id` ASC) VISIBLE,
  CONSTRAINT `fk_request_section1`
    FOREIGN KEY (`section_id`)
    REFERENCES `udacidy`.`section` (`id`),
  CONSTRAINT `fk_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`user_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_role_user1`
    FOREIGN KEY (`id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 37
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
