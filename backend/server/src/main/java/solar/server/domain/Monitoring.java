package solar.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solar.server.domain.monitoring.Month;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monitoring {
    List<Month> month;
}


