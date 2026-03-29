package pm.inlive.services;

import pm.inlive.dto.params.AccommodationSearchParams;
import pm.inlive.dto.request.AccommodationCreateRequest;
import pm.inlive.dto.request.AccommodationDictionariesUpdateRequest;
import pm.inlive.dto.request.AccommodationUpdateRequest;
import pm.inlive.dto.response.AccSearchRequestResponse;
import pm.inlive.dto.response.AccommodationResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AccommodationService {

    void createAccommodation(AccommodationCreateRequest request, String createdBy);

    AccommodationResponse getAccommodationById(Long id);

    Page<AccommodationResponse> searchWithParams(AccommodationSearchParams accommodationSearchParams, Pageable pageable);

    @Transactional
    void updateAccommodation(Long id, AccommodationUpdateRequest request);

    void updateDictionaries(Long accommodationId, AccommodationDictionariesUpdateRequest request);

    void updateAccommodationPhotos(Long id, List<MultipartFile> photoUrls);

    void deleteAccommodationPhotos(Long id, List<String> photoUrls);

    @Transactional
    void deleteAccommodation(Long id);

    @Transactional
    void approveAccommodation(Long id, String approvedBy);

    @Transactional
    void rejectAccommodation(Long id, String rejectedBy);

    Page<AccommodationResponse> getAccommodationsByOwner(String ownerId, AccommodationSearchParams accommodationSearchParams, Pageable pageable);

    Page<AccSearchRequestResponse> getRelevantRequests(Long accommodationId, Pageable pageable);
}
