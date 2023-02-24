package dnd.danverse.domain.review.service;

import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
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
}
