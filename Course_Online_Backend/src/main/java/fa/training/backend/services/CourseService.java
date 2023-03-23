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

    public List<Course> findAll(Sort sort) {
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

    public List<Course> getCoursesByCategory(Pageable pageable) {
        Page<Course> pagedResult = courseRepository.findAll(pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Course>();
        }
    }

    
    public void softDeleteCourse(Course course){
//        courseRepository.
    }
    public List<Course> findByCourseName(Integer pageNo, Integer pageSize, HashMap<String, String> orderHashMap, String courseName) {
        Pageable pageable = ServiceHelper.getPageable(pageNo, pageSize, orderHashMap);
        return courseRepository.findByCourseNameIgnoreCaseContaining(courseName, pageable);
    }

    //	public Course findById(int id) throws RecordNotFoundException {
//		Optional<Course> course = courseRepository.findById(id);
//		if (course.isPresent()) {
//			return course.get();
//		} else {
//			throw new RecordNotFoundException("No course exist for given id");
//		}
//	}
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

    public List<Course> searchCoursesByName(String name, Pageable pageable) {
        List<Course> courses = courseRepository.searchCoursesByName(name, new Date(), pageable);
        return courses;
    }

    public List<Course> findCourseByCategory(List<Category> categories) {
        List<Course> courses = courseRepository.findCourseByCategoriesIn(categories);
        return courses;
    }

    //	public List<Course> sortByRating
    public int totalCourseByCate(int categoryId) {
        return courseRepository.countAllCoursesByCate(categoryId);
    }

    public int totalCourse() {
        return courseRepository.countAllCourse();
    }

    public int totalCourseByName(String name) {
        return courseRepository.countCourseByName(name, new Date());
    }
    public Course createCourseOrUpdate(Course course){
        return courseRepository.save(course);
    }
    public void deleteCourse(int courseId){
        courseRepository.deleteById(courseId);
    }
}
