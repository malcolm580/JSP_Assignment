-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- 主機: 127.0.0.1
-- 產生時間： 2017-12-03 16:08:46
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

--
-- 資料表的匯出資料 `Module`
--

INSERT INTO `Module` (`ModuleID`, `ModuleName`) VALUES
  (0, 'IT Professionalism'),
  (1, 'Contemporary Topics in Software Engineering'),
  (2, 'Enterprise Systems Development'),
  (3, 'Human Computer Interaction and GUI Programming'),
  (4, 'English and Communication: Persuasive Presentations ');

--
-- 資料表的匯出資料 `Question`
--

INSERT INTO `Question` (`QuestionID`, `QuizID`, `QuestionType`, `Question`, `CorrectOptionID`) VALUES
  (1, 1, 'Multiple', 'Which fruit is orange?', 2),
  (3, 1, 'Multiple', 'Who teach OOT?', 30);

--
-- 資料表的匯出資料 `QuestionOption`
--

INSERT INTO `QuestionOption` (`OptionID`, `QuestionID`, `Option`) VALUES
  (1, 1, 'Apple'),
  (2, 1, 'Orange'),
  (3, 1, 'WaterMelon'),
  (17, 1, 'Lemon'),
  (28, 3, 'Train Head'),
  (29, 3, 'Handsome Jessie'),
  (30, 3, 'Professor X'),
  (31, 3, '.');

--
-- 資料表的匯出資料 `Quiz`
--

INSERT INTO `Quiz` (`QuizID`, `ModuleID`, `QuizName`, `AttemptLimit`, `TimeLimit`, `TotalQuestion`) VALUES
  (1, 0, 'IT Professionalism Quiz 1', 10, 10, 0),
  (3, 4, 'Language Quizzes', 3, 10, 10),
  (4, 2, 'Enterprise Systems Development Test 1', 0, 3600, 0),
  (5, 1, 'fuck', 0, 0, 0),
  (6, 0, 'fuck', 0, 0, 0),
  (7, 0, 'fuck', 0, 0, 0);

--
-- 資料表的匯出資料 `QuizResult`
--

INSERT INTO `QuizResult` (`QuizResultID`, `UserID`, `QuizID`, `Duration`, `AnsweringQuestionState_JSON`, `CorrectCount`)
VALUES
  (2, 4, 1, NULL,
   '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"17\"},{\"QuestionID\":\"1\"}]]}', 9),
  (3, 4, 1, NULL, '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"17\"},{\"QuestionID\":\"1\"}]]}', 8),
  (4, 4, 1, NULL, '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"17\"},{\"QuestionID\":\"1\"}]]}', 7),
  (6, 5, 1, 0, '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"17\"},{\"QuestionID\":\"1\"}]]}', 5),
  (7, 4, 1, 0, '{\"Question\":[[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}]]}', 5),
  (8, 2, 1, 0, '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 5),
  (9, 6, 1, 0, '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 5),
  (10, 4, 1, 0, '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 2),
  (11, 4, 1, 0, '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 2),
  (12, 8, 1, 0, '{\"Question\":[[{\"OptionID\":\"17\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"28\"},{\"QuestionID\":\"3\"}]]}', 5),
  (13, 4, 1, 0, '{\"Question\":[[{\"OptionID\":\"17\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"31\"},{\"QuestionID\":\"3\"}]]}', 5),
  (14, 9, 1, 0,
   '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 2),
  (15, 4, 1, 0,
   '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}]]}', 1),
  (16, 4, 1, 0,
   '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}]]}', 1),
  (17, 4, 1, 0,
   '{\"Question\":[[{\"OptionID\":\"29\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}]]}', 1),
  (18, 4, 1, 0,
   '{\"Question\":[[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"1\"},{\"QuestionID\":\"1\"}]]}', 1),
  (19, 4, 1, 8,
   '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 2),
  (20, 4, 1, 4,
   '{\"Question\":[[{\"OptionID\":\"31\"},{\"QuestionID\":\"3\"}],[{\"OptionID\":\"3\"},{\"QuestionID\":\"1\"}]]}', 5),
  (21, 4, 1, 5,
   '{\"Question\":[[{\"OptionID\":\"2\"},{\"QuestionID\":\"1\"}],[{\"OptionID\":\"30\"},{\"QuestionID\":\"3\"}]]}', 2);

--
-- 資料表的匯出資料 `User`
--

INSERT INTO `User` (`UserID`, `Username`, `Password`, `Role`, `Email`, `Disabled`) VALUES
  (1, 'teacher', 'password', 'Teacher', 'youmalcolm580@gmail.com', 0),
  (2, 'Elmo', 'JVF09ZLR3YB', 'Student', 'dictum.magna.Ut@convallis.com', 0),
  (3, 'Dale', '123', 'Student', 'nibh@ac.edu', 0),
  (4, 'student', 'password', 'Student', 'consectetuer.cursus@mi.co.uk', 0),
  (5, 'Owen', 'WFB51GYR3QC', 'Student', 'dictum@sitametorci.ca', 0),
  (6, 'Hall', 'TZY86ADB6NN', 'Student', 'a@Morbiaccumsanlaoreet.edu', 0),
  (7, 'Kennan', 'OOE15THF7PM', 'Student', 'eget.dictum@auctor.com', 0),
  (8, 'Brady', 'EFC23JWR1CI', 'Student', 'nec@etmagnisdis.edu', 1),
  (9, 'Gray', 'FHU59ASP8PZ', 'Student', 'eu@a.co.uk', 1),
  (11, 'admin', 'password', 'Admin', 'admin@elearning.com', 0);

--
-- 資料表的匯出資料 `UserModule`
--

INSERT INTO `UserModule` (`id`, `UserID`, `ModuleID`) VALUES
  (4, 1, 1),
  (5, 1, 0),
  (6, 1, 2),
  (7, 1, 3),
  (8, 1, 4),
  (9, 3, 1),
  (10, 4, 0),
  (11, 4, 2);

--
-- 資料表的匯出資料 `UserQuiz`
--

INSERT INTO `UserQuiz` (`id`, `UserUserID`, `QuizQuizID`) VALUES
  (1, 1, 1),
  (3, 2, 1),
  (4, 3, 1),
  (5, 4, 1),
  (8, 1, 3),
  (19, 7, 1),
  (20, 6, 1),
  (22, 5, 1),
  (25, 4, 4),
  (26, 1, 4);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
