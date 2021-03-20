# TradeExchange

Sample Project for a scalable Trade Exchange application

___

## Technologies/Libraries used

* SpringBoot

* Kafka

* Redis

* MongoDB

___

### Prerequisites for running the application

1. Kafka broker running on port 9092
2. Redis server running on default port 6379
3. MongoDB instance running on default port 27017

___

### Data Modeling

* Order

* Trade

___

### Kafka Topics

* OrderEvent

___

### Redis Cache

* Non Matched Orders

___

### Steps to build and deploy the application

1. Go to the project root location and execute the below command to build and verify the project.

    ```bash
    mvnw clean verify install
    ```

    and check for the below log

    ```bash
    [INFO] BUILD SUCCESS
    ```

2. Deploy the application.

    ```bash
    mvnw spring-boot:run
    ```

___


