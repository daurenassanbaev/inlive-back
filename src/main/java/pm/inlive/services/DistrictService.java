package pm.inlive.services;

import pm.inlive.dto.response.DistrictResponse;

import java.util.List;

public interface DistrictService {
    List<DistrictResponse> getAllDistricts();

    List<DistrictResponse> getDistrictsByCity(Long cityId);
}
