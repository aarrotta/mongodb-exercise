# mongodb-exercise
Spring Rest webservice to access MongoDB

<h5>MongoDB</h5>
If you don't have an instance of MongoDB server running on your machine then use the following guide to install and run it: https://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu
<h5>Compile</h5>
```
maven clean package
```
<h5>Run</h5>
```
java -jar <project-root>/target/mongodb-exercise-0.0.1-SNAPSHOT.jar
```
<h5>Test</h5>
To check if the server is running hit the following url: 
<a href="http://localhost:8080/ex1/alumni?name=Andrea">localhost:8080/ex1/alumni?name=Andrea</a>
