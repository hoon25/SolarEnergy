package solar.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import solar.server.domain.Monitoring;
import solar.server.domain.monitoring.*;


import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface MonitoringMapper {
    WeatherFct getWeatherFctData(@Param("year")String year, @Param("month")String month, @Param("day")String day, @Param("hour")String hour,@Param("userLoc") String userLoc);
    SunTime getSunTimeData(@Param("month")String month, @Param("day")String day);

    List<Month> getMonthData(@Param("userId") String userId, @Param("userLoc") String userLoc);
    List<Day> getDayData(@Param("userId") String userId, @Param("userLoc") String userLoc);
    List<Hour> getHourData(@Param("userId") String userId, @Param("userLoc") String userLoc);


    void sethRealPower(@Param("year")String year, @Param("month")String month, @Param("day")String day, @Param("hour")String hour, @Param("hRealPower") String value, @Param("userId")String userId );
    void setdRealPower(@Param("dRealId") String dRealId, @Param("year")String year, @Param("month")String month, @Param("day")String day, @Param("dRealPower") String value, @Param("userId")String userId );
    void updatedRealPower(@Param("dRealId") String dRealId, @Param("dRealPower") String value);
    void setmRealPower(@Param("mRealId") String mRealId, @Param("year")String year, @Param("month")String month, @Param("mRealPower") String value, @Param("userId")String userId );
    void updatemRealPower(@Param("mRealId") String dRealId,@Param("mRealPower") String value);
}
