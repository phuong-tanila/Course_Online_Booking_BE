package fa.training.backend.services;

import java.util.*;

import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.helpers.ServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import fa.training.backend.entities.Category;
import fa.training.backend.entities.Course;
import fa.training.backend.repositories.CourseRepository;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    FeedbackService feedbackService;
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
    public List<Course> findAllSort(Sort sort) {
        return courseRepository.findAll(sort);
    }

    public List<Course> getAllCourses(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Course> pagedResult = courseRepository.findAll(pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Course>();
        }
    }

    public List<Course> getCoursesPagination(Pageable pageable) {
        Page<Course> pagedResult = courseRepository.findAll(pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Course>();
        }
    }

    public List<Course> findByCourseName(Integer pageNo, Integer pageSize, HashMap<String, String> orderHashMap, String courseName) {
        Pageable pageable = ServiceHelper.getPageable(pageNo, pageSize, orderHashMap);
        return courseRepository.findByCourseNameIgnoreCaseContaining(courseName, pageable);
    }

    public Course findById(int id) throws RecordNotFoundException {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
			System.out.println(course.get().chapters);
			return course.get();
		} else {
			throw new RecordNotFoundException("No course exist for given id");
		}
       
    }

    public Optional<Course> findByIdIsActive(int id) {
        Optional<Course> course = courseRepository.findWithStartDateTimeBeforePresent(new Date(), id);
        return course;
    }

    public List<Course> customGetAll() {
        return courseRepository.customGetCouse();
    }

    public List<Course> sortCoursesByRating(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<Course> pagedResult = courseRepository.findAll(pageable);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Course>();
        }
    }

    public List<Course> findCourseByCategory(int categoryId, Pageable pageable) {
        List<Course> courses = courseRepository.findCoursesByCategory(categoryId, pageable);
        return courses;
    }

    public List<Course> findCourseByCategory(List<Category> categories) {
        List<Course> courses = courseRepository.findCourseByCategoriesIn(categories);
        return courses;
    }
    public int totalCourseByCate(int categoryId){
        return courseRepository.countAllCourses(categoryId);
    }

    public List<Course> getCoursesByCategoryId(int id) {
        return courseRepository.findCoursesByCategoriesId(id);
    }
    public List<Course> getCoursesByTeacherId(int id){
        return courseRepository.findCoursesByTeacherId(id);
    }
}
