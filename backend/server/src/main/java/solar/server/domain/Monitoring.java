package solar.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solar.server.domain.monitoring.*;
import solar.server.domain.mqtt.MinuteMqtt;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monitoring {
    MonitoringMember monitoringMember;
    List<Month> month;
    List<Day> day;
    List<Hour> hour;
    MinuteMqtt minuteMqtt;
    WeatherFct weatherFct;
}


