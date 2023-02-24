package dnd.danverse.domain.review.service;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.service.MemberPureService;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performance.service.PerformancePureService;
import dnd.danverse.domain.review.dto.request.ReviewContentDto;
import dnd.danverse.domain.review.dto.response.ReviewInfoDto;
import dnd.danverse.domain.review.entity.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 후기 저장 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class ReviewSaveComplexService {

  private final ReviewPureService reviewPureService;
  private final MemberPureService memberPureService;
  private final PerformancePureService performancePureService;

  /**
   * 1. member Id를 통해서 우선 프로필이 있는지 확인한다.
   * 2. 공연 Id를 통해서 공연 정보를 가져온다.
   * 3. 후기 객체를 생성한다.
   * 4. 후기 저장한다.
   * 5. 프로필이 있으면 응답 이름에 프로필 이름을 넣어서 후기를 응답한다.
   * @param contentDto 후기 내용
   * @param memberId 후기 작성자 Id
   * @return 후기 정보
   */
  public List<ReviewInfoDto> saveReview(ReviewContentDto contentDto, Long performId, Long memberId) {
    // member Id를 통해서 우선 프로필이 있는지 확인한다.
    Member member = memberPureService.findMemberWithProfile(memberId);
    Performance perform = performancePureService.getPerformance(performId);
    Review review = contentDto.toReview(member, perform);
    reviewPureService.saveReview(review);

    return reviewPureService.findAllReviewsWithWriter(perform.getId());
  }
}
