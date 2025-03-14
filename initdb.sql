-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: test_db
-- ------------------------------------------------------
-- Server version	8.0.13

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
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `brand` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `created_at` datetime(6) DEFAULT NULL,
                         `created_by` varchar(255) DEFAULT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         `updated_by` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (1,NULL,NULL,NULL,NULL,'Dell'),(2,NULL,NULL,NULL,NULL,'HP'),(3,NULL,NULL,NULL,NULL,'Lenovo'),(4,NULL,NULL,NULL,NULL,'Asus'),(5,NULL,NULL,NULL,NULL,'Acer'),(6,NULL,NULL,NULL,NULL,'Apple'),(7,NULL,NULL,NULL,NULL,'MSI'),(8,NULL,NULL,NULL,NULL,'Razer'),(9,NULL,NULL,NULL,NULL,'Samsung'),(10,NULL,NULL,NULL,NULL,'LG');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `carts` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `created_at` datetime(6) DEFAULT NULL,
                         `created_by` varchar(255) DEFAULT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         `updated_by` varchar(255) DEFAULT NULL,
                         `quantity` int(11) DEFAULT NULL,
                         `product_id` varchar(255) DEFAULT NULL,
                         `user_id` bigint(20) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKmd2ap4oxo3wvgkf4fnaye532i` (`product_id`),
                         KEY `FKb5o626f86h46m4s7ms6ginnop` (`user_id`),
                         CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                         CONSTRAINT `FKmd2ap4oxo3wvgkf4fnaye532i` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
INSERT INTO `carts` VALUES (8,'2024-12-23 12:55:03.190721','admin@gmail.com',NULL,NULL,1,'a2734e82-ab0b-11ef-85bb-005056c00001',1);
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `categories` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `created_at` datetime(6) DEFAULT NULL,
                              `created_by` varchar(255) DEFAULT NULL,
                              `updated_at` datetime(6) DEFAULT NULL,
                              `updated_by` varchar(255) DEFAULT NULL,
                              `code` varchar(255) DEFAULT NULL,
                              `name` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'2024-11-20 15:40:09.000000','system',NULL,NULL,'OFFICE','Laptop Văn phòng'),(2,'2024-11-20 15:40:09.000000','system',NULL,NULL,'ENTERTAINMENT','Laptop Giải trí'),(3,'2024-11-20 15:40:09.000000','system',NULL,NULL,'GRAPHICS','Laptop Đồ họa'),(4,'2024-11-20 15:40:09.000000','system','2024-11-25 09:53:05.523193','admin@gmail.com','vanphong','laptop laptop haha'),(5,'2024-11-25 09:52:44.056962','staff1',NULL,NULL,'giaitri','laptop giaitri');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_order_details`
--

DROP TABLE IF EXISTS `import_order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `import_order_details` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `created_at` datetime(6) DEFAULT NULL,
                                        `created_by` varchar(255) DEFAULT NULL,
                                        `updated_at` datetime(6) DEFAULT NULL,
                                        `updated_by` varchar(255) DEFAULT NULL,
                                        `price` double DEFAULT NULL,
                                        `quantity` int(11) DEFAULT NULL,
                                        `import_order_id` bigint(20) DEFAULT NULL,
                                        `product_id` varchar(255) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `FK38tsfqowfvuy8jkgraja3iuv5` (`import_order_id`),
                                        KEY `FK3mnxfhpalpqsbg24lf6or5n7b` (`product_id`),
                                        CONSTRAINT `FK38tsfqowfvuy8jkgraja3iuv5` FOREIGN KEY (`import_order_id`) REFERENCES `import_orders` (`id`),
                                        CONSTRAINT `FK3mnxfhpalpqsbg24lf6or5n7b` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_order_details`
--

