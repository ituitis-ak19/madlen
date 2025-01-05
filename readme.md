# Madlen Case API

## Overview
The Madlen Case API is designed to manage questions, metadata, and related services. It supports creating, updating, fetching, and deleting questions, as well as tracking metadata about the questions.

## API Endpoints

### Metadata Management
- **GET /metadata**  
  Fetches metadata information including total questions, coverage pages, and primary topics.

### Question Management
- **GET /questions**  
  Fetches all questions with pagination and optional filters.  
  **Parameters:**
    - `page` (required): Page number for pagination.
    - `filters` (optional): Filters for the question query, provided as a list of `FilterDto` objects.

- **GET /questions/{id}**  
  Fetches a question by its ID.  
  **Parameters:**
    - `id` (required): The unique ID of the question to be retrieved.

- **POST /questions**  
  Creates a new question.  
  **Body:**
    - A valid `CreateQuestionRequestDto` object.

- **DELETE /questions/{id}**  
  Deletes a question by its ID.  
  **Parameters:**
    - `id` (required): The unique ID of the question to be deleted.

## Models

### Metadata
The metadata model tracks the following information:
- `totalQuestions`: The total number of questions in the system.
- `coveragePages`: A set of page numbers that the questions cover.
- `primaryTopics`: A set of topics that the questions focus on.

### Question
A question includes the following properties:
- `id`: The unique identifier for the question.
- `questionText`: The content of the question.
- `contextPages`: A list of pages that are relevant to the question.
- `difficultyLevel`: The difficulty level of the question.
- `cognitiveLevel`: The cognitive level required to answer the question.
- `keyConcepts`: A list of key concepts covered by the question.
- `courseName`: The name of the course to which the question belongs.
- `modelAnswer`: The model answer to the question, including main argument, key points, supporting evidence, and conclusion.
- `gradingCriteria`: A list of criteria for grading the answer to the question.

### ModelAnswer
The model answer includes the following:
- `mainArgument`: The main argument of the answer.
- `keyPoints`: A list of key points to support the main argument.
- `supportingEvidence`: A list of supporting evidence for the key points.
- `conclusion`: The conclusion drawn from the key points and evidence.

### SupportingEvidence
Supporting evidence is linked to a specific point and page:
- `point`: The point of evidence.
- `pageReference`: The page where the evidence can be found.


## Technologies Used
- **Spring Boot**: The primary framework for building the API.
- **JPA (Hibernate)**: Used for persistence and database interaction.
- **Jakarta Validation**: Ensures valid input for question creation and updates.
- **Postgres**: Used for keeping persistent data.
- **Docker**: Ensures portability and simplified deployment.

## Deployment Instructions

1. **Build Docker images by using dockerfile**
- docker build -f Dockerfile -t madlen .
2. **Start all necessary services (Couchbase, Redis, etc.) using Docker Compose:**
- docker-compose up -d
