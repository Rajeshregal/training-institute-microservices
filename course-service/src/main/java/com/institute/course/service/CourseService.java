package com.institute.course.service;

import com.institute.course.dto.CourseRequest;
import com.institute.course.dto.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse createCourse(CourseRequest request);

    CourseResponse getCourseById(Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse updateCourse(Long id, CourseRequest request);

    String deleteCourse(Long id);
}