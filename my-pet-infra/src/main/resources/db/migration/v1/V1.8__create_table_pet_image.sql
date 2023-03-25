CREATE TABLE IF NOT EXISTS pet_image (
    id bigint NOT NULL AUTO_INCREMENT,
    pet_id bigint NOT NULL,
    path varchar(255) NOT NULL,
    name varchar(50) NOT NULL,
    content_type varchar(20) NOT NULL,
    size bigint NOT NULL,
    created_at datetime(6),
    modified_at datetime(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE pet_image
    ADD CONSTRAINT uk_image_meta_data_path UNIQUE (path);
