package org.ramazanmamyrbek.smarttailor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ResponseDto {

    @Schema(
            description = "Status code in the response"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;

    @Schema(
            description = "Response object in the response"
    )
    private Object reponseObject = "None";

    public ResponseDto(String statusCode, String statusMsg) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
    }

    public ResponseDto(String statusCode, String statusMsg, Object reponseObject) {
        this.statusCode = statusCode;
        this.statusMsg = statusMsg;
        this.reponseObject = reponseObject;
    }
}
