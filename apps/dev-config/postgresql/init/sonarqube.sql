CREATE USER sonarqube WITH PASSWORD 'sonarqube';
CREATE DATABASE sonarqube;
GRANT ALL PRIVILEGES ON DATABASE sonarqube TO sonarqube;

\c sonarqube
GRANT CREATE ON SCHEMA public TO PUBLIC;