package solar.server.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import solar.server.domain.SearchEnergyPredic;
import solar.server.domain.searchEnergyPredic.PredicData;

import java.util.List;

@Mapper
@Repository
public interface SearchEnergyPredicMapper {
    List<PredicData> getSearchEnergyPredic(@Param("locName") String locName, @Param("volume") String volume);
}
