-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema udacidy
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema udacidy
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `udacidy` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `udacidy`.`content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`content` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 716
DEFAULT CHARACTER SET = utf8;


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
AUTO_INCREMENT = 51
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`conference`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`conference` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content_id` INT(11) NOT NULL,
  `author_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_conference_content1_idx` (`content_id` ASC) VISIBLE,
  INDEX `fk_conference_user1_idx` (`author_id` ASC) VISIBLE,
  CONSTRAINT `fk_conference_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `udacidy`.`content` (`id`),
  CONSTRAINT `fk_conference_user1`
    FOREIGN KEY (`author_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 97
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`section` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content_id` INT(11) NOT NULL,
  `conference_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_section_content2_idx` (`content_id` ASC) VISIBLE,
  INDEX `fk_section_conference1_idx` (`conference_id` ASC) VISIBLE,
  CONSTRAINT `fk_section_conference1`
    FOREIGN KEY (`conference_id`)
    REFERENCES `udacidy`.`conference` (`id`),
  CONSTRAINT `fk_section_content2`
    FOREIGN KEY (`content_id`)
    REFERENCES `udacidy`.`content` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 473
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`request` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `creation_date_time` DATETIME NOT NULL,
  `status` VARCHAR(150) NOT NULL,
  `conference_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_request_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_request_conference1_idx` (`conference_id` ASC) VISIBLE,
  CONSTRAINT `fk_request_conference1`
    FOREIGN KEY (`conference_id`)
    REFERENCES `udacidy`.`conference` (`id`),
  CONSTRAINT `fk_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 824
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`request_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`request_form` (
  `id` INT NOT NULL,
  `section_id` INT(11) NOT NULL,
  `request_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_request_form_section_idx` (`section_id` ASC) VISIBLE,
  INDEX `fk_request_form_request1_idx` (`request_id` ASC) VISIBLE,
  CONSTRAINT `fk_request_form_section`
    FOREIGN KEY (`section_id`)
    REFERENCES `udacidy`.`section` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_form_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `udacidy`.`request` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

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
AUTO_INCREMENT = 51
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
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 716
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`conference`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`conference` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content_id` INT(11) NOT NULL,
  `author_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_conference_content1_idx` (`content_id` ASC) VISIBLE,
  INDEX `fk_conference_user1_idx` (`author_id` ASC) VISIBLE,
  CONSTRAINT `fk_conference_content1`
    FOREIGN KEY (`content_id`)
    REFERENCES `udacidy`.`content` (`id`),
  CONSTRAINT `fk_conference_user1`
    FOREIGN KEY (`author_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 97
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`conversation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`conversation` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `date_creation` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 107
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`conversation_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`conversation_group` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `conversation_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`, `conversation_id`),
  INDEX `fk_conversation_group_conversation_idx` (`conversation_id` ASC) VISIBLE,
  INDEX `fk_conversation_group_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_conversation_group_conversation`
    FOREIGN KEY (`conversation_id`)
    REFERENCES `udacidy`.`conversation` (`id`),
  CONSTRAINT `fk_conversation_group_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 118
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`conversation_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`conversation_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_conversation_type_conversation`
    FOREIGN KEY (`id`)
    REFERENCES `udacidy`.`conversation` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 107
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`message` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(250) NOT NULL,
  `creator_id` INT(11) NOT NULL,
  `creation_date_time` DATETIME NULL DEFAULT NULL,
  `conversation_id` INT(11) NOT NULL,
  `image_path` VARCHAR(200) NULL DEFAULT NULL,
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
AUTO_INCREMENT = 1370
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`request` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `creation_date_time` DATETIME NOT NULL,
  `status` VARCHAR(150) NOT NULL,
  `conference_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_request_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_request_conference1_idx` (`conference_id` ASC) VISIBLE,
  CONSTRAINT `fk_request_conference1`
    FOREIGN KEY (`conference_id`)
    REFERENCES `udacidy`.`conference` (`id`),
  CONSTRAINT `fk_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `udacidy`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 824
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`section`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`section` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content_id` INT(11) NOT NULL,
  `conference_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_section_content2_idx` (`content_id` ASC) VISIBLE,
  INDEX `fk_section_conference1_idx` (`conference_id` ASC) VISIBLE,
  CONSTRAINT `fk_section_conference1`
    FOREIGN KEY (`conference_id`)
    REFERENCES `udacidy`.`conference` (`id`),
  CONSTRAINT `fk_section_content2`
    FOREIGN KEY (`content_id`)
    REFERENCES `udacidy`.`content` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 473
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `udacidy`.`request_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `udacidy`.`request_form` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `section_id` INT(11) NOT NULL,
  `request_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_request_form_section_idx` (`section_id` ASC) VISIBLE,
  INDEX `fk_request_form_request1_idx` (`request_id` ASC) VISIBLE,
  CONSTRAINT `fk_request_form_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `udacidy`.`request` (`id`),
  CONSTRAINT `fk_request_form_section`
    FOREIGN KEY (`section_id`)
    REFERENCES `udacidy`.`section` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
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
AUTO_INCREMENT = 51
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
