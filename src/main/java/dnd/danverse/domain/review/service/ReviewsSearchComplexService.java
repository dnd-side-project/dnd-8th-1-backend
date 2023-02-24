package dnd.danverse.domain.review.service;

import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoWithPerformDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 공연 후기 전체 조회 복합 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewsSearchComplexService {

  private final ReviewPureService reviewPureService;

  /**
   * 모든 리뷰를 조회한다.
   *
   * @param performId 공연 ID
   * @return ReviewInfoDto 리스트
   */
  public List<ReviewInfoDto> findAllReviews(Long performId) {
    return reviewPureService.findAllReviewsWithWriter(performId);
  }

  /**
   * 최근 리뷰를 조회한다.
   * 메인 화면에서 보여질 공연 정보 및 후기 내용을 6개 조회한다.
   * @return ReviewInfoWithPerformDto 리스트
   */
  public List<ReviewInfoWithPerformDto> findRecentReviews() {
    List<ReviewInfoWithPerformDto> recentReviews = reviewPureService.findRecentReviews();
    recentReviews.subList(0, Math.min(6, recentReviews.size()));
    return recentReviews;
  }
}
