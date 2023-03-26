package fa.training.backend.controller;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.Feedback;
import fa.training.backend.entities.User;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.mapper.CategoryMapper;
import fa.training.backend.mapper.CourseMapper;
import fa.training.backend.mapper.FeedbackMapper;
import fa.training.backend.model.CourseModel;
import fa.training.backend.model.ExceptionResponse;
import fa.training.backend.model.FeedbackModel;
import fa.training.backend.services.CategoryService;
import fa.training.backend.services.CourseService;
import fa.training.backend.services.FeedbackService;
import fa.training.backend.util.SortOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin")
//@RequestMapping(path="/JSON", produces="application/json")
public class AdminController {

    @Autowired
    public CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    public FeedbackMapper feedbackMapper;

    @PostMapping("/create")
//    @RolesAllowed("AD")
    ResponseEntity<CourseModel> createCourse(
            @Valid @RequestBody CourseModel courseModel,
            Principal principal
    ) {
        Course course = courseMapper.toEntity(courseModel);
        User createBy = (User) ((Authentication) principal).getPrincipal();
        course.setCreateBy(createBy);
        course.setCreateDate(new Date());
        course.setLastUpdateDate(new Date());
        course.setLastUpdateUser(createBy);
        course = courseService.createCourseOrUpdate(course);
        return new ResponseEntity<>(courseMapper.toModel(course), HttpStatus.CREATED);
    }

    @PutMapping("/update")
//    @RolesAllowed("AD")
    ResponseEntity updateCourse(@Valid @RequestBody Course course) {
        course.setLastUpdateDate(new Date());
//        course.setLastUpdateUser(admin);
        courseService.createCourseOrUpdate(course);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
//    @RolesAllowed("AD")
    ResponseEntity deleteCourse(@Valid @PathVariable("id") int courseId) {
//        courseService.deleteCourse(courseId);
        try {
            Course course = courseService.findById(courseId);
            course.setStatus(false);
            courseService.createCourseOrUpdate(course);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RecordNotFoundException ex) {
            ExceptionResponse res = new ExceptionResponse(
                    "Not found",
                    "The specified id is not found"
            );
            return new ResponseEntity<ExceptionResponse>(
                    res, HttpStatus.NOT_FOUND
            );
        }
    }

//    @PutMapping("/c")
//    public ResponseEntity<CourseModel> test2(@RequestBody Course course) {
//        try {
//            Course c = courseService.createCourseOrUpdate(course);
//            CourseModel result = courseMapper.toModel(c);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PatchMapping("/d")
//    public ResponseEntity<CourseModel> test3(@RequestBody Course course) {
//        try {
//            Course c = courseService.createCourseOrUpdate(course);
//            CourseModel result = courseMapper.toModel(c);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
