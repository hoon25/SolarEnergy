package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import solar.server.domain.Member;
import solar.server.domain.Monitoring;
import solar.server.domain.monitoring.Month;
import solar.server.mapper.MemberMapper;
import solar.server.mapper.MonitoringMapper;
import solar.server.service.MonitoringService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

//    private final MemberMapper memberMapper;
//    private final MonitoringMapper monitoringMapper;
    private final MonitoringService monitoringService;

//    @GetMapping("/monitoringTest")
//    public Optional<Member> monitoringMember (){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//        System.out.println(authentication);
//        System.out.println(authentication.getDetails());
//        return memberMapper.selectUser(userId);
//    }


    @GetMapping("")
    public Monitoring monitoring() {
        return monitoringService.monitoring();
    }

    // 패털 접기 동작 (Topic : panelOperation, message : ON/OFF)
    @GetMapping("/paneloperation")
    public void panelOperation(@RequestParam(value="panelOperation") String panelOperation) {
        monitoringService.senderMqtt("panelOperation", panelOperation);
    }

    // 청소 동작 (Topic : cleanerOperation, message : ON)
    @GetMapping("/cleaneroperation")
    public void cleanerOperation() {
        monitoringService.senderMqtt("cleanerOperation", "ON");
    }
//
//
//
//    @GetMapping("/test")
//    public HashMap<Object,Object> test(){
//        HashMap<Object,Object> minuteData = monitoringService.subscribeMqtt();
//        return minuteData;
//    }





}
