package dnd.danverse.domain.matching.repository;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.profile.entity.Profile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventMatchRepository extends JpaRepository<EventMatch, Long> {

  Optional<EventMatch> findByEventAndProfileGuest(Event event, Profile profile);
}
