-- ================================
-- SUPPRESSION ET CRÉATION DE LA BASE
-- ================================
DROP DATABASE IF EXISTS sooatel_db;
CREATE DATABASE sooatel_db;
\c sooatel_db;

-- ================================
-- UTILISATEURS ET RÔLES
-- ================================
CREATE TABLE roles (
   id SERIAL PRIMARY KEY,
   role_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   username VARCHAR(100) UNIQUE NOT NULL,
   email VARCHAR(150) UNIQUE NOT NULL,
   password VARCHAR(255) NOT NULL
);

CREATE TABLE roles_users (
   id SERIAL PRIMARY KEY,
   role_id INT REFERENCES roles(id) ON DELETE CASCADE,
   user_id INT REFERENCES users(id) ON DELETE CASCADE
);

-- ================================
-- INGRÉDIENTS ET STOCK
-- ================================
CREATE TABLE ingredient_groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE units (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    abbreviation VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE ingredients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    unit_id INT NOT NULL REFERENCES units(id) ON DELETE CASCADE,
    group_id INT REFERENCES ingredient_groups(id) ON DELETE SET NULL
);

CREATE TABLE stocks (
    id SERIAL PRIMARY KEY,
    ingredient_id INT REFERENCES ingredients(id) ON DELETE CASCADE,
    quantity DOUBLE PRECISION NOT NULL CHECK (quantity >= 0)
);

CREATE TABLE operations (
    id SERIAL PRIMARY KEY,
    stock_id INT REFERENCES stocks(id) ON DELETE SET NULL,
    operation_type VARCHAR(50) NOT NULL,
    operation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT
);

CREATE TABLE purchases (
    id SERIAL PRIMARY KEY,
    ingredient_id INT REFERENCES ingredients(id) ON DELETE CASCADE,
    quantity DOUBLE PRECISION NOT NULL CHECK (quantity > 0),
    cost DECIMAL(10, 2) NOT NULL CHECK (cost >= 0),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ================================
-- MENUS ET CATÉGORIES
-- ================================
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'available',
    description TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    category_id INT REFERENCES categories(id) ON DELETE SET NULL
);

CREATE TABLE menu_ingredients (
    id SERIAL PRIMARY KEY,
    menu_id INT REFERENCES menus(id) ON DELETE CASCADE,
    ingredient_id INT REFERENCES ingredients(id) ON DELETE CASCADE,
    quantity DOUBLE PRECISION NOT NULL CHECK (quantity > 0)
);

-- ================================
-- CLIENTS ET LIEUX
-- ================================
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20)
);

CREATE TABLE floors (
    id SERIAL PRIMARY KEY,
    floor_number INT NOT NULL UNIQUE CHECK (floor_number >= 0),
    description TEXT
);

CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    room_number INT NOT NULL UNIQUE,
    capacity INT NOT NULL CHECK (capacity > 0),
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    floor_id INT REFERENCES floors(id) ON DELETE SET NULL
);

CREATE TABLE tables (
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    capacity INT NOT NULL CHECK (capacity > 0)
);

-- ================================
-- SESSIONS D’OCCUPATION
-- ================================
CREATE TABLE session_occupations (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(id) ON DELETE SET NULL,
    table_id INT REFERENCES tables(id) ON DELETE SET NULL,
    room_id INT REFERENCES rooms(id) ON DELETE SET NULL,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ended_at TIMESTAMP,
    description TEXT,
    CONSTRAINT chk_one_location CHECK (
        (table_id IS NOT NULL AND room_id IS NULL)
        OR (table_id IS NULL AND room_id IS NOT NULL)
    )
);

-- ================================
-- RÉSERVATIONS
-- ================================
CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(id) ON DELETE SET NULL,
    room_id INT REFERENCES rooms(id) ON DELETE SET NULL,
    table_id INT REFERENCES tables(id) ON DELETE SET NULL,
    reservation_start TIMESTAMP NOT NULL,
    reservation_end TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    description TEXT,
    CONSTRAINT chk_room_or_table_reservation CHECK (
        (room_id IS NOT NULL AND table_id IS NULL)
        OR (room_id IS NULL AND table_id IS NOT NULL)
    )
);

-- ================================
-- COMMANDES
-- ================================
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(id) ON DELETE SET NULL,
    room_id INT REFERENCES rooms(id) ON DELETE SET NULL,
    table_id INT REFERENCES tables(id) ON DELETE SET NULL,
    session_id INT REFERENCES session_occupations(id) ON DELETE SET NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    description TEXT,
    CONSTRAINT chk_room_or_table_order CHECK (
        (room_id IS NOT NULL AND table_id IS NULL)
        OR (room_id IS NULL AND table_id IS NOT NULL)
    )
);

CREATE TABLE order_lines (
    id SERIAL PRIMARY KEY,
    order_id INT REFERENCES orders(id) ON DELETE CASCADE,
    menu_id INT REFERENCES menus(id) ON DELETE SET NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0),
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED
);

-- ================================
-- FACTURATION AVEC PAIEMENT INTÉGRÉ
-- ================================
CREATE TABLE invoices (
    id SERIAL PRIMARY KEY,
    customer_id INT REFERENCES customers(id) ON DELETE SET NULL,
    session_id INT REFERENCES session_occupations(id) ON DELETE SET NULL,
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL CHECK (total_amount >= 0),
    amount_paid DECIMAL(10, 2) CHECK (amount_paid >= 0),
    payment_method VARCHAR(50),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_status VARCHAR(50) NOT NULL DEFAULT 'unpaid',
    description TEXT
);

CREATE TABLE invoice_lines (
    id SERIAL PRIMARY KEY,
    invoice_id INT REFERENCES invoices(id) ON DELETE CASCADE,
    menu_id INT REFERENCES menus(id) ON DELETE SET NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0),
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * unit_price) STORED
);

-- ================================
-- CAISSE ET HISTORIQUE
-- ================================
CREATE TABLE cash (
    id SERIAL PRIMARY KEY,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cash_history (
    id SERIAL PRIMARY KEY,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10, 2) NOT NULL CHECK (amount > 0),
    transaction_type VARCHAR(30) CHECK (
        transaction_type IN (
            'MENU_SALE_DEPOSIT',
            'MANUAL_DEPOSIT',
            'INGREDIENT_PURCHASE',
            'MANUAL_WITHDRAWAL'
        )
    ) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    description TEXT
);
