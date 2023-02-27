package dnd.danverse.domain.mypage.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.mypage.dto.response.MyPerformsDto;
import dnd.danverse.domain.mypage.dto.response.MyReviewDto;
import dnd.danverse.domain.mypage.service.MyPerformSearchService;
import dnd.danverse.domain.mypage.service.MyReviewSearchService;
import dnd.danverse.global.response.DataResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 활동 내역 관련 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mypage")
@Api(tags = "MyPageController : 활동 내역 관련 API")
public class MyPageController {

  private final MyReviewSearchService myReviewSearchService;
  private final MyPerformSearchService myPerformSearchService;

  /**
   * 사용자가 작성한 후기들을 전체 조회할 수 있는 컨트롤러.
   *
   * @param sessionUser 자신이 작성한 후기 조회를 요청하는 사용자.
   * @return 후기 조회에 성공하면 200 상태코드와 함께 성공 메시지가 나타납니다.
   */
  @GetMapping("/performances/reviews")
  @ApiOperation(value = "후기 조회", notes = "자신이 작성한 후기 리스트를 조회할 수 있다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<List<MyReviewDto>>> getMyReviews(@AuthenticationPrincipal SessionUser sessionUser) {
    List<MyReviewDto> reviews = myReviewSearchService.getMyReviews(sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "작성 후기 조회 성공", reviews), HttpStatus.OK);
  }

  /**
   * 사용자가 작성한 공연들을 전체 조회할 수 있는 컨트롤러.
   *
   * @param sessionUser 자신이 작성한 공연 정보 조회를 요청하는 사용자.
   * @return 공연 전체 조회에 성공하면 200 상태코드와 함께 성공 메시지가 나타납니다.
   */
  @GetMapping("/performances")
  @ApiOperation(value = "공연 조회", notes = "자신이 작성한 공연 리스트를 조회할 수 있다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<List<MyPerformsDto>>> searchMyPerforms(@AuthenticationPrincipal SessionUser sessionUser) {
    List<MyPerformsDto> myPerforms = myPerformSearchService.findMyPerforms(sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "작성한 공연 조회 성공", myPerforms), HttpStatus.OK);
  }

}
