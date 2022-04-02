-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 04, 2022 at 10:46 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 7.4.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ebike`
--

DELIMITER $$
--
-- Functions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `insertRent` (`timeT` DATETIME, `bikeId` INT, `custId` INT) RETURNS INT(11) BEGIN

  INSERT INTO rent(timeCreate,bikeId,custId) VALUES (timeT,bikeId,custId);
  UPDATE biketype
SET status = 'Rent'
WHERE id = bikeId;
	
    return 1;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `insertReturn` (`orID` INT, `bikeID` INT, `totMoney` INT, `timeFns` DATETIME, `id` INT) RETURNS INT(11) BEGIN
   UPDATE rent SET total = totMoney,
                   timeFinish = timeFns,
                   returnId = id
   WHERE rent.orderId = orID;
/*UPDATE biketype
SET status = 'Available'
WHERE id = bikeId; */
return 1;

END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `renting` (`sID` INT, `bID` INT, `timeT` DATETIME, `custId` INT) RETURNS INT(11) BEGIN

INSERT INTO rent(timeCreate,bikeId,custId) VALUES (timeT,bID,custId);

UPDATE storehasbike
SET number = number-1
WHERE  storeid = sID and bikeid = bID;
    return 1;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `returning` (`orID` INT, `bikeID` INT, `totMoney` INT, `timeFns` DATETIME, `id` INT) RETURNS INT(11) BEGIN
   UPDATE rent
   SET total = totMoney,
       timeFinish = timeFns,
       returnId = id
   WHERE rent.orderId = orID;

	UPDATE storehasbike
	SET number = number+1
	WHERE  storeid = id and bikeid = bikeID;

return 1;

END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `updateReturn` (`bikeID` INT, `sID` INT) RETURNS INT(11) BEGIN
 UPDATE  store
  SET emptyDock = emptyDock-1,
		 status = CASE WHEN emptyDock = 0 THEN "Full" ELSE status END
    WHERE  storeid = sID;
    
    UPDATE biketype
    set   storeid = sID,status='Available'
    where id = bikeID;

RETURN 1;
END$$

CREATE DEFINER=`root`@`localhost` FUNCTION `updateStoreRent` (`sId` INT) RETURNS INT(11) BEGIN
    UPDATE  store
    SET emptyDock = emptyDock+1,
		 status = 'Available'
    WHERE  storeid = (SELECT storeid from biketype where id = sid );
	RETURN 1;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `biketype`
--

CREATE TABLE `biketype` (
  `id` int(11) NOT NULL,
  `storeid` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `type` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `license` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `manuafactur` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `producer` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `status` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `biketype`
--

INSERT INTO `biketype` (`id`, `storeid`, `name`, `type`, `weight`, `license`, `manuafactur`, `producer`, `cost`, `status`) VALUES
(1, 1, 'Calos', 'Bike', 23, '1', '2001', 'Italy', 2300, 'Available'),
(2, 1, 'Centa', 'Bike', 42, '2', '2002', 'France', 1200, 'Available'),
(3, 1, 'Delta', 'Electric', 12, '3', '2003', 'Naiko', 1000, 'Available'),
(4, 1, 'Chigo', 'Electric', 32, '4', '2004', 'Denta', 1500, 'Available'),
(5, 2, 'GT', 'Twin', 12, '4', '2008', 'Italy', 1255, 'Available'),
(6, 2, 'Kona', 'Twin', 8, '1', '2009', 'Brazil', 3000, 'Available'),
(7, 2, 'Scott', 'ElectricBike', 9, '2', '2010', 'Brazil', 4500, 'Available'),
(8, 2, 'Merida', 'Bike', 23, '3', '2009', 'Italy', 3000, 'Available'),
(9, 3, 'Marin', 'TwinBike', 53, '4', '2009', 'Brazil', 4500, 'Available'),
(10, 3, 'Giant', 'TwinBike', 35, '5', '2015', 'Brazil', 4500, 'Available'),
(11, 3, 'Romance', 'ElectricBike', 46, '6', '2009', 'Italy', 3000, 'Available'),
(12, 3, 'Alan', 'ElectricBike', 22, '2', '2021', 'USA', 2800, 'Available'),
(13, 4, 'Alex', 'Bike', 1, '1', '2031', 'USA', 4700, 'Available'),
(14, 4, 'Argon', 'TwinBike', 2, '2', '2015', 'France', 2800, 'Available'),
(15, 4, 'Ascari', 'Bike', 3, '1', '2018', 'USA', 2800, 'Available'),
(16, 4, 'Avanti', 'TwinBike', 4, '2', '2019', 'France', 4700, 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `rent`
--

CREATE TABLE `rent` (
  `orderId` int(11) NOT NULL,
  `total` int(11) DEFAULT NULL,
  `timeCreate` datetime DEFAULT NULL,
  `timeFinish` datetime DEFAULT NULL,
  `bikeId` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `custId` int(11) DEFAULT NULL,
  `returnId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `rent`
