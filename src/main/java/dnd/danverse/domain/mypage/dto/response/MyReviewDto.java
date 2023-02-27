package dnd.danverse.domain.mypage.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 자신이 작성한 후기 정보 응답 Dto.
 */
@Getter
public class MyReviewDto {

  /**
   * 후기 고유 ID.
   */
  @ApiModelProperty(value = "후기 고유 ID")
  private final Long reviewId;

  /**
   * 후기 내용.
   */
  @ApiModelProperty(value = "후기 내용")
  private final String content;


  /**
   * 후기 작성 시간.
   */
  @ApiModelProperty(value = "후기 작성 시간")
  private final LocalDateTime createdDate;

  /**
   * 후기 작성 대상 공연.
   */
  @ApiModelProperty(value = "후기를 작성한 공연")
  private final PerformSimpleDto performance;


  @Getter
  static class PerformSimpleDto {

    /**
     * 공연 고유 ID.
     */
    @ApiModelProperty(value = "공연 고유 ID")
    private final Long id;

    /**
     * 공연 제목.
     */
    @ApiModelProperty(value = "공연 제목")
    private final String title;

    public PerformSimpleDto(Long id, String title) {
      this.id = id;
      this.title = title;
    }
  }

  /**
   * 리뷰 Id, 리뷰 내용, 작성 날짜, 공연 Id, 공연 제목을 반환하는 응답 Dto 생성.
   */
  public MyReviewDto(Long reviewId, String content, LocalDateTime createdDate,
      Long performId, String performTitle) {
    this.reviewId = reviewId;
    this.content = content;
    this.createdDate = createdDate;
    this.performance = new PerformSimpleDto(performId, performTitle);
  }

}
