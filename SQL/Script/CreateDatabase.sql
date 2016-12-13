-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema polygon
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema polygon
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `polygon` DEFAULT CHARACTER SET utf8 ;
USE `polygon` ;

-- -----------------------------------------------------
-- Table `polygon`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `type` ENUM('CUSTOMER', 'TECHNICIAN', 'ADMIN') NULL DEFAULT 'CUSTOMER',
  `name` VARCHAR(45) NULL,
  `phone` INT NULL,
  `company` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `postcode` INT(4) NULL,
  `city` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `polygon`.`building`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`building` (
  `building_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `date_created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `address` VARCHAR(60) NULL,
  `postcode` INT(4) NULL,
  `city` VARCHAR(45) NULL,
  `condition` ENUM('GOOD', 'MEDIUM', 'POOR', 'NONE') NULL DEFAULT 'NONE',
  `construction_year` INT NULL,
  `purpose` VARCHAR(45) NULL,
  `sqm` INT NULL,
  `healthcheck_pending` TINYINT(1) NULL DEFAULT 0,
  `assigned_tech_id` INT NULL,
  `user_id` INT(11) NULL,
  PRIMARY KEY (`building_id`),
  INDEX `fk_user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_building-user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `polygon`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`area`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`area` (
  `area_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `sqm` INT NULL,
  `building_id` INT NULL,
  PRIMARY KEY (`area_id`),
  INDEX `fk_building_id_idx` (`building_id` ASC),
  CONSTRAINT `fk_area-building_id`
    FOREIGN KEY (`building_id`)
    REFERENCES `polygon`.`building` (`building_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`room` (
  `room_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `sqm` INT NULL,
  `moisture_scan` TINYINT(1) NULL DEFAULT 0,
  `area_id` INT NULL,
  PRIMARY KEY (`room_id`),
  INDEX `fk_area_id_idx` (`area_id` ASC),
  CONSTRAINT `fk_room-area`
    FOREIGN KEY (`area_id`)
    REFERENCES `polygon`.`area` (`area_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`healthcheck`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`healthcheck` (
  `healthcheck_id` INT NOT NULL AUTO_INCREMENT,
  `date_created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `tech_id` INT NULL,
  `building_responsible` VARCHAR(45) NULL,
  `building_id` INT NULL,
  PRIMARY KEY (`healthcheck_id`),
  INDEX `fk_healthcheck-building_id_idx` (`building_id` ASC),
  CONSTRAINT `fk_healthcheck-building_id`
    FOREIGN KEY (`building_id`)
    REFERENCES `polygon`.`building` (`building_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`issue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`issue` (
  `issue_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  `recommendation` VARCHAR(45) NULL,
  `building_id` INT NULL,
  `area_id` INT NULL,
  `room_id` INT NULL,
  `healthcheck_id` INT NULL,
  PRIMARY KEY (`issue_id`),
  INDEX `fk_issue_building_idx` (`building_id` ASC),
  INDEX `fk_issue_area_idx` (`area_id` ASC),
  INDEX `fk_issue-room_idx` (`room_id` ASC),
  INDEX `fk_issue_healthcheck_id_idx` (`healthcheck_id` ASC),
  CONSTRAINT `fk_issue-building_id`
    FOREIGN KEY (`building_id`)
    REFERENCES `polygon`.`building` (`building_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_issue-area_id`
    FOREIGN KEY (`area_id`)
    REFERENCES `polygon`.`area` (`area_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_issue-room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `polygon`.`room` (`room_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_issue_healthcheck_id`
    FOREIGN KEY (`healthcheck_id`)
    REFERENCES `polygon`.`healthcheck` (`healthcheck_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`moisture_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`moisture_info` (
  `moisture_info_id` INT NOT NULL AUTO_INCREMENT,
  `measure_point` VARCHAR(45) NULL,
  `moisture_value` INT NULL,
  `room_id` INT NULL,
  PRIMARY KEY (`moisture_info_id`),
  INDEX `fk_room_id_idx` (`room_id` ASC),
  CONSTRAINT `fk_moisture_info-room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `polygon`.`room` (`room_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`building_issue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`building_issue` (
  `building_issue_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  `type` ENUM('ROOF', 'FACADE', 'OTHER') NULL,
  `building_id` INT NULL,
  PRIMARY KEY (`building_issue_id`),
  INDEX `fk_building_id_idx` (`building_id` ASC),
  CONSTRAINT `fk_building_issue-building_id`
    FOREIGN KEY (`building_id`)
    REFERENCES `polygon`.`building` (`building_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`damage_repair`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`damage_repair` (
  `damage_repair_id` INT NOT NULL AUTO_INCREMENT,
  `previous_damage` TINYINT(1) NULL,
  `date_occurred` DATETIME NULL,
  `location` VARCHAR(45) NULL,
  `details` VARCHAR(45) NULL,
  `work_done` VARCHAR(45) NULL,
  `type` ENUM('DAMP', 'ROTFUNGUS', 'MOULD', 'FIRE', 'OTHER') NULL,
  `room_id` INT NULL,
  PRIMARY KEY (`damage_repair_id`),
  INDEX `fk_room_id_idx` (`room_id` ASC),
  CONSTRAINT `fk_damage_repair-room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `polygon`.`room` (`room_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`image` (
  `image_id` INT NOT NULL AUTO_INCREMENT,
  `img_name` VARCHAR(100) NULL,
  `img_file` MEDIUMBLOB NULL,
  `issue_id` INT NULL,
  `building_id` INT NULL,
  PRIMARY KEY (`image_id`),
  INDEX `fk_image-issue_idx` (`issue_id` ASC),
  INDEX `fk_image-building_id_idx` (`building_id` ASC),
  CONSTRAINT `fk_image-issue`
    FOREIGN KEY (`issue_id`)
    REFERENCES `polygon`.`issue` (`issue_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_image-building_id`
    FOREIGN KEY (`building_id`)
    REFERENCES `polygon`.`building` (`building_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `polygon`.`document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `polygon`.`document` (
  `document_id` INT NOT NULL AUTO_INCREMENT,
  `date_created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `document_name` VARCHAR(100) NULL,
  `document_file` MEDIUMBLOB NULL,
  `document_type` VARCHAR(45) NULL,
  `building_id` INT NULL,
  INDEX `fk_document-building_idx` (`building_id` ASC),
  PRIMARY KEY (`document_id`),
  CONSTRAINT `fk_document-building_id`
    FOREIGN KEY (`building_id`)
    REFERENCES `polygon`.`building` (`building_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
