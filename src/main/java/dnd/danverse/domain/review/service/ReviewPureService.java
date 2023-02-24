package dnd.danverse.domain.review.service;

import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.entity.Review;
import dnd.danverse.domain.review.repository.ReviewRepository;
import java.util.List;
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

  /**
   * 공연에 대한 모든 리뷰를 조회한다.
   * Review 와 Member 는 fetch join,
   * Member 와 Profile 은 left join fetch 를 시도하려 했지만,
   * 응답에 필요한 데이터에 비해, 너무 많은 컬럼을 조회하게 되어, 성능이 떨어지는 문제가 발생했다.
   * 따라서, 별로의 Dto 를 만들어, 응답에 필요한 데이터만을 조회하도록 했다.
   * @param performId 공연 ID
   * @return 공연 리뷰 리스트
   */
  @Transactional(readOnly = true)
  public List<ReviewInfoDto> findAllReviewsWithWriter(Long performId) {
    log.info("dto 로 응답, Review 와 Member 를  join , Member 와 Profile left  join 조회한다. 공연 ID : {} ", performId);
    return reviewRepository.findAllReviewsWithWriter(performId);
  }
}
