package solar.server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import solar.server.domain.Member;
import solar.server.mapper.MemberMapper;
import solar.server.security.JwtTokenProvider;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberMapper memberMapper;


    // 회원가입
    @PostMapping("/create")
    public void create(@RequestBody Map<String, String> member) {
        memberMapper.insertUser(Member.builder()
            .userId(member.get("userId"))
            .userPwd(passwordEncoder.encode(member.get("userPwd")))
            .userLoc(member.get("userLoc"))
            .userVolume(Long.parseLong(member.get("userVolume")))
            .roles(Collections.singletonList("userRole"))
            .build());
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginUser) {
        Member member = memberMapper.selectUser(loginUser.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if (!passwordEncoder.matches(loginUser.get("userPwd"), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
//        return jwtTokenProvider.createToken(member.getUsername(),member.getRoles());
        return jwtTokenProvider.createToken(member.getUsername(),member.getRoles());
    }


}
