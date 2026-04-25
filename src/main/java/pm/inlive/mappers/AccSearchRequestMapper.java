package pm.inlive.mappers;

import pm.inlive.dto.response.AccSearchRequestResponse;
import pm.inlive.dto.response.DistrictResponse;
import pm.inlive.dto.response.DictionaryResponse;
import pm.inlive.entities.AccSearchRequest;
import pm.inlive.entities.Dictionary;
import pm.inlive.entities.District;
import pm.inlive.entities.User;
import pm.inlive.entities.enums.DictionaryKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AccSearchRequestMapper {

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", expression = "java(getAuthorName(request))")
    @Mapping(target = "checkInDate", expression = "java(request.getFromDate() != null ? request.getFromDate().toLocalDate() : null)")
    @Mapping(target = "checkOutDate", expression = "java(request.getToDate() != null ? request.getToDate().toLocalDate() : null)")
    @Mapping(target = "status", expression = "java(request.getStatus() != null ? request.getStatus().name() : null)")
    @Mapping(target = "unitTypes", expression = "java(extractUnitTypes(request))")
    @Mapping(target = "districts", expression = "java(extractDistricts(request))")
    @Mapping(target = "services", expression = "java(extractDictionariesByKey(request, pm.inlive.entities.enums.DictionaryKey.ACC_SERVICE))")
    @Mapping(target = "conditions", expression = "java(extractDictionariesByKey(request, pm.inlive.entities.enums.DictionaryKey.ACC_CONDITION))")
    AccSearchRequestResponse toDto(AccSearchRequest request);

    @Mapping(target = "key", expression = "java(dictionary.getKey().name())")
    DictionaryResponse dictionaryToDto(Dictionary dictionary);

    @Mapping(target = "cityId", source = "city.id")
    DistrictResponse districtToDto(District district);

    default String getAuthorName(AccSearchRequest request) {
        if (request.getAuthor() == null) return null;
        User author = request.getAuthor();
        if (author.getFirstName() != null && author.getLastName() != null) {
            return author.getFirstName() + " " + author.getLastName();
        }
        return author.getUsername();
    }

    default List<String> extractUnitTypes(AccSearchRequest request) {
        if (request.getUnitTypes() == null) {
            return List.of();
        }
        return request.getUnitTypes().stream()
                .map(ut -> ut.getUnitType().name())
                .collect(Collectors.toList());
    }

    default List<DistrictResponse> extractDistricts(AccSearchRequest request) {
        if (request.getDistricts() == null) {
            return List.of();
        }
        return request.getDistricts().stream()
                .map(rd -> districtToDto(rd.getDistrict()))
                .collect(Collectors.toList());
    }

    default List<DictionaryResponse> extractDictionariesByKey(AccSearchRequest request, DictionaryKey key) {
        if (request.getDictionaries() == null) {
            return List.of();
        }
        return request.getDictionaries().stream()
                .filter(rd -> rd.getDictionary() != null && rd.getDictionary().getKey() == key)
                .map(rd -> dictionaryToDto(rd.getDictionary()))
                .collect(Collectors.toList());
    }
}
