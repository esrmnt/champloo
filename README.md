# champloo

This serves as a personal repository and notes-to-self on kafka. Its primary goal is to capture insights, discoveries, and solutions encountered during learning and experimentation.

#### Execute Locally

#### Infrastructure setup 

```shell
docker-compose.exe -f .\infra\docker-compose.yaml up
```

#### producer and consumers
```python
$ virtualenv env
$ .\env\Scripts\activite
(env)$ pip install -r .\src\requirements.txt
```
#### Generation of Avro Schema

Add the confluent repositiry in the POM file 
```xml
    <repositories>
        <repository>
            <id>confluent</id>
            <url>https://packages.confluent.io/maven/</url>
        </repository>
    </repositories>
```
Add the plugins avro maven plugin to the POM file

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
Add the dependencies to the pom file 

```xml
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

#### References
- https://kafka.apache.org/documentation/
- https://www.udemy.com/course/apache-kafka/
- https://www.conduktor.io/kafka/what-is-apache-kafka/ 