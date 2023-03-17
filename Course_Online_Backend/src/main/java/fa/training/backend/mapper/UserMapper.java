package fa.training.backend.mapper;

import fa.training.backend.entities.Feedback;
import fa.training.backend.entities.User;
import fa.training.backend.model.FeedbackModel;
import fa.training.backend.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserMapper {
    UserModel toModel(User user);
    User toEntity(UserModel userModel);
}
