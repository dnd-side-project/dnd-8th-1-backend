package dnd.danverse.domain.mypage.service;

import dnd.danverse.domain.mypage.dto.response.MyReviewDto;
import dnd.danverse.domain.review.service.ReviewPureService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 활동 내역에서 자신이 작성한 리뷰 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class MyReviewSearchService {

  private final ReviewPureService reviewPureService;

  /**
   * memberId 를 통해서 자신이 작성한 모든 후기들을 볼 수 있다.
   *
   * @param memberId 멤버 고유 Id.
   * @return 후기 응답 Dto 리스트.
   */
  public List<MyReviewDto> getMyReviews(Long memberId) {
    return reviewPureService.findAllReviewsByMemberId(memberId);
  }


}
