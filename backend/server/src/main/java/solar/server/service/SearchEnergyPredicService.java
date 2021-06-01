package solar.server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import solar.server.domain.SearchEnergyPredic;
import solar.server.domain.searchEnergyPredic.PredicData;
import solar.server.domain.vo.SearchVO;
import solar.server.mapper.SearchEnergyPredicMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchEnergyPredicService {

    private final SearchEnergyPredicMapper searchEnergyPredicMapper;

    public String getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return userId;
    }

    public SearchEnergyPredic setSearchEnergyPredic(String locName, String volume){
//        String userId = getUserId();
        return SearchEnergyPredic.builder()
                .userId(getUserId())
                .predicDataList(setPredicData(locName,volume))
                .build();
    }

    public List<PredicData> setPredicData(String locName, String volume) {
        List<PredicData> plists = searchEnergyPredicMapper.getSearchEnergyPredic(locName, volume);
        for (PredicData plist : plists){
            plist.setVolume(volume);
        }

        return plists;
    }





}
