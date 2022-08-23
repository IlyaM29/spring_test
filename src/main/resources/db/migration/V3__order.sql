create table orders(
    id bigserial primary key,
    user_id bigint not null references users(id),
    total_price int not null,
    address varchar,
    phone varchar(255),
    created_at timestamp,
    updated_at timestamp
);

create table order_items(
    id bigserial primary key,
    product_id bigint not null references products(id),
    order_id bigint not null references orders(id),
    quantity int not null,
    price_per_product int not null,
    price int not null,
    created_at timestamp,
    updated_at timestamp
);
