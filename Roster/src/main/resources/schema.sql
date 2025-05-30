CREATE TABLE roster_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50),
    last_name VARCHAR(50)
);

CREATE TABLE roster (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    from_date_time TIMESTAMP,
    to_date_time TIMESTAMP,
    roster_user_id BIGINT,
    CONSTRAINT fk_roster_user
                FOREIGN KEY (roster_user_id)
                REFERENCES roster_user(id)
                ON DELETE CASCADE
);