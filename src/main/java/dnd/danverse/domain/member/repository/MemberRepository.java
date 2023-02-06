package dnd.danverse.domain.member.repository;

import dnd.danverse.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자 정보를 데이터를 저장하기 위한 Repository
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByUsername(String oauth2Id);
}
