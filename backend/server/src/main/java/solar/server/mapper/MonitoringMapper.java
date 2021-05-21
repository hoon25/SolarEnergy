package solar.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import solar.server.domain.Monitoring;
import solar.server.domain.monitoring.Month;


import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface MonitoringMapper {

    List<Month> getMonitoringData(@Param("userId") String userId, @Param("userLoc") String userLoc);

}
