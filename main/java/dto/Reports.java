package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reports {
    private String reportID;
    private String reportType;
    private LocalDate reportDate;
    private LocalTime reportTime;
}
