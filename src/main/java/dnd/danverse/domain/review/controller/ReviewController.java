package dnd.danverse.domain.review.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.review.dto.request.ReviewContentDto;
import dnd.danverse.domain.review.dto.request.ReviewUpdateDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoWithPerformDto;
import dnd.danverse.domain.review.service.ReviewDeleteComplexService;
import dnd.danverse.domain.review.service.ReviewSaveComplexService;
import dnd.danverse.domain.review.service.ReviewUpdateComplexService;
import dnd.danverse.domain.review.service.ReviewsSearchComplexService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 후기 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
@Api(tags = "ReviewController : 공연 후기 관련 API")
public class ReviewController {

  private final ReviewSaveComplexService reviewSaveComplexService;
  private final ReviewsSearchComplexService reviewsSearchComplexService;
  private final ReviewUpdateComplexService reviewUpdateComplexService;
  private final ReviewDeleteComplexService reviewDeleteComplexService;

  /**
   * 후기를 저장한다.
   *
   * @param contentDto 후기 내용
   * @param user       후기 작성자
   * @return 후기 정보
   */
  @PostMapping("/{performId}/reviews")
  @ApiOperation(value = "후기를 등록한다.", notes = "후기를 등록한다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
          required = true, dataType = "string", paramType = "header"),
      @ApiImplicitParam(name = "performId", value = "공연 고유 ID", required = true, dataType = "long", paramType = "path")
  })
  public ResponseEntity<DataResponse<List<ReviewInfoDto>>> createReview(
      @PathVariable("performId") Long performId, @RequestBody ReviewContentDto contentDto,
      @AuthenticationPrincipal SessionUser user) {
    List<ReviewInfoDto> allReviews = reviewSaveComplexService.saveReview(contentDto, performId,
        user.getId());
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "리뷰가 성공적으로 등록되었습니다.", allReviews),
        HttpStatus.CREATED);
  }

  /**
   * 공연에 대한 모든 후기를 조회한다.
   *
   * @param performId 공연 고유 ID
   * @return 후기 정보
   */
  @GetMapping("/{performId}/reviews")
  @ApiOperation(value = "공연에 대한 모든 후기를 조회한다.", notes = "공연에 대한 모든 후기를 조회한다.")
  @ApiImplicitParam(name = "performId", value = "공연 고유 ID", required = true, dataType = "long", paramType = "path")
  public ResponseEntity<DataResponse<List<ReviewInfoDto>>> findAllReviews(
      @PathVariable("performId") Long performId) {
    List<ReviewInfoDto> allReviews = reviewsSearchComplexService.findAllReviews(performId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "리뷰 조회에 성공했습니다.", allReviews),
        HttpStatus.OK);
  }

  /**
   * 최근 등록된 공연 후기를 6개 조회한다.
   *
   * @return 후기 정보
   */
  @GetMapping("/recent/reviews")
  @ApiOperation(value = "최근 등록된 공연 후기를 6개 조회한다.", notes = "최근 등록된 공연 후기를 6개 조회한다.")
  public ResponseEntity<DataResponse<List<ReviewInfoWithPerformDto>>> findRecentReviews() {
    List<ReviewInfoWithPerformDto> recentReviews = reviewsSearchComplexService.findRecentReviews();
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "최근 리뷰 조회에 성공했습니다.", recentReviews),
        HttpStatus.OK);
  }

  /**
   * 후기를 수정한다.
   *
   * @param updateDto 수정할 내용
   * @param user      API 요청자
   * @return 수정된 후기 정보
   */
  @PatchMapping("/reviews")
  @ApiOperation(value = "후기를 수정한다.", notes = "후기를 수정한다. 응답으로 수정된 후기 데이터 1건을 응답한다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<ReviewInfoDto>> updateReview(@RequestBody ReviewUpdateDto updateDto,
      @AuthenticationPrincipal SessionUser user) {
    ReviewInfoDto reviewInfoDto = reviewUpdateComplexService.updateReview(updateDto, user.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "리뷰가 성공적으로 수정되었습니다.", reviewInfoDto),
        HttpStatus.OK);
  }


  /**
   * 후기를 삭제한다.
   *
   * @param reviewId 후기 고유 ID
   * @param sessionUser API 요청자
   * @return 성공적으로 삭제된 후기 메시지 응답
   */
  @DeleteMapping("/reviews/{reviewId}")
  @ApiOperation(value = "후기를 삭제한다.", notes = "공연에 작성된 후기를 삭제한다")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "reviewId", value = "후기 고유 ID", required = true, dataType = "long", paramType = "path"),
      @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
          required = true, dataType = "string", paramType = "header")
  })
  public ResponseEntity<MessageResponse> deleteReview(@PathVariable("reviewId") Long reviewId, @AuthenticationPrincipal SessionUser sessionUser) {
    reviewDeleteComplexService.deleteReview(reviewId, sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "리뷰가 성공적으로 삭제되었습니다."), HttpStatus.OK);
  }


}