--

INSERT INTO `rent` (`orderId`, `total`, `timeCreate`, `timeFinish`, `bikeId`, `custId`, `returnId`) VALUES
(262, 13000, '2021-12-24 16:54:23', '2021-12-24 16:55:02', '15', 1, 4),
(263, 67000, '2021-12-24 16:55:44', '2021-12-24 17:00:58', '10', 1, 3),
(264, 16000, '2021-12-24 17:03:16', '2021-12-24 17:04:08', '9', 1, 3),
(265, 13000, '2021-12-24 19:25:54', '2021-12-24 19:26:32', '5', 1, 2),
(266, 13000, '2021-12-25 14:58:33', '2021-12-25 14:59:10', '7', 1, 2),
(267, 10000, '2021-12-26 11:50:52', '2021-12-26 11:50:57', '17', 1, 1),
(268, 10000, '2021-12-26 22:13:10', '2021-12-26 22:13:22', '11', 1, 3),
(269, 10000, '2021-12-27 08:23:09', '2021-12-27 08:23:27', '5', 1, 2),
(270, 10000, '2021-12-27 09:15:07', '2021-12-27 09:15:22', '4', 1, 1),
(271, 28000, '2021-12-29 13:07:10', '2021-12-29 13:09:03', '1', 1, 1),
(272, 55500, '2021-12-29 13:09:46', '2021-12-29 13:12:20', '3', 1, 1),
(273, 19000, '2021-12-29 13:12:47', '2021-12-29 13:13:58', '1', 1, 1),
(274, 33000, '2021-12-29 13:14:27', '2021-12-29 13:15:43', '3', 1, 1),
(276, 15000, '2021-12-30 11:38:42', '2021-12-30 11:38:47', '7', 1, 2),
(279, 34000, '2021-12-31 00:14:10', '2021-12-31 00:16:31', '1', 1, 1),
(280, 217500, '2021-12-31 02:35:57', '2021-12-31 02:47:39', '7', 1, 2),
(305, 16296000, '2022-01-03 13:48:44', '2022-01-04 04:53:42', '6', 1, 1),
(306, 160000, '2022-01-04 05:29:58', '2022-01-04 05:42:54', '1', 1, 2),
(307, 64000, '2022-01-04 05:45:48', '2022-01-04 05:50:34', '1', 1, 2),
(308, 34000, '2022-01-04 05:49:34', '2022-01-04 05:52:01', '1', 2, 2),
(309, 22000, '2022-01-04 06:03:16', '2022-01-04 06:04:33', '1', 1, 1),
(310, 19000, '2022-01-04 06:03:59', '2022-01-04 06:05:06', '2', 2, 1),
(311, 91500, '2022-01-04 07:25:32', '2022-01-04 07:30:10', '3', 1, 4),
(312, 24000, '2022-01-04 07:27:08', '2022-01-04 07:27:56', '5', 2, 1),
(313, 181500, '2022-01-04 08:26:00', '2022-01-04 08:35:33', '6', 1, 3),
(314, 16000, '2022-01-04 08:36:16', '2022-01-04 08:37:02', '1', 1, 1),
(315, 13000, '2022-01-04 08:37:20', '2022-01-04 08:37:54', '1', 1, 1),
(316, 16000, '2022-01-04 08:38:13', '2022-01-04 08:39:09', '1', 1, 1),
(317, 15000, '2022-01-04 11:43:04', '2022-01-04 11:43:29', '6', 1, 4),
(318, 37500, '2022-01-04 11:46:31', '2022-01-04 11:48:04', '6', 1, 3),
(319, 199500, '2022-01-04 12:25:46', '2022-01-04 12:36:23', '5', 1, 2),
(320, 42000, '2022-01-05 00:05:49', '2022-01-05 00:07:48', '6', 2, 2),
(321, 19000, '2022-01-05 00:12:45', '2022-01-05 00:13:47', '1', 2, 1),
(322, 34000, '2022-01-05 00:15:13', '2022-01-05 00:17:42', '1', 2, 4),
(323, 15000, '2022-01-05 00:16:45', '2022-01-05 00:17:10', '5', 1, 1),
(324, 94000, '2022-01-05 00:18:23', '2022-01-05 00:25:49', '1', 2, 1),
(325, 10000, '2022-01-05 00:26:02', '2022-01-05 00:26:26', '1', 2, 1),
(326, 13000, '2022-01-05 00:28:05', '2022-01-05 00:28:43', '1', 2, 1),
(327, 10000, '2022-01-05 00:29:01', '2022-01-05 00:29:27', '1', 2, 1),
(328, 88000, '2022-01-05 00:29:51', '2022-01-05 00:36:46', '1', 2, 1),
(329, 114000, '2022-01-05 00:35:55', '2022-01-05 00:41:46', '5', 1, 4),
(330, 25000, '2022-01-05 00:40:32', '2022-01-05 00:42:08', '1', 2, 1),
(331, 52000, '2022-01-05 00:42:18', '2022-01-05 00:46:16', '1', 2, 1),
(332, 73500, '2022-01-05 00:42:45', '2022-01-05 00:46:30', '5', 1, 4),
(333, 33000, '2022-01-05 00:45:30', '2022-01-05 00:46:56', '6', 3, 4),
(334, 46000, '2022-01-05 01:13:49', '2022-01-05 01:17:10', '1', 2, 1),
(335, 91000, '2022-01-05 01:18:15', '2022-01-05 01:25:23', '1', 2, 4),
(336, 15000, '2022-01-05 01:21:41', '2022-01-05 01:21:54', '3', 1, 1),
(337, 73500, '2022-01-05 01:22:26', '2022-01-05 01:25:57', '5', 1, 1),
(338, 55500, '2022-01-05 01:23:53', '2022-01-05 01:26:32', '5', 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `store`
--

CREATE TABLE `store` (
  `storeid` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `address` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `status` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `maxbike` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `store`
--

INSERT INTO `store` (`storeid`, `name`, `address`, `status`, `maxbike`) VALUES
(1, 'F-x Bike', '225 Hoàng Hoa Thám, Ba Đình, Hà Nội', 'Full', 4),
(2, 'Toan Thang Cycles', '23 Lê Trọng Tấn, La Khê, Hà Nội', 'Full', 4),
(3, 'Xe đạp 24h', '7A Trường Chinh, Quận Thanh Xuân, Hà Nội ', 'Full', 4),
(4, 'Xe Đạp Thế Giới', '163 Cầu Diễn, Bắc Từ Liêm, Hà Nội', 'Full', 4);

-- --------------------------------------------------------

--
-- Table structure for table `storehasbike`
--

CREATE TABLE `storehasbike` (
  `storeid` int(11) NOT NULL,
  `bikeid` int(11) NOT NULL,
  `number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `storehasbike`
--

INSERT INTO `storehasbike` (`storeid`, `bikeid`, `number`) VALUES
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(1, 4, 1),
(1, 5, 0),
(1, 6, 0),
(2, 1, 2),
(2, 2, 0),
(2, 3, 0),
(2, 4, 0),
(2, 5, 1),
(2, 6, 1),
(3, 1, 0),
(3, 2, 0),
(3, 3, 1),
(3, 4, 1),
(3, 5, 0),
(3, 6, 2),
(4, 1, 0),
(4, 2, 0),
(4, 3, 0),
(4, 4, 0),
(4, 5, 1),
(4, 6, 3);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `orderId` int(11) NOT NULL,
  `message` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `money` float DEFAULT NULL,
  `card` varchar(17) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `orderId`, `message`, `money`, `card`) VALUES
(455, 262, '', 400000, '1234567891111111'),
(456, 262, '1', 13000, '1234567891111111'),
(457, 263, '1', 550000, '1234567891111111'),
(458, 263, '', 67000, '1234567891111111'),
(459, 264, '', 550000, '1234567891111111'),
(460, 264, '1', 16000, '1234567891111111'),
(461, 265, '1', 400000, '1234567891111111'),
(462, 265, '1', 13000, '1234567891111111'),
(463, 266, '1', 700000, '1234567891111111'),
(464, 266, '1', 13000, '1234567891111111'),
(465, 267, '1', 700000, '1234567891111111'),
(466, 267, '1', 10000, '1234567891111111'),
(467, 268, '', 700000, '123'),
(468, 268, '', 10000, ''),
(469, 269, '', 400000, '1234567891111111'),
(470, 269, '', 10000, '1234567891111111'),
(471, 270, '1', 700000, '1234567891111111'),
(472, 270, '', 10000, '1234567891111111'),
(473, 271, '1', 400000, '1234567891111111'),
(474, 271, '', 28000, '1234567891111111'),
(475, 272, '1', 700000, '1234567891111111'),
(476, 272, '1', 55500, '1234567891111111'),
(477, 273, '1', 400000, '1234567891111111'),
(478, 273, '1', 19000, '1234567891111111'),
(483, 276, '1', 700000, '1234567891111111'),
(484, 276, '1', 15000, '1234567891111111'),
(486, 279, 'trans', 400000, '1234567891111111'),
(487, 279, 'transs', 34000, '1234567891111111'),
(488, 280, '', 700000, '1234567891111111'),
(489, 280, '', 217500, '1234567891111111'),
(519, 306, '', 400000, ''),
(520, 306, '', 160000, ''),
(521, 307, '', 400000, ''),
(522, 308, '', 400000, ''),
(523, 307, '', 64000, ''),
(524, 308, '', 34000, ''),
(525, 309, '', 400000, ''),
(526, 310, '', 400000, ''),
(527, 309, '', 22000, ''),
(528, 310, '', 19000, ''),
(529, 311, '', 700000, ''),
(530, 312, '', 550000, ''),
(531, 312, '', 24000, ''),
(532, 311, '', 91500, ''),
(533, 313, '', 550000, ''),
(534, 313, '', 181500, ''),
(535, 314, '', 400000, ''),
(536, 314, '', 16000, ''),
(537, 315, '', 400000, ''),
(538, 315, '', 13000, ''),
(539, 316, '', 400000, ''),
(540, 316, '', 16000, ''),
(541, 317, '', 550000, ''),
(542, 317, '', 15000, ''),
(543, 318, '', 550000, ''),
(544, 318, '', 37500, ''),
(545, 319, '', 550000, ''),
(546, 319, '', 199500, ''),
(547, 320, '', 550000, ''),
(548, 320, '', 42000, ''),
(549, 321, '', 400000, ''),
(550, 321, '', 19000, ''),
(551, 322, '', 400000, ''),
(552, 323, '', 550000, ''),
(553, 323, '', 15000, ''),
(554, 322, '', 34000, ''),
(555, 324, '', 400000, ''),
(556, 324, '', 94000, ''),
(557, 325, '', 400000, ''),
(558, 325, '', 10000, ''),
(559, 326, '', 400000, ''),
(560, 326, '', 13000, ''),
(561, 327, '', 400000, ''),
(562, 327, '', 10000, ''),
(563, 328, '', 400000, ''),
(564, 329, '', 550000, ''),
(565, 328, '', 88000, ''),
(566, 330, '', 400000, ''),
(567, 329, '', 114000, ''),
(568, 330, '', 25000, ''),
(569, 331, '', 400000, ''),
(570, 332, '', 550000, ''),
(571, 333, '', 550000, ''),
(572, 331, '', 52000, ''),
(573, 332, '', 73500, ''),
(574, 333, '', 33000, ''),
(575, 334, '', 400000, ''),
(576, 334, '', 46000, ''),
(577, 335, '', 400000, ''),
(578, 336, '', 700000, ''),
(579, 336, '', 15000, ''),
(580, 337, '', 550000, ''),
(581, 338, '', 550000, ''),
(582, 335, '', 91000, ''),
(583, 337, '', 73500, ''),
(584, 338, '', 55500, '');

-- --------------------------------------------------------

--
-- Table structure for table `useraccount`
--

CREATE TABLE `useraccount` (
  `id` int(11) NOT NULL,
  `user` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `pass` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `address` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `useraccount`
--

INSERT INTO `useraccount` (`id`, `user`, `pass`, `name`, `address`, `email`) VALUES
(1, 'tu', '456', 'Quoc Tu', '23B Tong Dan', 'dangquoctuhn@gmail.com'),
(2, 'tri', '123', 'Duc Tri', '12 ABCDE', 'tringuyen@gmail.com'),
(3, 'kiet', '789', 'The Kiet', '99 QQQQQ', 'thekiet@outlook.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `biketype`
--
ALTER TABLE `biketype`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_UNIQUE` (`id`),
  ADD KEY `fk_storeid_idx` (`storeid`);

--
-- Indexes for table `rent`
--
ALTER TABLE `rent`
  ADD PRIMARY KEY (`orderId`),
  ADD KEY `fk_idx` (`custId`);

--
-- Indexes for table `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`storeid`),
  ADD UNIQUE KEY `storeid_UNIQUE` (`storeid`),
  ADD UNIQUE KEY `name_UNIQUE` (`name`),
  ADD UNIQUE KEY `address_UNIQUE` (`address`);

--
-- Indexes for table `storehasbike`
--
ALTER TABLE `storehasbike`
  ADD PRIMARY KEY (`storeid`,`bikeid`),
  ADD KEY `fk_has_bike` (`bikeid`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_idx` (`orderId`);

--
-- Indexes for table `useraccount`
--
ALTER TABLE `useraccount`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_UNIQUE` (`user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `biketype`
--
ALTER TABLE `biketype`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `rent`
--
ALTER TABLE `rent`
  MODIFY `orderId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=339;

--
-- AUTO_INCREMENT for table `store`
--
ALTER TABLE `store`
  MODIFY `storeid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=585;

--
-- AUTO_INCREMENT for table `useraccount`
--
ALTER TABLE `useraccount`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `biketype`
--
ALTER TABLE `biketype`
  ADD CONSTRAINT `fk_storeid` FOREIGN KEY (`storeid`) REFERENCES `store` (`storeid`);

--
-- Constraints for table `rent`
--
ALTER TABLE `rent`
  ADD CONSTRAINT `fk_to` FOREIGN KEY (`custId`) REFERENCES `useraccount` (`id`);

--
-- Constraints for table `storehasbike`
--
ALTER TABLE `storehasbike`
  ADD CONSTRAINT `fk_has_bike` FOREIGN KEY (`bikeid`) REFERENCES `biketype` (`id`),
  ADD CONSTRAINT `fk_has_store` FOREIGN KEY (`storeid`) REFERENCES `store` (`storeid`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `fk` FOREIGN KEY (`orderId`) REFERENCES `rent` (`orderId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
