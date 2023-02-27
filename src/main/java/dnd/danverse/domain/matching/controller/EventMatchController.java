package dnd.danverse.domain.matching.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.matching.dto.request.EventIdRequestDto;
import dnd.danverse.domain.matching.dto.request.ProfileIdRequestDto;
import dnd.danverse.domain.matching.dto.response.ApplicantsResponseDto;
import dnd.danverse.domain.matching.service.EventAcceptComplexService;
import dnd.danverse.domain.matching.service.EventMatchComplexService;
import dnd.danverse.domain.matching.service.EventMatchSearchService;
import dnd.danverse.global.response.DataResponse;
import dnd.danverse.global.response.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 이벤트 매칭을 위한 컨트롤러.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Api(tags = "EventMatchController : 이벤트 매칭 관련 API")
public class EventMatchController {

  private final EventMatchComplexService eventMatchComplexService;
  private final EventAcceptComplexService eventAcceptComplexService;
  private final EventMatchSearchService eventMatchSearchService;

  /**
   * 이벤트에 지원한다.
   *
   * @param requestDto  신청하고자 하는 이벤트 ID가 담긴 DTO
   * @param sessionUser 현재 로그인한 유저의 정보
   * @return 성공 메시지
   */
  @PostMapping("/apply")
  @ApiOperation(value = "이벤트 지원", notes = "이벤트 지원을 할 수 있다. 프로필 등록을 한 사용자에 한해서 이벤트 지원 가능.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<MessageResponse> matchEvent(@RequestBody EventIdRequestDto requestDto,
       @AuthenticationPrincipal SessionUser sessionUser) {
    eventMatchComplexService.matchEvent(requestDto, sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "이벤트 매칭 성공"), HttpStatus.OK);
  }

  /**
   * 신청한 사용자에 한해서 이벤트 신청을 취소한다.
   *
   * @param eventId 신청 취소하려는 이벤트 글 아이디.
   * @param sessionUser 취소하려는 사용자
   * @return MessageResponse 상태 메시지 담은 dto
   */
  @DeleteMapping("{eventId}/cancel-apply")
  @ApiOperation(value = "이벤트 지원 취소", notes = "이벤트 지원을 취소할 수 있다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "eventId", value = "이벤트 고유 ID", required = true),
      @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
          required = true, dataType = "string", paramType = "header")
  })
  public ResponseEntity<MessageResponse> cancelEventApply(@PathVariable("eventId") Long eventId,
       @AuthenticationPrincipal SessionUser sessionUser) {
    eventMatchComplexService.cancelEventApply(eventId, sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "이벤트 신청 취소 성공"),
        HttpStatus.OK);
  }


  /**
   * 이벤트 신청자 프로필 리스트를 조회한다.
   *
   * @param eventId 작성한 이벤트 글 아이디.
   * @param sessionUser 조회하려는 사용자
   * @return ApplicantsResponseDto 신청한 멤버들의 정보를 담은 Dto.
   */
  @GetMapping("/{eventId}/applicants")
  @ApiOperation(value = "이벤트 신청자 리스트 조회", notes = "이벤트 작성자는 자신의 이벤트에 신청한 프로필 리스트를 볼 수 있다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "eventId", value = "이벤트 고유 ID", required = true),
      @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
          required = true, dataType = "string", paramType = "header")
  })
  public ResponseEntity<DataResponse<List<ApplicantsResponseDto>>> getApplicants(@PathVariable("eventId") Long eventId,
      @AuthenticationPrincipal SessionUser sessionUser) {
    List<ApplicantsResponseDto> resultList = eventMatchSearchService.getApplicants(eventId, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "신청자 리스트 조회 성공", resultList), HttpStatus.OK);
  }

  /**
   * 이벤트 지원자 요청을 수락한다.
   * @param eventId 이벤트 글 아이디
   * @param requestDto 수락하려는 사용자의 프로필 아이디
   * @return MessageResponse 상태 메시지 담은 dto
   */
  @PatchMapping("/{eventId}/accept")
  @ApiOperation(value = "이벤트 지원자 요청 수락", notes = "이벤트 주최자는 자신의 이벤트에 신청한 지원의 요청을 수락할 수 있다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "eventId", value = "이벤트 고유 ID", required = true),
      @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
          required = true, dataType = "string", paramType = "header")
  })
  public ResponseEntity<MessageResponse> acceptApplicant(@PathVariable("eventId") Long eventId,
      @RequestBody ProfileIdRequestDto requestDto) {
    // TODO : requestDto.getMemberID로 변경 필요성이 있다.
    eventAcceptComplexService.acceptApplicant(eventId, requestDto.getMemberId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "신청자 수락 성공"), HttpStatus.OK);
  }


}
