# champloo

This serves as a personal repository and notes-to-self on kafka. Its primary goal is to capture insights, discoveries, and solutions encountered during learning and experimentation.

## Infrastructure setup 

The YAML file (infra\docker-compose.yaml) defines a multi-container application for running a Kafka infrastructure using Docker. It includes services for Zookeeper, Kafka broker, Schema Registry, Kafka Connect, Control Center, KSQL Server, KSQL CLI, KSQL Data Generator, and a Kafka REST Proxy. Each service is configured with its own image, ports, environment variables, and dependencies on other services. The environment variables provide details like Kafka connection details, Schema Registry URL, and monitoring configurations. This YAML file allows for easy deployment and management of a complete infrastructure for development purposes.


```shell
docker-compose.exe -f .\infra\docker-compose.yaml up
```

## Kafka Clients

### Producer and Consumers

Sample java producer and consumer are available in the project under folder **champloo**.

#### Schemas

**Avro Schema Generation in Maven**

To generate Avro schemas within a Maven project, the `avro-maven-plugin`can be leveraed. This plugin simplifies the process by automatically compiling and generating Java classes from Avro schemas. After including the plugin in the `pom.xml` file, its behavior, such as specifying the source directory for schemas, the target directory for generated classes, and other customization options can be configured. The plugin makes it easier to work with Avro data formats in your Maven projects.

Add the plugins avro maven plugin in `pom.xml`

```xml
<plugin>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.12.0</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <sourceDirectory>${project.basedir}/src/main/avro/</sourceDirectory>
        <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
    </configuration>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
    <source>1.8</source>
    <target>1.8</target>
    </configuration>
</plugin>
```
Add the dependencies to the `pom.xml`

```xml
<!-- https://mvnrepository.com/artifact/org.apache.avro/avro -->
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro</artifactId>
    <version>1.11.1</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.avro/avro-maven-plugin -->
<dependency>
    <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
    <version>1.12.0</version>
</dependency>
```
There after navigate to 
- vs code > maven > champloo > plugins > avro 
- execute action schema

#### References
- https://kafka.apache.org/documentation/
- https://www.udemy.com/course/apache-kafka/
- https://www.conduktor.io/kafka/what-is-apache-kafka/ 