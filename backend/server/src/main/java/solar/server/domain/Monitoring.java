package solar.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solar.server.domain.monitoring.*;
import solar.server.domain.monitoring.MinuteMqtt;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monitoring {
    ServerTime serverTime;
    MonitoringMember monitoringMember;
    SunTime sunTime;
    WeatherFct weatherFct;
    List<Month> month;
    List<Day> day;
    List<Hour> hour;
    MinuteMqtt minuteMqtt;
}


