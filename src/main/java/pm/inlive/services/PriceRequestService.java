package pm.inlive.services;

import pm.inlive.dto.request.PriceRequestCreateRequest;
import pm.inlive.dto.request.PriceRequestUpdateRequest;
import pm.inlive.dto.request.PriceRequestClientResponseRequest;
import pm.inlive.dto.response.PriceRequestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PriceRequestService {
    void createPriceRequest(PriceRequestCreateRequest request);

    void updatePriceRequest(Long priceRequestId, PriceRequestUpdateRequest request);

    void hidePriceRequest(Long priceRequestId);

    PriceRequestResponse getPriceRequestById(Long id);

    Page<PriceRequestResponse> getPriceRequestsByUnitId(Long unitId, Pageable pageable);

    Page<PriceRequestResponse> getPriceRequestsBySearchRequestId(Long searchRequestId, Pageable pageable);

    void respondToPriceRequest(Long priceRequestId, PriceRequestClientResponseRequest request, String clientId);
}
