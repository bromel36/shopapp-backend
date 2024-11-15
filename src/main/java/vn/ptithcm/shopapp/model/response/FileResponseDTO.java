package vn.ptithcm.shopapp.model.response;

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
}
