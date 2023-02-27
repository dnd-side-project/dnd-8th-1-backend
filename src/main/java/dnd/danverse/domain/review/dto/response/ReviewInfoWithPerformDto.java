package dnd.danverse.domain.review.dto.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 메인 화면에서 공연에 대한 최신 후기 6개를 조회할 때 사용하는 응답 Dto
 */
@Getter
public class ReviewInfoWithPerformDto {

  /**
   * 후기 고유 ID
   */
  @ApiModelProperty(value = "후기 고유 ID")
  private final Long reviewId;

  /**
   * 후기 내용
   */
  @ApiModelProperty(value = "후기 내용")
  private final String content;

  /**
   * 후기 작성자가 프로필을 등록한지 여부
   */
  @ApiModelProperty(value = "후기 작성자가 프로필을 등록한지 여부")
  private final boolean hasProfile;


  /**
   * 후기 작성 시간
   */
  @ApiModelProperty(value = "후기 작성 시간")
  private final LocalDateTime createdDate;

  /**
   * 후기 작성자
   */
  @ApiModelProperty(value = "후기 작성자")
  private final Writer writer;

  /**
   * 후기 작성 대상 공연
   */
  @ApiModelProperty(value = "후기 작성 대상 공연")
  private final PerformSimpleInfo performance;


  /**
   * 후기 작성자 정보를 담는다.
   */
  @Getter
  static class Writer {

    /**
     * 작성자 고유 ID 상황에 따라서 member 의 ID 또는 profile 의 ID 가 담긴다.
     */
    @ApiModelProperty(value = "작성자 멤버 고유 ID")
    private final Long id;

    /**
     * 작성자 이름 상황에 따라서 member 의 이름 또는 profile 의 이름이 담긴다.
     */
    @ApiModelProperty(value = "작성자 이름")
    private final String name;

    public Writer(Long memberId, String name) {
      this.id = memberId;
      this.name = name;
    }
  }

  @Getter
  static class PerformSimpleInfo {

    /**
     * 공연 고유 ID
     */
    @ApiModelProperty(value = "공연 고유 ID")
    private final Long id;
    /**
     * 공연 제목
     */
    @ApiModelProperty(value = "공연 제목")
    private final String title;
    /**
     * 공연 이미지 URL
     */
    @ApiModelProperty(value = "공연 이미지 URL")
    private final String imgUrl;

    public PerformSimpleInfo(Long id, String title, String imgUrl) {
      this.id = id;
      this.title = title;
      this.imgUrl = imgUrl;
    }
  }

  public ReviewInfoWithPerformDto(Long reviewId, String content,
      LocalDateTime createdDate, Long memberId, String memberName, Long profileId, String profileName,
      Long performId, String performTitle, String performImgUrl) {
    this.reviewId = reviewId;
    this.content = content;
    this.createdDate = createdDate;

    if (hasProfile(profileId)) {
      this.writer = new Writer(memberId, profileName);
      this.hasProfile = true;
    } else {
      this.writer = new Writer(memberId, memberName);
      this.hasProfile = false;
    }

    this.performance = new PerformSimpleInfo(performId, performTitle, performImgUrl);
  }

  private boolean hasProfile(Long profileId) {
    return profileId != null;
  }


}
