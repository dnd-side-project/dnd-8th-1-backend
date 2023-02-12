package dnd.danverse.domain.event.controller;

import dnd.danverse.domain.event.dto.request.EventCondDto;
import dnd.danverse.domain.event.dto.response.EventInfoResponse;
import dnd.danverse.domain.event.service.EventProfileService;
import dnd.danverse.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이벤트를 조회하는 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

  private final EventProfileService eventProfileService;

  /**
   * 이벤트 필터링과, 페이징을 적용한 이벤트 조회.
   *
   * @param eventCondDto 이벤트 필터링 조건
   * @param pageable     페이징 조건
   * @return 페이징 처리된 이벤트 목록 (모집 기간이 지난 이벤트는 제외)
   */
  @GetMapping()
  public ResponseEntity<DataResponse<Page<EventInfoResponse>>> searchAllEvent(
      EventCondDto eventCondDto, Pageable pageable) {
    Page<EventInfoResponse> events = eventProfileService.searchAllEventWithCond(
        eventCondDto, pageable);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "이벤트 조회 성공", events), HttpStatus.OK);
  }

}
