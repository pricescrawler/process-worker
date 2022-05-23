# Prices Crawler - Process Worker

## 1. Description

The main goal of this project is to automate prices crawler tasks with camunda workflows.

**Camunda URL:** http://localhost:8080

## 2. Environment Variables

| #   | Name                         | Type   | Description                   | Default    |
|-----|------------------------------|--------|-------------------------------|------------|
| 1   | ACTIVE_PROFILE               | String | Default active profile        | local/prod |
| 2   | ADMIN_USER                   | String | Default camunda user          | -          |
| 3   | ADMIN_PASSWORD               | String | Default camunda user password | -          |
| 4   | DATABASE_URL                 | String | PostgreSQL server url         | -          |
| 5   | SPRING_DATA_MONGODB_URI      | String | MongoDB server url            | -          |
| 6   | SPRING_DATA_MONGODB_DATABASE | String | MongoDb database name         | -          |