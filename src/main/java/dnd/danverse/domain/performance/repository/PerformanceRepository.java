package dnd.danverse.domain.performance.repository;

import dnd.danverse.domain.performance.entity.Performance;
import java.time.LocalDate;
import java.util.List;
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
}
