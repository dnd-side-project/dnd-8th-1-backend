package dnd.danverse.domain.review.service;

import dnd.danverse.domain.review.entity.Review;
import dnd.danverse.domain.validation.ReviewWriterValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 후기 삭제를 위한 복합 Service
 */
@Service
@RequiredArgsConstructor
public class ReviewDeleteComplexService {

  private final ReviewWriterValidationService reviewWriterValidationService;
  private final ReviewPureService reviewPureService;


  /**
   * 후기를 삭제를 원하는 API 요청자가 작성자인지 검증한다.
   * 검증이 완료되면 후기를 삭제한다.
   *
   * @param reviewId 삭제할 후기 ID
   * @param memberId 후기 삭제를 원하는 API 접속자 ID
   */
  public void deleteReview(Long reviewId, Long memberId) {
    Review review = reviewWriterValidationService.validateReviewWriter(reviewId, memberId);

    reviewPureService.deleteReview(review);
  }

}
