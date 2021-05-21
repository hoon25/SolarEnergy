package solar.server.domain.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Month {
    int month;
    Double mpredPower;
    Double mrealPower;
    String userId;
    String Loc;
}
