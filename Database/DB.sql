-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ApplicationProjectDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ApplicationProjectDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ApplicationProjectDB` DEFAULT CHARACTER SET utf8 ;
USE `ApplicationProjectDB` ;

-- -----------------------------------------------------
-- Table `ApplicationProjectDB`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ApplicationProjectDB`.`USER` ;

CREATE TABLE IF NOT EXISTS `ApplicationProjectDB`.`USER` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(45) NULL,
  `USER_UNAME` VARCHAR(45) NOT NULL,
  `USER_PASSWORD` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `USER_UNAME_UNIQUE` (`USER_UNAME` ASC) VISIBLE,
  PRIMARY KEY (`USER_ID`),
  UNIQUE INDEX `USER_ID_UNIQUE` (`USER_ID` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ApplicationProjectDB`.`PASSWORD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ApplicationProjectDB`.`PASSWORD` ;

CREATE TABLE IF NOT EXISTS `ApplicationProjectDB`.`PASSWORD` (
  `USER_ID` INT NOT NULL,
  `PWD_ID` INT NOT NULL AUTO_INCREMENT,
  `PWD_APP` VARCHAR(45) NULL,
  `PWD_UNAME` VARCHAR(45) NULL,
  `PWD_PASSWORD` VARCHAR(45) NULL,
  PRIMARY KEY (`PWD_ID`),
  UNIQUE INDEX `PWD_ID_UNIQUE` (`PWD_ID` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
