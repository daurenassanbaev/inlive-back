package pm.inlive.services;

import pm.inlive.dto.params.DictionarySearchParams;
import pm.inlive.dto.request.DictionaryCreateRequest;
import pm.inlive.dto.request.DictionaryUpdateRequest;
import pm.inlive.dto.response.DictionaryResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictionaryService {

    void createDictionary(DictionaryCreateRequest request);

    DictionaryResponse getDictionaryById(Long id);

    Page<DictionaryResponse> searchWithParams(DictionarySearchParams dictionarySearchParams, Pageable pageable);

    @Transactional
    void updateDictionary(Long id, DictionaryUpdateRequest request);

    @Transactional
    void deleteDictionary(Long id);
}
