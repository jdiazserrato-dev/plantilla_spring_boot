INSERT INTO health_check (status) VALUES ('OK') ON DUPLICATE KEY UPDATE status = 'OK';
