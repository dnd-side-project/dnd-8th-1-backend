package dnd.danverse.domain.event.repository;

import static dnd.danverse.domain.event.entitiy.QEvent.event;
import static dnd.danverse.domain.profile.entity.QProfile.profile;
import static org.aspectj.util.LangUtil.isEmpty;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dnd.danverse.domain.event.dto.request.EventCondDto;
import dnd.danverse.domain.event.dto.response.EventInfoResponse;
import dnd.danverse.domain.event.dto.response.QEventInfoResponse;
import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.event.entitiy.EventType;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

/**
 * 이벤트를 필터링을 통해서 조회하는 커스텀 인터페이스를 구현한 구현체
 * EventRepository 랑 네이밍을 맞추기 위해 Impl 을 붙여서 사용하면 된다.
 * 그러면 EventRepositoryImpl 는 Bean 으로 관리되며, EventRepository 에서 사용할 수 있다.
 */
public class EventRepositoryImpl implements EventFilterCustom {

  private final JPAQueryFactory queryFactory;

  public EventRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  /**
   * 이벤트 필터링과, 페이징을 적용한 이벤트 조회.
   * @param eventCond 이벤트 필터링 조건
   * @param pageable 페이징 조건
   * @return 페이징 처리된 이벤트 목록 (모집 기간이 지난 이벤트는 제외)
   */
  public Page<EventInfoResponse> searchAllEventWithCond(EventCondDto eventCond, Pageable pageable) {

    // dto 를 통해서 이벤트 및 프로필 정보만 조회
    List<EventInfoResponse> eventContent = queryFactory
        .select(new QEventInfoResponse(
            event.id,
            event.title,
            event.location,
            event.eventType,
            event.eventImg.imageUrl,
            event.deadline,
            profile.id,
            profile.profileName,
            profile.profileImg.imageUrl
        ))
        .from(event)
        .join(event.profile, profile)
        .where(
            locationEq(eventCond.getLocation()),
            eventTypeEq(EventType.of(eventCond.getType()))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();


    return PageableExecutionUtils.getPage(eventContent, pageable, () -> getTotalCount(eventCond));
  }



  /**
   * 전체 이벤트 갯수를 조회한다.
   * 원래 countQuery.fetchCount() 를 사용해도 되지만, deprecated 되었다.
   * PageableExecutionUtils.getPage 의 3번째 파라미터는 LongSupplier 로써 Functional Interface 이다.
   * Functional Interface 로써 활용하기 위해 따로 메서드로 빼서 사용한다.
   * 해당 Functional Interface 의 함수 호출 조건은 return 값이 long 이여야 한다.
   * 추가적으로 전체 갯수를 조회하는 쿼리는 필터링 조건이 모두 이벤트만 조회하면 되기 때문에 프로필 join 을 하지 않는다.
   * @param eventCond 이벤트 필터링 조건
   * @return 전체 이벤트 갯수
   */
  private long getTotalCount(EventCondDto eventCond) {
    // 전체 갯수 따로 조회 (필터링 조건은 이벤트만 있기 때문에 카운트 쿼리는 이벤트 대상으로만 한다, join 안한다. )
    JPAQuery<Event> countQuery = queryFactory
        .select(event)
        .from(event)
        .where(
            locationEq(eventCond.getLocation()),
            eventTypeEq(EventType.of(eventCond.getType())));

    // countQuery.fetchCount() 는 deprecated 되었다. 대신 size() 를 사용한다.
    return countQuery.fetch().size();
  }

  /**
   * 이벤트 필터링 조건 중, location 이 null 이거나 빈 문자열이면 null 을 반환한다.
   * null 을 반환하면 where 절에서 해당 조건은 무시된다.
   * null 을 반환하지 않으면 해당 조건이 where 절에 추가된다.
   * @param location 이벤트 필터링 조건 중, location
   * @return BooleanExpression 을 반환하여 동적 쿼리를 만든다.
   */
  private BooleanExpression locationEq(String location) {
    return isEmpty(location)  ? null : event.location.eq(location);
  }

  /**
   * 이벤트 필터링 조건 중, type 이 null 이면 null 을 반환한다.
   * null 을 반환하면 where 절에서 해당 조건은 무시된다.
   * null 을 반환하지 않으면 해당 조건이 where 절에 추가된다.
   * @param eventType 이벤트 필터링 조건 중, type
   * @return BooleanExpression 을 반환하여 동적 쿼리를 만든다.
   */
  private BooleanExpression eventTypeEq(EventType eventType) {
    return eventType == null ? null : event.eventType.eq(eventType);
  }


}
