package solar.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import solar.server.domain.SearchEnergyPredic;
import solar.server.domain.vo.SearchVO;
import solar.server.service.SearchEnergyPredicService;

import java.util.List;


@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8080/searchpredic")
@RequestMapping("/searchpredic")
@RestController
public class SearchEnergyPredicController {

    private final SearchEnergyPredicService searchEnergyPredicService;

    @GetMapping("")
    public SearchEnergyPredic searchEnergyPredic(SearchVO searchVO) {
        
        return searchEnergyPredicService.setSearchEnergyPredic(searchVO.getLocName(),searchVO.getVolume());
}


}
