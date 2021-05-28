package solar.server.domain.mqtt;

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



//    double temperature;
//    double humidity;
//
//    String panelCover;
//    int angular;
//    double panelTemperature;
//    double illumination;
//    String lastCleanTime;
}
