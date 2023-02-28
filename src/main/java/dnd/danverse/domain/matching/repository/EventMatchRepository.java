package dnd.danverse.domain.matching.repository;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.entity.EventMatch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMatchRepository extends JpaRepository<EventMatch, Long> {

  @Query("select em from EventMatch em where em.event.id = :eventId and em.profileGuest.id = :profileId")
  Optional<EventMatch> findByEventAndProfileGuest(@Param("eventId") Long eventId, @Param("profileId") Long profileId);

  @Query("select em from EventMatch em join fetch em.profileGuest p where em.event = :event")
  List<EventMatch> findByEvent(@Param("event") Event event);

  @Query("SELECT DISTINCT em.event.id FROM EventMatch em WHERE em.event.id IN :eventIds AND em.isMatched = true")
  List<Long> findDistinctEventIdsByEventIdIn(@Param("eventIds") List<Long> eventIds);
}
