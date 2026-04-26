package com.institute.payment.client;

import com.institute.payment.dto.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "course-service",
        url = "http://localhost:8084"
)
public interface CourseClient {

    @GetMapping("/api/course/code/{courseCode}")
    CourseResponse getCourseByCode(
            @PathVariable String courseCode
    );
}