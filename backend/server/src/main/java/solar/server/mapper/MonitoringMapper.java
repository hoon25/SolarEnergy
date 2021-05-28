package solar.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import solar.server.domain.Monitoring;
import solar.server.domain.monitoring.Day;
import solar.server.domain.monitoring.Hour;
import solar.server.domain.monitoring.MonitoringMember;
import solar.server.domain.monitoring.Month;


import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface MonitoringMapper {
    List<Month> getMonthData(@Param("userId") String userId, @Param("userLoc") String userLoc);
    List<Day> getDayData(@Param("userId") String userId, @Param("userLoc") String userLoc);
    List<Hour> getHourData(@Param("userId") String userId, @Param("userLoc") String userLoc);
    void sethRealPower(@Param("year")String year, @Param("month")String month, @Param("day")String day, @Param("hour")String hour, @Param("hRealPower") String value, @Param("userId")String userId );

}
