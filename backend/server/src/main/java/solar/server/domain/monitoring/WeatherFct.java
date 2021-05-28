package solar.server.domain.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherFct {
    int year;
    int month;
    int day;
    int hour;
    double temperature;
    double humidity;
    double windDirection;
    double windSpeed;
    double cloud;
    String locName;
}
