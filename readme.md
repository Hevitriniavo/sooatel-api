![](https://img.shields.io/badge/Spring_boot_3-blueviolet?style=for-the-badge)
![](https://img.shields.io/badge/Java_17-yellow?style=for-the-badge)

$\mathcal{{Spring \ java \ 21  } \ \}$

• $\ \ \textcolor{blueviolet}{Gestion  \ d'\ un  \ restaurant    }$  


````sql
DROP DATABASE IF EXISTS sooatel_db;
CREATE DATABASE sooatel_db;

\c  sooatel_db;

CREATE TABLE roles (
   id SERIAL PRIMARY KEY,
   role VARCHAR(2000)
);

CREATE TABLE users (
       id SERIAL PRIMARY KEY,
       username VARCHAR(2000),
       email VARCHAR(2000),
       password VARCHAR(255),
);

CREATE TABLE roles_users (
   id SERIAL PRIMARY KEY,
   role_id INT REFERENCES roles(id) ON DELETE CASCADE,
   user_id INT REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE units (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE ingredients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit_id INT REFERENCES units(id) ON DELETE CASCADE
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category_id INT REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE tables (
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    capacity INT NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE stocks (
    id SERIAL PRIMARY KEY,
    ingredient_id INT REFERENCES ingredients(id) ON DELETE CASCADE,
    quantity DOUBLE PRECISION NOT NULL
);

CREATE TABLE operations (
    id SERIAL PRIMARY KEY,
    stock_id INT REFERENCES stocks(id) ON DELETE SET NULL,
    operation_type VARCHAR(50) NOT NULL,
    operation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);

CREATE TABLE menu_ingredients (
    id SERIAL PRIMARY KEY,
    menu_id INT REFERENCES menus(id) ON DELETE CASCADE,
    ingredient_id INT REFERENCES ingredients(id) ON DELETE CASCADE,
    quantity DOUBLE PRECISION NOT NULL
);

CREATE TABLE purchases (
  id SERIAL PRIMARY KEY,
  ingredient_id INT REFERENCES ingredients(id) ON DELETE CASCADE,
  purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  quantity DOUBLE PRECISION NOT NULL,
  cost DECIMAL(10, 2) NOT NULL,
  description TEXT
);

CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    email VARCHAR(255),
    address TEXT
);

CREATE TABLE floors (
    id SERIAL PRIMARY KEY,
    floor_number INT NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    room_number INT NOT NULL UNIQUE,
    capacity INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    floor_id INT REFERENCES floors(id) ON DELETE SET NULL
);

CREATE TABLE menu_orders (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(id) ON DELETE SET NULL ,
    menu_id INT REFERENCES menus(id) ON DELETE SET NULL,
    room_id INT REFERENCES rooms(id) ON DELETE SET NULL,
    table_id INT REFERENCES tables(id) ON DELETE SET NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    quantity DOUBLE PRECISION NOT NULL,
    cost DECIMAL(10, 2) NOT NULL
    CONSTRAINT chk_room_or_table CHECK (
        (room_id IS NOT NULL AND table_id IS NULL) OR
        (room_id IS NULL AND table_id IS NOT NULL)
    )
);

CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(id) ON DELETE SET NULL,
    room_id INT REFERENCES rooms(id) ON DELETE SET NULL,
    table_id INT REFERENCES tables(id) ON DELETE SET NULL,
    payment_id INT REFERENCES payments(id) ON DELETE SET NULL,
    reservation_start TIMESTAMP NOT NULL,
    reservation_end TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    description TEXT,
    CONSTRAINT chk_room_or_table_reservation CHECK (
        (room_id IS NOT NULL AND table_id IS NULL) OR
        (room_id IS NULL AND table_id IS NOT NULL)
    )
);

CREATE TABLE payments (
    id SERIAL PRIMARY KEY,
    reservation_id INT REFERENCES reservations(id) ON DELETE CASCADE,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    description TEXT
);

````