-- roles
CREATE ROLE masaca WITH
    NOSUPERUSER
    NOCREATEDB
    NOLOGIN;
CREATE USER masaca_adm WITH
    NOSUPERUSER
    NOCREATEDB
    PASSWORD 'masaca'
    IN ROLE masaca;
CREATE USER masaca_api WITH
    NOSUPERUSER
    NOCREATEDB
    PASSWORD 'masaca'
    IN ROLE masaca;

-- db
CREATE DATABASE masaca WITH OWNER masaca_adm;
GRANT CONNECT ON DATABASE masaca TO masaca;
