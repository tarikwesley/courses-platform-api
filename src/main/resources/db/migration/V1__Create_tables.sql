CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `username` VARCHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `role` VARCHAR(50) NOT NULL,
    `created_at` DATE NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `courses` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `code` VARCHAR(50) NOT NULL,
    `instructor` VARCHAR(100) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `status` VARCHAR(50) NOT NULL,
    `created_at` DATE NOT NULL,
    `inactivated_at` DATE,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `registrations` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `course_id` BIGINT NOT NULL,
    `registration_at` DATE NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`id`)
);

CREATE TABLE IF NOT EXISTS `reviews` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `comment` TEXT NOT NULL,
    `rating` INT NOT NULL,
    `user_id` BIGINT NOT NULL,
    `course_id` BIGINT NOT NULL,
    `created_at` DATE NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`id`)
);