create table file_t
(
    file_id serial primary key not null unique ,
    file_name varchar not null unique ,
    file_status varchar
)