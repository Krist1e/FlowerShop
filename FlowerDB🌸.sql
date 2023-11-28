-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema FlowerShopDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema FlowerShopDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FlowerShopDB` DEFAULT CHARACTER SET utf8mb3 ;
USE `FlowerShopDB` ;

-- -----------------------------------------------------
-- Table `FlowerShopDB`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `imagePath` TEXT NULL DEFAULT NULL,
  `price` FLOAT NOT NULL,
  `discount` FLOAT NULL DEFAULT '0',
  `is_in_stock` TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`coupon`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`coupon` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `discount` FLOAT NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(30) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `password` CHAR(64) NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_user_role_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `FlowerShopDB`.`role` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`shopping_cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`shopping_cart` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total_price` FLOAT NOT NULL DEFAULT '0',
  `user_id` INT NOT NULL,
  `coupon_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  INDEX `fk_shopping_cart_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_shopping_cart_coupon1_idx` (`coupon_id` ASC) VISIBLE,
  CONSTRAINT `fk_shopping_cart_coupon1`
    FOREIGN KEY (`coupon_id`)
    REFERENCES `FlowerShopDB`.`coupon` (`id`),
  CONSTRAINT `fk_shopping_cart_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `FlowerShopDB`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`cart_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`cart_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `price` FLOAT NOT NULL,
  `shopping_cart_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UQ_shopping_cart_id_product_id` (`shopping_cart_id` ASC, `product_id` ASC) VISIBLE,
  INDEX `fk_cart_item_shopping_cart1_idx` (`shopping_cart_id` ASC) VISIBLE,
  INDEX `fk_cart_item_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShopDB`.`product` (`id`),
  CONSTRAINT `fk_cart_item_shopping_cart1`
    FOREIGN KEY (`shopping_cart_id`)
    REFERENCES `FlowerShopDB`.`shopping_cart` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 264
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`order_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`order_status` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `total_price` FLOAT NOT NULL,
  `order_datetime` DATETIME NOT NULL,
  `address` VARCHAR(150) NOT NULL,
  `telephone` VARCHAR(20) NOT NULL,
  `comments` TEXT NULL DEFAULT NULL,
  `order_status_id` INT NOT NULL,
  `coupon_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_order_order_status1_idx` (`order_status_id` ASC) VISIBLE,
  INDEX `fk_order_coupon1_idx` (`coupon_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_coupon1`
    FOREIGN KEY (`coupon_id`)
    REFERENCES `FlowerShopDB`.`coupon` (`id`),
  CONSTRAINT `fk_order_order_status1`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `FlowerShopDB`.`order_status` (`id`),
  CONSTRAINT `fk_order_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `FlowerShopDB`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `FlowerShopDB`.`order_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`order_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `price` FLOAT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UQ_order_id_product_id` (`order_id` ASC, `product_id` ASC) VISIBLE,
  INDEX `fk_order_item_order1_idx` (`order_id` ASC) INVISIBLE,
  INDEX `fk_order_item_product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_item_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `FlowerShopDB`.`order` (`id`),
  CONSTRAINT `fk_order_item_product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `FlowerShopDB`.`product` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb3;

USE `FlowerShopDB` ;

-- -----------------------------------------------------
-- Placeholder table for view `FlowerShopDB`.`cart_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`cart_view` (`cart_id` INT, `user_id` INT, `total_price` INT, `coupon_id` INT, `coupon_code` INT, `coupon_name` INT, `coupon_discount` INT, `item_id` INT, `item_quantity` INT, `item_price` INT, `product_id` INT, `product_name` INT, `product_description` INT, `product_imagePath` INT, `product_price` INT, `product_discount` INT);

-- -----------------------------------------------------
-- Placeholder table for view `FlowerShopDB`.`order_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`order_view` (`order_id` INT, `user_id` INT, `username` INT, `email` INT, `total_price` INT, `status` INT, `order_datetime` INT, `address` INT, `telephone` INT, `comments` INT, `coupon_id` INT, `coupon_code` INT, `coupon_name` INT, `coupon_discount` INT, `item_id` INT, `product_id` INT, `product_name` INT, `product_description` INT, `product_imagePath` INT, `product_price` INT, `product_discount` INT, `item_quantity` INT, `item_price` INT);

-- -----------------------------------------------------
-- Placeholder table for view `FlowerShopDB`.`user_view`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FlowerShopDB`.`user_view` (`id` INT, `username` INT, `password` INT, `email` INT, `role` INT);

-- -----------------------------------------------------
-- View `FlowerShopDB`.`cart_view`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FlowerShopDB`.`cart_view`;
USE `FlowerShopDB`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `FlowerShopDB`.`cart_view` AS select `sc`.`id` AS `cart_id`,`sc`.`user_id` AS `user_id`,`sc`.`total_price` AS `total_price`,`sc`.`coupon_id` AS `coupon_id`,`c`.`code` AS `coupon_code`,`c`.`name` AS `coupon_name`,`c`.`discount` AS `coupon_discount`,`ci`.`id` AS `item_id`,`ci`.`quantity` AS `item_quantity`,`ci`.`price` AS `item_price`,`p`.`id` AS `product_id`,`p`.`name` AS `product_name`,`p`.`description` AS `product_description`,`p`.`imagePath` AS `product_imagePath`,`p`.`price` AS `product_price`,`p`.`discount` AS `product_discount` from (((`FlowerShopDB`.`shopping_cart` `sc` left join `FlowerShopDB`.`coupon` `c` on((`sc`.`coupon_id` = `c`.`id`))) left join `FlowerShopDB`.`cart_item` `ci` on((`sc`.`id` = `ci`.`shopping_cart_id`))) left join `FlowerShopDB`.`product` `p` on((`ci`.`product_id` = `p`.`id`)));

-- -----------------------------------------------------
-- View `FlowerShopDB`.`order_view`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FlowerShopDB`.`order_view`;
USE `FlowerShopDB`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `FlowerShopDB`.`order_view` AS select `o`.`id` AS `order_id`,`u`.`id` AS `user_id`,`u`.`username` AS `username`,`u`.`email` AS `email`,`o`.`total_price` AS `total_price`,`os`.`name` AS `status`,`o`.`order_datetime` AS `order_datetime`,`o`.`address` AS `address`,`o`.`telephone` AS `telephone`,`o`.`comments` AS `comments`,`c`.`id` AS `coupon_id`,`c`.`code` AS `coupon_code`,`c`.`name` AS `coupon_name`,`c`.`discount` AS `coupon_discount`,`oi`.`id` AS `item_id`,`p`.`id` AS `product_id`,`p`.`name` AS `product_name`,`p`.`description` AS `product_description`,`p`.`imagePath` AS `product_imagePath`,`p`.`price` AS `product_price`,`p`.`discount` AS `product_discount`,`oi`.`quantity` AS `item_quantity`,`oi`.`price` AS `item_price` from (((((`FlowerShopDB`.`order` `o` join `FlowerShopDB`.`user` `u` on((`o`.`user_id` = `u`.`id`))) join `FlowerShopDB`.`order_status` `os` on((`o`.`order_status_id` = `os`.`id`))) left join `FlowerShopDB`.`coupon` `c` on((`o`.`coupon_id` = `c`.`id`))) join `FlowerShopDB`.`order_item` `oi` on((`o`.`id` = `oi`.`order_id`))) join `FlowerShopDB`.`product` `p` on((`oi`.`product_id` = `p`.`id`)));

-- -----------------------------------------------------
-- View `FlowerShopDB`.`user_view`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `FlowerShopDB`.`user_view`;
USE `FlowerShopDB`;
CREATE  OR REPLACE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `FlowerShopDB`.`user_view` AS select `u`.`id` AS `id`,`u`.`username` AS `username`,`u`.`password` AS `password`,`u`.`email` AS `email`,`r`.`name` AS `role` from (`FlowerShopDB`.`user` `u` left join `FlowerShopDB`.`role` `r` on((`u`.`role_id` = `r`.`id`)));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
