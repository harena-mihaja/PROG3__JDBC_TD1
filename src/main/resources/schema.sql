CREATE TABLE Product (
    id integer primary key,
    name varchar(255) not null,
    price numeric(10,2) not null,
    creation_datetime timestamp default current_timestamp not null
);

CREATE TABLE Product_category (
    id integer primary key,
    name varchar(255) not null,
    product_id integer not null,
    FOREIGN KEY (product_id) references product(id)
);
