package dnd.danverse.domain.review.dto.request;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.review.entity.Review;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 후기 내용을 담은 요청 Dto
 */
@Getter
@NoArgsConstructor
public class ReviewContentDto {

  /**
   * 리뷰 내용
   */
  @ApiModelProperty(value = "후기 내용")
  private String review;


  /**
   * ReviewContentDto 를 Review 객체로 변환한다.
   * @param member 후기 작성자
   * @param performance 후기 작성 대상 공연
   * @return Review 객체
   */
  public Review toReview(Member member, Performance performance) {
    return Review.builder()
        .member(member)
        .performance(performance)
        .content(review)
        .build();
  }
}
