package fa.training.backend.controller;

import java.util.*;

import fa.training.backend.entities.Category;
import fa.training.backend.entities.Feedback;
import fa.training.backend.exception.RecordNotFoundException;

import fa.training.backend.mapper.CategoryMapper;
import fa.training.backend.mapper.CourseMapper;
import fa.training.backend.mapper.FeedbackMapper;
import fa.training.backend.model.CourseModel;
import fa.training.backend.model.FeedbackModel;
import fa.training.backend.repositories.CourseRepository;
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
import org.springframework.web.bind.annotation.*;

import fa.training.backend.entities.Course;
import fa.training.backend.services.CategoryService;
import fa.training.backend.services.CourseService;

import org.springframework.data.domain.Sort.Order;

@RestController
@Slf4j
@RequestMapping("/courses")
//@RequestMapping(path="/JSON", produces="application/json")
public class CourseController {
	@Autowired
	public CourseService courseService;
	@Autowired
	public CategoryService categoryService;
    @Autowired
    public FeedbackService feedbackService;
	@Autowired
	private CourseMapper courseMapper;
    @Autowired
    public CategoryMapper categoryMapper;
    @Autowired
    public FeedbackMapper feedbackMapper;
    @Autowired
    public SortOrder sortOrder;

    //api for history
    @GetMapping("/history/{id}")
    ResponseEntity<CourseModel> getCourseById(@PathVariable("id") int id) throws RecordNotFoundException {
        Course course = courseService.findById(id);
        if (course != null) {
            CourseModel courseModel = courseMapper.toModel(course);
            return new ResponseEntity<CourseModel>(courseModel, new HttpHeaders(), HttpStatus.OK);
        } else return new ResponseEntity<CourseModel>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    //api for course-detail
    @GetMapping("/{id}")
    ResponseEntity<CourseModel> getCourseByIdIsActive(@PathVariable("id") int id) throws RecordNotFoundException {
        Course course = courseService.findById(id);
        CourseModel courseModel= courseMapper.toModel(course);
        HashMap<String, String> orders = new HashMap<>();
        orders.put("createAt", "desc");
        List<Feedback> feedbacks = feedbackService.getFeedbacksByCourseId(id, 1, 5, orders);
        System.out.println(123);
        System.out.println(course.getChapters());

        System.out.println(456);
        System.out.println(courseModel.getChapters());
        courseModel.chapters.stream().sorted((c1, c2) -> {
            return c1.chapterIndex - c2.chapterIndex;
        });
        Set<FeedbackModel> feedbackModels = new HashSet<>(feedbackMapper.toModelList(feedbacks));
        courseModel.setFeedbacks(feedbackModels);
        return new ResponseEntity<CourseModel>(courseModel, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/slider-popular")
    public ResponseEntity<List<CourseModel>> getCoursesPopular(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "soldCount") String sortBy) {
        List<Course> listCourses = courseService.getAllCourses(pageNo, pageSize, sortBy);
        List<CourseModel> result = new ArrayList<>();
        listCourses.forEach(c -> result.add(courseMapper.toModel(c)));
        return new ResponseEntity<List<CourseModel>>(result, new HttpHeaders(), HttpStatus.OK);
    }
    @GetMapping("/slider-newest")
    public ResponseEntity<List<CourseModel>> getCoursesNewest(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "createDate") String sortBy) {
        List<Course> listCourses = courseService.getAllCourses(pageNo, pageSize, sortBy);
        List<CourseModel> result = new ArrayList<>();
        listCourses.forEach(c -> result.add(courseMapper.toModel(c)));
        return new ResponseEntity<List<CourseModel>>(result, new HttpHeaders(), HttpStatus.OK);
    }

//    @GetMapping("/list")
//    public ResponseEntity<List<CourseModel>> getAllCourses(
//            @RequestParam(defaultValue = "0") Integer pageNo,
//            @RequestParam(defaultValue = "20") Integer pageSize,
//            @RequestParam(defaultValue = "tuitionFee") String sortBy) {
//        List<Course> listCourses = courseService.getAllCourses(pageNo, pageSize, sortBy);
//        List<CourseModel> result = new ArrayList<>();
//        listCourses.forEach(c -> result.add(courseMapper.toModel(c)));
//        return new ResponseEntity<List<CourseModel>>(result, new HttpHeaders(), HttpStatus.OK);
//    }

//    @GetMapping("/")
//	@GetMapping("/sortbyrating")
//    public ResponseEntity<List<Course>> sortByRating(
//                        @RequestParam(defaultValue = "0") Integer pageNo,
//                        @RequestParam(defaultValue = "5") Integer pageSize,
//                        @RequestParam(defaultValue = "rating") String sortBy)
//    {
//        List<Course> listCourses = courseService.sortCoursesByRating(pageNo, pageSize, sortBy);
//
//        return new ResponseEntity<List<Course>>(listCourses, new HttpHeaders(), HttpStatus.OK);
//    }
//

//	@GetMapping(value = "/categories")
//	public List<Course> getCourseByCategory(
//			@PathParam("categories") List<String> categoryName,
//			@PathParam("pageNo")
//	){
//		List<Category> categories = new ArrayList<>();
//		categoryName.forEach(cateName -> {
//			categories.add(categoryService.getCategoryByName(cateName));
//		});
//		List<Course> list= courseService.getCourseByCategory(categories, pageable);
//		return  list;
//	}


//	@GetMapping(value = "/categories/{categoryName}")
//	public List<Course> getCourseByCategory(String categoryName){
//		Category category= categoryService.getCategoryByName(categoryName);
//		List<Course> list= courseService.findCourseByCategory(category);
//		return list;
//	}

    /**
     * @apiNote wait for checking naming conventions
     */
    @GetMapping("/get-course-by-name")
    public ResponseEntity<List<CourseModel>> getCourseByName(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String[] sortBy,
            @RequestParam(defaultValue = "desc") String[] diretions,
            @RequestParam(defaultValue = "java") String courseName
    ) throws RecordNotFoundException {
        if (sortBy.length != diretions.length) {
            return new ResponseEntity("the length of sortBy and diretions are not identical", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        HashMap<String, String> orderHashMap = new HashMap<>();
        for (int i = 0; i < sortBy.length; i++) {
            String currentProperty = sortBy[i];
            String currentDirection = diretions[i];
            orderHashMap.put(currentProperty, currentDirection);
        }
        List<Course> list = courseService.findByCourseName(pageNo, pageSize, orderHashMap, courseName);
        List<CourseModel> results = new ArrayList<>();
        list.forEach(course -> results.add(courseMapper.toModel(course)));
        return new ResponseEntity<List<CourseModel>>(results, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/a")
    public ResponseEntity<List<CourseModel>> getCourseCustom() {
        List<Course> courses = courseService.customGetAll();
        List<CourseModel> result = new ArrayList<>();
        courses.forEach(c -> {
            result.add(courseMapper.toModel(c));
        });
        return new ResponseEntity<List<CourseModel>>(result, new HttpHeaders(), HttpStatus.OK);
    }

    private Sort.Direction getSortDirection(String direction) {
        return direction.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    @GetMapping("/b")
    public ResponseEntity<List<CourseModel>> test(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder.getSortOrder(sort)));
            List<Course> courses = courseService.getCoursesPagination(pageable);
            List<CourseModel> result = courseMapper.toListModel(courses);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}