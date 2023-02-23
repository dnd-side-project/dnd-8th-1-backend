package dnd.danverse.domain.review.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.review.dto.request.ReviewContentDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.service.ReviewSaveComplexService;
import dnd.danverse.global.response.DataResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 후기 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
@ApiIgnore
public class ReviewController {

  private final ReviewSaveComplexService reviewSaveComplexService;

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
  public ResponseEntity<DataResponse<ReviewInfoDto>> createReview(
      @PathVariable("performId") Long performId, @RequestBody ReviewContentDto contentDto,
      @AuthenticationPrincipal SessionUser user) {
    ReviewInfoDto reviewInfoDto = reviewSaveComplexService.saveReview(contentDto, performId, user.getId());
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "리뷰가 성공적으로 등록되었습니다.", reviewInfoDto),
        HttpStatus.CREATED);
  }


}
