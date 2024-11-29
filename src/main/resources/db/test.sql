
-- Таблица полетов
drop table if exists flights

CREATE TABLE flights (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description TEXT NOT NULL,
                         date DATE NOT NULL,
                         price NUMERIC(10, 2) NOT NULL
);

insert into flights (name, description, date, price) VALUES ('abc', 'ad', '12-03-03'::date, 120)