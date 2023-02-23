package dnd.danverse.domain.performgenre.repository;

import dnd.danverse.domain.performgenre.entity.PerformGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformGenreRepository extends JpaRepository<PerformGenre, Long> {
  @Modifying
  @Query("delete from PerformGenre pg where pg.performance.id = :performId")
  void deleteByPerformId(@Param("performId") Long performId);

}
