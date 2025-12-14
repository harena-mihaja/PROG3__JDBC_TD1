-- Create DB and user
CREATE DATABASE product_management_db;
CREATE USER product_manager_user WITH PASSWORD '123456';

-- Allow user to connect to DB
GRANT CONNECT ON DATABASE product_management_db TO product_manager_user;

-- Connect to DB
\c product_management_db

-- Allow user to CRUD in DB
GRANT CREATE ON SCHEMA public TO product_manager_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO product_manager_user;
