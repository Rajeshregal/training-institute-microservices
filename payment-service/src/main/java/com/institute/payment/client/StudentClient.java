package com.institute.payment.client;

import com.institute.payment.dto.CourseResponse;
import com.institute.payment.dto.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name="student-service"
     //   url="http://localhost:8082"
)
public interface StudentClient {
    @GetMapping("/api/student/code/{studentCode}")
    StudentResponse getStudentByCode(@PathVariable String studentCode);


}
