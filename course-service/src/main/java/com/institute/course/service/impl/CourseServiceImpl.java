package com.institute.course.service.impl;

import com.institute.course.dto.CourseRequest;
import com.institute.course.dto.CourseResponse;
import com.institute.course.entity.Course;
import com.institute.course.repository.CourseRepository;
import com.institute.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponse createCourse(CourseRequest request) {

        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }

        Course course = Course.builder()
                .courseCode(request.getCourseCode())
                .courseName(request.getCourseName())
                .duration(request.getDuration())
                .fee(request.getFee())
                .teacherCode(request.getTeacherCode())
                .build();

        courseRepository.save(course);

        return mapToResponse(course);
    }

    @Override
    public CourseResponse getCourseById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        return mapToResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CourseResponse updateCourse(
            Long id,
            CourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        course.setCourseName(request.getCourseName());
        course.setDuration(request.getDuration());
        course.setFee(request.getFee());
        course.setTeacherCode(request.getTeacherCode());

        courseRepository.save(course);

        return mapToResponse(course);
    }

    @Override
    public String deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        courseRepository.delete(course);

        return "Course deleted successfully";
    }

    private CourseResponse mapToResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .courseCode(course.getCourseCode())
                .courseName(course.getCourseName())
                .duration(course.getDuration())
                .fee(course.getFee())
                .teacherCode(course.getTeacherCode())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .status(course.getStatus())
                .build();
    }
    @Override
    public CourseResponse getCourseByCode(
            String courseCode) {

        Course course = courseRepository
                .findByCourseCode(courseCode)
                .orElseThrow(() -> new RuntimeException(
                        "Course not found with code: " + courseCode
                ));

        return mapToResponse(course);
    }
}