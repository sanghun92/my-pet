CREATE TABLE IF NOT EXISTS members (
    id bigint NOT NULL AUTO_INCREMENT,
    email varchar(50) NOT NULL,
    password varchar(255) NOT NULL,
    nickname varchar(30) NOT NULL,
    certification_code binary(16) NOT NULL,
    certificated_at datetime(6),
    created_at datetime(6),
    modified_at datetime(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE members
    ADD CONSTRAINT uk_member_email UNIQUE (email);
