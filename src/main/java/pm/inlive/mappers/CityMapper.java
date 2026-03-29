package pm.inlive.mappers;

import pm.inlive.dto.response.CityResponse;
import pm.inlive.entities.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityResponse toDto(City city);
}
