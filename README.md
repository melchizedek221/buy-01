# BUY 01

## Usage
To run the application you need to follow these instructions below:
- Create a docker network: ``` docker network create microservices-network ```
- For each service run these commands:
``` ./mvnw clean package```
then
``` docker-compose up --build -d ```
