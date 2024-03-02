# Partior-Assignment
# Star Wars Information API

## Description
This project provides an API to retrieve information related to the Star Wars universe, including details about starships, crew members on the Death Star, and the presence of Princess Leia on Alderaan. Refer to https://swapi.dev/

## System Requirements
- Java 8 or higher
- Spring Boot 3.X
- Maven

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone (https://github.com/SubramanyaKNP/partior-assignment/)
   ```

2. Navigate to the project directory:
   ```bash
   cd star-wars-api
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   java -jar target/star-wars-api.jar
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

## Testing
The project includes unit tests and integration tests to validate the code and the connection with the SWAPI. To run the tests, execute:
```bash
mvn test
```

## Authors
- Subramanya KNP(knp.s@yahoo.com)