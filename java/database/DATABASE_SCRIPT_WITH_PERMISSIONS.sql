

DROP TABLE IF EXISTS item; 
DROP TABLE IF EXISTS room;
DROP TABLE IF EXISTS floors;
DROP TABLE IF EXISTS house;

 

CREATE TABLE house (
        user_id INTEGER NOT NULL,
        home_id serial NOT NULL,
        foundation_size INTEGER,
        region varchar(100),
        number_of_floors INTEGER,
        house_name varchar(100),
        CONSTRAINT pk_house_home_id PRIMARY KEY(home_id),
        CONSTRAINT fk_house_user_id FOREIGN KEY(user_id) REFERENCES users (user_id)
);
CREATE TABLE floors (
        floor_id serial NOT NULL,
        home_id INTEGER NOT NULL,
        floor_level INTEGER,
        number_of_rooms INTEGER,
        CONSTRAINT pk_floors_floor_id PRIMARY KEY(floor_id),
        CONSTRAINT fk_floors_home_id FOREIGN KEY(home_id) REFERENCES house (home_id)
        ON DELETE CASCADE
);
CREATE TABLE room (
        room_id serial NOT NULL,
        floor_id INTEGER NOT NULL,
        flooring_type varchar(100),
        room_length INTEGER,
        room_width INTEGER,
        room_name varchar(100),
        room_cost INTEGER,
        CONSTRAINT pk_room_room_id PRIMARY KEY(room_id),
        CONSTRAINT fk_room_floor_id FOREIGN KEY(floor_id) REFERENCES floors (floor_id)
        ON DELETE CASCADE
);
CREATE TABLE item (
        item_id serial NOT NULL,
        room_id INTEGER NOT NULL,
        item_name varchar(100),
        item_price INTEGER,
        CONSTRAINT pk_item_item_id PRIMARY KEY(item_id),
        CONSTRAINT fk_item_room_id FOREIGN KEY(room_id) REFERENCES room (room_id)
        ON DELETE CASCADE
);

GRANT SELECT, INSERT, UPDATE, DELETE ON floors TO final_capstone_appuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON item TO final_capstone_appuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON room TO final_capstone_appuser;
GRANT SELECT, INSERT, UPDATE, DELETE ON house TO final_capstone_appuser;


GRANT USAGE, SELECT ON floors_floor_id_seq TO final_capstone_appuser;
GRANT USAGE, SELECT ON seq_user_id TO final_capstone_appuser;
GRANT USAGE, SELECT ON item_item_id_seq TO final_capstone_appuser;
GRANT USAGE, SELECT ON room_room_id_seq TO final_capstone_appuser;
GRANT USAGE, SELECT ON house_home_id_seq TO final_capstone_appuser;

