# hibernate-bug-testing Project

This project uses Quarkus and has as purpose to show navigation properties possibile bugs of reactive hibernate.

Reactive Hibernate is the next generation of the Hibernate ORM where is possibile to structure and run reactive
queries for more flexible and lightweight applications.

## How to run the tests
1. run the PostgresSQL container (or physical) instance of PostgreSQL database;
2. populate with proper values the application.properties file for connection string;
3. run init-schema.sql file in the database to initialize the database as expected to run the tests;
4. run the Quarkus tests in the test folder.
