# Partior-Assignment
# Star Wars Information API

## Description
This project provides an API to retrieve information related to the Star Wars, including details about starships, crew members on the Death Star, and the presence of Princess Leia on Alderaan. Refer to https://swapi.dev/

## System Requirements
- Java 8 or higher
- Spring Boot 3.X
- TestNg 7.X
- Mockito 5.X
- Lombok
- Maven

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone (https://github.com/SubramanyaKNP/partior-assignment/)
   ```

2. Navigate to the project directory:
   ```bash
   cd starship-assignment
   ```
3. Run the application:
   ```bash
   mvn clean install -DskipTests
   mvn spring-boot:run
   ```
   The application will start and be accessible at `http://localhost:8080`.

## Endpoints

### 1. Fetch Star Wars Information

- **URL:** `/information`
- **Method:** GET
- **Description:** Fetches information about Darth Vader's starship, the number of crew members onboard the Death Star, and the presence of Princess Leia on Alderaan.

#### Request Body:
   None

#### Response Body:
```json
{
   "starship": {
      "name": "String",
      "class": "String",
      "model": "String"
   },
   "crew": "Number",
   "isLeiaOnPlanet": "Boolean"
}
```

### 2. Asynchronous Information Retrieval

- **URL:** `/async/information`
- **Method:** GET
- **Description:** Returns information asynchronously, allowing the client to receive an immediate response and then implement a listener for the response or a polling endpoint.

#### Request Body:
   None

#### Response Body:
   The response format is the same as `/information` endpoint.

## Error Handling
Robust error handling has been implemented to handle potential issues when connecting to the SWAPI. Proper error messages will be returned to the client in case of failures.

## Validation and Business Logic
- If no starships were found, the value for starship will be set as an empty object (`{}`).
- If there is no crew onboard the Death Star, the crew value will be set to 0.
- If Princess Leia is on the planet, the `isLeiaOnPlanet` field will be set to true; otherwise, it will be set to false.

## How to run the testcases
The project includes unit tests and integration tests to validate the code and the connection with the SWAPI. To run the tests, execute:
```bash
mvn test
```
## Test Cases for starship assignment
Work in progress .......

<details>

### No Crew Onboard Death Star

#### Description:
- Mock a response from SWAPI where no crew is onboard the Death Star.
- Verify that the returned StarshipAPIResponse object has the crew count set to 0.

#### Test Steps:
1. Mock SWAPI response with no crew onboard the Death Star.
2. Call fetchInformation() method.
3. Assert that the returned StarshipAPIResponse object has the crew count set to 0.

### Leia Not on Alderaan

### Description:
- Mock a response from SWAPI where Princess Leia is not on Alderaan.
- Verify that the returned StarshipAPIResponse object has the isLeiaOnPlanet field set to false.

### Test Steps:
1. Mock SWAPI response with Princess Leia not on Alderaan.
2. Call fetchInformation() method.
3. Assert that the returned StarshipAPIResponse object has the isLeiaOnPlanet field set to false.

### SWAPI Unavailable

### Description:
- Mock an exception or error when attempting to fetch information from SWAPI.
- Verify that the service class handles the error gracefully and returns an appropriate response, such as setting the starship information to an empty object and setting other fields as expected.

### Test Steps:
1. Mock SWAPI to throw an exception or error.
2. Call fetchInformation() method.
3. Assert that the service class handles the error gracefully and returns an appropriate response.

### Concurrent Access

### Description:
- Simulate concurrent access to the /information endpoint by multiple threads.
- Verify that the service class is thread-safe and behaves correctly under concurrent access.

### Test Steps:
1. Simulate concurrent access to the /information endpoint.
2. Verify that the service class behaves correctly under concurrent access.

### Async Endpoint Response

### Description:
- Test the asynchronous /async/information endpoint.
- Verify that the endpoint returns immediately and that the client can receive the response asynchronously.

### Test Steps:
1. Call the asynchronous /async/information endpoint.
2. Verify that the endpoint returns immediately.
3. Verify that the client can receive the response asynchronously.

### Test Steps:
1. Mock an error scenario when accessing the async endpoint.
2. Verify that the error is handled correctly, and appropriate error responses are returned.

</details>

## Authors
- Subramanya KNP(knp.s@yahoo.com)
