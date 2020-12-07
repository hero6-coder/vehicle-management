CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `status` int NOT NULL DEFAULT '1',
  `created_at` date NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE IF NOT EXISTS vehicles (
  `vehicle_id` int NOT NULL AUTO_INCREMENT,
  `engine_number` varchar(255) NOT NULL,
  `registration_date` date NOT NULL,
  `user_id` int,
  PRIMARY KEY (`vehicle_id`),
  UNIQUE KEY `engine_number` (`engine_number`),
  FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

CREATE TABLE IF NOT EXISTS `maintenance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vehicle_id` int NOT NULL,
  `price` decimal(15,2),
  `booking_date` date not null,
  `note` text,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`vehicle_id`) REFERENCES vehicles(`vehicle_id`)
);