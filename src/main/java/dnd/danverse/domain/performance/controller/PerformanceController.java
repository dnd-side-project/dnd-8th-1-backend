package dnd.danverse.domain.performance.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.performance.dto.request.PerformCondDto;
import dnd.danverse.domain.performance.dto.request.PerformSavedRequestDto;
import dnd.danverse.domain.performance.dto.request.PerformUpdateRequestDto;
import dnd.danverse.domain.performance.dto.response.ImminentPerformsDto;
import dnd.danverse.domain.performance.dto.response.PageDto;
import dnd.danverse.domain.performance.dto.response.PerformDetailResponse;
import dnd.danverse.domain.performance.dto.response.PerformInfoResponse;
import dnd.danverse.domain.performance.dto.response.PerformListResponse;
import dnd.danverse.domain.performance.service.PerformDeleteService;
import dnd.danverse.domain.performance.service.PerformFilterService;
import dnd.danverse.domain.performance.service.PerformSaveComplexService;
import dnd.danverse.domain.performance.service.PerformSearchComplexService;
import dnd.danverse.domain.performance.service.PerformUpdateComplexService;
import dnd.danverse.domain.performance.service.PerformancePureService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공연 관련 데이터를 처리하기 위한 Contorller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
@Api(tags = "PerformanceController : 공연 관련 API")
public class PerformanceController {

  private final PerformancePureService performancePureService;
  private final PerformFilterService performFilterService;
  private final PerformSearchComplexService performSearchComplexService;
  private final PerformSaveComplexService performSaveComplexService;
  private final PerformUpdateComplexService performUpdateComplexService;
  private final PerformDeleteService performDeleteService;

  /**
   * 공연이 임박한 공연을 조회할 수 있다. 오늘 날짜 기준으로, 최근 공연 4개를 조회할 수 있다.
   *
   * @return 임박한 공연 목록
   */
  @GetMapping("/imminent")
  @ApiOperation(value = "임박한 공연 조회", notes = "오늘 날짜 기준으로, 최근 공연 4개를 조회할 수 있다.")
  public ResponseEntity<DataResponse<List<ImminentPerformsDto>>> searchImminentPerformance() {

    List<ImminentPerformsDto> performs = performancePureService.searchImminentPerforms();
    return ResponseEntity.ok(DataResponse.of(HttpStatus.OK, "임박한 공연 조회 성공", performs));
  }

  /**
   * 공연 필터링과, 페이징을 적용한 공연 조회.
   *
   * @param performCondDto 공연 필터링 조건
   * @return 페이징 처리된 공연 목록
   */
  @GetMapping()
  @ApiOperation(value = "공연 전체 조회", notes = "공연 필터링(지역,장르)와 날짜(년,월,일별), 페이징을 적용한 공연 조회.")
  public ResponseEntity<DataResponse<PageDto<PerformInfoResponse>>> searchPerformanceWithCond(
      PerformCondDto performCondDto) {

    PageDto<PerformInfoResponse> performInfoResponsePageDto = performFilterService.searchAllPerformWithCond(
        performCondDto);

    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.OK, "공연 조회 성공", performInfoResponsePageDto), HttpStatus.OK);
  }


  /**
   * 팀 이름으로 공연 조회
   * 예정된 공연과 마감된 공연을 확인할 수 있다.
   *
   * @param teamName 팀 이름.
   */
  @GetMapping("/team")
  @ApiOperation(value = "팀 이름으로 공연 조회", notes = "팀 이름으로 공연을 조회할 수 있다.")
  @ApiImplicitParam(name = "name", value = "팀 이름", required = true)
  public ResponseEntity<DataResponse<PerformListResponse>> searchPerformsByTeam(@RequestParam("name") String teamName) {
    PerformListResponse performListResponse = performFilterService.searchPerformsByTeam(teamName);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "팀 이름으로 공연 조회 성공", performListResponse), HttpStatus.OK);
  }


  /**
   * 공연 글 상세 조회.
   *
   * @param performId 상세조회하려는 공연 글 Id.
   * @return 공연 responseDto.
   */
  @GetMapping("/{performId}")
  @ApiOperation(value = "공연 글 상세조회", notes = "공연 정보 글을 상세 조회할 수 있다.")
  @ApiImplicitParam(name = "performId", value = "공연 고유 ID", required = true)
  public ResponseEntity<DataResponse<PerformDetailResponse>> getDetailPerform(@PathVariable("performId") Long performId) {
    PerformDetailResponse response = performSearchComplexService.getDetailPerform(performId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "공연 상세조회 성공", response), HttpStatus.OK);
  }

  /**
   * 공연 글 등록.
   *
   * @param performSavedDto 공연 글 등록하기 위한 요청 Dto.
   * @param sessionUser 글을 등록하려고 하는 사용자.
   * @return 등록 성공하면 201 상태코드와 함께 응답 Dto를 반환.
   */
  @PostMapping("")
  @ApiOperation(value = "공연 글 등록", notes = "프로필을 등록한 사용자에 한하여 공연 글을 등록할 수 있다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<PerformDetailResponse>> postPerform(@RequestBody @Validated PerformSavedRequestDto performSavedDto, @AuthenticationPrincipal SessionUser sessionUser) {
    PerformDetailResponse response = performSaveComplexService.postPerform(performSavedDto, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "공연 등록 성공", response), HttpStatus.CREATED);
  }

  /**
   * 공연 글 수정.
   *
   * @param updateRequest 공연 글 수정을 위한 요청 Dto.
   * @param sessionUser 글을 수정하려고 하는 사용자.
   * @return 글 수정에 성공하면 200 상태코드와 함께 응답 Dto를 반환.
   */
  @PatchMapping("")
  @ApiOperation(value = "공연 글 수정", notes = "작성자에 한하여 글을 수정할 수 있다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<PerformDetailResponse>> updatePerform(@RequestBody PerformUpdateRequestDto updateRequest, @AuthenticationPrincipal SessionUser sessionUser) {
    PerformDetailResponse response = performUpdateComplexService.updatePerform(updateRequest, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "공연 수정 성공", response), HttpStatus.OK);
  }

  /**
   * 공연 글 삭제.
   *
   * @param performId 삭제하려는 공연 고유 Id.
   * @param sessionUser 삭제 요청 사용자 고유 Id.
   * @return 글 삭제에 성공하면 200 상태코드와 함께 메시지를 반환.
   */
  @DeleteMapping("/{performId}")
  @ApiOperation(value = "공연 글 삭제", notes = "작성자에 한하여 글을 삭제할 수 있다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "performId", value = "공연 고유 ID", required = true),
      @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
          required = true, dataType = "string", paramType = "header")
  })
  public ResponseEntity<MessageResponse> deletePerform(@PathVariable("performId") Long performId,
      @AuthenticationPrincipal SessionUser sessionUser) {
    performDeleteService.deletePerform(performId, sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "공연 삭제 성공"), HttpStatus.OK);
  }

}
