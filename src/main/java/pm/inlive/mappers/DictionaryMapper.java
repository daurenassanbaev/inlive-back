package pm.inlive.mappers;

import pm.inlive.dto.response.DictionaryResponse;
import pm.inlive.entities.Dictionary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {
    DictionaryResponse toDto(Dictionary dictionary);
}
