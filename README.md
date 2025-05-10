# Fiasco Festival API

A Spring Boot RESTful service for managing music festivals, performances and users, backed by MongoDB.

## Features

- **User Management**: registration, authentication, and user lookup  
- **Festival Management**: create, read, update, delete; state transitions (decision, announced)  
- **Performance Management**: create, list, search by state or festival, approve or delete performances  
- **Validation & Error Handling** with custom exceptions  
- **Integration Tests** covering controllers, services, and repositories  

## Tech Stack

- **Java 21**  
- **Spring Boot 3.x** (Web, Data MongoDB, Security, Validation)  
- **MongoDB**  
- **Lombok**  
- **Maven** (with Maven Wrapper)  

## Prerequisites

- Java 21 SDK  
- MongoDB running locally (default URI `mongodb://localhost:27017/festival`)  
- Git  

## Getting Started
**Clone the repo**  
   ```bash
   git clone <your-repo-url>
   cd Software-Dev-project-main
