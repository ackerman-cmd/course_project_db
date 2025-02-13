-- Удаление триггеров и функций, если они существуют
DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM pg_trigger WHERE tgname = 'trg_check_balloon_availability')
        THEN
            IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'bookings') THEN
                EXECUTE 'DROP TRIGGER trg_check_balloon_availability ON bookings';
            END IF;
        END IF;

        IF EXISTS (SELECT 1 FROM pg_proc WHERE proname = 'check_balloon_availability') THEN
            DROP FUNCTION check_balloon_availability();
        END IF;
    END $$;


-- Удаление представлений, если они существуют
DROP VIEW IF EXISTS customer_bookings;

-- Удаление таблиц в обратном порядке зависимостей
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS balloons;
DROP TABLE IF EXISTS pilots;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS backup;

-- Создание таблиц

-- Пользователи
CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL CHECK (role IN ('admin', 'editor', 'user'))
);

-- Клиенты
CREATE TABLE customers (
                           customer_id SERIAL PRIMARY KEY,
                           user_id INT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                           full_name VARCHAR(100) NOT NULL
);

-- Пилоты
CREATE TABLE pilots (
                        pilot_id SERIAL PRIMARY KEY,
                        full_name VARCHAR(100) NOT NULL,
                        license_number VARCHAR(50) NOT NULL UNIQUE
);

-- Воздушные шары
CREATE TABLE balloons (
                          balloon_id SERIAL PRIMARY KEY,
                          model VARCHAR(50) NOT NULL,
                          capacity INT NOT NULL CHECK (capacity > 0),
                          status VARCHAR(20) NOT NULL CHECK (status IN ('available', 'in_service', 'under_repair'))
);

-- Маршруты
CREATE TABLE routes (
                        route_id SERIAL PRIMARY KEY,
                        start_location VARCHAR(100) NOT NULL,
                        end_location VARCHAR(100) NOT NULL
);

-- Бронирования
CREATE TABLE bookings (
                          booking_id SERIAL PRIMARY KEY,
                          customer_id INT NOT NULL REFERENCES customers(customer_id) ON DELETE CASCADE,
                          pilot_id INT NOT NULL REFERENCES pilots(pilot_id),
                          balloon_id INT NOT NULL REFERENCES balloons(balloon_id),
                          route_id INT NOT NULL REFERENCES routes(route_id),
                          flight_date TIMESTAMP NOT NULL
);

CREATE TABLE backup (
                        id SERIAL PRIMARY KEY,
                        backup_name VARCHAR(255) NOT NULL,
                        backup_time TIMESTAMP NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        details TEXT
);


-- Создание индексов
CREATE INDEX idx_booking_date ON bookings(flight_date);

-- Создание функции проверки доступности воздушного шара
CREATE OR REPLACE FUNCTION check_balloon_availability()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM bookings
        WHERE balloon_id = NEW.balloon_id
          AND flight_date = NEW.flight_date
    ) THEN
        RAISE EXCEPTION 'Balloon is not available for the selected date.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера для проверки доступности воздушного шара
CREATE TRIGGER trg_check_balloon_availability
    BEFORE INSERT OR UPDATE ON bookings
    FOR EACH ROW
EXECUTE FUNCTION check_balloon_availability();

-- Создание индекса для ускорения поиска по пилоту и дате
CREATE INDEX idx_booking_pilot_date ON bookings(pilot_id, flight_date);

-- Создание функции проверки доступности пилота
CREATE OR REPLACE FUNCTION check_pilot_availability()
    RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM bookings
        WHERE pilot_id = NEW.pilot_id
          AND flight_date = NEW.flight_date
    ) THEN
        RAISE EXCEPTION 'Pilot is not available for the selected date.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера для проверки доступности пилота
CREATE TRIGGER trg_check_pilot_availability
    BEFORE INSERT OR UPDATE ON bookings
    FOR EACH ROW
EXECUTE FUNCTION check_pilot_availability();

-- Создание представления для аналитики
CREATE VIEW customer_bookings AS
SELECT
    c.customer_id,
    c.full_name,
    COUNT(b.booking_id) AS total_bookings
FROM customers c
         LEFT JOIN bookings b ON c.customer_id = b.customer_id
GROUP BY c.customer_id;
