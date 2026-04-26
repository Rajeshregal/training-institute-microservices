# Training Institute Microservices - Consolidated API Documentation

---

## 📋 Table of Contents
1. [Auth Service](#auth-service)
2. [Student Service](#student-service)
3. [Teacher Service](#teacher-service)
4. [Course Service](#course-service)
5. [Payment Service](#payment-service)
6. [Role-Based Access Summary](#role-based-access-summary)

---

## 🔐 Authentication Service
**Base URL:** `http://localhost:8081/api/auth`  
**Gateway Route:** `POST/GET /api/auth/**`  
**Access Level:** PUBLIC (No Authentication Required)

| # | HTTP Method | Endpoint | Description | Request Body | Response | Role Access | Port |
|---|---|---|---|---|---|---|---|
| 1 | POST | `/register` | Register a new user | RegisterRequest | String (token/message) | PUBLIC | 8081 |
| 2 | POST | `/login` | Authenticate user and get token | LoginRequest | AuthResponse | PUBLIC | 8081 |

**Request/Response Models:**
- **RegisterRequest:** username, email, password, role
- **LoginRequest:** email, password
- **AuthResponse:** token, username, email, role, expiresIn

---

## 👨‍🎓 Student Service
**Base URL:** `http://localhost:8082/api/student`  
**Gateway Route:** `GET/POST/PUT/DELETE /api/student/**`  
**Port:** 8082

| # | HTTP Method | Endpoint | Description | Request Body | Response Type | Role Access | Notes |
|---|---|---|---|---|---|---|---|
| 1 | POST | `/` | Create new student | StudentRequest | StudentResponse | ADMIN | Create a new student record |
| 2 | GET | `/{id}` | Get student by ID | N/A | StudentResponse | ADMIN, TRAINER, STUDENT | Individual access allowed for STUDENT role |
| 3 | GET | `/` | Get all students | N/A | List<StudentResponse> | ADMIN, TRAINER | Retrieve all student records |
| 4 | PUT | `/{id}` | Update student | StudentRequest | StudentResponse | ADMIN | Update existing student record |
| 5 | DELETE | `/{id}` | Delete student | N/A | String (message) | ADMIN | Delete student record |

**Request/Response Models:**
- **StudentRequest:** name, email, enrollmentDate, status, courseId
- **StudentResponse:** id, name, email, enrollmentDate, status, courseId, createdAt, updatedAt

---

## 👨‍🏫 Teacher Service
**Base URL:** `http://localhost:8083/api/teacher`  
**Gateway Route:** `GET/POST/PUT/DELETE /api/teacher/**`  
**Port:** 8083

| # | HTTP Method | Endpoint | Description | Request Body | Response Type | Role Access | Notes |
|---|---|---|---|---|---|---|---|
| 1 | POST | `/` | Create new teacher | TeacherRequest | TeacherResponse | ADMIN | Add a new teacher/trainer |
| 2 | GET | `/{id}` | Get teacher by ID | N/A | TeacherResponse | ADMIN, TRAINER | View teacher details |
| 3 | GET | `/` | Get all teachers | N/A | List<TeacherResponse> | ADMIN, TRAINER | List all teachers/trainers |
| 4 | PUT | `/{id}` | Update teacher | TeacherRequest | TeacherResponse | ADMIN | Modify teacher information |
| 5 | DELETE | `/{id}` | Delete teacher | N/A | String (message) | ADMIN | Remove teacher record |

**Request/Response Models:**
- **TeacherRequest:** name, email, department, qualifications, experience
- **TeacherResponse:** id, name, email, department, qualifications, experience, createdAt, updatedAt

---

## 📚 Course Service
**Base URL:** `http://localhost:8084/api/course`  
**Gateway Route:** `GET/POST/PUT/DELETE /api/course/**`  
**Port:** 8084

| # | HTTP Method | Endpoint | Description | Request Body | Response Type | Role Access | Notes |
|---|---|---|---|---|---|---|---|
| 1 | POST | `/` | Create new course | CourseRequest | CourseResponse | ADMIN | Create a new course |
| 2 | GET | `/{id}` | Get course by ID | N/A | CourseResponse | ADMIN, TRAINER, STUDENT | Public read access for all roles |
| 3 | GET | `/` | Get all courses | N/A | List<CourseResponse> | ADMIN, TRAINER, STUDENT | List available courses |
| 4 | PUT | `/{id}` | Update course | CourseRequest | CourseResponse | ADMIN | Modify course details |
| 5 | DELETE | `/{id}` | Delete course | N/A | String (message) | ADMIN | Remove course |

**Request/Response Models:**
- **CourseRequest:** courseName, description, duration, credits, instructor, startDate, endDate
- **CourseResponse:** id, courseName, description, duration, credits, instructor, startDate, endDate, createdAt, updatedAt

---

## 💳 Payment Service
**Base URL:** `http://localhost:8085/api/payment`  
**Gateway Route:** `GET/POST/PUT/DELETE /api/payment/**`  
**Port:** 8085

| # | HTTP Method | Endpoint | Description | Request Body | Response Type | Role Access | Notes |
|---|---|---|---|---|---|---|---|
| 1 | POST | `/` | Create new payment | PaymentRequest | PaymentResponse | ADMIN, STUDENT | Students can make payments |
| 2 | GET | `/{id}` | Get payment by ID | N/A | PaymentResponse | ADMIN, STUDENT | Individual payment verification |
| 3 | GET | `/` | Get all payments | N/A | List<PaymentResponse> | ADMIN | Admin view only for all payments |
| 4 | PUT | `/{id}` | Update payment | PaymentRequest | PaymentResponse | ADMIN | Modify payment records |
| 5 | DELETE | `/{id}` | Delete payment | N/A | String (message) | ADMIN | Remove payment entry |

**Request/Response Models:**
- **PaymentRequest:** studentId, amount, paymentDate, method, status, invoiceNumber
- **PaymentResponse:** id, studentId, amount, paymentDate, method, status, invoiceNumber, transactionId, createdAt, updatedAt

---

## 📊 API Gateway Configuration

| Service | Base URL | Gateway Port | Service Port | Path Pattern |
|---|---|---|---|---|
| API Gateway | - | 9090 | - | N/A |
| Auth Service | http://localhost:8081 | 9090 | 8081 | /api/auth/** |
| Student Service | http://localhost:8082 | 9090 | 8082 | /api/student/** |
| Teacher Service | http://localhost:8083 | 9090 | 8083 | /api/teacher/** |
| Course Service | http://localhost:8084 | 9090 | 8084 | /api/course/** |
| Payment Service | http://localhost:8085 | 9090 | 8085 | /api/payment/** |

---

## 🔑 Role-Based Access Summary

### Roles Defined in System:
1. **ADMIN** - Full system access
2. **TRAINER** (TEACHER) - Limited access to view data
3. **STUDENT** - Self-service and course access
4. **PUBLIC** - Unauthenticated users (Auth endpoints only)

### Access Matrix:

| Service | Endpoint Type | ADMIN | TRAINER | STUDENT | PUBLIC |
|---|---|---|---|---|---|
| **Auth** | Register | ✅ | ✅ | ✅ | ✅ |
| **Auth** | Login | ✅ | ✅ | ✅ | ✅ |
| **Student** | POST (Create) | ✅ | ❌ | ❌ | ❌ |
| **Student** | GET (Single) | ✅ | ✅ | ✅ (self) | ❌ |
| **Student** | GET (All) | ✅ | ✅ | ❌ | ❌ |
| **Student** | PUT (Update) | ✅ | ❌ | ❌ | ❌ |
| **Student** | DELETE | ✅ | ❌ | ❌ | ❌ |
| **Teacher** | POST (Create) | ✅ | ❌ | ❌ | ❌ |
| **Teacher** | GET (Single) | ✅ | ✅ | ❌ | ❌ |
| **Teacher** | GET (All) | ✅ | ✅ | ❌ | ❌ |
| **Teacher** | PUT (Update) | ✅ | ❌ | ❌ | ❌ |
| **Teacher** | DELETE | ✅ | ❌ | ❌ | ❌ |
| **Course** | POST (Create) | ✅ | ❌ | ❌ | ❌ |
| **Course** | GET (Single) | ✅ | ✅ | ✅ | ❌ |
| **Course** | GET (All) | ✅ | ✅ | ✅ | ❌ |
| **Course** | PUT (Update) | ✅ | ❌ | ❌ | ❌ |
| **Course** | DELETE | ✅ | ❌ | ❌ | ❌ |
| **Payment** | POST (Create) | ✅ | ❌ | ✅ | ❌ |
| **Payment** | GET (Single) | ✅ | ❌ | ✅ | ❌ |
| **Payment** | GET (All) | ✅ | ❌ | ❌ | ❌ |
| **Payment** | PUT (Update) | ✅ | ❌ | ❌ | ❌ |
| **Payment** | DELETE | ✅ | ❌ | ❌ | ❌ |

---

## 🛡️ Security Implementation

### Authentication Flow:
1. User registers via `/api/auth/register`
2. User logs in via `/api/auth/login` → Receives JWT Token
3. Token is used in Authorization header for subsequent requests
4. Each endpoint validates token and checks role permissions using `@PreAuthorize` annotation

### Security Annotations Used:
```
@PreAuthorize("hasRole('ADMIN')")                    - ADMIN role only
@PreAuthorize("hasAnyRole('ADMIN','TRAINER')")       - ADMIN or TRAINER roles
@PreAuthorize("hasAnyRole('ADMIN','TRAINER','STUDENT')")  - Multiple roles
```

### Request Headers Required:
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

---

## 📡 API Calling Examples

### Example 1: Login
```bash
POST http://localhost:9090/api/auth/login
Body: {
  "email": "user@institute.com",
  "password": "password123"
}
Response: {
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "username": "john_doe",
  "email": "user@institute.com",
  "role": "STUDENT",
  "expiresIn": 86400
}
```

### Example 2: Get All Courses (STUDENT Role)
```bash
GET http://localhost:9090/api/course
Headers:
  Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
  Content-Type: application/json

Response: [
  {
    "id": 1,
    "courseName": "Java Programming",
    "description": "Learn Java basics",
    "duration": "8 weeks",
    "credits": 4,
    "instructor": "John Smith",
    "startDate": "2024-05-01",
    "endDate": "2024-06-30",
    "createdAt": "2024-04-20T10:30:00"
  }
]
```

### Example 3: Create Payment (STUDENT Role)
```bash
POST http://localhost:9090/api/payment
Headers:
  Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
  Content-Type: application/json

Body: {
  "studentId": 5,
  "amount": 500.00,
  "paymentDate": "2024-04-25",
  "method": "CREDIT_CARD",
  "status": "COMPLETED",
  "invoiceNumber": "INV-2024-001"
}

Response: {
  "id": 10,
  "studentId": 5,
  "amount": 500.00,
  "paymentDate": "2024-04-25",
  "method": "CREDIT_CARD",
  "status": "COMPLETED",
  "invoiceNumber": "INV-2024-001",
  "transactionId": "TXN-2024-12345",
  "createdAt": "2024-04-25T14:30:00"
}
```

---

## 📝 Summary Statistics

| Metric | Count |
|---|---|
| Total Microservices | 6 (1 Gateway + 5 Services) |
| Total Endpoints | 26 |
| Total CRUD Operations | 25 |
| Authentication Endpoints | 2 |
| Unique Roles | 4 |
| Services with Role-Based Access | 5 |

---

## 🚀 How to Access APIs Through Gateway

All APIs should be accessed through the API Gateway at port **9090**:

**Pattern:** `http://localhost:9090/api/{service}/{endpoint}`

### Examples:
- Register: `http://localhost:9090/api/auth/register`
- Get Students: `http://localhost:9090/api/student`
- Create Course: `http://localhost:9090/api/course`
- Make Payment: `http://localhost:9090/api/payment`
- Update Teacher: `http://localhost:9090/api/teacher/{id}`

Direct service access (bypassing gateway) is also possible:
- Student Service: `http://localhost:8082/api/student`
- Teacher Service: `http://localhost:8083/api/teacher`
- Course Service: `http://localhost:8084/api/course`
- Payment Service: `http://localhost:8085/api/payment`
- Auth Service: `http://localhost:8081/api/auth`

---

**Last Updated:** April 26, 2026  
**Document Version:** 1.0

