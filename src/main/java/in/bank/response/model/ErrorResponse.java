package in.bank.response.model;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final int status;
    private final String message;
    private String details;
    private List<ValidatonError> errors;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ValidatonError {
        private String field;
        private String message;
    }

    public void addValidationError(String field, String message) {
        if (Objects.isNull(errors)) {
            errors = new ArrayList<>();
        }
        ValidatonError error = new ValidatonError(field, message);
        errors.add(error);
    }

}
