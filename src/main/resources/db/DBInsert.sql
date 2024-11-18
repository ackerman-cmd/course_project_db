-- Наполнение таблиц

-- Пользователи
INSERT INTO users (username, password_hash, role)
VALUES
    ('admin_user', 'hashed_password_1', 'admin'),
    ('editor_user', 'hashed_password_2', 'editor'),
    ('test_user', 'hashed_password_3', 'user');

-- Клиенты
INSERT INTO customers (user_id, full_name)
VALUES
    (3, 'John Doe'),
    (3, 'Jane Smith');

-- Пилоты
INSERT INTO pilots (full_name, license_number)
VALUES
    ('James Pilot', 'LIC123456'),
    ('Anna Sky', 'LIC987654');

-- Воздушные шары
INSERT INTO balloons (model, capacity, status)
VALUES
    ('Model A', 4, 'available'),
    ('Model B', 6, 'in_service'),
    ('Model C', 2, 'under_repair');

-- Маршруты
INSERT INTO routes (start_location, end_location)
VALUES
    ('City Park', 'Mountain View'),
    ('Lake Shore', 'Green Valley'),
    ('Downtown', 'Countryside');

-- Бронирования
INSERT INTO bookings (customer_id, pilot_id, balloon_id, route_id, flight_date, status)
VALUES
    (1, 1, 1, 1, '2024-11-20', 'confirmed'),
    (2, 2, 2, 2, '2024-11-21', 'pending'),
    (1, 1, 3, 3, '2024-11-22', 'cancelled');

-- Платежи
INSERT INTO payments (booking_id, amount, payment_date)
VALUES
    (1, 300.00, '2024-11-15'),
    (2, 450.00, '2024-11-16'),
    (3, 150.00, '2024-11-17');
