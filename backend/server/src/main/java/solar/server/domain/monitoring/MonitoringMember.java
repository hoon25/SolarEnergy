package solar.server.domain.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringMember {
    private String userId;
    private String userLoc;
    private Long userVolume;
}
