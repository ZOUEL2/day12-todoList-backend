create table if not exists todo (
                                     id BIGINT auto_increment primary key,
                                     text varchar(255) not null,
    description text ,
    done boolean default false,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
    );