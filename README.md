# Ticketsystem
A simple jira ticketing system

## Requirements
```
Java 8
Gradle 6.5
PostgresDB 10
```

## API Specification:
```
1. Get all tickets
	URL: /ticket
	Method: GET
	Query Params: agent=<agent name>, customer=<customer name>, status=<TicketStatus Enum value>
	
2. Get ticket by Id
	URL: /ticket/{id}
	Method: GET
	
3. Create ticket (User needs to be created first)
	URL: /ticket
	Method: POST
	Body: {
			"title": "Login not working as expected",
			"description": "Login button on the home page does not work as expected.",
			"type": "BUG",
			"priority": "P1",
			"createdBy": {
				"id": 1
			},
			"assignedTo": {
				"id": 2
			},
			"customer": {
				"id": 2
			},
			"status": "OPEN"
		}
	Note: 
		1. If assignedTo is not provided, the ticket is auto assigned to agent with lowest ticket count. 

4. Update ticket (This is not a PATCH method. So the entire GET response needs to be sent in the PUT body)
	URL: /ticket/{id}
	Method: PUT
	Body: {
			"title": "Login not working as expected",
			"description": "Login button on the home page does not work as expected.",
			"type": "BUG",
			"priority": "P1",
			"createdBy": {
				"id": 1
			},
			"assignedTo": {
				"id": 2
			},
			"customer": {
				"id": 2
			},
			"status": "OPEN"
		}
	Note: Edit details, update status, assign ticket to agent can be done via this API
	
5. Delete ticket
	URL: /ticket/{id}
	Method: DELETE
	
6. Add response to a ticket
	URL: /ticket/{id}/response
	Method: POST
	Body: {
		   "respondedBy": {
			   "id": 1
		   },
		   "response": "Test response 5"
		}
	
7. Add user to the system
	URL: /user
	Method: POST
	Body: {
			"name": "Test User",
			"email": "test@gmail.com"
		}
```
## Scheduler

Please check SpringSchedulerConfig for the scheduler which runs every 10mins to update the status of RESOLVED tickets older than 30 days to CLOSED

## DB Schema
For database schema please refer data.sql file

## To compile
```
gradle clean build -x test
```

## To Run
```
java -jar build/libs/ticket-0.0.1-SNAPSHOT.jar
```
