\c postgres
DROP DATABASE IF EXISTS spring_sample;
DROP ROLE IF EXISTS student;
CREATE ROLE student WITH PASSWORD 'himitu' LOGIN;
CREATE DATABASE spring_sample OWNER student ENCODING 'UTF8';
