package dnd.danverse.domain.profile.repository;

import dnd.danverse.domain.profile.entity.Profile;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

  @Query("SELECT p FROM Profile p")
  List<Profile> findAllProfiles();

  @Query("SELECT p FROM Profile p WHERE p.member.id IN :memberIds")
  List<Profile> findAllProfilesWithMemberIds(@Param("memberIds") Set<Long> memberIds);
}
