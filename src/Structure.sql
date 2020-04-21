DROP DATABASE IF EXISTS Hotel;
CREATE DATABASE Hotel;
USE Hotel;


-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `User` (
  `idUser` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `position` TINYINT UNSIGNED NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(20) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Client` (
  `idClient` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `passportNumber` VARCHAR(45) NOT NULL,
  `passportData` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idClient`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Room` (
  `idRoom` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `bedsCount` TINYINT UNSIGNED NOT NULL,
  `price` INT UNSIGNED NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRoom`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Reservation` (
  `idReservation` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idRoom` INT UNSIGNED NOT NULL,
  `idClient` INT UNSIGNED NOT NULL,
  `startDate` DATE NOT NULL,
  `endDate` DATE NOT NULL,
  `sum` INT NOT NULL,
  PRIMARY KEY (`idReservation`),
  CONSTRAINT `idRoom`
    FOREIGN KEY (`idRoom`)
    REFERENCES `Room` (`idRoom`),
  CONSTRAINT `idClient`
    FOREIGN KEY (`idClient`)
    REFERENCES `Client` (`idClient`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Worker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Worker` (
  `idWorker` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `patronymic` VARCHAR(45) NOT NULL,
  `position` INT NOT NULL,
  PRIMARY KEY (`idWorker`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DayOfWeek`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DayOfWeek` (
  `idDayOfWeek` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`idDayOfWeek`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Schedule`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Schedule` (
  `idSchedule` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idWorker` INT UNSIGNED NOT NULL,
  `idDayOfWeek` INT UNSIGNED NOT NULL,
  `from` TIME NOT NULL,
  `to` TIME NOT NULL,
  PRIMARY KEY (`idSchedule`),
  CONSTRAINT `idWorker`
    FOREIGN KEY (`idWorker`)
    REFERENCES `Worker` (`idWorker`),
  CONSTRAINT `idDayOfWeek`
    FOREIGN KEY (`idDayOfWeek`)
    REFERENCES `DayOfWeek` (`idDayOfWeek`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cleaning`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cleaning` (
  `idCleaning` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` INT UNSIGNED NOT NULL,
  `dateTime` DATETIME NOT NULL,
  PRIMARY KEY (`idCleaning`))
ENGINE = InnoDB;


