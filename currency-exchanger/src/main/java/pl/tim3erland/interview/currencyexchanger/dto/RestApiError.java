package pl.tim3erland.interview.currencyexchanger.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestApiError {
    private String errorCode;
    private String errorMessage;
}
