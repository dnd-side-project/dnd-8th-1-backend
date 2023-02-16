package dnd.danverse.domain.matching.repository;

import dnd.danverse.domain.matching.entity.EventMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMatchRepository extends JpaRepository<EventMatch, Long> {

}
