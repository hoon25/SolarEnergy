package solar.server.domain.searchEnergyPredic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PredicData {
    int year;
    int month;
    double mpredPower;
    String locName;
    String volume;
}
