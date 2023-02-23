package dnd.danverse.domain.review.service;

import dnd.danverse.domain.review.entity.Review;
import dnd.danverse.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 리뷰 순수 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewPureService {

  private final ReviewRepository reviewRepository;

  /**
   * Review 를 저장한다.
   * @param review Review
   * @return Review
   */
  @Transactional
  public Review saveReview(Review review) {
    log.info("Review 를 저장한다. 내용 : {} ", review.getContent());
    return reviewRepository.save(review);
  }
}
