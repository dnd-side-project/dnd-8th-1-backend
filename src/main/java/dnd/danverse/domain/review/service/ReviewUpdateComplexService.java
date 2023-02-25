package dnd.danverse.domain.review.service;

import dnd.danverse.domain.review.dto.request.ReviewUpdateDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.entity.Review;
import dnd.danverse.domain.validation.ReviewWriterValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 후기 수정을 위한 복합 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewUpdateComplexService {

  private final ReviewPureService reviewPureService;
  private final ReviewWriterValidationService reviewWriterValidationService;


  /**
   * 후기를 수정한다.
   *
   * @param updateDto 수정할 후기 정보
   * @param memberId 수정자 ID
   * @return 수정된 후기 정보
   */
  public ReviewInfoDto updateReview(ReviewUpdateDto updateDto, Long memberId) {
    Review review = reviewWriterValidationService.validateReviewWriter(updateDto.getId(), memberId);

    reviewPureService.updateReview(review, updateDto.getReview());

    return new ReviewInfoDto(review, review.getMember());
  }
}
