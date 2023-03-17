package fa.training.backend.mapper;

import fa.training.backend.entities.Feedback;
import fa.training.backend.model.FeedbackModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface FeedbackMapper {
    FeedbackModel toModel(Feedback feedback);
    Feedback toEntity(FeedbackModel feedbackModel);

    List<Feedback> toEntityList(List<FeedbackModel> models);

    List<FeedbackModel> toModelList(List<Feedback> entity);

}
