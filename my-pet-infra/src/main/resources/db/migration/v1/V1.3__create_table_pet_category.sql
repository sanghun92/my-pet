CREATE TABLE IF NOT EXISTS pet_category (
    id bigint NOT NULL AUTO_INCREMENT,
    type varchar(6) NOT NULL,
    name varchar(30) NOT NULL,
    parent_id bigint,
    created_at datetime(6),
    modified_at datetime(6),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE pet_category
    ADD CONSTRAINT fk_pet_category_parent
    FOREIGN KEY (parent_id)
    REFERENCES pet_category (id);
