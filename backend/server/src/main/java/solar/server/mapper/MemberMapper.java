package solar.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import solar.server.domain.Member;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Mapper
@Repository
public interface MemberMapper {

    List<HashMap<Object, Object>> selectAllUsers(HashMap<Object, Object> vo);
    Optional<Member> selectUser(String userId);
    void insertUser(Member member);
}
