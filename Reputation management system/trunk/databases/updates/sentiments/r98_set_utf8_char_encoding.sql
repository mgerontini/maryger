TRUNCATE `tweets`;
ALTER TABLE `tweets` CHARACTER SET = utf8 , COLLATE = utf8_general_ci ;
ALTER TABLE `tweets` CHANGE COLUMN `content` `content` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL  ;
ALTER TABLE `tweets` CHANGE COLUMN `author` `author` VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL  ;