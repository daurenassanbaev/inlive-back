package pm.inlive.services.impl;

import pm.inlive.dto.response.CityResponse;
import pm.inlive.entities.City;
import pm.inlive.mappers.CityMapper;
import pm.inlive.repositories.CityRepository;
import pm.inlive.services.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper mapper;

    @Override
    public List<CityResponse> getAllCities() {
        log.info("Fetching all cities");
        List<City> cities = cityRepository.findAllByIsDeletedFalse();
        return cities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
