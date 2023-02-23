package dnd.danverse.domain.event.repository;

import dnd.danverse.domain.event.dto.request.EventCondDto;
import dnd.danverse.domain.event.dto.response.EventInfoResponse;
import org.springframework.data.domain.Page;

/**
 * 이벤트를 필터링을 통해서 조회하는 커스텀 인터페이스.
 */
public interface EventFilterCustom {

  Page<EventInfoResponse> searchAllEventWithCond(EventCondDto eventCondDto);

}
