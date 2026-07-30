### Task 1: Project Scaffolding and Maven Configuration

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\pom.xml`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\TemplateApiApplication.java`

**Interfaces:**
- Consumes: None (first task)
- Produces: Maven project structure, main application class

- [ ] **Step 1: Create project directory structure**

```powershell
mkdir "C:\Users\jorel\Desktop\template-api"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\aspect"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\config"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\controller"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\domain"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\dtos"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\enums"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\dao"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\vos"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\datasource"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\service"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\util"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\resources"
mkdir "C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\controller"
mkdir "C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\service"
mkdir "C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\persistence"
```

- [ ] **Step 2: Create pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.1.0</version>
        <relativePath/>
    </parent>
    <groupId>com.jorel</groupId>
    <artifactId>template-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>template-api</name>
    <description>Spring Boot Template API Project</description>
    <properties>
        <java.version>25</java.version>
        <jdbi.version>3.47.0</jdbi.version>
        <springdoc.version>2.8.6</springdoc.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jdbi</groupId>
            <artifactId>jdbi3-core</artifactId>
            <version>${jdbi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jdbi</groupId>
            <artifactId>jdbi3-mysql</artifactId>
            <version>${jdbi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jdbi</groupId>
            <artifactId>jdbi3-sqlobject</artifactId>
            <version>${jdbi.version}</version>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mysql</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

- [ ] **Step 3: Create TemplateApiApplication.java**

```java
package com.jorel.template_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApiApplication.class, args);
    }
}
```

- [ ] **Step 4: Verify project compiles**

Run: `mvn compile`
Expected: BUILD SUCCESS

- [ ] **Step 5: Commit**

```bash
git init
git add .
git commit -m "feat: initial project scaffolding with Maven and Java 25"
```
