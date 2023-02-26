CREATE TABLE pets (
    id bigint NOT NULL AUTO_INCREMENT,
    member_id bigint NOT NULL,
    name varchar(20) NOT NULL,
    category_id bigint,
    birth_day date,
    gender varchar(8) NOT NULL,
    body_weight integer NOT NULL,
    body_type varchar(8) NOT NULL,
    pet_image_id bigint,
    created_at datetime(6),
    modified_at datetime(6),
    is_deleted boolean,
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
