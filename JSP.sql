CREATE TABLE `User` (
  UserID   int(10) NOT NULL AUTO_INCREMENT, 
  Username varchar(255) NOT NULL UNIQUE, 
  Password varchar(255) NOT NULL, 
  Role     varchar(255) NOT NULL, 
  Email    varchar(255), 
  PRIMARY KEY (UserID)) type=InnoDB;
CREATE TABLE Module (
  ModuleID   int(10) NOT NULL AUTO_INCREMENT, 
  ModuleName varchar(255) NOT NULL, 
  PRIMARY KEY (ModuleID)) type=InnoDB;
CREATE TABLE Metrial (
  ModuleID         int(10) NOT NULL, 
  ContentType      varchar(255) NOT NULL, 
  Content          varchar(255), 
  BlackListId_JSON varchar(255) NOT NULL, 
  PRIMARY KEY (ModuleID)) type=InnoDB;
CREATE TABLE Quiz (
  QuizID    int(10) NOT NULL AUTO_INCREMENT, 
  ModuleID  int(10) NOT NULL, 
  TimeLimit int(10), 
  QuizName  varchar(255) NOT NULL, 
  PRIMARY KEY (QuizID)) type=InnoDB;
CREATE TABLE Question (
  QuizID        int(10) NOT NULL, 
  QuestionID    int(10) NOT NULL AUTO_INCREMENT, 
  QuestionType  varchar(255) NOT NULL, 
  Question      varchar(255) NOT NULL, 
  CorrectAnswer varchar(255), 
  Options_JSON  varchar(255), 
  PRIMARY KEY (QuestionID)) type=InnoDB;
CREATE TABLE QuizResult (
  UserID                      int(10), 
  CourseID                    int(10), 
  QuizID                      int(10) NOT NULL, 
  QuizResultID                int(10) NOT NULL AUTO_INCREMENT, 
  Duration                    int(10), 
  AnsweringQuestionState_JSON int(10), 
  PRIMARY KEY (QuizResultID)) type=InnoDB;
CREATE TABLE UserModule (
  UserID   int(10) NOT NULL UNIQUE, 
  ModuleID int(10) NOT NULL UNIQUE, 
  PRIMARY KEY (UserID, 
  ModuleID)) type=InnoDB;
ALTER TABLE Metrial ADD INDEX FKMetrial663747 (ModuleID), ADD CONSTRAINT FKMetrial663747 FOREIGN KEY (ModuleID) REFERENCES Module (ModuleID);
ALTER TABLE Quiz ADD INDEX FKQuiz354528 (ModuleID), ADD CONSTRAINT FKQuiz354528 FOREIGN KEY (ModuleID) REFERENCES Module (ModuleID);
ALTER TABLE Question ADD INDEX FKQuestion143290 (QuizID), ADD CONSTRAINT FKQuestion143290 FOREIGN KEY (QuizID) REFERENCES Quiz (QuizID);
ALTER TABLE QuizResult ADD INDEX FKQuizResult219291 (QuizID), ADD CONSTRAINT FKQuizResult219291 FOREIGN KEY (QuizID) REFERENCES Quiz (QuizID);
ALTER TABLE UserModule ADD INDEX FKUserModule886446 (UserID), ADD CONSTRAINT FKUserModule886446 FOREIGN KEY (UserID) REFERENCES `User` (UserID);
ALTER TABLE UserModule ADD INDEX FKUserModule652649 (ModuleID), ADD CONSTRAINT FKUserModule652649 FOREIGN KEY (ModuleID) REFERENCES Module (ModuleID);
