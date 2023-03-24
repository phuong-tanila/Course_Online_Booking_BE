package fa.training.backend.services;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.Feedback;
import fa.training.backend.entities.User;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.helpers.ServiceHelper;
import fa.training.backend.mapper.FeedbackMapper;
import fa.training.backend.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;
	@Autowired
	FeedbackMapper feedbackMapper;
	public List<Feedback> findAll() {
		return feedbackRepository.findAll();
	}
	public List<Feedback> getFeedbacksByCourseId(int courseId, Integer pageNo, Integer pageSize, HashMap<String, String> orderHashMap) throws RecordNotFoundException {
		Pageable pageable = ServiceHelper.getPageable(pageNo, pageSize, orderHashMap);

		Page<Feedback> pagedResult = feedbackRepository.findAllByCourseId(courseId, pageable);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<>();
		}
	}

	public Feedback createFeedback(Feedback f) {
		return feedbackRepository.save(f);
	}
	public List<Feedback> getFeedbackByCourse(Course course) {
		return feedbackRepository.findFeedbackByCourse(course);
	}
	public List<Feedback> sortFeedbackByCourse(Course course) {
		Sort ratingSort = Sort.by("rating").descending();
		return feedbackRepository.findFeedbackByCourse(course, ratingSort);
	}
	public List<Feedback> getFeedbackByUser(User user) {
		return feedbackRepository.findFeedbackByUser(user);
	}
}
