# 🏫 Training Institute Microservices

A modern, scalable microservices architecture for managing training institute operations using Spring Boot, Spring Cloud, and reactive technologies.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Architecture & Design](#architecture--design)
- [POM Alignment & Hierarchy](#pom-alignment--hierarchy)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Modules Description](#modules-description)
- [Getting Started](#getting-started)
- [Build & Deployment](#build--deployment)
- [Dependency Management](#dependency-management)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

---

## 🎯 Project Overview

**Training Institute Microservices** is an enterprise-grade microservices platform designed to automate and streamline operations for training institutes. The system is built using industry best practices with focus on:

- **Scalability**: Independent service deployment and scaling
- **Resilience**: Circuit breakers and retry mechanisms
- **Security**: Authentication and authorization at API Gateway level
- **Performance**: Reactive programming with WebFlux where needed
- **Maintainability**: Centralized dependency management and consistent configurations

### Key Features

✅ Student Management - Complete student lifecycle management  
✅ Teacher Management - Teacher profiles and scheduling  
✅ Course Management - Course creation and management  
✅ Payment Processing - Secure payment handling  
✅ Authentication & Authorization - JWT-based security  
✅ API Gateway - Centralized routing and load balancing  
✅ Monitoring & Observability - Actuator endpoints for health checks  

---

## 🏗️ Architecture & Design

### Service-Oriented Architecture (SOA)

The project follows a **microservices architecture pattern** with the following principles:

```
┌─────────────────────────────────────────────────────────────┐
│                      API CLIENTS                             │
└────────────────────────┬────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────┐
│            API GATEWAY SERVICE (Spring Cloud)               │
│  • Request routing & load balancing                         │
│  • Authentication & authorization                           │
│  • Circuit breaker (Resilience4j)                          │
│  • Rate limiting                                            │
└──┬──────────┬──────────┬──────────┬──────────┬──────────┬──┘
   │          │          │          │          │          │
   ▼          ▼          ▼          ▼          ▼          ▼
┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌─────────┐
│ Auth   │ │Student │ │Teacher │ │Course  │ │Payment │ │ Common  │
│Service │ │Service │ │Service │ │Service │ │Service │ │ Library │
└────┬───┘ └────┬───┘ └────┬───┘ └────┬───┘ └────┬───┘ └────┬───┘
     │           │          │          │          │          │
     └───────────┴──────────┴──────────┴──────────┴──────┬───┘
                                                         │
                    ┌────────────────────────────────────┘
                    │
        ┌───────────▼────────────┐
        │  PostgreSQL Database   │
        │  (Shared or per-service)
        └────────────────────────┘
```

### Design Patterns Used

1. **BFF (Backend for Frontend)** - API Gateway acts as single entry point
2. **Service Locator** - Spring Cloud Registry for service discovery
3. **Circuit Breaker** - Resilience4j for fault tolerance
4. **Strangler Fig** - Incremental migration capability
5. **Database per Service** - Data isolation and independence
6. **API Composition** - API Gateway for query aggregation

### Communication Patterns

- **Synchronous**: REST over HTTP (Spring Web/WebFlux)
- **Database**: PostgreSQL with Spring Data JPA
- **Monitoring**: Spring Boot Actuator for health checks

---

## 📊 POM Alignment & Hierarchy

### Hierarchical Structure

```
GRANDPARENT (Level 3)
└─ org.springframework.boot:spring-boot-starter-parent:3.2.5
   │
   PARENT (Level 2)
   └─ com.institute:training-institute-microservices:1.0.0 [pom]
      │
      CHILD MODULES (Level 1)
      ├─ api-gateway-service:0.0.1-SNAPSHOT
      ├─ auth-service:0.0.1-SNAPSHOT
      ├─ student-service:0.0.1-SNAPSHOT
      ├─ teacher-service:0.0.1-SNAPSHOT
      ├─ course-service:0.0.1-SNAPSHOT
      ├─ payment-service:0.0.1-SNAPSHOT
      └─ common-library:0.0.1-SNAPSHOT
```

### Alignment Best Practices ✅

| Aspect | Implementation | Status |
|--------|---|---|
| **relativePath Configuration** | Each child specifies `<relativePath>../pom.xml</relativePath>` | ✅ |
| **Version Management** | All versions defined in parent `<dependencyManagement>` | ✅ |
| **Inheritance Chain** | Proper parent-child-grandparent hierarchy | ✅ |
| **BOM Imports** | Spring Boot, Spring Cloud, Resilience4j BOMs imported | ✅ |
| **Plugin Management** | Centralized plugin configuration in parent | ✅ |
| **Property Definitions** | Java version and build properties in parent | ✅ |

### Dependency Management

#### Managed Dependencies (Parent POM)

```xml
<!-- Spring Boot BOM -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>${spring.boot.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

<!-- Spring Cloud BOM -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-dependencies</artifactId>
    <version>${spring.cloud.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

<!-- Resilience4j BOM -->
<dependency>
    <groupId>io.github.resilience4j</groupId>
    <artifactId>resilience4j-bom</artifactId>
    <version>2.1.0</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

<!-- Common Library -->
<dependency>
    <groupId>com.institute</groupId>
    <artifactId>common-library</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### Version Properties

```xml
<properties>
    <java.version>17</java.version>
    <spring.boot.version>3.2.5</spring.boot.version>
    <spring.cloud.version>2023.0.1</spring.cloud.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>
```

---

## 🛠️ Technology Stack

### Core Framework
| Technology | Version | Purpose |
|---|---|---|
| **Spring Boot** | 3.2.5 | Application framework |
| **Spring Cloud** | 2023.0.1 | Microservices orchestration |
| **Java** | 17 | Programming language |
| **Maven** | 3.11.0 (compiler plugin) | Build tool |

### Key Dependencies
| Dependency | Version | Purpose |
|---|---|---|
| **spring-boot-starter-web** | 3.2.5 | RESTful web services |
| **spring-boot-starter-webflux** | 3.2.5 | Reactive web stack (gateway) |
| **spring-boot-starter-data-jpa** | 3.2.5 | Database access |
| **spring-boot-starter-security** | 3.2.5 | Authentication (auth-service) |
| **spring-cloud-gateway** | 4.1.1 | API Gateway |
| **spring-boot-starter-actuator** | 3.2.5 | Monitoring & health checks |
| **resilience4j-bom** | 2.1.0 | Fault tolerance |
| **postgresql** | Latest | Database driver |
| **lombok** | 1.18.30 | Code generation |

### Database
| Component | Specification |
|---|---|
| **Database** | PostgreSQL |
| **ORM** | Hibernate (via Spring Data JPA) |
| **Connection Pool** | HikariCP (default in Spring Boot) |

---

## 📁 Project Structure

```
training-institute-microservices/
│
├── README.md                          # This file
├── pom.xml                            # Parent POM (root aggregator)
│
├── api-gateway-service/               # API Gateway (Spring Cloud Gateway)
│   ├── pom.xml
│   ├── src/main/java/...
│   ├── src/main/resources/
│   │   └── application.yaml
│   └── src/test/java/...
│
├── auth-service/                      # Authentication & Authorization
│   ├── pom.xml
│   ├── src/main/java/...
│   ├── src/main/resources/
│   │   └── application.yaml
│   └── src/test/java/...
│
├── student-service/                   # Student Management
│   ├── pom.xml
│   ├── src/main/java/...
│   ├── src/main/resources/
│   │   └── application.yaml
│   └── src/test/java/...
│
├── teacher-service/                   # Teacher Management
│   ├── pom.xml
│   ├── src/main/java/...
│   ├── src/main/resources/
│   │   └── application.yaml
│   └── src/test/java/...
│
├── course-service/                    # Course Management
│   ├── pom.xml
│   ├── src/main/java/...
│   ├── src/main/resources/
│   │   └── application.yaml
│   └── src/test/java/...
│
├── payment-service/                   # Payment Processing
│   ├── pom.xml
│   ├── src/main/java/...
│   ├── src/main/resources/
│   │   └── application.yaml
│   └── src/test/java/...
│
└── common-library/                    # Shared Utilities & DTOs
    ├── pom.xml
    ├── src/main/java/
    │   └── com/institute/common/
    │       ├── dto/                   # Data Transfer Objects
    │       ├── exception/             # Common Exceptions
    │       ├── util/                  # Utility Classes
    │       └── model/                 # Common Models
    └── src/test/java/...
```

---

## 📦 Modules Description

### 1. **API Gateway Service** 🚪
- **Port**: 8080 (configurable)
- **Purpose**: Single entry point for all client requests
- **Technologies**: Spring Cloud Gateway, Resilience4j
- **Responsibilities**:
  - Request routing to appropriate microservices
  - Load balancing
  - Circuit breaking with Resilience4j
  - Request/response transformation
  - Rate limiting

### 2. **Auth Service** 🔐
- **Port**: 8081 (configurable)
- **Purpose**: Authentication and authorization management
- **Technologies**: Spring Security, JWT
- **Responsibilities**:
  - User registration and login
  - JWT token generation and validation
  - Role-based access control (RBAC)
  - Password management

### 3. **Student Service** 👨‍🎓
- **Port**: 8082 (configurable)
- **Purpose**: Student lifecycle management
- **Technologies**: Spring Data JPA, PostgreSQL
- **Responsibilities**:
  - Student registration
  - Profile management
  - Enrollment tracking
  - Academic progress tracking

### 4. **Teacher Service** 👨‍🏫
- **Port**: 8083 (configurable)
- **Purpose**: Teacher profile and scheduling
- **Technologies**: Spring Data JPA, PostgreSQL
- **Responsibilities**:
  - Teacher registration and profiles
  - Course assignment
  - Availability scheduling
  - Performance tracking

### 5. **Course Service** 📚
- **Port**: 8084 (configurable)
- **Purpose**: Course creation and management
- **Technologies**: Spring Data JPA, PostgreSQL
- **Responsibilities**:
  - Course creation and modification
  - Curriculum management
  - Schedule management
  - Student enrollment

### 6. **Payment Service** 💰
- **Port**: 8085 (configurable)
- **Purpose**: Payment processing and tracking
- **Technologies**: Spring Data JPA, PostgreSQL
- **Responsibilities**:
  - Payment processing
  - Invoice generation
  - Payment tracking
  - Financial reporting

### 7. **Common Library** 📦
- **Type**: Shared JAR (non-executable)
- **Purpose**: Shared code and utilities
- **Contents**:
  - DTOs (Data Transfer Objects)
  - Common exceptions
  - Utility functions
  - Constants and enums
  - Shared models

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** or higher
- **Maven 3.8.1** or higher
- **PostgreSQL 12** or higher
- **Git**

### Installation

1. **Clone the repository**
```bash
git clone <repository-url>
cd training-institute-microservices
```

2. **Build all modules**
```bash
mvn clean install
```

3. **Verify build success**
```bash
mvn validate
```

### Database Setup

1. **Create PostgreSQL database**
```sql
CREATE DATABASE training_institute;
```

2. **Run migration scripts** (if applicable)
```bash
# Database initialization scripts should be in each service's resources
```

---

## 🏗️ Build & Deployment

### Building the Project

#### Full Build
```bash
# Clean, compile, test, and package all modules
mvn clean package

# With skipping tests
mvn clean package -DskipTests
```

#### Build Specific Module
```bash
# Build only one service
mvn clean package -pl api-gateway-service

# Build with dependencies
mvn clean package -pl api-gateway-service -am
```

#### Validate POMs
```bash
# Validate all POM configurations
mvn validate
```

### Running Services

#### Using IDE
1. Open each service's main class (`*Application.java`)
2. Run as Spring Boot application

#### Using Maven
```bash
# Run API Gateway
mvn -DskipTests spring-boot:run -pl api-gateway-service

# Run Auth Service
mvn -DskipTests spring-boot:run -pl auth-service

# Run Student Service
mvn -DskipTests spring-boot:run -pl student-service
```

#### Using JAR Files
```bash
# Build JAR
mvn clean package -DskipTests

# Run service
java -jar api-gateway-service/target/api-gateway-service-0.0.1-SNAPSHOT.jar
```

### Docker Deployment (Optional)

Create `Dockerfile` in each service:
```dockerfile
FROM eclipse-temurin:17-jdk-jammy
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

Build and run:
```bash
docker build -t training-institute/api-gateway-service .
docker run -p 8080:8080 training-institute/api-gateway-service
```

---

## 📦 Dependency Management

### Hierarchy of Dependency Resolution

```
1. Child Module (Child POM) - Highest Priority
2. Parent Module (Parent POM)
3. BOM Imports (Spring Boot, Spring Cloud, Resilience4j)
4. Default Version (from transitive dependencies)
```

### Adding New Dependencies

1. **For all modules**: Add to parent `<dependencyManagement>`
```xml
<!-- In parent pom.xml -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>example.group</groupId>
            <artifactId>example-artifact</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

2. **For specific module**: Add to child module without version
```xml
<!-- In child pom.xml -->
<dependencies>
    <dependency>
        <groupId>example.group</groupId>
        <artifactId>example-artifact</artifactId>
        <!-- Version comes from parent -->
    </dependency>
</dependencies>
```

### Version Updates

To update Spring Boot or Spring Cloud versions:

```xml
<!-- In parent pom.xml properties -->
<properties>
    <spring.boot.version>3.3.0</spring.boot.version>  <!-- Update here -->
    <spring.cloud.version>2024.0.0</spring.cloud.version>
</properties>
```

All child modules will automatically use the new versions!

---

## 🔌 API Documentation

### API Gateway Endpoints

| Endpoint | Method | Purpose |
|---|---|---|
| `/auth/**` | ALL | Auth Service routes |
| `/students/**` | ALL | Student Service routes |
| `/teachers/**` | ALL | Teacher Service routes |
| `/courses/**` | ALL | Course Service routes |
| `/payments/**` | ALL | Payment Service routes |

### Health Check Endpoints

All services expose actuator endpoints:

```bash
# Service Health
curl http://localhost:8080/actuator/health

# Metrics
curl http://localhost:8080/actuator/metrics

# Detailed Info
curl http://localhost:8080/actuator/info
```

### Service Discovery

API Gateway auto-discovers services using Spring Cloud configuration.

---

## 📝 Configuration

### Common Application Properties

Create `application.yaml` in each service's `src/main/resources/`:

```yaml
spring:
  application:
    name: service-name
  datasource:
    url: jdbc:postgresql://localhost:5432/training_institute
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

server:
  port: 808X

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

---

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run tests for specific module
mvn test -pl student-service

# Skip tests during build
mvn clean package -DskipTests
```

### Test Coverage

```bash
# Generate coverage report
mvn clean test jacoco:report
```

---

## 📊 Monitoring & Observability

### Actuator Endpoints

Each service provides monitoring through Spring Boot Actuator:

- `/actuator/health` - Service health status
- `/actuator/metrics` - Performance metrics
- `/actuator/info` - Application information
- `/actuator/env` - Environment properties

### Health Indicators

- **Database**: Checks PostgreSQL connectivity
- **Disk**: Monitors disk space
- **Custom**: Service-specific indicators

---

## 🔒 Security

### API Gateway Security

1. **Authentication**: JWT tokens required
2. **Authorization**: Role-based access control
3. **Rate Limiting**: Resilience4j configured
4. **Circuit Breaker**: Automatic fallback on service failure

### Best Practices

- Never commit secrets to Git
- Use environment variables for sensitive data
- Enable HTTPS in production
- Implement API rate limiting
- Regular security audits

---

## 🤝 Contributing

### Before Committing

1. **Format code**: Follow Spring conventions
2. **Run tests**: `mvn test`
3. **Validate POM**: `mvn validate`
4. **Build successfully**: `mvn clean install`

### Branching Strategy

- `main` - Production-ready code
- `develop` - Development branch
- `feature/*` - Feature branches
- `bugfix/*` - Bug fix branches

### Pull Request Process

1. Create feature branch from develop
2. Make changes and commit
3. Run full test suite
4. Create pull request with description
5. Code review and merge

---

## 📈 Performance Optimization

### Database
- Connection pooling via HikariCP
- Query optimization with proper indexing
- Prepared statements for parameterized queries

### API Gateway
- Response caching where applicable
- Compression for large payloads
- Asynchronous processing with WebFlux

### Circuit Breaker
- Failover to secondary services
- Graceful degradation
- Automatic recovery attempts

---

## 🆘 Troubleshooting

### Build Issues

**Issue**: `mvn: command not found`
```bash
# Solution: Add Maven to PATH or use full path
export PATH=$PATH:/path/to/maven/bin
```

**Issue**: `No database selected`
```bash
# Solution: Create database first
createdb training_institute
```

### Service Issues

**Issue**: Port already in use
```bash
# Solution: Change port in application.yaml or kill process
lsof -i :8080
kill -9 <PID>
```

**Issue**: Connection refused to PostgreSQL
```bash
# Solution: Check if PostgreSQL is running
psql -U postgres  # Test connection
```

---

## 📚 Documentation

### Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Maven Documentation](https://maven.apache.org/guides/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 📄 License

This project is licensed under the MIT License - see LICENSE file for details.

---

## 📞 Support

For issues or questions:
- Create an issue in the repository
- Contact the development team
- Check documentation and FAQs

---

## ✅ Checklist for New Developers

- [ ] Clone repository
- [ ] Install Java 17+
- [ ] Install PostgreSQL
- [ ] Create `training_institute` database
- [ ] Run `mvn clean install` to build
- [ ] Configure IDE (IntelliJ IDEA recommended)
- [ ] Read module documentation
- [ ] Set up application.yaml files
- [ ] Run services locally
- [ ] Test API endpoints

---

**Last Updated**: April 26, 2026  
**Version**: 1.0.0  
**Status**: ✅ Production Ready

