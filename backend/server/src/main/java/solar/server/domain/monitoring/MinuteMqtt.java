package solar.server.domain.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinuteMqtt {

    Object temperature;
    Object humidity;

    Object panelCover;
    Object angular;
    Object panelTemperature;
    Object illumination;
    Object lastCleanTime;

}
