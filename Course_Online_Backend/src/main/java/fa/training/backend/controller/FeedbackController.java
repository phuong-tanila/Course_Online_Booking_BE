package fa.training.backend.controller;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.Feedback;
import fa.training.backend.entities.User;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.mapper.FeedbackMapper;
import fa.training.backend.model.FeedbackModel;
import fa.training.backend.services.CourseService;
import fa.training.backend.services.FeedbackService;
import fa.training.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FeedbackController {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    /*Show tat ca feedback*/
    @GetMapping("/list-feedback")
    public List<FeedbackModel> getAllFeedback() {
        List<FeedbackModel> modelList = new ArrayList<>();
        List<Feedback> feedbacks = feedbackService.findAll();
        for (Feedback feedback : feedbacks) {
            FeedbackModel feedbackModel = feedbackMapper.toModel(feedback);
            modelList.add(feedbackModel);
        }
        return modelList;
    }

    /*Show feedback cua 1 course theo courseid*/
    @GetMapping("/feedback-by-course/{id}")
    public List<FeedbackModel> getFeedbackByCourse(@PathVariable("id") int id) throws RecordNotFoundException {
        Course course = courseService.findById(id);
        List<FeedbackModel> modelList = new ArrayList<>();
        List<Feedback> feedbacks = feedbackService.getFeedbackByCourse(course);
        for (Feedback feedback : feedbacks) {
            FeedbackModel feedbackModel = feedbackMapper.toModel(feedback);
            modelList.add(feedbackModel);
        }
        return modelList;
    }
    /*Show feedback cua 1 user theo userId*/
    @GetMapping("/feedback-by-user/{id}")
    public List<FeedbackModel> getFeedbackByUser(@PathVariable("id") int id) throws RecordNotFoundException {
        User user = userService.findById(id);
        List<FeedbackModel> modelList = new ArrayList<>();
        List<Feedback> feedbacks = feedbackService.getFeedbackByUser(user);
        for (Feedback feedback : feedbacks) {
            FeedbackModel feedbackModel = feedbackMapper.toModel(feedback);
            modelList.add(feedbackModel);
        }
        return modelList;
    }
}