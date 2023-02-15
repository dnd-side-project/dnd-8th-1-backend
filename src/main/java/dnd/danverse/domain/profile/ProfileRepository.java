package dnd.danverse.domain.profile;

import dnd.danverse.domain.profile.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 사용자의 프로필 데이터를 처리하기 위한 Repository.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  @Query("select p from Profile p where p.member.id = :memberId")
  Optional<Profile> findByMember(@Param("memberId") Long memberId);

}
