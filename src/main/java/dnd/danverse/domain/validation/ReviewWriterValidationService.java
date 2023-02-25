package dnd.danverse.domain.validation;

import static dnd.danverse.global.exception.ErrorCode.REVIEW_NOT_WRITER;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.review.entity.Review;
import dnd.danverse.domain.review.exception.NotReviewWriterException;
import dnd.danverse.domain.review.service.ReviewPureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 후기 작성자 검증 복합 half Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewWriterValidationService {

  private final ReviewPureService reviewPureService;

  /**
   * 후기 작성자 검증
   * @param reviewId 후기 ID
   * @param memberId API 요청자 ID
   * @return 후기
   */
  @Transactional(readOnly = true)
  public Review validateReviewWriter(Long reviewId, Long memberId) {
    Review review = reviewPureService.findReviewWithMemberById(reviewId);
    Member writer = review.getMember();

    if (writer.isNotSame(memberId)) {
      throw new NotReviewWriterException(REVIEW_NOT_WRITER);
    }

    return review;
  }
}
