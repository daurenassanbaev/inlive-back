package pm.inlive.services;

import pm.inlive.dto.request.AccSearchRequestCreateRequest;
import pm.inlive.dto.request.AccSearchRequestUpdatePriceRequest;
import pm.inlive.dto.response.AccSearchRequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccSearchRequestService {
    void createSearchRequest(AccSearchRequestCreateRequest request, String authorId);

    AccSearchRequestResponse getSearchRequestById(Long id);

    Page<AccSearchRequestResponse> getMySearchRequests(String authorId, Pageable pageable);

    void updateSearchRequestPrice(Long id, AccSearchRequestUpdatePriceRequest request, String authorId);

    void cancelSearchRequest(Long id, String authorId);
}
