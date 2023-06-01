# workshop-performance
Databases comparative based on a Java project connected by JDBC to IRIS, MySQL and Postgresql.

You can find more in-depth information in https://learning.intersystems.com.

New to IRIS Interoperability framework? Have a look at [IRIS Interoperability Intro Workshop](https://github.com/intersystems-ib/workshop-interop-intro).

# What do you need to install? 
* [Git](https://git-scm.com/downloads) 
* [Docker](https://www.docker.com/products/docker-desktop) (if you are using Windows, make sure you set your Docker installation to use "Linux containers").
* [Docker Compose](https://docs.docker.com/compose/install/)
* [Visual Studio Code](https://code.visualstudio.com/download) + [InterSystems ObjectScript VSCode Extension](https://marketplace.visualstudio.com/items?itemName=daimor.vscode-objectscript)

# Setup
Build the image we will use during the workshop:

```console
$ git clone https://github.com/intersystems-ib/workshop-performance
$ cd workshop-performance
$ docker-compose build
```

# Example

The main purpose of this example is to test the performance of different databases connected by JDBC to a Java project.

## Test Production 
* Run the containers we will use in the workshop:
```
docker-compose build

docker-compose up -d
```
Automatically an IRIS instance, a MySQL and a PostgreSQL databases will be deployed, a Tomcat will be deployed with the war generated from our Java project (you can check the code under the src folder).

You can check PostgreSQL and MySQL databases from Adminer 

## MySQL Adminer

* Open [Adminer](http://localhost:8080) using the following parameters:
  * Server: `mysql`
  * Username: `testuser`
  * Password: `testpassword`
  * Database: `test`
* Review the records in patient, city and country tables.

## PostgreSQL Adminer

* Open [Adminer](http://localhost:8080) using the following parameters:
  * Server: `posrgres`
  * Username: `testuser`
  * Password: `testpassword`
  * Database: `testuser`
* Select Schema `test`
* Review the records in patient, city and country tables.

## IRIS database

* Open the [Management Portal](http://localhost:52773/csp/sys/UtilHome.csp).
* Login using the default `superuser`/ `SYS` account.
* Open System Explorer --> SQL
* Select NAMESPACE USER and Schema `Test`

# Modifying default war 

The war generated doesn't need further modifications to work, but you can edit the java project to test different queries. In order to generate a new war you will have to install [Maven](https://maven.apache.org/download.cgi) in your computer and add the InterSystems IRIS JDBC library into your [local maven repository](https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html).

To generate a new war file from Visual Studio you only have to open a terminal and run:

```
mvn package -DskipTests
```

A new war will be created under `\target\` folder, rename it to `performance.war` and move it into `\tomcat\` folder. The next step to deploy your new war is to clean the Docker containers and images and recreate it again launching:

```
docker-compose build
docker-compose up -d
```

# Testing with Postman

This project contains a json file to be loaded into Postman application (`performance.postman_collection.json`). Once you have imported this file you will be able to launch the tests created by default.