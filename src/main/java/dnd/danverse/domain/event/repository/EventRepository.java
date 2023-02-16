package dnd.danverse.domain.event.repository;

import dnd.danverse.domain.event.entitiy.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 이벤트를 조회하는 레포지토리.
 * JpaRepository 를 상속받아 기본적인 CRUD 메서드를 제공받는다.
 * EventFilterCustom 을 상속받아 필터링을 위한 커스텀 메서드를 제공받는다.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventFilterCustom {
  @Query("select e from Event e join fetch e.profile where e.id = :eventId")
  Event findProfileJoinFetch(@Param("eventId") Long eventId);

}