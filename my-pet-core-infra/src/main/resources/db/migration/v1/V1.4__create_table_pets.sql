CREATE TABLE pets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    name VARCHAR(20) NOT NULL,
    category_id BIGINT,
    birth_day DATE,
    gender VARCHAR(8) NOT NULL,
    body_weight INTEGER NOT NULL,
    body_type VARCHAR(8) NOT NULL,
    pet_image_id BIGINT,
    created_at DATETIME(6),
    modified_at DATETIME(6),
    is_deleted BOOLEAN NOT NULL,
    deleted_at DATETIME(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE pets
    ADD CONSTRAINT uk_pets_name UNIQUE (name);

ALTER TABLE pets
    ADD CONSTRAINT fk_pets_pet_category
        FOREIGN KEY (category_id)
            REFERENCES pet_category (id);

ALTER TABLE pets
    ADD CONSTRAINT fk_pets_image
        FOREIGN KEY (pet_image_id)
            REFERENCES image_meta_data (id);
