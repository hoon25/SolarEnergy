package solar.server.domain.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hour {
    int year;
    int month;
    int day;
    int hour;
    double hpredPower;
    double hrealPower;
    String userId;
    int locCode;
}

