package fa.training.backend.controller;

import fa.training.backend.entities.Category;
import fa.training.backend.entities.Course;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.mapper.CategoryMapper;
import fa.training.backend.mapper.CourseMapper;
import fa.training.backend.mapper.FeedbackMapper;
import fa.training.backend.model.CategoryModels.CategoryModelDetail;
import fa.training.backend.model.CourseModel;
import fa.training.backend.services.CategoryService;
import fa.training.backend.services.CourseService;
import fa.training.backend.util.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public CategoryMapper categoryMapper;
    @Autowired
    public CourseService courseService;
    @Autowired
    public CourseMapper courseMapper;
    @Autowired
    public SortOrder sortOrder;

    @GetMapping("/{id}")
    public ResponseEntity<List<CourseModel>> getCourseByCategory(@PathVariable("id") int id,
                                                   @RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "20") Integer pageSize,
                                                   @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder.getSortOrder(sort)));
            List<Course> courses = courseService.findCourseByCategory(id, pageable);
            List<CourseModel> result = courseMapper.toListModel(courses);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("total-course/{id}")
    public int totalCourseByCate (
            @PathVariable("id") int id
    ){
        try {
            return courseService.totalCourseByCate(id);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CourseModel>> getAllCourses(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Course> listCourses = courseService.getAllCourses(pageNo, pageSize, sortBy);
        List<CourseModel> result = courseMapper.toListModel(listCourses);
        return new ResponseEntity<List<CourseModel>>(result, new HttpHeaders(), HttpStatus.OK);
    }


}
