package solar.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import solar.server.domain.Member;
import solar.server.mapper.MemberMapper;
import solar.server.security.JwtTokenProvider;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public void create(Map<String, String> member) {
        memberMapper.insertUser(Member.builder()
            .userId(member.get("userId"))
            .userPwd(passwordEncoder.encode(member.get("userPwd")))
            .userLoc(member.get("userLoc"))
            .userVolume(Long.parseLong(member.get("userVolume")))
//            .roles(Collections.singletonList("userRole"))
//            .roles(Collections.singletonList("USER"))
            .build());

    }

    // 로그인
    public String login(Map<String, String> loginUser) {
        Member member = memberMapper.selectUser(loginUser.get("userId"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));
        if (!passwordEncoder.matches(loginUser.get("userPwd"), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String loginId = jwtTokenProvider.getUserPk(jwtTokenProvider.createToken(member.getUsername(),member.getRoles()));
        String loginToken = jwtTokenProvider.createToken(member.getUsername(),member.getRoles());
        System.out.println("loginId = " + loginId);
        System.out.println("loginToken = " + loginToken);

        return jwtTokenProvider.createToken(member.getUsername(),member.getRoles());
    }




}
