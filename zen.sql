CREATE DATABASE  IF NOT EXISTS `zenstucom` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `zenstucom`;
SELECT * FROM teacher;
SELECT * FROM owner;

select username, pass from customer where username = '"+nombre+"' and pass='" +password+ "' union select username, pass from owner where username = '"+nombre+"' and pass= '" +password+ "' union select username, pass from teacher where username = "mika" and pass= "mika" union select username, pass from admin where username = "mika" and pass= "mika";

-- MySQL dump 10.13  Distrib 5.6.41, for Linux (x86_64)
--
-- Host: localhost    Database: zenstucom
-- ------------------------------------------------------
-- Server version	5.6.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `idactivity` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `places` int(11) NOT NULL,
  `price` decimal(4,2) NOT NULL,
  `hours` int(11) NOT NULL,
  `teacher` varchar(10) NOT NULL,
  `center` varchar(80) NOT NULL,
  PRIMARY KEY (`idactivity`),
  KEY `fk_activity_1_idx` (`center`),
  KEY `fk_activity_2_idx` (`teacher`),
  CONSTRAINT `fk_activity_1` FOREIGN KEY (`center`) REFERENCES `center` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_activity_2` FOREIGN KEY (`teacher`) REFERENCES `teacher` (`username`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `username` varchar(10) NOT NULL,
  `pass` varchar(10) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `center`
--

DROP TABLE IF EXISTS `center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `center` (
  `name` varchar(80) NOT NULL,
  `city` int(11) NOT NULL,
  `rooms` int(11) NOT NULL,
  `price` decimal(4,2) NOT NULL,
  `owner` varchar(10) NOT NULL,
  PRIMARY KEY (`name`),
  KEY `fk_center_1_idx` (`owner`),
  KEY `fk_center_2_idx` (`city`),
  CONSTRAINT `fk_center_1` FOREIGN KEY (`owner`) REFERENCES `owner` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_center_2` FOREIGN KEY (`city`) REFERENCES `city` (`idcity`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `center`
--

LOCK TABLES `center` WRITE;
/*!40000 ALTER TABLE `center` DISABLE KEYS */;
/*!40000 ALTER TABLE `center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `idcity` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`idcity`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `username` varchar(10) NOT NULL,
  `pass` varchar(10) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `city` int(11) NOT NULL,
  PRIMARY KEY (`username`),
  KEY `fk_customer_1_idx` (`city`),
  CONSTRAINT `fk_customer_1` FOREIGN KEY (`city`) REFERENCES `city` (`idcity`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `owner`
--

DROP TABLE IF EXISTS `owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `owner` (
  `username` varchar(10) NOT NULL,
  `pass` varchar(10) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `email` varchar(60) NOT NULL,
  `phone` varchar(9) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `owner`
--

LOCK TABLES `owner` WRITE;
/*!40000 ALTER TABLE `owner` DISABLE KEYS */;
/*!40000 ALTER TABLE `owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration` (
  `customer` varchar(10) NOT NULL,
  `activity` int(11) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`customer`,`activity`),
  KEY `fk_registration_2_idx` (`activity`),
  CONSTRAINT `fk_registration_1` FOREIGN KEY (`customer`) REFERENCES `customer` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_registration_2` FOREIGN KEY (`activity`) REFERENCES `activity` (`idactivity`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `username` varchar(10) NOT NULL,
  `pass` varchar(10) NOT NULL,
  `expertise` varchar(20) NOT NULL,
  `hours` int(11) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-25 18:16:43
SELECT * FROM teacher;