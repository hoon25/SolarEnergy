package solar.server.domain.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    int year;
    int month;
    int day;
    double dpredPower;
    double drealPower;
    String userId;
    int locCode;
}
