package dnd.danverse.domain.review.service;

import static dnd.danverse.global.exception.ErrorCode.REVIEW_NOT_FOUND;

import dnd.danverse.domain.mypage.dto.response.MyReviewDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoWithPerformDto;
import dnd.danverse.domain.review.entity.Review;
import dnd.danverse.domain.review.exception.ReviewNotFoundException;
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
   *
   * @param review Review.
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
   *
   * @param performId 공연 ID
   * @return 공연 리뷰 리스트
   */
  @Transactional(readOnly = true)
  public List<ReviewInfoDto> findAllReviewsWithWriter(Long performId) {
    log.info("dto 로 응답, Review 와 Member 를  join , Member 와 Profile left  join 조회한다. 공연 ID : {} ", performId);
    return reviewRepository.findAllReviewsWithWriter(performId);
  }

  /**
   * 최근 리뷰를 조회한다.
   * Entity 를 그대로 응답하는 것이 아닌, 별도의 Dto 를 만들어, 응답에 필요한 데이터만을 조회하도록 했다.
   *
   * @return 최근 리뷰 리스트.
   */
  @Transactional(readOnly = true)
  public List<ReviewInfoWithPerformDto> findRecentReviews() {
    return reviewRepository.findRecentReviews();
  }

  /**
   * 공연에 대한 리뷰를 조회한다
   * Review, Member, Profile 을 fetch join 한다.
   *
   * @param reviewId 리뷰 ID
   * @return 리뷰
   */
  @Transactional(readOnly = true)
  public Review findReviewWithMemberById(Long reviewId) {
    log.info("Review 와 Member 와 Profile 을 fetch join 조회한다. 리뷰 ID : {} ", reviewId);
    return reviewRepository.findReviewWithMemberById(reviewId).orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND));
  }

  /**
   * Review 를 수정 한다.
   *
   * @param review 수정하고자 하는 Review.
   * @param reviewContent 수정하고자 하는 Review 내용
   * @return 수정된 Review
   */
  @Transactional
  public Review updateReview(Review review, String reviewContent) {
    log.info("Review 를 수정한다. 내용 : {} ", reviewContent);
    return review.updateReview(reviewContent);
  }

  /**
   * memberId 를 통해서 작성한 모든 리뷰를 가져온다.
   *
   * @param memberId 멤버의 고유 Id.
   * @return 자신이 작성한 리뷰 응답 Dto.
   */
  @Transactional(readOnly = true)
  public List<MyReviewDto> findAllReviewsByMemberId(Long memberId) {
    log.info("작성자의 아이디가 {}인 리뷰를 조회한다.", memberId);
    return reviewRepository.findReviewsWithMemberId(memberId);
  }
}
