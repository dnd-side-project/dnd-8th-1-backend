package dnd.danverse.domain.matching.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.matching.dto.request.EventIdRequestDto;
import dnd.danverse.domain.matching.service.EventMatchComplexService;
import dnd.danverse.global.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 이벤트 매칭을 위한 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventMatchController {

  private final EventMatchComplexService eventMatchComplexService;

  /**
   * 이벤트 매칭을 진행한다.
   * @param requestDto 신청하고자 하는 이벤트 ID가 담긴 DTO
   * @param sessionUser 현재 로그인한 유저의 정보
   * @return 성공 메시지
   */
  @PostMapping("/match")
  public ResponseEntity<MessageResponse> matchEvent(@RequestBody EventIdRequestDto requestDto, @AuthenticationPrincipal
  SessionUser sessionUser) {
    eventMatchComplexService.matchEvent(requestDto, sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "이벤트 매칭 성공"), HttpStatus.OK);
  }


}
