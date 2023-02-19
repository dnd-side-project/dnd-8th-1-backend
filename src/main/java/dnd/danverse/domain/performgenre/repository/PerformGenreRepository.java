package dnd.danverse.domain.performgenre.repository;

import dnd.danverse.domain.performgenre.entity.PerformGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformGenreRepository extends JpaRepository<PerformGenre, Long> {

}
