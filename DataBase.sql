-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: obl
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookcopies`
--

DROP TABLE IF EXISTS `bookcopies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookcopies` (
  `ISBN` varchar(13) NOT NULL,
  `SerialCode` int(11) NOT NULL,
  `Availability` varchar(45) DEFAULT 'Available',
  PRIMARY KEY (`ISBN`,`SerialCode`),
  CONSTRAINT `ISBN` FOREIGN KEY (`ISBN`) REFERENCES `books` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookcopies`
--

LOCK TABLES `bookcopies` WRITE;
/*!40000 ALTER TABLE `bookcopies` DISABLE KEYS */;
INSERT INTO `bookcopies` VALUES ('1234567890123',1,'Available'),('1234567890123',2,'Borrowed'),('1234567890123',3,'Borrowed'),('7418529637896',1,'Borrowed'),('7777444411110',1,'Borrowed'),('7777444411110',2,'Available'),('7777444411110',3,'Available'),('7777444411110',4,'Available'),('8888555522221',1,'Available'),('8888555522221',2,'Available'),('8888555522221',3,'Available'),('9780605064225',1,'Available'),('9780605064225',2,'Available'),('9780605064225',3,'Available'),('9780605064225',4,'Available'),('9780605064225',5,'Available'),('9780747569428',1,'Available'),('9780747569428',2,'Available'),('9780747569428',3,'Available'),('9780747569428',4,'Available'),('9780747569428',5,'Available'),('9780807281611',1,'Available'),('9780807281611',2,'Available'),('9780807281611',3,'Available'),('9780807281611',4,'Available'),('9780807281611',5,'Available'),('9780807283165',1,'Available'),('9780807283165',2,'Available'),('9780807283165',3,'Available'),('9780807283165',4,'Available'),('9780807283165',5,'Available'),('9780807283165',6,'Available'),('9780807283165',7,'Available'),('9780807283165',8,'Available'),('9780807283165',9,'Available'),('9780807283165',10,'Available'),('9780807283165',11,'Available'),('9780807283165',12,'Available'),('9780807283165',13,'Available'),('9780807283165',14,'Available'),('9780807283165',15,'Available'),('9780807283165',16,'Available'),('9780807283165',17,'Available'),('9780807283165',18,'Available'),('9780807283165',19,'Available'),('9780807283165',20,'Available'),('9780807286012',1,'Available'),('9780807286012',2,'Available'),('9780807286012',3,'Available'),('9780807286012',4,'Available'),('9780807286012',5,'Available'),('9780807286012',6,'Available'),('9780807286012',7,'Available'),('9780807286012',8,'Available'),('9780807286012',9,'Available'),('9780807286012',10,'Available'),('9780807286012',11,'Available'),('9780807286012',12,'Available'),('9780807286012',13,'Available'),('9780807286012',14,'Available'),('9780807286012',15,'Available'),('9780807286012',16,'Available'),('9780807286012',17,'Available'),('9780807286012',18,'Available'),('9781413185096',1,'Borrowed'),('9781413185096',2,'Available'),('9781413185096',3,'Available'),('9781413185096',4,'Available'),('9781413185096',5,'Available'),('9781413185096',6,'Available'),('9781413185096',7,'Available'),('9781413185096',8,'Available'),('9781413185096',9,'Available'),('9781413185096',10,'Available'),('9781523480500',1,'Available'),('9781523480500',2,'Available'),('9781523480500',3,'Available'),('9781523480500',4,'Available'),('9781523480500',5,'Available'),('9781523480500',6,'Available'),('9781523480500',7,'Available'),('9781523480500',8,'Available'),('9781523480500',9,'Available'),('9781523480500',10,'Available'),('9781523480500',11,'Available'),('9781523480500',12,'Available'),('9781523480500',13,'Available'),('9781523480500',14,'Available'),('9781523480500',15,'Available'),('9781551923703',1,'Available'),('9781551923703',2,'Available'),('9781551923703',3,'Available'),('9781551923703',4,'Available'),('9781551923703',5,'Available'),('9781551923703',6,'Available'),('9781551923703',7,'Available'),('9781551923703',8,'Available'),('9781551923703',9,'Available'),('9781551923703',10,'Available'),('9781551923703',11,'Available'),('9781551923703',12,'Available'),('9781551923703',13,'Available'),('9781551923703',14,'Available'),('9781551923703',15,'Available'),('9781581306576',1,'Borrowed'),('9781581306576',2,'Available'),('9781581306576',3,'Available'),('9781594130007',1,'Available'),('9781594130007',2,'Available'),('9781594130007',3,'Available'),('9781594130007',4,'Available'),('9781594130007',5,'Available'),('9781594130007',6,'Available'),('9781594130007',7,'Available'),('9781594130007',8,'Available'),('9781594130038',1,'Available'),('9781594130038',2,'Available'),('9781594130038',3,'Available'),('9781594130038',4,'Available'),('9781594130038',5,'Available'),('9781594130038',6,'Available'),('9781594130038',7,'Available'),('9781594130038',8,'Available'),('9781594130038',9,'Available'),('9781594130038',10,'Available'),('9781594130038',11,'Available'),('9781594130038',12,'Available'),('9781594130038',13,'Available'),('9781594130038',14,'Available'),('9781594130038',15,'Available'),('9781594130038',16,'Available'),('9781594130038',17,'Available'),('9781594130038',18,'Available'),('9781594130038',19,'Available'),('9781594130038',20,'Available'),('9781594132216',1,'Available'),('9781594132216',2,'Available'),('9781594132216',3,'Available'),('9781594132216',4,'Available'),('9781594132216',5,'Available'),('9781594132216',6,'Available'),('9781594132216',7,'Available'),('9781594132216',8,'Available'),('9781594132216',9,'Available'),('9781594132216',10,'Available'),('9781781100028',1,'Available'),('9781781100028',2,'Available'),('9781781100028',3,'Available'),('9781781100028',4,'Available'),('9781781100028',5,'Available'),('9781781100028',6,'Available'),('9781781100028',7,'Available'),('9781781100028',8,'Available'),('9781781100028',9,'Available'),('9781781100028',10,'Available'),('9782070643059',1,'Available'),('9782070643059',2,'Available'),('9782070643059',3,'Available'),('9782070643059',5,'Available'),('9782070643059',6,'Available'),('9782070643059',7,'Available'),('9782070643059',8,'Available'),('9783551577771',1,'Available'),('9783551577771',2,'Available'),('9783551577771',3,'Available'),('9783551577771',4,'Available'),('9783551577771',5,'Available'),('9783551577771',6,'Available'),('9783551577771',7,'Available'),('9783551577771',8,'Available'),('9784863891623',1,'Available'),('9784863891623',2,'Available'),('9784863891623',3,'Available'),('9784863891623',4,'Available'),('9784863891623',5,'Available'),('9784863891623',6,'Available'),('9784863891623',7,'Available'),('9784863891623',8,'Available'),('9784863891623',9,'Available'),('9784863891623',10,'Available'),('9784863891623',11,'Available'),('9784863891623',12,'Available'),('9784863891623',13,'Available'),('9784863891623',14,'Available'),('9784863891623',15,'Available'),('9784863892385',1,'Available'),('9784863892385',2,'Available'),('9784863892385',3,'Available'),('9784863892385',4,'Available'),('9784863892385',5,'Available'),('9784863892385',6,'Available'),('9784863892385',7,'Available'),('9784863892385',8,'Available'),('9787020033454',1,'Available'),('9787020033454',2,'Available'),('9787020033454',3,'Available'),('9787020033454',4,'Available'),('9787020033454',5,'Available'),('9787020033454',6,'Available'),('9787020033454',7,'Available'),('9787020033454',8,'Available'),('9787020033454',9,'Available'),('9787020033454',10,'Available'),('9787020103331',1,'Available'),('9787020103331',2,'Available'),('9787020103331',3,'Available'),('9787020103331',4,'Available'),('9787020103331',5,'Available'),('9787020103331',6,'Available'),('9787020103331',7,'Available'),('9787020103331',8,'Available'),('9788372786975',1,'Available'),('9788372786975',2,'Available'),('9788372786975',3,'Available'),('9788372786975',4,'Available'),('9788372786975',5,'Available'),('9788372786975',6,'Available'),('9788372786975',7,'Available'),('9788372786975',8,'Available'),('9788372786975',9,'Available'),('9788372786975',10,'Available'),('9788380082168',1,'Available'),('9788380082168',2,'Available'),('9788380082168',3,'Available'),('9788380082168',4,'Available'),('9788380082168',5,'Available'),('9788380082168',6,'Available'),('9788380082168',7,'Available'),('9788380082168',8,'Available'),('9788380082168',9,'Available'),('9788380082168',10,'Available'),('9788417016678',1,'Available'),('9788417016678',2,'Available'),('9788417016678',3,'Available'),('9788417016678',4,'Available'),('9788417016678',5,'Available'),('9788417016678',6,'Available'),('9788417016678',7,'Available'),('9788417016678',8,'Available'),('9788417016678',9,'Available'),('9788417016678',10,'Available'),('9788417016678',11,'Available'),('9788417016678',12,'Available'),('9788417016678',13,'Available'),('9788417016678',14,'Available'),('9788417016678',15,'Available'),('9788417016678',16,'Available'),('9788417016678',17,'Available'),('9788417016678',18,'Available'),('9788417016678',19,'Available'),('9788417016678',20,'Available'),('9788478885626',1,'Available'),('9788478885626',2,'Available'),('9788478885626',3,'Available'),('9788478885626',4,'Available'),('9788478885626',5,'Available'),('9788478885626',6,'Available'),('9788478885626',7,'Available'),('9788478885626',8,'Available'),('9788478885626',9,'Available'),('9788478885626',10,'Available'),('9788478885626',11,'Available'),('9788478885626',12,'Available'),('9788478885626',13,'Available'),('9788478885626',14,'Available'),('9788478885626',15,'Available'),('9788478885626',16,'Available'),('9788478885626',17,'Available'),('9788478885626',18,'Available'),('9788478885626',19,'Available'),('9788478885626',20,'Available'),('9788498383638',1,'Available'),('9788498383638',2,'Available'),('9788498383638',3,'Available'),('9788498383638',4,'Available'),('9788498383638',5,'Available'),('9788498383638',6,'Available'),('9788498383638',7,'Available'),('9788498383638',8,'Available'),('9788532530837',1,'Available'),('9788532530837',2,'Available'),('9788532530837',3,'Available'),('9788532530837',4,'Available'),('9788532530837',5,'Available'),('9788532530837',6,'Available'),('9788532530837',7,'Available'),('9788532530837',8,'Available'),('9788532530837',9,'Available'),('9788532530837',10,'Available'),('9788702075397',1,'Available'),('9788702075397',2,'Available'),('9788702075397',3,'Available'),('9788702075397',4,'Available'),('9788702075397',5,'Available'),('9788702075397',6,'Available'),('9788702075397',7,'Available'),('9788702075397',8,'Available'),('9788702075397',9,'Available'),('9788702075397',10,'Available'),('9788702075397',11,'Available'),('9788702075397',12,'Available'),('9788702075397',13,'Available'),('9788702075397',14,'Available'),('9788702075397',15,'Available'),('9788867155958',1,'Available'),('9788867155958',2,'Available'),('9788867155958',3,'Available'),('9788867155958',4,'Available'),('9788867155958',5,'Available'),('9788867155958',6,'Available'),('9788867155958',7,'Available'),('9788867155958',8,'Available'),('9788867155958',9,'Available'),('9788867155958',10,'Available'),('9788867155958',11,'Available'),('9788867155958',12,'Available'),('9788867155958',13,'Available'),('9788867155958',14,'Available'),('9788867155958',15,'Available'),('9788867155958',16,'Available'),('9788867155958',17,'Available'),('9788867155958',18,'Available'),('9788867155958',19,'Available'),('9788867155958',20,'Available'),('9788867155958',21,'Available'),('9788867155958',22,'Available'),('9788867155958',23,'Available'),('9788867155958',24,'Available'),('9788867155958',25,'Available'),('9788867155958',26,'Available'),('9788867155958',27,'Available'),('9788867155958',28,'Available'),('9788867155958',29,'Available'),('9788867155958',30,'Available'),('9788983925442',1,'Available'),('9788983925442',2,'Available'),('9788983925442',3,'Available'),('9788983925442',4,'Available'),('9788983925442',5,'Available'),('9788983925442',6,'Available'),('9788983925442',7,'Available'),('9788983925442',8,'Available'),('9788983925442',9,'Available'),('9788983925442',10,'Available'),('9788983925442',11,'Available'),('9788983925442',12,'Available'),('9789544467616',1,'Available'),('9789544467616',2,'Available'),('9789544467616',3,'Available'),('9789544467616',4,'Available'),('9789544467616',5,'Available'),('9789544467616',6,'Available'),('9789544467616',7,'Available'),('9789544467616',8,'Available'),('9789544467616',9,'Available'),('9789544467616',10,'Available'),('9789604533084',1,'Available'),('9789604533084',2,'Available'),('9789604533084',3,'Available'),('9789604533084',4,'Available'),('9789604533084',5,'Available'),('9789604533084',6,'Available'),('9789604533084',7,'Available'),('9789604533084',8,'Available'),('9789604533084',9,'Available'),('9789604533084',10,'Available'),('9789604533084',11,'Available'),('9789604533084',12,'Available'),('9789604533084',13,'Available'),('9789604533084',14,'Available'),('9789604533084',15,'Available'),('9789654826358',1,'Available'),('9789654826358',2,'Available'),('9789654826358',3,'Available'),('9789654826358',4,'Available'),('9789654826358',5,'Available'),('9789654826358',6,'Available'),('9789654826358',7,'Available'),('9789654826358',8,'Available'),('9789654826358',9,'Available'),('9789654826358',10,'Available'),('9789654826358',11,'Available'),('9789654826358',12,'Available'),('9789654826358',13,'Available'),('9789654826358',14,'Available'),('9789654826358',15,'Available'),('9789654826358',16,'Available'),('9789654826358',17,'Available'),('9789654826358',18,'Available'),('9789654826358',19,'Available'),('9789654826358',20,'Available'),('9789667047405',1,'Available'),('9789667047405',2,'Available'),('9789667047405',3,'Available'),('9789667047405',4,'Available'),('9789667047405',5,'Available'),('9789667047405',6,'Available'),('9789667047405',7,'Available'),('9789667047405',8,'Available'),('9789667047405',9,'Available'),('9789667047405',10,'Available');
/*!40000 ALTER TABLE `bookcopies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `ISBN` varchar(13) NOT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Author` varchar(500) DEFAULT NULL,
  `Edition` int(11) DEFAULT NULL,
  `Publication` date DEFAULT NULL,
  `PurchaseDate` date DEFAULT NULL,
  `Category` varchar(500) DEFAULT NULL,
  `Catalogue` varchar(45) DEFAULT NULL,
  `Department` varchar(45) DEFAULT NULL,
  `TotalCopies` int(11) DEFAULT NULL,
  `AvailableCopies` int(11) DEFAULT NULL,
  `Shelf` varchar(45) DEFAULT NULL,
  `Demand` varchar(45) DEFAULT NULL,
  `Description` longtext,
  `Deleted` varchar(5) DEFAULT NULL,
  `ContentsTable` varchar(100) DEFAULT NULL,
  `Picture` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('1234567890123','Ahmads Book','Ahmad',3,'2018-08-13','2019-01-28','Biography','ECS17','English,Computer Science',3,1,'CS17','Regular','The biography of Ahmad Masalha','false','/Books/Contents/1234567890123','/Books/Pictures/1234567890123'),('7418529637896','Yana Bio','Yana',3,'2019-01-27','2019-01-28','Biography','BCS06','Computer Science',1,0,'CS07','Low','Yana Bio','false','/Books/Contents/7418529637896','/Books/Pictures/7418529637896'),('7777444411110','Lord of the Rings','JRR Tolkien',1,'2018-08-13','2019-01-28','English,Literature','AD08','English',4,3,'LT12','High','Frodo baggins gets a golden ring from Bilbo, but one does not simply walk into Mordor','false','/Books/Contents/7777444411110','/Books/Pictures/7777444411110'),('8888555522221','Rula','Rula',3,'2019-01-27','2019-01-28','English','BCS07','English',3,3,'BE08','Regular','Descreption','false','/Books/Contents/8888555522221','/Books/Pictures/8888555522221'),('9780605064225','harry potter and the order of the phoenix','JK Rowling',1,'2003-01-31','2008-05-20','Adventure,Fantasy,English','AFE21','English',5,5,'FE41','High','In his fifth year at Hogwart\'s, Harry faces challenges at every turn, from the dark threat of He-Who-Must-Not-Be-Named and the unreliability of the government of the magical world to the rise of Ron Weasley as the keeper of the Gryffindor Quidditch Team. Along the way he learns about the strength of his friends, the fierceness of his enemies, and the meaning of sacrifice.','false',NULL,NULL),('9780747569428','harry potter and the order of the phoenix','JK Rowling',20,'2004-07-31','2008-06-03','Adventure,Fantasy,English','AFE21','English',5,5,'FE41','Regular','In his fifth year at Hogwart\'s, Harry faces challenges at every turn, from the dark threat of He-Who-Must-Not-Be-Named and the unreliability of the government of the magical world to the rise of Ron Weasley as the keeper of the Gryffindor Quidditch Team. Along the way he learns about the strength of his friends, the fierceness of his enemies, and the meaning of sacrifice.','false',NULL,NULL),('9780807281611','harry potter and the sorcerer\'s stone','JK Rowling',50,'1999-04-01','2008-05-20','Adventure,Fantasy,English,Braille','BFE17','English',5,5,'BF17','Low','This is the braille version of the international bestseller. \"Harry Potter and the Sorcerer\'s Stone\" has reached a level of best-sellerdom never before achieved by a children\'s novel in the United States--The New York Times, April 1, 1999. If you haven\'t heard about this book, you\'ve been asleep. Written for 8 to 12-year olds, \"Harry Potter\" appeals equally to adults. Who is Harry Potter? Harry Potter is an old-fashioned hero. He learns that choices show more of who one is than abilities. If you\'re looking for magic and adventure, read this book.','false',NULL,NULL),('9780807283165','harry potter and the prisoner of azkaban','JK Rowling',50,'2016-07-31','2018-03-07','Adventure,Fantasy,English','AFE19','English',20,20,'FE28','Regular','When the Knight Bus crashes through the darkness and screeches to a halt in front of him its the start of another far from ordinary year at Hogwarts for Harry Potter Sirius Black escaped massmurderer and follower of Lord Voldemort is on the run and they say he is coming after Harry In his first ever Divination class Professor Trelawney sees an omen of death in Harrys tea leaves But perhaps most terrifying of all are the Dementors patrolling the school grounds with their soulsucking kiss','false',NULL,NULL),('9780807286012','harry potter and the chamber of secrets','JK Rowling',50,'2001-04-07','2008-05-08','Adventure,Fantasy,English','AFE18','English',18,18,'FE25','Low','In one of the most hotly anticipated sequel in memory, J.K. Rowling takes up where she left off with Harry\'s second year at the Hogwarts School of Witchcraft and Wizardry. Old friends and new torments abound, including a spirit named Moaning Myrtle who haunts the girls\' bathroom, an outrageously conceited professor, Gilderoy Lockheart, and a mysterious force that turns Hogwarts students to stone.','false',NULL,NULL),('9781413185096','harry potter and the goblet of fire','JK Rowling',5,'2003-10-30','2008-05-08','Adventure,Fantasy,English','AFE20','English',10,9,'FE33','Regular','Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.','false',NULL,NULL),('9781523480500','harry potter and the deathly hallows','JK Rowling',15,'2009-07-07','2012-02-04','Adventure,Fantasy,English','AFE32','English',15,15,'FE43','High','Readers beware. The brilliant, breathtaking conclusion to J.K. Rowling\'s spellbinding series is not for the faint of heart--such revelations, battles, and betrayals await in Harry Potter and the Deathly Hallows that no fan will make it to the end unscathed. Luckily, Rowling has prepped loyal readers for the end of her series by doling out increasingly dark and dangerous tales of magic and mystery, shot through with lessons about honor and contempt, love and loss, and right and wrong. Fear not, you will find no spoilers in our review--to tell the plot would ruin the journey, and Harry Potter and the Deathly Hallows is an odyssey the likes of which Rowling\'s fans have not yet seen, and are not likely to forget. But we would be remiss if we did not offer one small suggestion before you embark on your final adventure with Harry--bring plenty of tissues.','false',NULL,NULL),('9781551923703','Harry Potter and the Chamber of Secrets','JK Rowling',7,'2000-06-06','2003-12-12','Adventure,Fantasy,English','AFE18','English',15,15,'FE25','High','The Dursleys were so mean and hideous that summer that all Harry Potter wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he\'s packing his bags, Harry receives a warning from a strange, impish creature named Dobby who says that if Harry Potter returns to Hogwarts, disaster will strike.','false',NULL,NULL),('9781581306576','harry potter and the prisoner of azkaban','JK Rowling',30,'2006-02-25','2008-05-08','Adventure,Fantasy,English','AFE19','English',3,3,'FE31','Low','This student packet features multiple-level reproducibles that offer solutions based on the latest reading strategies. The packet includes content-rich activity sheets, quizzes, and a final exam for direct student use.','false',NULL,NULL),('9781594130007','harry potter and the sorcerer\'s stone','JK Rowling',20,'1997-12-01','2000-06-17','Adventure,Fantasy,English','AFE17','English',8,8,'FE17','High','Say you\'ve spent the first 10 years of your life sleeping under the stairs of a family who loathes you. Then, in an absurd, magical twist of fate you find yourself surrounded by wizards, a caged snowy owl, a phoenix-feather wand, and jellybeans that come in every flavor, including strawberry, curry, grass, and sardine. Not only that, but you discover that you are a wizard yourself! This is exactly what happens to young Harry Potter in J.K. Rowling\'s enchanting, funny debut novel, Harry Potter and the Sorcerer\'s Stone. In the nonmagic human world--the world of \"Muggles\"--Harry is a nobody, treated like dirt by the aunt and uncle who begrudgingly inherited him when his parents were killed by the evil Voldemort. But in the world of wizards, small, skinny Harry is famous as a survivor of the wizard who tried to kill him. He is left only with a lightning-bolt scar on his forehead, curiously refined sensibilities, and a host of mysterious powers to remind him that he\'s quite, yes, altogether different from his aunt, uncle, and spoiled, piglike cousin Dudley.','false',NULL,NULL),('9781594130038','harry potter and the goblet of fire','JK Rowling',28,'2004-07-31','2008-06-03','Adventure,Fantasy,English','AFE20','English',20,20,'FE34','Regular','Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.','false',NULL,NULL),('9781594132216','harry potter and the half-blood prince','JK Rowling',25,'2007-07-02','2008-05-08','Adventure,Fantasy,English','AFE22','English',10,10,'FE42','Regular','Sixth-year Hogwarts student Harry Potter gains valuable insights into the boy Voldemort once was, even as his own world is transformed by maturing friendships, schoolwork assistance from an unexpected source, and devastating losses.','false',NULL,NULL),('9781781100028','harry potter and the prisoner of azkaban','JK Rowling',10,'2001-06-30','2003-12-12','Adventure,Fantasy,English','AFE19','English',10,10,'FE30','High','For twelve long years, the dread fortress of Azkaban held an infamous prisoner named Sirius Black. Convicted of killing thirteen people with a single curse, he was said to be the heir apparent to the Dark Lord, Voldemort. Now he has escaped, leaving only two clues as to where he might be headed: Harry Potter\'s defeat of You-Know-Who was Black\'s downfall as well. And the Azkban guards heard Black muttering in his sleep, \"He\'s at Hogwarts...he\'s at Hogwarts.\" Harry Potter isn\'t safe, not even within the walls of his magical school, surrounded by his friends. Because on top of it all, there may well be a traitor in their midst.','false',NULL,NULL),('9782070643059','Harry Potter Et La Coupe De Feu','JK Rowling',49,'2011-11-29','2012-02-04','Adventure,Fantasy,French','AFF19','All',8,8,'FF29','Low','Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger. Harry Potter and the Goblet of Fire (French Edition)','false',NULL,NULL),('9783551577771','harry potter and the deathly hallows','JK Rowling',40,'2015-07-31','2016-04-07','Adventure,Fantasy,English','AFE32','English',8,8,'FE43','High','The heart of Book 7 is a hero\'s mission--not just in Harry\'s quest for the Horcruxes, but in his journey from boy to man--and Harry faces more danger than that found in all six books combined, from the direct threat of the Death Eaters and you-know-who, to the subtle perils of losing faith in himself. Attentive readers would do well to remember Dumbledore\'s warning about making the choice between \"what is right and what is easy,\" and know that Rowling applies the same difficult principle to the conclusion of her series. While fans will find the answers to hotly speculated questions about Dumbledore, Snape, and you-know-who, it is a testament to Rowling\'s skill as a storyteller that even the most astute and careful reader will be taken by surprise.','false',NULL,NULL),('9784863891623','harry potter and the chamber of secrets','JK Rowling',52,'2004-07-31','2010-12-21','Adventure,Fantasy,English','AFE18','English',15,15,'FE28','Regular','Between the new spirit spooking his school and the mysterious forces that turn students into stone, Harry has a lot on his mind as he begins his second year at Hogwarts School of Witchcraft and Wizardry.','false',NULL,NULL),('9784863892385','harry potter and the goblet of fire','JK Rowling',48,'2008-02-29','2012-02-04','Adventure,Fantasy,English','AFE20','English',8,8,'FE36','Regular','Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.','false',NULL,NULL),('9787020033454','Harry Potter and the Prisoner of Azkaban','JK Rowling',25,'2002-07-31','2008-05-08','Adventure,Fantasy,English','AFE19','English',10,10,'FE30','High','When the Knight Bus crashes through the darkness and screeches to a halt in front of him its the start of another far from ordinary year at Hogwarts for Harry Potter Sirius Black escaped massmurderer and follower of Lord Voldemort is on the run and they say he is coming after Harry In his first ever Divination class Professor Trelawney sees an omen of death in Harrys tea leaves But perhaps most terrifying of all are the Dementors patrolling the school grounds with their soulsucking kiss','false',NULL,NULL),('9787020103331','harry potter and the half-blood prince','JK Rowling',49,'2015-07-31','2016-04-07','Adventure,Fantasy,English','AFE22','English',8,8,'FE42','High','It\'s no surprise that everyone\'s favorite teen wizard is still battling Voldemort. What does perplex the young hero is a forgotten textbook with secret writing that brings together Harry Potter and the Half-Blood Prince. J. K. Rowling returns Harry, Hermione, and Ron to Hogworts amidst troubling signs that the Dark Lord and the Deatheaters are gaining strength. Fortunately, Headmaster Dumbledore is helping his apt pupil prepare for an expected showdown by taking Harry to remembered incidents in the life of his old enemy. Less dangerous, but still disturbing, Ron and Hermione have put Harry in the middle of their incessant bickering. Then there\'s Slytherin Prefect Draco Malfoy who\'s under orders to commit murder','false',NULL,NULL),('9788372786975','harry potter and the deathly hallows','JK Rowling',20,'2011-07-01','2012-02-04','Adventure,Fantasy,English','AFE32','English',10,10,'FE43','Regular','Harry Potter is preparing to leave the Dursleys and Privet Drive for the last time. But the future that awaits him is full of danger, not only for him, but for anyone close to him - and Harry has already lost so much. Only by destroying Voldemort\'s remaining Horcruxes can Harry free himself and overcome the Dark Lord\'s forces of evil.','false',NULL,NULL),('9788380082168','harry potter and the prisoner of azkaban','JK Rowling',49,'2014-05-15','2016-04-07','Adventure,Fantasy,English','AFE19','English',10,10,'FE28','Regular','When the Knight Bus crashes through the darkness and screeches to a halt in front of him its the start of another far from ordinary year at Hogwarts for Harry Potter Sirius Black escaped massmurderer and follower of Lord Voldemort is on the run and they say he is coming after Harry In his first ever Divination class Professor Trelawney sees an omen of death in Harrys tea leaves But perhaps most terrifying of all are the Dementors patrolling the school grounds with their soulsucking kiss','false',NULL,NULL),('9788417016678','harry potter and the sorcerer\'s stone','JK Rowling',52,'2005-02-18','2012-02-04','Adventure,Fantasy,English','AFE17','English',20,20,'FE24','Regular','Harry Potter\'s life is miserable. His parents are dead and he\'s stuck with his heartless relatives, who force him to live in a tiny closet under the stairs. But his fortune changes when he receives a letter that tells him the truth about himself: he\'s a wizard. A mysterious visitor rescues him from his relatives and takes him to his new home, Hogwarts School of Witchcraft and Wizardry. After a lifetime of bottling up his magical powers, Harry finally feels like a normal kid. But even within the Wizarding community, he is special. He is the boy who lived: the only person to have ever survived a killing curse inflicted by the evil Lord Voldemort, who launched a brutal takeover of the Wizarding world, only to vanish after failing to kill Harry. Though Harry\'s first year at Hogwarts is the best of his life, not everything is perfect. There is a dangerous secret object hidden within the castle walls, and Harry believes it\'s his responsibility to prevent it from falling into evil hands. But doing so will bring him into contact with forces more terrifying than he ever could have imagined. Full of sympathetic characters, wildly imaginative situations, and countless exciting details, the first installment in the series assembles an unforgettable magical world and sets the stage for many high–stakes adventures to come.','false',NULL,NULL),('9788478885626','Harry Potter and the Chamber of Secrets','JK Rowling',8,'1999-03-21','2008-05-08','Adventure,Fantasy,English','AFE18','English',20,20,'FE26','Regular','Between the new spirit spooking his school and the mysterious forces that turn students into stone, Harry has a lot on his mind as he begins his second year at Hogwarts School of Witchcraft and Wizardry.','false',NULL,NULL),('9788498383638','harry potter and the half-blood prince','JK Rowling',45,'2011-07-02','2016-04-07','Adventure,Fantasy,English','AFE22','English',8,8,'FE42','High','The war against Voldemort is not going well; even Muggle governments are noticing. Ron scans the obituary pages of the Daily Prophet, looking for familiar names. Dumbledore is absent from Hogwarts for long stretches of time, and the Order of the Phoenix has already suffered losses.','false',NULL,NULL),('9788532530837','harry potter and the half-blood prince','JK Rowling',48,'2013-08-23','2016-04-07','Adventure,Fantasy,English','AFE22','English',10,10,'FE41','Regular','The war against Voldemort is not going well; even Muggle governments are noticing. Ron scans the obituary pages of the Daily Prophet, looking for familiar names. Dumbledore is absent from Hogwarts for long stretches of time, and the Order of the Phoenix has already suffered losses.','false',NULL,NULL),('9788702075397','Harry Potter and the Chamber Of Secrets','JK Rowling',10,'1999-07-18','2012-02-04','Adventure,Fantasy,English','AFE18','English',15,15,'FE27','Regular','The Dursleys were so mean and hideous that summer that all Harry Potter wanted was to get back to the Hogwarts School for Witchcraft and Wizardry. But just as he\'s packing his bags, Harry receives a warning from a strange, impish creature named Dobby who says that if Harry Potter returns to Hogwarts, disaster will strike.','false',NULL,NULL),('9788867155958','harry potter and the sorcerer\'s stone','JK Rowling',49,'1999-05-17','2008-06-03','Adventure,Fantasy,English','AFE17','English',30,30,'FE22','Regular','Harry Potter\'s life is miserable. His parents are dead and he\'s stuck with his heartless relatives, who force him to live in a tiny closet under the stairs. But his fortune changes when he receives a letter that tells him the truth about himself: he\'s a wizard. A mysterious visitor rescues him from his relatives and takes him to his new home, Hogwarts School of Witchcraft and Wizardry. After a lifetime of bottling up his magical powers, Harry finally feels like a normal kid. But even within the Wizarding community, he is special. He is the boy who lived: the only person to have ever survived a killing curse inflicted by the evil Lord Voldemort, who launched a brutal takeover of the Wizarding world, only to vanish after failing to kill Harry. Though Harry\'s first year at Hogwarts is the best of his life, not everything is perfect. There is a dangerous secret object hidden within the castle walls, and Harry believes it\'s his responsibility to prevent it from falling into evil hands. But doing so will bring him into contact with forces more terrifying than he ever could have imagined. Full of sympathetic characters, wildly imaginative situations, and countless exciting details, the first installment in the series assembles an unforgettable magical world and sets the stage for many high–stakes adventures to come.','false',NULL,NULL),('9788983925442','harry potter and the order of the phoenix','JK Rowling',48,'2014-07-31','2016-04-07','Adventure,Fantasy,English','AFE21','English',12,12,'FE41','Regular','In his fifth year at Hogwart\'s, Harry faces challenges at every turn, from the dark threat of He-Who-Must-Not-Be-Named and the unreliability of the government of the magical world to the rise of Ron Weasley as the keeper of the Gryffindor Quidditch Team. Along the way he learns about the strength of his friends, the fierceness of his enemies, and the meaning of sacrifice.','false',NULL,NULL),('9789544467616','harry potter and the order of the phoenix','JK Rowling',12,'2003-06-21','2008-05-08','Adventure,Fantasy,English','AFE21','English',10,10,'FE40','High','In his fifth year at Hogwart\'s, Harry faces challenges at every turn, from the dark threat of He-Who-Must-Not-Be-Named and the unreliability of the government of the magical world to the rise of Ron Weasley as the keeper of the Gryffindor Quidditch Team. Along the way he learns about the strength of his friends, the fierceness of his enemies, and the meaning of sacrifice.','false',NULL,NULL),('9789604533084','harry potter and the sorcerer\'s stone','JK Rowling',50,'2001-01-15','2010-12-21','Adventure,Fantasy,English','AFE17','English',15,15,'FE23','Regular','Harry Potter\'s life is miserable. His parents are dead and he\'s stuck with his heartless relatives, who force him to live in a tiny closet under the stairs. But his fortune changes when he receives a letter that tells him the truth about himself: he\'s a wizard. A mysterious visitor rescues him from his relatives and takes him to his new home, Hogwarts School of Witchcraft and Wizardry. After a lifetime of bottling up his magical powers, Harry finally feels like a normal kid. But even within the Wizarding community, he is special. He is the boy who lived: the only person to have ever survived a killing curse inflicted by the evil Lord Voldemort, who launched a brutal takeover of the Wizarding world, only to vanish after failing to kill Harry. Though Harry\'s first year at Hogwarts is the best of his life, not everything is perfect. There is a dangerous secret object hidden within the castle walls, and Harry believes it\'s his responsibility to prevent it from falling into evil hands. But doing so will bring him into contact with forces more terrifying than he ever could have imagined. Full of sympathetic characters, wildly imaginative situations, and countless exciting details, the first installment in the series assembles an unforgettable magical world and sets the stage for many high–stakes adventures to come.','false',NULL,NULL),('9789654826358','harry potter and the deathly hallows','JK Rowling',1,'2007-10-30','2008-06-03','Adventure,Fantasy,English','AFE32','English',20,20,'FE44','High','Harry Potter is preparing to leave the Dursleys and Privet Drive for the last time. But the future that awaits him is full of danger, not only for him, but for anyone close to him - and Harry has already lost so much. Only by destroying Voldemort\'s remaining Horcruxes can Harry free himself and overcome the Dark Lord\'s forces of evil.','false',NULL,NULL),('9789667047405','harry potter and the goblet of fire','JK Rowling',50,'2016-01-31','2018-03-07','Adventure,Fantasy,English','AFE20','English',10,10,'FE35','High','Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.Fourteen-year-old Harry Potter joins the Weasleys at the Quidditch World Cup, then enters his fourth year at Hogwarts Academy where he is mysteriously entered in an unusual contest that challenges his wizarding skills, friendships and character, amid signs that an old enemy is growing stronger.','false',NULL,NULL);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrows`
--

DROP TABLE IF EXISTS `borrows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `borrows` (
  `BorrowerID` varchar(10) NOT NULL,
  `IssueDate` timestamp NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `CopyISBN` varchar(13) NOT NULL,
  `CopyCode` int(11) NOT NULL,
  `DueDate` date DEFAULT NULL,
  `ReturnDate` date DEFAULT NULL,
  `Extendable` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`BorrowerID`,`IssueDate`),
  KEY `borrowedCopy_idx` (`CopyISBN`,`CopyCode`),
  CONSTRAINT `bID` FOREIGN KEY (`BorrowerID`) REFERENCES `readercards` (`rid`) ON UPDATE CASCADE,
  CONSTRAINT `borrowedCopy` FOREIGN KEY (`CopyISBN`, `CopyCode`) REFERENCES `bookcopies` (`isbn`, `serialcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrows`
--

LOCK TABLES `borrows` WRITE;
/*!40000 ALTER TABLE `borrows` DISABLE KEYS */;
INSERT INTO `borrows` VALUES ('204493563','2019-02-04 00:05:32','Borrow','1234567890123',2,'2019-02-17',NULL,'true'),('228951456','2019-01-19 16:00:00','Borrow','9781413185096',1,'2019-02-01',NULL,'true'),('313444817','2019-02-04 00:05:56','Borrow','1234567890123',3,'2019-02-17',NULL,'true'),('315715649','2019-02-03 22:25:05','Borrow','1234567890123',1,'2019-03-03','2019-02-03','true'),('315715649','2019-02-03 22:26:37','Borrow','7777444411110',1,'2019-02-06',NULL,'false'),('318210077','2019-02-03 23:38:39','Borrow','7418529637896',1,'2019-02-17',NULL,'true'),('874563025','2019-02-03 20:29:52','Borrow','7418529637896',1,'2019-02-17','2019-02-03','true'),('874563025','2019-02-03 20:43:44','Borrow','1234567890123',2,'2019-02-17','2019-02-03','true');
/*!40000 ALTER TABLE `borrows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extensions`
--

DROP TABLE IF EXISTS `extensions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `extensions` (
  `ExtenderID` varchar(10) NOT NULL,
  `BorrowDate` timestamp NOT NULL,
  `IssueDate` timestamp NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `IsManual` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`ExtenderID`,`BorrowDate`,`IssueDate`),
  CONSTRAINT `borrowing` FOREIGN KEY (`ExtenderID`, `BorrowDate`) REFERENCES `borrows` (`BorrowerID`, `IssueDate`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extensions`
--

LOCK TABLES `extensions` WRITE;
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
INSERT INTO `extensions` VALUES ('315715649','2019-02-03 22:25:05','2019-02-03 22:43:09','Borrow By Dvora Tolido','true');
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarians`
--

DROP TABLE IF EXISTS `librarians`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `librarians` (
  `libID` varchar(10) NOT NULL,
  `Role` varchar(45) DEFAULT 'Librarian',
  `Department` varchar(45) DEFAULT 'Workforce',
  PRIMARY KEY (`libID`),
  CONSTRAINT `userID` FOREIGN KEY (`libID`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarians`
--

LOCK TABLES `librarians` WRITE;
/*!40000 ALTER TABLE `librarians` DISABLE KEYS */;
INSERT INTO `librarians` VALUES ('202554852','Librarian','Workforce'),('221221545','Librarian','Workforce'),('227789510','Librarian','Workforce'),('229852149','Manager','Management'),('315718629','Librarian','Workforce'),('322125855','Librarian','Workforce'),('323485251','Manager','Management'),('330002587','Librarian','Workforce'),('331665203','Librarian','Workforce');
/*!40000 ALTER TABLE `librarians` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `losings`
--

DROP TABLE IF EXISTS `losings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `losings` (
  `LoserID` varchar(13) NOT NULL,
  `IssueDate` timestamp NOT NULL,
  `CopyISBN` varchar(13) NOT NULL,
  `CopyCode` int(11) NOT NULL,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`LoserID`,`IssueDate`),
  KEY `lostCopy_idx` (`CopyISBN`,`CopyCode`),
  CONSTRAINT `loserID` FOREIGN KEY (`LoserID`) REFERENCES `readercards` (`rid`) ON UPDATE CASCADE,
  CONSTRAINT `lostCopy` FOREIGN KEY (`CopyISBN`, `CopyCode`) REFERENCES `bookcopies` (`isbn`, `serialcode`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `losings`
--

LOCK TABLES `losings` WRITE;
/*!40000 ALTER TABLE `losings` DISABLE KEYS */;
/*!40000 ALTER TABLE `losings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifys`
--

DROP TABLE IF EXISTS `notifys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notifys` (
  `userID` varchar(10) NOT NULL,
  `IssueDate` timestamp NOT NULL,
  `Message` longtext,
  PRIMARY KEY (`userID`,`IssueDate`),
  CONSTRAINT `notifiedUser` FOREIGN KEY (`userID`) REFERENCES `readercards` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifys`
--

LOCK TABLES `notifys` WRITE;
/*!40000 ALTER TABLE `notifys` DISABLE KEYS */;
INSERT INTO `notifys` VALUES ('318210077','2019-02-04 00:14:38','A Book you ordered is now available.'),('318223898','2019-02-04 00:12:01','A Book you ordered is now available.');
/*!40000 ALTER TABLE `notifys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `OrdererID` varchar(10) NOT NULL,
  `IssueDate` timestamp NOT NULL,
  `OrderedBook` varchar(13) NOT NULL,
  `SaveDate` date DEFAULT NULL,
  `Name` varchar(45) DEFAULT NULL,
  `Realised` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`OrdererID`,`IssueDate`),
  KEY `oBook_idx` (`OrderedBook`),
  CONSTRAINT `oBook` FOREIGN KEY (`OrderedBook`) REFERENCES `books` (`isbn`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rcID` FOREIGN KEY (`OrdererID`) REFERENCES `readercards` (`rid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('315210335','2019-02-04 00:09:14','1234567890123',NULL,'Order','false'),('318210077','2019-02-04 00:07:34','1234567890123','2019-02-03','Order','false');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `readercards`
--

DROP TABLE IF EXISTS `readercards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `readercards` (
  `rID` varchar(10) NOT NULL,
  `Graduated` varchar(5) DEFAULT 'false',
  `Status` varchar(45) DEFAULT 'Active',
  PRIMARY KEY (`rID`),
  CONSTRAINT `rID` FOREIGN KEY (`rID`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `readercards`
--

LOCK TABLES `readercards` WRITE;
/*!40000 ALTER TABLE `readercards` DISABLE KEYS */;
INSERT INTO `readercards` VALUES ('052554422','true','Locked'),('201456774','false','Active'),('202554852','true','Locked'),('204493563','false','Active'),('210560446','false','Active'),('212412649','false','Active'),('218188452','false','Active'),('219336685','false','Active'),('221221545','false','Active'),('226753014','false','Active'),('228951456','false','Active'),('301522689','false','Active'),('313221459','false','Active'),('313444817','false','Active'),('315210335','false','Active'),('315715649','false','Active'),('316480995','false','Active'),('316998125','false','Active'),('317558997','false','Active'),('318210077','false','Active'),('318223898','false','Active'),('320456874','false','Active'),('324025849','false','Active'),('325450236','false','Active'),('332984558','false','Active'),('333025896','false','Active'),('334456784','false','Active'),('335784169','false','Active'),('336659852','false','Active'),('337023654','false','Active'),('338259874','false','Active'),('339025897','false','Active'),('340998521','false','Active'),('874563025','false','Active'),('888555236','false','Active');
/*!40000 ALTER TABLE `readercards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reports` (
  `IssueDate` timestamp NOT NULL,
  `Text` longtext,
  PRIMARY KEY (`IssueDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES ('2019-02-04 03:45:33','Number of Active Reader Cards:    33 0.9428571428571428%\nNumber of Suspended Reader Cards: 0 0.0%\nNumber of Locked Reader Cards:    2 0.05714285714285714%\nTotal number of Reader Cards:     35 100%\n\nTotal number of copies: 401\nThere are 0 Late Borrowers, 0.0% of Subscribers.\n'),('2019-02-04 03:52:20','Number of Active Reader Cards:    33 0.9428571428571428%\nNumber of Suspended Reader Cards: 0 0.0%\nNumber of Locked Reader Cards:    2 0.05714285714285714%\nTotal number of Reader Cards:     35 100%\n\nTotal number of copies: 401\nThere are 0 Late Borrowers, 0.0% of Subscribers.\n'),('2019-02-04 04:04:28','Number of Active Reader Cards:    33 0.9428571428571428%\nNumber of Suspended Reader Cards: 0 0.0%\nNumber of Locked Reader Cards:    2 0.05714285714285714%\nTotal number of Reader Cards:     35 100%\n\nTotal number of copies: 401\nThere are 0 Late Borrowers, 0.0% of Subscribers.\n'),('2019-02-04 04:13:08','Number of Active Reader Cards:    33 0.9428571428571428%\nNumber of Suspended Reader Cards: 0 0.0%\nNumber of Locked Reader Cards:    2 0.05714285714285714%\nTotal number of Reader Cards:     35 100%\n\nTotal number of copies: 401\nThere are 0 Late Borrowers, 0.0% of Subscribers.\n');
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `ID` varchar(10) NOT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `Username` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `OnlineStatus` varchar(5) DEFAULT 'false',
  `Picture` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Phone_UNIQUE` (`Phone`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  UNIQUE KEY `Username_UNIQUE` (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('052554422','Nawar','Younis','Nonomass','nonomass59','0527788586','nawarmas@hotmail.com','false',NULL),('201456774','Dennis','Sander','Dannys','741852963','052332332','danni@siue.edu','false',NULL),('202554852','Gaby','Issa','gabissa','illinois171819','0526626123','gaby@siue.edu','false',NULL),('204493563','Rulla','Rammal','rullaflower','rulala123','0549452131','rola0393@gmail.com','false',NULL),('210560446','Amy','Nusser','amissur','international','0544875126','amy@siue.edu','false',NULL),('212412649','Logan','Paul','loganpaul','123456789','0505448225','paul@siue.edu','false',NULL),('218188452','May','Goodmoon','Mayqueen','may21karma','0502552112','may@siue.edu','false',NULL),('219336685','Salam','Salami','Salams','peace4salam','0505221445','salam@siue.edu','false',NULL),('221221545','Igor','Cerk','cerkigor','igorobesto','0523663123','igor@siue.edu','false',NULL),('226753014','Malka','Kingson','thequeen','queenofall17','0505223693','malka@siue.edu','false',NULL),('227789510','Matthias','Golani','golani2012','golanimatt2012','0526996236','matt@siue.edu','false',NULL),('228951456','Elijah','Wood','woody96','elijahisthecoolest','0524784114','eli@siue.edu','false',NULL),('229852149','Dvora','Tolido','dvorato','dvoracs4life','0526598777','dvora@siue.edu','false',NULL),('301522689','Justin','Sipes','justo','elbesto4life','0505445789','justo@siue.edu','false',NULL),('313221459','Lucas','Francis','lucas19','vivaparis','0549255419','lucas@siue.edu','false',NULL),('313444817','Ammar','Amer','Amorius','Druze4Life','0525169358','amar164@gmail.com','false',NULL),('315210335','Ashraf','Masalha','drash','albas1551','0525741255','ash@siue.edu','false',NULL),('315715649','Ahmad','Masalha','amasalha','123456789','0525736077','ahmadsmassalha@s.braude.ac.il','false',NULL),('315718629','Loen','Graceson','loenista','engineer4life','0508441881','leon@siue.edu','false',NULL),('316480995','Maysam','Wasser','mawass','misamisa18','0544854996','maysam@siue.edu','false',NULL),('316998125','Laura','Grosmann','Lauretta','deutsches17','0525889123','laura@siue.edu','false',NULL),('317558997','Malak','Angela','theangel','angelsofwhite','0525733055','malak@siue.edu','false',NULL),('318210077','Yana','Irani','yanoshka','yana3alaa','0543121848','yana.irani.5@gmail.com','false',NULL),('318223898','Mahmoud','Omar','Mamdoo7','Asadi4Ever','0509669989','mahmoudkhomar@gmail.com','false',NULL),('320456874','April','Saba','Aprilsaba','aprilis4love','0526699855','april@siue.edu','false',NULL),('322125855','Elena','Igorov','elenishka','elena1995','0528998778','elena@siue.edu','false',NULL),('323485251','Katerina','Habibi','katrishka','katerina1995','0525665125','katy@siue.edu','false',NULL),('324025849','Veronica','Kukushkina','kukushkive','kukushuku17','0544124124','vero@siue.edu','false',NULL),('325450236','Sarai','Schinwalder','drsue','automata4life','0525881561','sarai@siue.edu','false',NULL),('330002587','Ora','Baror','oraora','oracs4long','0521234565','ora@siue.edu','false',NULL),('331665203','Liat','Gazit','liatzit','liatsecrets','0527418525','liat@siue.edu','false',NULL),('332984558','Hanan','Hanania','hanania','hanania4ya','0520852963','hanan@siue.edu','false',NULL),('333025896','Shlomo','Baria','shlombar','shlomobaria15','0545963654','shlomo@siue.edu','false',NULL),('334456784','Yara','Josephson','yaralove','loveforyara','0543258741','yara@siue.edu','false',NULL),('335784169','Aymeric','Franciscan','aymerico','eternallycool','0540985855','aymeric@siue.edu','false',NULL),('336659852','Henry','Windsor','henryking','londonseye','0504521458','henry@siue.edu','false',NULL),('337023654','Luna','Lovegod','looney','narglesrreal','0502569996','luna@siue.edu','false',NULL),('338259874','Harry','Potter','harrypotter','chosenone4real','0500477777','harry@siue.edu','false',NULL),('339025897','Hermione','Granger','hermionebooks','ilovebooks','0500366659','hermi@siue.edu','false',NULL),('340998521','Ron','Weazley','ronaldweezee','ihatebooks','0500258845','ron@siue.edu','false',NULL),('874563025','Yarab','Najah','Naja7','741852963','0525896300','naja7@barude.com','false','/Users/Pictures/874563025.png'),('888555236','Najeha','Mansoura','Beiznillah','yarabyallah','0525478966','beiz@siue.com','false','/Users/Pictures/888555236.png');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-03 23:20:26
