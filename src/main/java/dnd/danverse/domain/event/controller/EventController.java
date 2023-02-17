package dnd.danverse.domain.event.controller;

import dnd.danverse.domain.event.dto.request.EventCondDto;
import dnd.danverse.domain.event.dto.request.EventSavedRequestDto;
import dnd.danverse.domain.event.dto.response.EventInfoResponse;
import dnd.danverse.domain.event.dto.response.EventWithProfileDto;
import dnd.danverse.domain.event.service.EventFilterService;
import dnd.danverse.domain.event.service.EventSaveComplexService;
import dnd.danverse.domain.event.service.EventSearchComplexService;
import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.performance.dto.response.PageDto;
import dnd.danverse.global.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이벤트를 조회하는 컨트롤러.
 */
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

  private final EventFilterService eventFilterService;
  private final EventSaveComplexService eventSaveComplexService;
  private final EventSearchComplexService eventSearchComplexService;

  /**
   * 이벤트 필터링과, 페이징을 적용한 이벤트 조회.
   *
   * @param eventCondDto 이벤트 필터링 조건
   * @param pageable     페이징 조건
   * @return 페이징 처리된 이벤트 목록 (모집 기간이 지난 이벤트는 제외)
   */
  @GetMapping()
  public ResponseEntity<DataResponse<PageDto<EventInfoResponse>>> searchAllEvent(
      EventCondDto eventCondDto, Pageable pageable) {
    PageDto<EventInfoResponse> events = eventFilterService.searchAllEventWithCond(
        eventCondDto, pageable);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "이벤트 조회 성공", events), HttpStatus.OK);
  }

  /**
   * 프로필 등록을 한 사용자에 한해서 이벤트 글 등록 가능.
   *
   * @param requestDto  이벤트 requestBody.
   * @param sessionUser 이벤트 작성을 요청하는 user.
   * @return "이벤트 등록 성공" 메시지와 함께 201 상태코드가 나타납니다.
   */
  @PostMapping()
  public ResponseEntity<DataResponse<EventWithProfileDto>> postEvent(@RequestBody EventSavedRequestDto requestDto,
      @AuthenticationPrincipal SessionUser sessionUser) {
    EventWithProfileDto responseDto = eventSaveComplexService.createEvent(requestDto, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "이벤트 등록 성공", responseDto),
        HttpStatus.CREATED);
  }

  /**
   * 이벤트 글 조회 가능.
   *
   * @param eventId 조회하고자 하는 이벤트 고유 Id.
   * @return "이벤트 상세 조회 성공" 메시지와 함께 200 상태코드가 나타납니다.
   */
  @GetMapping("/{eventId}")
  public ResponseEntity<DataResponse<EventWithProfileDto>> getEvent(@PathVariable("eventId") Long eventId) {
    EventWithProfileDto response = eventSearchComplexService.searchDetail(eventId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "이벤트 상세 조회 성공", response), HttpStatus.OK);
  }

}
