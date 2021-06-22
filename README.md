# TODO-List App

It's a Spring boot App based on Java version 8, it was choosed this tech stack because is the stack that I have being working lately.


## Run Locally
Pre-requisites:
  - Maven version 10
  - Open JDK version 16 
  - Docker version 20
  
Run `mvn clean package`

Run `cp target/task-0.0.1-SNAPSHOT.jar src/main/docker`

Run `cd ./src/main/docker/ && docker-compose up`




