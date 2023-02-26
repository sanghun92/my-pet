CREATE TABLE IF NOT EXISTS image_meta_data (
    id bigint NOT NULL AUTO_INCREMENT,
    image_key binary(16) NOT NULL,
    name varchar(50) NOT NULL,
    content_type varchar(20) NOT NULL,
    size bigint NOT NULL,
    created_at datetime(6),
    modified_at datetime(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE image_meta_data
    ADD CONSTRAINT uk_image_key UNIQUE (image_key);
