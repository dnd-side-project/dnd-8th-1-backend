package dnd.danverse.domain.member.repository;

import dnd.danverse.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 사용자 정보를 데이터를 저장하기 위한 Repository
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  @Query("select m from Member m left join fetch m.profile p where m.username = :oauth2Id")
  Optional<Member> findByUsername(@Param("oauth2Id") String oauth2Id);
}
