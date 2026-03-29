package pm.inlive.services;

import pm.inlive.dto.response.CityResponse;

import java.util.List;

public interface CityService {
    List<CityResponse> getAllCities();
}
