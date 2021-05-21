package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import solar.server.domain.Member;
import solar.server.domain.Monitoring;
import solar.server.domain.monitoring.Month;
import solar.server.mapper.MemberMapper;
import solar.server.mapper.MonitoringMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MonitoringController {

    private final MemberMapper memberMapper;
    private final MonitoringMapper monitoringMapper;

    @GetMapping("/monitoringTest")
    public Optional<Member> monitoringMember (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println(authentication);
        System.out.println(authentication.getDetails());
        return memberMapper.selectUser(userId);
    }

    @GetMapping("/monitoring")
    public Monitoring monitoring(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<Member> member = memberMapper.selectUser(userId);
        String userLoc = member.get().getUserLoc();

        return Monitoring.builder().month(monitoringMapper.getMonitoringData(userId,userLoc)).build();
    }





}
