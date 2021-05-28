package solar.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solar.server.domain.monitoring.Day;
import solar.server.domain.monitoring.Hour;
import solar.server.domain.monitoring.MonitoringMember;
import solar.server.domain.monitoring.Month;
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
}


