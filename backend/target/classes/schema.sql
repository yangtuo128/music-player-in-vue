-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS music_player DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE music_player;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    phone VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    username VARCHAR(50),
    email VARCHAR(100),
    gender VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert some sample data
INSERT INTO users (phone, password, username, email, gender) VALUES
('13800138000', '123456', 'admin', 'admin@example.com', 'male'),
('13900139000', '654321', 'user1', 'user1@example.com', 'female');