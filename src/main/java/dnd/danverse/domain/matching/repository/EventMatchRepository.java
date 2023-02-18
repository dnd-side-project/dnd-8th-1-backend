package dnd.danverse.domain.matching.repository;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.profile.entity.Profile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMatchRepository extends JpaRepository<EventMatch, Long> {

  Optional<EventMatch> findByEventAndProfileGuest(Event event, Profile profile);

  @Query("select em from EventMatch em join fetch em.profileGuest p where em.event.id = :eventId")
  List<EventMatch> findByEventId(@Param("eventId") Long eventId);
}
