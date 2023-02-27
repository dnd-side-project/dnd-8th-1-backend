package dnd.danverse.domain.performance.repository;

import dnd.danverse.domain.performance.entity.Performance;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 공연 관련 데이터를 처리하기 위한 Repository.
 */
@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformFilterCustom {

  @Query("select p from Performance p where p.startDate >= :now order by p.startDate asc")
  List<Performance> findImminentPerforms(@Param("now") LocalDate now);

  /**
   * 공연 주최자의 프로필과 공연을 fetch join하여 반환.
   *
   * @param performId 조회하고자 하는 공연 정보글의 Id.
   * @return Performance
   */
  @Query("select p from Performance p join fetch p.profileHost where p.id = :performId")
  Optional<Performance> findPerformanceWithProfile(@Param("performId") Long performId);

  @Query("select p from Performance p where p.profileHost.id = :profileId order by p.createdAt desc")
  List<Performance> findAllByProfileId(@Param("profileId") Long profileId);
}
