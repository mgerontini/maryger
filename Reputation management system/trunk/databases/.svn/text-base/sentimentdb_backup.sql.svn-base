-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 16, 2012 at 08:59 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sentimentdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `entity`
--

CREATE TABLE IF NOT EXISTS `entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL COMMENT 'like "ipad", "apple"',
  `score` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `entity_relatedword`
--

CREATE TABLE IF NOT EXISTS `entity_relatedword` (
  `entity` text NOT NULL,
  `related_word` text NOT NULL,
  `relation_score` double NOT NULL,
  FULLTEXT KEY `entity` (`entity`,`related_word`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `entity_relatedword`
--

INSERT INTO `entity_relatedword` (`entity`, `related_word`, `relation_score`) VALUES
('apple', 'ipad', 2),
('apple', 'ipod', 3);

-- --------------------------------------------------------

--
-- Table structure for table `sentiments`
--

CREATE TABLE IF NOT EXISTS `sentiments` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `tweet_id` bigint(20) NOT NULL,
  `entity_id` bigint(11) NOT NULL,
  `sentiment` double NOT NULL COMMENT 'sentiment value for a single entity not the whole tweet',
  `score` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `tweets`
--

CREATE TABLE IF NOT EXISTS `tweets` (
  `id` bigint(11) NOT NULL,
  `content` text NOT NULL,
  `author` varchar(30) NOT NULL,
  `date` datetime NOT NULL,
  `pagerank` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `content` (`content`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tweets`
--

INSERT INTO `tweets` (`id`, `content`, `author`, `date`, `pagerank`) VALUES
(191910862452297728, 'RT @ItzTrizz617: Wouldnt an ipad mini just be a iphone?', 'VidaSterLinG', '2012-04-16 17:28:09', NULL),
(191910862326480897, 'RT @Cinepolis: ¡Ahora consulta tu cartelera de muestras y festivales desde las aplicaciones de Cinépolis de iPhone y iPad! http://t.co/OH88rKTV', 'ClauuCpp', '2012-04-16 17:28:09', NULL),
(191910862557155330, 'RT @harryjim_: iPad Mini... iPod Touch?', 'Juuuliaaaar', '2012-04-16 17:28:09', NULL),
(191910862703964161, '@SincereJones  I just received an iPAD 3 4 F_R_E_E. Get yours before it is gone.  @Free_iPad3_', 'crystalhz4', '2012-04-16 17:28:09', NULL),
(191910863068872705, '#ipad . Reagan, Whos scared of this?. , like 123', 'DixieMunoz3', '2012-04-16 17:28:09', NULL),
(191910863148552193, 'RT @RizzleKicks: So an iPad mini would be a smaller version of a small version of a portable version of a computer', 'MollieSpratt', '2012-04-16 17:28:09', NULL),
(191910864352317442, 'iPad Mini Frost  Did you examine this? http://t.co/eQofMu7P', 'GenaWolfe', '2012-04-16 17:28:10', NULL),
(191910863932887041, 'Photo: uso correcto del iPad http://t.co/Lr5okHdd', 'Kangrejo', '2012-04-16 17:28:10', NULL),
(191910865505751040, 'wouldnt a mini iPad be an iPod? #confused :p', 'arianabell7', '2012-04-16 17:28:10', NULL),
(191910865220546561, '#Pyrus Electronics (Tm) Slide-in Faux Leather  http://t.co/FTG2fYYE #(latest #(TM) #2 #Black #Case #for #Generation #iPad #Slidein', 'MP3PlayerE', '2012-04-16 17:28:10', NULL),
(191910865585446912, '@RaileanRum  FREE new iPAD 3. This was my possibility to keep them 4 FREE:  @Free_iPad3_', 'realidadpo8', '2012-04-16 17:28:10', NULL),
(191910865623187457, 'Get a free Apple iPad 2 from TraInn, the BEST freebie network! http://t.co/eLPdOFMr', 'pollsofpolls', '2012-04-16 17:28:10', NULL),
(191910867154108416, '@Trick_Or_Trice  I just got a F-R-E-E The New iPad - Get yours before it is all gone:  @Free_iPad3_', 'keyettae0', '2012-04-16 17:28:10', NULL),
(191910869758783488, 'Apple be popping out ANYTHANG nowadays... alrightttttt. Whats next??.... a fugging Ipad invisible????... um already got one of those.', 'eLiZZYv', '2012-04-16 17:28:11', NULL),
(191910870408904704, 'Windows 8 art?k iPad`da... http://t.co/Wqf7UZhE', 'gece24com', '2012-04-16 17:28:11', NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