LOCK TABLES `import_order_details` WRITE;
/*!40000 ALTER TABLE `import_order_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_orders`
--

DROP TABLE IF EXISTS `import_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `import_orders` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `created_at` datetime(6) DEFAULT NULL,
                                 `created_by` varchar(255) DEFAULT NULL,
                                 `updated_at` datetime(6) DEFAULT NULL,
                                 `updated_by` varchar(255) DEFAULT NULL,
                                 `date` datetime(6) DEFAULT NULL,
                                 `total` int(11) DEFAULT NULL,
                                 `supplier_id` bigint(20) DEFAULT NULL,
                                 `user_id` bigint(20) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FK5d8le1rq70wflcxloxhwnthlt` (`supplier_id`),
                                 KEY `FKlo6g0jkgh8hu8t43c0fbfbu9d` (`user_id`),
                                 CONSTRAINT `FK5d8le1rq70wflcxloxhwnthlt` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
                                 CONSTRAINT `FKlo6g0jkgh8hu8t43c0fbfbu9d` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_orders`
--

LOCK TABLES `import_orders` WRITE;
/*!40000 ALTER TABLE `import_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_logs`
--

DROP TABLE IF EXISTS `inventory_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `inventory_logs` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `created_at` datetime(6) DEFAULT NULL,
                                  `created_by` varchar(255) DEFAULT NULL,
                                  `updated_at` datetime(6) DEFAULT NULL,
                                  `updated_by` varchar(255) DEFAULT NULL,
                                  `date` datetime(6) DEFAULT NULL,
                                  `quantity` int(11) DEFAULT NULL,
                                  `import_order_id` bigint(20) DEFAULT NULL,
                                  `order_id` bigint(20) DEFAULT NULL,
                                  `product_id` varchar(255) DEFAULT NULL,
                                  `user_id` bigint(20) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKq3ligi9kq5heaic724l5f8la7` (`import_order_id`),
                                  KEY `FKgou4cl6x8vcn6b1yd1dwo2rcf` (`order_id`),
                                  KEY `FKjd60bqs3714ngtsswkghcsm3a` (`product_id`),
                                  KEY `FKh0t74u3olgmjito46t9s6x5l4` (`user_id`),
                                  CONSTRAINT `FKgou4cl6x8vcn6b1yd1dwo2rcf` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                                  CONSTRAINT `FKh0t74u3olgmjito46t9s6x5l4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `FKjd60bqs3714ngtsswkghcsm3a` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                                  CONSTRAINT `FKq3ligi9kq5heaic724l5f8la7` FOREIGN KEY (`import_order_id`) REFERENCES `import_orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_logs`
--

LOCK TABLES `inventory_logs` WRITE;
/*!40000 ALTER TABLE `inventory_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `invoices` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `created_at` datetime(6) DEFAULT NULL,
                            `created_by` varchar(255) DEFAULT NULL,
                            `updated_at` datetime(6) DEFAULT NULL,
                            `updated_by` varchar(255) DEFAULT NULL,
                            `date` datetime(6) DEFAULT NULL,
                            `tax` varchar(255) DEFAULT NULL,
                            `order_id` bigint(20) DEFAULT NULL,
                            `user_id` bigint(20) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK_e718q5klx5pempy28p2nx88a6` (`order_id`),
                            KEY `FKbwr4d4vyqf2bkoetxtt8j9dx7` (`user_id`),
                            CONSTRAINT `FK4ko3y00tkkk2ya3p6wnefjj2f` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                            CONSTRAINT `FKbwr4d4vyqf2bkoetxtt8j9dx7` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `order_detail` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `created_at` datetime(6) DEFAULT NULL,
                                `created_by` varchar(255) DEFAULT NULL,
                                `updated_at` datetime(6) DEFAULT NULL,
                                `updated_by` varchar(255) DEFAULT NULL,
                                `price` double DEFAULT NULL,
                                `quantity` int(11) DEFAULT NULL,
                                `order_id` bigint(20) DEFAULT NULL,
                                `product_id` varchar(255) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKrws2q0si6oyd6il8gqe2aennc` (`order_id`),
                                KEY `FKc7q42e9tu0hslx6w4wxgomhvn` (`product_id`),
                                CONSTRAINT `FKc7q42e9tu0hslx6w4wxgomhvn` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                                CONSTRAINT `FKrws2q0si6oyd6il8gqe2aennc` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES (1,'2024-11-25 09:35:29.538738','admin@gmail.com',NULL,NULL,2999.99,16,1,'a2735f61-ab0b-11ef-85bb-005056c00001'),(2,'2024-11-25 09:37:49.871286','admin@gmail.com',NULL,NULL,1499.99,1,2,'a27324a1-ab0b-11ef-85bb-005056c00001'),(3,'2024-11-25 10:04:07.462344','admin@gmail.com',NULL,NULL,2121,3,4,'7913904f-d4cb-4de8-9236-44525190d973'),(4,'2024-11-25 10:04:07.471265','admin@gmail.com',NULL,NULL,33.23,1,4,'a27324a1-ab0b-11ef-85bb-005056c00001'),(5,'2024-11-25 10:04:07.475265','admin@gmail.com',NULL,NULL,22.3,1,4,'a273470e-ab0b-11ef-85bb-005056c00001'),(6,'2024-12-22 09:54:59.454646','admin@gmail.com',NULL,NULL,125000000,1,5,'a2734e82-ab0b-11ef-85bb-005056c00001'),(7,'2024-12-22 09:56:27.842324','admin@gmail.com',NULL,NULL,125000000,1,6,'a2734e82-ab0b-11ef-85bb-005056c00001'),(8,'2024-12-22 14:50:53.352401','admin@gmail.com',NULL,NULL,23000000,1,7,'a273555f-ab0b-11ef-85bb-005056c00001'),(9,'2024-12-22 14:50:53.356400','admin@gmail.com',NULL,NULL,12500000,1,7,'a2734e82-ab0b-11ef-85bb-005056c00001');
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `created_at` datetime(6) DEFAULT NULL,
                          `created_by` varchar(255) DEFAULT NULL,
                          `updated_at` datetime(6) DEFAULT NULL,
                          `updated_by` varchar(255) DEFAULT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          `payment_method` enum('CASH','ONLINE') DEFAULT NULL,
                          `phone` varchar(255) DEFAULT NULL,
                          `shipping_address` varchar(255) DEFAULT NULL,
                          `status` enum('PENDING','UNPAID','PAID','SHIPPING','COMPLETED','CANCELED') DEFAULT NULL,
                          `total_money` double DEFAULT NULL,
                          `user_id` bigint(20) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
                          CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2024-11-25 09:35:29.444187','admin@gmail.com','2024-11-25 09:35:29.663291','admin@gmail.com','I\'M SUPER USER','ONLINE','1111','áđâsd','PENDING',47999.84,1),(2,'2024-11-25 09:37:49.846198','admin@gmail.com','2024-11-25 10:42:29.625951','admin@gmail.com','','CASH','','','COMPLETED',1499.99,1),(4,'2024-11-25 10:04:07.418686','admin@gmail.com','2024-11-25 10:04:07.509272','admin@gmail.com','tien','ONLINE','98q74q','tp ho chi minh muon nam',NULL,223332,3),(5,'2024-12-22 09:54:59.316698','admin@gmail.com','2024-12-22 09:54:59.495649','admin@gmail.com','I\'M SUPER USER','CASH','0817138594','Duong so 22\nHiep Binh Chanh','PENDING',125000000,1),(6,'2024-12-22 09:56:27.825320','admin@gmail.com','2024-12-22 09:56:27.846327','admin@gmail.com','I\'M SUPER USER','CASH','0817138594','Duong so 22\nHiep Binh Chanh','PENDING',125000000,1),(7,'2024-12-22 14:50:53.309854','admin@gmail.com','2024-12-22 14:50:53.392400','admin@gmail.com','I\'M SUPER USER','CASH','0817138594','Duong so 22\nHiep Binh Chanh','PENDING',35500000,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `payments` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `created_at` datetime(6) DEFAULT NULL,
                            `created_by` varchar(255) DEFAULT NULL,
                            `updated_at` datetime(6) DEFAULT NULL,
                            `updated_by` varchar(255) DEFAULT NULL,
                            `amount_paid` double DEFAULT NULL,
                            `order_id` bigint(20) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK81gagumt0r8y3rmudcgpbk42l` (`order_id`),
                            CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,'2024-11-25 09:35:29.494183','admin@gmail.com',NULL,NULL,0,1),(2,'2024-11-25 09:37:49.853956','admin@gmail.com',NULL,NULL,0,2),(4,'2024-11-25 10:04:07.423685','admin@gmail.com',NULL,NULL,1,4),(5,'2024-12-22 09:54:59.412096','admin@gmail.com',NULL,NULL,0,5),(6,'2024-12-22 09:56:27.828321','admin@gmail.com',NULL,NULL,0,6),(7,'2024-12-22 14:50:53.321856','admin@gmail.com',NULL,NULL,0,7);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_role`
--

DROP TABLE IF EXISTS `permission_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `permission_role` (
                                   `role_id` bigint(20) NOT NULL,
                                   `permission_id` bigint(20) NOT NULL,
                                   KEY `FK6mg4g9rc8u87l0yavf8kjut05` (`permission_id`),
                                   KEY `FK3vhflqw0lwbwn49xqoivrtugt` (`role_id`),
                                   CONSTRAINT `FK3vhflqw0lwbwn49xqoivrtugt` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
                                   CONSTRAINT `FK6mg4g9rc8u87l0yavf8kjut05` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE;
/*!40000 ALTER TABLE `permission_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `permissions` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `created_at` datetime(6) DEFAULT NULL,
                               `created_by` varchar(255) DEFAULT NULL,
                               `updated_at` datetime(6) DEFAULT NULL,
                               `updated_by` varchar(255) DEFAULT NULL,
                               `api_path` varchar(255) DEFAULT NULL,
                               `method` varchar(255) DEFAULT NULL,
                               `module` varchar(255) DEFAULT NULL,
                               `name` varchar(255) DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_instances`
--

DROP TABLE IF EXISTS `product_instances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `product_instances` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `created_at` datetime(6) DEFAULT NULL,
                                     `created_by` varchar(255) DEFAULT NULL,
                                     `updated_at` datetime(6) DEFAULT NULL,
                                     `updated_by` varchar(255) DEFAULT NULL,
                                     `serial` varchar(255) DEFAULT NULL,
                                     `import_order_id` bigint(20) DEFAULT NULL,
                                     `order_id` bigint(20) DEFAULT NULL,
                                     `product_id` varchar(255) DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     KEY `FK7aayf0ky80rgcuouv1qivf3ng` (`import_order_id`),
                                     KEY `FKqyu0r2rrm6rd5upruvyvypm9h` (`order_id`),
                                     KEY `FK72d9n5tidov45n7kidh9a0cfj` (`product_id`),
                                     CONSTRAINT `FK72d9n5tidov45n7kidh9a0cfj` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                                     CONSTRAINT `FK7aayf0ky80rgcuouv1qivf3ng` FOREIGN KEY (`import_order_id`) REFERENCES `import_orders` (`id`),
                                     CONSTRAINT `FKqyu0r2rrm6rd5upruvyvypm9h` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_instances`
--

LOCK TABLES `product_instances` WRITE;
/*!40000 ALTER TABLE `product_instances` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_instances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `products` (
                            `id` varchar(255) NOT NULL,
                            `color` varchar(255) DEFAULT NULL,
                            `cpu` varchar(255) DEFAULT NULL,
                            `created_at` datetime(6) DEFAULT NULL,
                            `created_by` varchar(255) DEFAULT NULL,
                            `description` mediumtext,
                            `gpu` varchar(255) DEFAULT NULL,
                            `memory` varchar(255) DEFAULT NULL,
                            `memory_type` enum('SSD','HDD','eMMC') DEFAULT NULL,
                            `model` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `os` varchar(255) DEFAULT NULL,
                            `port` varchar(255) DEFAULT NULL,
                            `price` double DEFAULT NULL,
                            `quantity` int(11) DEFAULT NULL,
                            `ram` int(11) DEFAULT NULL,
                            `screen` varchar(255) DEFAULT NULL,
                            `slider` varchar(255) DEFAULT NULL,
                            `sold` int(11) DEFAULT NULL,
                            `status` bit(1) DEFAULT NULL,
                            `tag` varchar(255) DEFAULT NULL,
                            `thumbnail` varchar(255) DEFAULT NULL,
                            `updated_at` datetime(6) DEFAULT NULL,
                            `updated_by` varchar(255) DEFAULT NULL,
                            `weight` double DEFAULT NULL,
                            `brand_id` bigint(20) DEFAULT NULL,
                            `category_id` bigint(20) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FKl2cyj2st6mjygl2pgwd057ivu` (`brand_id`),
                            KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
                            CONSTRAINT `FKl2cyj2st6mjygl2pgwd057ivu` FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`),
                            CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('7913904f-d4cb-4de8-9236-44525190d973','black','Intel Core i5','2024-11-25 09:58:08.720583','staff1','','alen','512 GB','SSD','ExpertBook','Dell XPS 112',NULL,'usb',25225000,0,8,'15.6 full hd','1734876294595-1732292420288-Acer-Swift-3.jpg 1734876298030-1732292437437-1732292426472-AcerNitro5.jpg 1734876301232-1732292420288-AcerNitro5.jpg',3,NULL,'programming','1734876307768-1732323981846-AsusVivoBook15.jpg','2024-12-22 14:05:11.194020','admin@gmail.com',1.56,1,2),('a27324a1-ab0b-11ef-85bb-005056c00001','Silver','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'Intel Iris Xe','16GB','SSD','XPS 13 9310','Dell XPS 13','Windows 10','2 x USB-C',15000000,98,16,'13.4 inch',NULL,52,_binary '','programming,learning','Dell-XPS-13.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.2,1,1),('a273470e-ab0b-11ef-85bb-005056c00001','Gold','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'Intel UHD','16GB','SSD','Spectre x360 14','HP Spectre x360','Windows 10','2 x USB-C',12000000,79,16,'13.3 inch',NULL,31,_binary '','learning','HPSpectrex360.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.3,2,2),('a2734ac6-ab0b-11ef-85bb-005056c00001','Black','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'Intel Iris Xe','16GB','SSD','ThinkPad X1 Carbon Gen 9','Lenovo ThinkPad X1','Windows 10','2 x USB-C',13000000,120,16,'14 inch',NULL,40,_binary '','learning,design','LenovoThinkPadX1.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.4,3,3),('a2734cbc-ab0b-11ef-85bb-005056c00001','Silver','AMD Ryzen 7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'AMD Radeon Vega 8','8GB','SSD','Swift 3 SF314','Acer Swift 3','Windows 10','1 x USB-C',13000000,150,8,'14 inch',NULL,70,_binary '','learning,entertain','Acer-Swift-3.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.4,5,4),('a2734e82-ab0b-11ef-85bb-005056c00001','Blue','Intel Core i5','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA GeForce MX250','8GB','SSD','ZenBook 14 UX434','Asus ZenBook 14','Windows 10','1 x USB-C',12500000,197,8,'14 inch',NULL,103,_binary '','design,entertain','AsusZenBook14.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.3,4,4),('a2735036-ab0b-11ef-85bb-005056c00001','Black','Intel Core i5','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA GeForce GTX 1650','8GB','SSD','GF63 Thin 10SCXR','MSI GF63 Thin','Windows 10','1 x USB-C',17000000,80,8,'15.6 inch',NULL,50,_binary '','entertain','MSIGF63Thin.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.9,7,3),('a27351ed-ab0b-11ef-85bb-005056c00001','Silver','Intel Core i5','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'Intel UHD','8GB','SSD','Pavilion x360 14','HP Pavilion x360','Windows 10','1 x USB-C',24000000,90,8,'14 inch',NULL,60,_binary '','entertain,office','HPPavilionx360.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.6,2,2),('a2735399-ab0b-11ef-85bb-005056c00001','Grey','Intel Core i3','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'Intel UHD','4GB','SSD','VivoBook 15 X512','Asus VivoBook 15','Windows 10','1 x USB-C',18000000,200,4,'15.6 inch',NULL,120,_binary '','office','AsusVivoBook15.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',1.8,4,2),('a273555f-ab0b-11ef-85bb-005056c00001','Black','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA GeForce GTX 1650','16GB','SSD','G3 15 3500','Dell G3 15','Windows 10','1 x USB-C',23000000,74,16,'15.6 inch',NULL,41,_binary '','office','DellG315.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2,1,1),('a273570d-ab0b-11ef-85bb-005056c00001','Red','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA GeForce RTX 3060','16GB','SSD','Nitro 5 AN515','Acer Nitro 5','Windows 10','1 x USB-C',18500000,60,16,'15.6 inch',NULL,30,_binary '','office,entertain','AcerNitro5.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2.3,5,4),('a27358c5-ab0b-11ef-85bb-005056c00001','Gray','Intel Xeon','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA Quadro T1000','32GB','SSD','ProArt StudioBook 15','Asus ProArt StudioBook','Windows 10','1 x USB-C',26000000,50,32,'15.6 inch',NULL,20,_binary '','entertain','AsusProArtStudioBook.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2.5,4,1),('a2735a60-ab0b-11ef-85bb-005056c00001','Black','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA Quadro RTX 3000','32GB','SSD','WS75 10TK','MSI WS75','Windows 10','1 x USB-C',28000000,40,32,'17.3 inch',NULL,15,_binary '','entertain,programming','MSIWS75.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2.7,7,3),('a2735c0b-ab0b-11ef-85bb-005056c00001','Space Gray','Intel Core i9','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'AMD Radeon Pro 5500M','16GB','SSD','MacBook Pro 16','Apple MacBook Pro 16','macOS','2 x USB-C',40000000,30,16,'16 inch',NULL,10,_binary '','programming','AppleMacBookPro16.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2,6,4),('a2735da9-ab0b-11ef-85bb-005056c00001','Black','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA Quadro P1000','32GB','SSD','ThinkPad P52','Lenovo ThinkPad P52','Windows 10','1 x USB-C',13500000,40,32,'15.6 inch',NULL,20,_binary '','office','LenovoThinkPadP52.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2.2,3,3),('a2735f61-ab0b-11ef-85bb-005056c00001','White','Intel Core i7','2024-11-25 15:59:53.000000','admin@gmail.com',NULL,'NVIDIA Quadro RTX 5000','32GB','SSD','ConceptD 7','Acer ConceptD 7','Windows 10','2 x USB-C',19000000,9,32,'15.6 inch',NULL,31,_binary '','design','AcerConceptD7.jpg','2024-11-25 15:59:53.000000','admin@gmail.com',2.4,5,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `created_at` datetime(6) DEFAULT NULL,
                         `created_by` varchar(255) DEFAULT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         `updated_by` varchar(255) DEFAULT NULL,
                         `code` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'2024-11-25 07:33:41.111326','',NULL,NULL,'SUPER_ADMIN','Admin role'),(2,'2024-11-25 07:33:41.187877','',NULL,NULL,'CUSTOMER','Customer role'),(3,'2024-11-25 09:42:51.263550','admin@gmail.com','2024-11-25 09:48:35.406261','user@gmail.com','staff','Nhân viên');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `suppliers` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `created_at` datetime(6) DEFAULT NULL,
                             `created_by` varchar(255) DEFAULT NULL,
                             `updated_at` datetime(6) DEFAULT NULL,
                             `updated_by` varchar(255) DEFAULT NULL,
                             `address` varchar(255) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `name` varchar(255) DEFAULT NULL,
                             `phone` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplies`
--

DROP TABLE IF EXISTS `supplies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `supplies` (
                            `supplier_id` bigint(20) NOT NULL,
                            `product_id` varchar(255) NOT NULL,
                            KEY `FK5bbi2li6xfauotdtdt5oenwd3` (`product_id`),
                            KEY `FK429kctm56wuny1wk9s325e19l` (`supplier_id`),
                            CONSTRAINT `FK429kctm56wuny1wk9s325e19l` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
                            CONSTRAINT `FK5bbi2li6xfauotdtdt5oenwd3` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplies`
--

LOCK TABLES `supplies` WRITE;
/*!40000 ALTER TABLE `supplies` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `created_at` datetime(6) DEFAULT NULL,
                         `created_by` varchar(255) DEFAULT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         `updated_by` varchar(255) DEFAULT NULL,
                         `active` bit(1) DEFAULT NULL,
                         `address` varchar(255) DEFAULT NULL,
                         `avatar` varchar(255) DEFAULT NULL,
                         `birthday` datetime(6) DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `full_name` varchar(255) DEFAULT NULL,
                         `gender` enum('MALE','FEMALE','OTHER') DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         `phone` varchar(255) DEFAULT NULL,
                         `refresh_token` text,
                         `shopping_address` varchar(255) DEFAULT NULL,
                         `role_id` bigint(20) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
                         CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2024-11-25 07:33:41.341601','','2024-12-16 05:15:15.077929','admin@gmail.com',_binary '',NULL,NULL,NULL,'admin@gmail.com','I\'M SUPER USER',NULL,'$2a$10$iT0QnJx5s61q6Js5P2K0guHpCv7P.IUIx.pB//xTrP1OXagd/H1lW',NULL,'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJleHAiOjE3NDI5NjYxMTUsImlhdCI6MTczNDMyNjExNSwidXNlciI6eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBnbWFpbC5jb20iLCJmdWxsTmFtZSI6IkknTSBTVVBFUiBVU0VSIn19.nJq46MK1FKSXmEzSAk4p3vy7rkm9UizTN1LscEOfp4LhudZQ_-b8KkfJShghdel8LU0D-ngE4f98mP7uTOWuGA',NULL,1),(2,'2024-11-25 09:40:22.711628','anonymousUser','2024-12-01 18:16:08.951128','customer1@gmail.com',_binary '','tphcm','default-avatar.jpg','2000-11-19 16:16:59.000980','customer1@gmail.com','nguyen minh tien','MALE','$2a$10$h7M7giPQDgT.8TJbAqCHVe2t5Xbo2WZYofOX5rvt7plJnw1j5MgKy','0030030030','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjdXN0b21lcjFAZ21haWwuY29tIiwiZXhwIjoxNzQxNzE2OTY4LCJpYXQiOjE3MzMwNzY5NjgsInVzZXIiOnsiaWQiOjIsImVtYWlsIjoiY3VzdG9tZXIxQGdtYWlsLmNvbSIsImZ1bGxOYW1lIjoibmd1eWVuIG1pbmggdGllbiJ9fQ.EtY6oxOrZNYFqEZtEJHESoivEL-TIs1aMCC-BTiuxd71Jn2F8JCFC1tRBr59kF9HiTm1t8UbbwVixf8LwMAd4A','thu duc',2),(3,'2024-11-25 09:50:44.271598','staff1','2024-11-25 09:52:21.315561','admin@gmail.com',_binary '\0','hn','default-avatar.jpg','2000-11-19 00:00:00.000000','customer10@gmail.com','nguyen minh tien','MALE','$2a$10$w4bIP43d07tBhgxUUp6OjeLF4/4Oi3wyvw3lRAR5fLrQzS.k2GPja','0030030030',NULL,'thu duc',3);
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

-- Dump completed on 2025-02-25 21:34:44
