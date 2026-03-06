create table carts
(
    id           binary(16) default (uuid_to_bin(UUID())) not null
        primary key,
    date_created datetime   default now()                 not null
);