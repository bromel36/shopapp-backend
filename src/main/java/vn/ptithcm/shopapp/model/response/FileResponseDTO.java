package vn.ptithcm.shopapp.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Builder
@Getter
@Setter
public class FileResponseDTO {
    private String fileName;
    private Instant uploadedTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fileLink;
}
