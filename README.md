# E-Store:  THE Pokemon Card Store
# Modify this document to expand any and all sections that are applicable for a better understanding from your users/testers/collaborators (remove this comment and other instructions areas for your FINAL release)

An online E-store system built in JDK 17.0.4.1, Rest API, and Angular web service.
  
## Team

- Zachary Wagner
- Daniel Pittman
- Jensen DeRosier
- Timothy Avila
- Gabriel Buxo


## Prerequisites

- Java (Old:8=>11) => (New: JDK 17.0.4.1) (Make sure to have correct JAVA_HOME setup in your environment)
- Maven 3.8.6
- Node.js 16.17.0
- NPM 8.19.1


## How to run it

1. Clone the repository and go to the estore-api directory.
2. Execute `mvn compile exec:java`
3. Open in your browser `http://localhost:8080/`
4. Going to `http://localhost:8080/products` in your browser will show all products in inventory
5. Open powershell(windows) or open terminal(mac/linux)
6. Use cURL instructions to interact with inventory

## Example cURL instructions

### WINDOWS:
- Curl.exe -X GET 'http://localhost:8080/products'
    - This gives full list of products

- Curl.exe -X POST 'http://localhost:8080/products' -H 'Content-Type: application/json' -d '{\"name\": \"Tangerine\",\"quantity\":\"1000\", \"price\":\"2.00\"}'
    - This makes a product

- Curl.exe -X GET 'http://localhost:8080/products/?name=an’
    - This searches the inventory

- Curl.exe -X PUT 'http://localhost:8080/products' -H 'Content-Type: application/json' -d '{\"id\": \"7\",\"name\": \"Tangerine\",\"quantity\":\"35\", \"price\":\"2.00\"}'
    - This overrides an inventory item

- Curl.exe -X DELETE 'http://localhost:8080/products/8'
    - This deletes an inventory item

### MAC/LINUX:
- Curl -X GET 'http://localhost:8080/products'
    - This gives full list of products

- Curl -X POST 'http://localhost:8080/products' -H 'Content-Type: application/json' -d '{"name": "Tangerine","quantity":"1000", "price":"2.00"}'
    - This makes a product

- Curl -X GET 'http://localhost:8080/products/?name=an’
    - This searches the inventory

- Curl -X PUT 'http://localhost:8080/products' -H 'Content-Type: application/json' -d '{"id": "7","name": "Tangerine","quantity":"35", "price":"2.00"}'
    - This overrides an inventory item

- Curl -X DELETE 'http://localhost:8080/products/8'
    - This deletes an inventory item



## Known bugs and disclaimers
- no known bugs at this time!

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory


## How to setup/run/test program 
1. Tester, first obtain the Acceptance Test plan
2. IP address of target machine running the app
3. Execute ________
4. ...
5. ...

## License

MIT License

See LICENSE for details.
