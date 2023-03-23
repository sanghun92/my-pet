CREATE TABLE IF NOT EXISTS email_verification
(
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255) NOT NULL,
    code        BINARY(16)   NOT NULL,
    verified_at DATETIME(6),
    created_at  DATETIME(6),
    modified_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE = InnoDB;

ALTER TABLE email_verification
    ADD CONSTRAINT uk_member_email UNIQUE (email);
