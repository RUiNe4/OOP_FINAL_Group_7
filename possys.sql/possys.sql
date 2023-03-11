-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 10, 2023 at 06:27 AM
-- Server version: 8.0.31
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `possys`
--

-- --------------------------------------------------------

--
-- Table structure for table `cartproducts`
--

DROP TABLE IF EXISTS `cartproducts`;
CREATE TABLE IF NOT EXISTS `cartproducts` (
  `cartID` int DEFAULT NULL,
  `productID` int DEFAULT NULL,
  `productName` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `productPrice` float DEFAULT NULL,
  `productQty` int DEFAULT NULL,
  `subPrice` float DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cartproducts`
--

INSERT INTO `cartproducts` (`cartID`, `productID`, `productName`, `productPrice`, `productQty`, `subPrice`) VALUES
(10002, 1065, 'Acer Nitro 5', 700, 2, 1400),
(10002, 1065, 'Acer Nitro 5', 700, 3, 2100),
(10001, 1068, 'Clock', 12.5, 3, 37.5),
(10001, 1054, 'Laptop', 700, 1, 1400),
(10002, 1068, 'Clock', 12.5, 6, 75),
(10003, 1054, 'Laptop', 700, 1, 700),
(10003, 1065, 'Acer Nitro 5', 700, 1, 700),
(10003, 1064, 'Monitor', 1290, 1, 1290),
(10003, 1068, 'Clock', 12.5, 10, 125),
(10004, 1054, 'Laptop', 700, 2, 1400),
(10004, 1068, 'Clock', 12.5, 1, 12.5),
(10004, 1064, 'Monitor', 1290, 1, 1290),
(10004, 1065, 'Acer Nitro 5', 700, 2, 1400),
(10005, 1069, 'Coke', 0.5, 4, 2),
(10005, 1070, 'Apple', 1, 1, 1),
(10006, 1071, 'Cherry', 0.25, 3, 0.75),
(10006, 1069, 'Coke', 0.5, 10, 5),
(10007, 1071, 'Cherry', 0.25, 1, 0.25),
(10007, 1072, 'Bread', 1, 2, 2),
(10007, 1070, 'Apple', 1, 3, 3),
(10008, 1071, 'Cherry', 0.25, 1, 0.25),
(10008, 1070, 'Apple', 1, 1, 1),
(10009, 1071, 'Cherry', 0.25, 2, 0.5),
(10009, 1072, 'Bread', 1, 3, 3),
(10009, 1076, 'Bag', 5, 2, 10);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `pID` int NOT NULL AUTO_INCREMENT,
  `pName` varchar(64) COLLATE utf8mb4_general_ci DEFAULT 'UNKNOWN',
  `pPrice` float DEFAULT '0',
  `pQty` int DEFAULT '0',
  `productImg` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `pType` varchar(64) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`pID`)
) ENGINE=MyISAM AUTO_INCREMENT=1088 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`pID`, `pName`, `pPrice`, `pQty`, `productImg`, `pType`) VALUES
(1071, 'Cherry', 0.25, 93, NULL, 'Fruits'),
(1070, 'Apple', 1, 45, NULL, 'Fruits'),
(1069, 'Coke', 0.5, 4, NULL, 'Beverages'),
(1072, 'Bread', 1, 95, NULL, 'Food'),
(1073, 'Banana', 0.5, 100, NULL, 'Fruits'),
(1075, 'Pen', 1, 100, NULL, 'Stationery'),
(1076, 'Bag', 5, 23, NULL, 'Stationery'),
(1077, 'Ruler', 0.25, 100, NULL, 'Stationery'),
(1078, 'testing', 1, 1, NULL, 'test'),
(1079, 'test2', 1, 1, NULL, 'test2'),
(1080, 'test3', 10, 1, NULL, 'test3'),
(1087, 'test5', 1, 0, NULL, 'test5');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `testDate` timestamp NULL DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `lastName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `age` int DEFAULT NULL,
  `dateOfBirth` date NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=MyISAM AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userID`, `firstName`, `lastName`, `age`, `dateOfBirth`, `email`, `password`, `phone`, `gender`, `type`, `address`, `userName`, `is_active`) VALUES
(1000, 'admin', 'admin', 18, '2003-02-26', 'admin@domain.com', 'admin', '012792811', 'Male', 'Admin', 'Phnom Penh', 'admin', 0),
(1001, 'Rethtihpong', 'Em', 18, '2003-02-26', 'rithtipongem@gmail.com', 'rethtihpong1001', '012792811', 'Male', 'Employee', 'Phnom Penh', 'rethtihpong1001', 0),
(1004, 'Liza', 'John', 18, '2003-02-26', 'liza@gmail.com', 'JLiza', '012792811', 'Female', 'Employee', 'Phnom Penh', 'JLiza', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
