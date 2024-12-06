-- Crea las tablas si no existen
CREATE TABLE IF NOT EXISTS job_type (
    job_type_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS application (
    application_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    company VARCHAR(255),
    position VARCHAR(255),
    location VARCHAR(255),
    requirements TEXT,
    job_type_id BIGINT,
    salary INT,
    link VARCHAR(255),
    notes TEXT,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (job_type_id) REFERENCES job_type(job_type_id)
);