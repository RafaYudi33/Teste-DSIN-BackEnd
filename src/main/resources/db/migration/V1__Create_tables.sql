
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `role` varchar(31) NOT NULL,
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `username` varchar(255) NOT NULL,
                         PRIMARY KEY (`id`)
);


DROP TABLE IF EXISTS `beauty_service`;
CREATE TABLE `beauty_service` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `description` varchar(255) DEFAULT NULL,
                                  `duration_minutes` int DEFAULT NULL,
                                  `name` varchar(255) DEFAULT NULL,
                                  `price` decimal(10,2) DEFAULT NULL,
                                  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `date_time` datetime(6) DEFAULT NULL,
                               `status` enum('CANCELADO','CONFIRMADO','PENDENTE') DEFAULT NULL,
                               `client_id` bigint NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKlqi1o7adfcj20xt05iinir8a4` (`client_id`),
                               CONSTRAINT `FKlqi1o7adfcj20xt05iinir8a4` FOREIGN KEY (`client_id`) REFERENCES `users` (`id`)
);


DROP TABLE IF EXISTS `appointment_service`;
CREATE TABLE `appointment_service` (
                                       `appointment_id` bigint NOT NULL,
                                       `service_id` bigint NOT NULL,
                                       KEY `FKkkf5aq54hvklhu5v4auyf3mgx` (`service_id`),
                                       KEY `FKcjud04hjkhiop1dwm0tial32k` (`appointment_id`),
                                       CONSTRAINT `FKcjud04hjkhiop1dwm0tial32k` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
                                       CONSTRAINT `FKkkf5aq54hvklhu5v4auyf3mgx` FOREIGN KEY (`service_id`) REFERENCES `beauty_service` (`id`)
);
