package pm.inlive.dto.request;

import pm.inlive.entities.enums.DictionaryKey;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DictionaryUpdateRequest {
    @NotNull(message = "{validation.dictionary.key.required}")
    private DictionaryKey key;

    @Size(max = 255, message = "{validation.dictionary.value.size}")
    private String value;
}
