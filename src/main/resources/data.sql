CREATE TABLE IF NOT EXISTS job_type (
    job_type_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS phase (
    phase_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Valores fijos de JobType
INSERT INTO job_type (name) VALUES ('Remote');
INSERT INTO job_type (name) VALUES ('Hybrid');
INSERT INTO job_type (name) VALUES ('On-Site');

-- Valores fijos de Phase
INSERT INTO phase (name) VALUES ('Exploring');
INSERT INTO phase (name) VALUES ('Submitted');
INSERT INTO phase (name) VALUES ('Reviewing');
INSERT INTO phase (name) VALUES ('Interview');
INSERT INTO phase (name) VALUES ('Tech interview');
INSERT INTO phase (name) VALUES ('Final interview');
INSERT INTO phase (name) VALUES ('Offer');
INSERT INTO phase (name) VALUES ('Hired');