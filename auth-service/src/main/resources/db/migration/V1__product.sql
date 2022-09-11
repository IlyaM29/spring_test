CREATE TABLE IF NOT EXISTS products
(
    id SERIAL PRIMARY KEY,
    cost INTEGER ,
    title VARCHAR
);

INSERT INTO products (title, cost) VALUES
    ('Хлеб', 10),
    ('Молоко', 24),
    ('Томаты', 20),
    ('Чай', 12),
    ('Кофе', 17),
    ('Масло', 15),
    ('Шоколад', 25),
    ('Творог', 22),
    ('Яблоки', 27),
    ('Бананы', 21);