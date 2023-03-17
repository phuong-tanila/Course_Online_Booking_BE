package fa.training.backend.mapper;

import fa.training.backend.entities.User;
import fa.training.backend.model.RegisterRequestModel;
import fa.training.backend.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserRegisterMapper {
    RegisterRequestModel toModel(User user);
    User toEntity(RegisterRequestModel registerRequestModel);
}
