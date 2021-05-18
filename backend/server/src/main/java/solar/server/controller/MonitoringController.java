package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import solar.server.domain.Member;
import solar.server.mapper.MemberMapper;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MonitoringController {

    private final MemberMapper memberMapper;

    @GetMapping("/monitoring")
    public Optional<Member> monitoringMember (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return memberMapper.selectUser(userId);
    }





}
