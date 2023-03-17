package fa.training.backend.services;

import fa.training.backend.entities.Feedback;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.helpers.ServiceHelper;
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
	public List<Feedback> getFeedbacksByCourseId(int courseId, Integer pageNo, Integer pageSize, HashMap<String, String> orderHashMap) throws RecordNotFoundException {
		Pageable pageable = ServiceHelper.getPageable(pageNo, pageSize, orderHashMap);

		Page<Feedback> pagedResult = feedbackRepository.findAllByCourseId(courseId, pageable);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			throw new RecordNotFoundException("ads");
		}
	}

	public Feedback createFeedback(Feedback f) {
		return feedbackRepository.save(f);
	}

}
