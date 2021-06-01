package solar.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import solar.server.domain.SearchEnergyPredic;
import solar.server.domain.vo.SearchVO;
import solar.server.service.SearchEnergyPredicService;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/searchpredic")
@RestController
public class SearchEnergyPredicController {

    private final SearchEnergyPredicService searchEnergyPredicService;

    @GetMapping("")
    public SearchEnergyPredic searchEnergyPredic(SearchVO searchVO) {
        
        return searchEnergyPredicService.setSearchEnergyPredic(searchVO.getLocName(),searchVO.getVolume());
}


}
