package pm.inlive.mappers;

import pm.inlive.dto.response.UserResponse;
import pm.inlive.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface UserMapper {
    @Mapping(target = "photoUrl", expression = "java(imageMapper.getPathToUserPhoto(user))")
    UserResponse toDto(User user, ImageMapper imageMapper);
}
