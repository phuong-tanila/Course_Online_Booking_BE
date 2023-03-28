package fa.training.backend.controller;

import fa.training.backend.mapper.FeedbackMapper;
import fa.training.backend.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackMapper feedbackMapper;
    @Autowired
    private FeedbackService feedbackService;
//    @GetMapping("/feedbacks/{courseId}")
//
//    public ResponseEntity<List<FeedbackModel>> getAllByUserId(
//            @PathVariable("courseId") Integer courseId,
//            @RequestParam(defaultValue = "0") int pageNo,
//            @RequestParam(defaultValue = "5") Integer pageSize,
//            @RequestParam(defaultValue = "rating") String[] sortBy,
//            @RequestParam(defaultValue = "desc") String[] direction
//    )
//    {
//        List<Feedback> feedbacks = feedbackService.getAllFeedbacks(courseId, pageNo, pageSize, sortBy, direction);
//        List<FeedbackModel> feedbackModels = new ArrayList<>();
//        feedbacks.forEach(f -> feedbackModels.add(mapStructConverter.toModel(f)));
//        return new ResponseEntity<List<FeedbackModel>>(feedbackModels, new HttpHeaders(), HttpStatus.OK);
//    }

    @GetMapping("/rating-star")
    public List<Integer> totalStar(){
        List<Integer> totalEachRating = new ArrayList<Integer>();
        for (int i = 1; i <= 5; i++) {
            int result = feedbackService.countStar(i);
            totalEachRating.add(result);
        }
        return totalEachRating;
    }
}
