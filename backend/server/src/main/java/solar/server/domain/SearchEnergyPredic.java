package solar.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import solar.server.domain.monitoring.MonitoringMember;
import solar.server.domain.searchEnergyPredic.PredicData;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchEnergyPredic {
    String userId;
    List<PredicData> predicDataList;

}
