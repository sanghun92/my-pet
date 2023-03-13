CREATE TABLE IF NOT EXISTS members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    certification_code BINARY(16) NOT NULL,
    certificated_at DATETIME(6),
    birth_day DATE,
    phone_number VARCHAR(13),
    is_deleted BOOLEAN NOT NULL,
    deleted_at DATETIME(6),
    created_at DATETIME(6),
    modified_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE members
    ADD CONSTRAINT uk_member_email UNIQUE (email);
