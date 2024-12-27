package pl.tim3erland.interview.currencyexchanger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestApiResponse<T extends RestApiBody> {
    private HttpStatus status;
    private List<RestApiError> errors;
    private T body;
}
