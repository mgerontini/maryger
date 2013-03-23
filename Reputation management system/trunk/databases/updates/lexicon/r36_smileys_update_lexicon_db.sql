--
-- Database: `lexicondb`
--

-- --------------------------------------------------------

--
-- Table structure for table `smileys`
--

CREATE TABLE IF NOT EXISTS `smileys` (
  `smiley` varchar(6) NOT NULL,
  `value` int(11) NOT NULL DEFAULT '0',
  FULLTEXT KEY `smiley` (`smiley`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `smileys`
--

INSERT INTO `smileys` (`smiley`, `value`) VALUES
('>:]', 1),
(':-)', 1),
(':)', 1),
(':o)', 1),
(':]', 1),
(':3', 1),
(':c)', 1),
(':>', 1),
('=]', 1),
('8)', 1),
('=)', 1),
(':}', 1),
(':^)', 1),
('>:D', 2),
(':-D', 2),
(':D', 2),
('8-D', 2),
('8D', 2),
('x-D', 2),
('xD', 2),
('X-D', 2),
('XD', 2),
('=-3', 2),
('=3', 2),
('8-)', 2),
(':-))', 2),
('>:[', -1),
(':-(', -1),
(':(', -1),
(':-c', -1),
(':c', -1),
(':-<', -1),
(':<', -1),
(':-[', -1),
(':[', -1),
(':{', -1),
('>.>', -1),
('<.<', -1),
('>.<', -1),
(':-||', -1),
('D:<', -1),
('D:', -2),
('D8', -2),
('D;', -2),
('D=', -2),
('DX', -2),
('v.v', -2),
('D-'':', -2),
('<3', 1),
('</3', -1),
('XD', 1),
('(-_-'')', -1),
('(-_-;)', -1),
('(o_O)', -1),
(':@', -2);