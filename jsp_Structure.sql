-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1
-- 產生時間： 2017-12-03 16:08:35
-- 伺服器版本: 10.1.21-MariaDB
-- PHP 版本： 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `jsp`
--

-- --------------------------------------------------------

--
-- 資料表結構 `Metrial`
--

CREATE TABLE `Metrial` (
  `MaterialID`  INT(10)      NOT NULL,
  `ModuleID`    INT(10)      NOT NULL,
  `Content`     VARCHAR(255) DEFAULT NULL,
  `ContentType` VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `MetrialUser`
--

CREATE TABLE `MetrialUser` (
  `MetrialModuleID` INT(10) NOT NULL,
  `UserID`          INT(10) NOT NULL,
  `isBlackListed`   CHAR(1) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `Module`
--

CREATE TABLE `Module` (
  `ModuleID`   INT(10)      NOT NULL,
  `ModuleName` VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `Question`
--

CREATE TABLE `Question` (
  `QuestionID`      INT(10)      NOT NULL,
  `QuizID`          INT(10)      NOT NULL,
  `QuestionType`    VARCHAR(255) NOT NULL,
  `Question`        VARCHAR(255) NOT NULL,
  `CorrectOptionID` INT(10) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `QuestionOption`
--

CREATE TABLE `QuestionOption` (
  `OptionID`   INT(10) NOT NULL,
  `QuestionID` INT(10) NOT NULL,
  `Option`     VARCHAR(255)
               CHARACTER SET utf8mb4
               COLLATE utf8mb4_unicode_520_ci DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `Quiz`
--

CREATE TABLE `Quiz` (
  `QuizID`        INT(10)      NOT NULL,
  `ModuleID`      INT(10)      NOT NULL,
  `QuizName`      VARCHAR(255) NOT NULL,
  `AttemptLimit`  INT(10) DEFAULT '0',
  `TimeLimit`     INT(10) DEFAULT '0',
  `TotalQuestion` INT(10)      NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `QuizResult`
--

CREATE TABLE `QuizResult` (
  `QuizResultID`                INT(10) NOT NULL,
  `UserID`                      INT(10)      DEFAULT NULL,
  `QuizID`                      INT(10) NOT NULL,
  `Duration`                    INT(20)      DEFAULT NULL,
  `AnsweringQuestionState_JSON` VARCHAR(255) DEFAULT NULL,
  `CorrectCount`                INT(10) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `User`
--

CREATE TABLE `User` (
  `UserID`   INT(10)      NOT NULL,
  `Username` VARCHAR(255) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `Role`     VARCHAR(255) NOT NULL DEFAULT 'student',
  `Email`    VARCHAR(255)          DEFAULT NULL,
  `Disabled` INT(1)                DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `UserModule`
--

CREATE TABLE `UserModule` (
  `id`       INT(10) NOT NULL,
  `UserID`   INT(10) NOT NULL,
  `ModuleID` INT(10) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- --------------------------------------------------------

--
-- 資料表結構 `UserQuiz`
--

CREATE TABLE `UserQuiz` (
  `id`         INT(10) NOT NULL,
  `UserUserID` INT(10) NOT NULL,
  `QuizQuizID` INT(10) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `Metrial`
--
ALTER TABLE `Metrial`
  ADD PRIMARY KEY (`MaterialID`),
  ADD KEY `FKMetrial663747` (`ModuleID`),
  ADD KEY `FK_Metrial` (`ModuleID`);

--
-- 資料表索引 `MetrialUser`
--
ALTER TABLE `MetrialUser`
  ADD PRIMARY KEY (`MetrialModuleID`, `UserID`),
  ADD KEY `FKMetrialUse790696` (`MetrialModuleID`),
  ADD KEY `FK_MetrialUser` (`MetrialModuleID`),
  ADD KEY `FKMetrialUse318555` (`UserID`);

--
-- 資料表索引 `Module`
--
ALTER TABLE `Module`
  ADD PRIMARY KEY (`ModuleID`);

--
-- 資料表索引 `Question`
--
ALTER TABLE `Question`
  ADD PRIMARY KEY (`QuestionID`),
  ADD KEY `FKQuestion143290` (`QuizID`);

--
-- 資料表索引 `QuestionOption`
--
ALTER TABLE `QuestionOption`
  ADD PRIMARY KEY (`OptionID`),
  ADD KEY `FKQuestionOp199920` (`QuestionID`);

--
-- 資料表索引 `Quiz`
--
ALTER TABLE `Quiz`
  ADD PRIMARY KEY (`QuizID`),
  ADD KEY `FKQuiz354528` (`ModuleID`);

--
-- 資料表索引 `QuizResult`
--
ALTER TABLE `QuizResult`
  ADD PRIMARY KEY (`QuizResultID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `quizresult_ibfk_1` (`UserID`),
  ADD KEY `FKQuizResult219291` (`QuizID`);

--
-- 資料表索引 `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- 資料表索引 `UserModule`
--
ALTER TABLE `UserModule`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `FKUserModule886446` (`UserID`),
  ADD KEY `FKUserModule652649` (`ModuleID`);

--
-- 資料表索引 `UserQuiz`
--
ALTER TABLE `UserQuiz`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKUserQuiz118044` (`UserUserID`),
  ADD KEY `FKUserQuiz572359` (`QuizQuizID`);

--
-- 在匯出的資料表使用 AUTO_INCREMENT
--

--
-- 使用資料表 AUTO_INCREMENT `Metrial`
--
ALTER TABLE `Metrial`
  MODIFY `MaterialID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 54;

--
-- 使用資料表 AUTO_INCREMENT `Module`
--
ALTER TABLE `Module`
  MODIFY `ModuleID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 5;

--
-- 使用資料表 AUTO_INCREMENT `Question`
--
ALTER TABLE `Question`
  MODIFY `QuestionID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 4;

--
-- 使用資料表 AUTO_INCREMENT `QuestionOption`
--
ALTER TABLE `QuestionOption`
  MODIFY `OptionID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 32;

--
-- 使用資料表 AUTO_INCREMENT `Quiz`
--
ALTER TABLE `Quiz`
  MODIFY `QuizID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 15;

--
-- 使用資料表 AUTO_INCREMENT `QuizResult`
--
ALTER TABLE `QuizResult`
  MODIFY `QuizResultID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 22;

--
-- 使用資料表 AUTO_INCREMENT `User`
--
ALTER TABLE `User`
  MODIFY `UserID` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 12;

--
-- 使用資料表 AUTO_INCREMENT `UserModule`
--
ALTER TABLE `UserModule`
  MODIFY `id` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 12;

--
-- 使用資料表 AUTO_INCREMENT `UserQuiz`
--
ALTER TABLE `UserQuiz`
  MODIFY `id` INT(10) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 34;

--
-- 已匯出資料表的限制(Constraint)
--

--
-- 資料表的 Constraints `Metrial`
--
ALTER TABLE `Metrial`
  ADD CONSTRAINT `FK_Metrial` FOREIGN KEY (`ModuleID`) REFERENCES `Module` (`ModuleID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- 資料表的 Constraints `MetrialUser`
--
ALTER TABLE `MetrialUser`
  ADD CONSTRAINT `FKMetrialUse318555` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_MetrialUser` FOREIGN KEY (`MetrialModuleID`) REFERENCES `Metrial` (`MaterialID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- 資料表的 Constraints `Question`
--
ALTER TABLE `Question`
  ADD CONSTRAINT `FKQuestion143290` FOREIGN KEY (`QuizID`) REFERENCES `Quiz` (`QuizID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- 資料表的 Constraints `QuestionOption`
--
ALTER TABLE `QuestionOption`
  ADD CONSTRAINT `FKQuestionOp199920` FOREIGN KEY (`QuestionID`) REFERENCES `Question` (`QuestionID`);

--
-- 資料表的 Constraints `Quiz`
--
ALTER TABLE `Quiz`
  ADD CONSTRAINT `FKQuiz354528` FOREIGN KEY (`ModuleID`) REFERENCES `Module` (`ModuleID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- 資料表的 Constraints `QuizResult`
--
ALTER TABLE `QuizResult`
  ADD CONSTRAINT `FKQuizResult219291` FOREIGN KEY (`QuizID`) REFERENCES `Quiz` (`QuizID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD CONSTRAINT `quizresult_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- 資料表的 Constraints `UserModule`
--
ALTER TABLE `UserModule`
  ADD CONSTRAINT `FKUserModule652649` FOREIGN KEY (`ModuleID`) REFERENCES `Module` (`ModuleID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD CONSTRAINT `FKUserModule886446` FOREIGN KEY (`UserID`) REFERENCES `User` (`UserID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

--
-- 資料表的 Constraints `UserQuiz`
--
ALTER TABLE `UserQuiz`
  ADD CONSTRAINT `FKUserQuiz118044` FOREIGN KEY (`UserUserID`) REFERENCES `User` (`UserID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  ADD CONSTRAINT `FKUserQuiz572359` FOREIGN KEY (`QuizQuizID`) REFERENCES `Quiz` (`QuizID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
