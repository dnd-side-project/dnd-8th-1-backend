package dnd.danverse.domain.review.dto.response;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.review.entity.Review;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 리뷰 정보를 담은 응답 Dto
 */
@Getter
public class ReviewInfoDto {

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
   * 후기 작성자 정보를 담는다.
   */
  static class Writer {

    /**
     * 작성자 고유 ID
     * 상황에 따라서 member 의 ID 또는 profile 의 ID 가 담긴다.
     */
    @ApiModelProperty(value = "작성자 고유 ID")
    private final Long id;

    /**
     * 작성자 이름
     * 상황에 따라서 member 의 이름 또는 profile 의 이름이 담긴다.
     */
    @ApiModelProperty(value = "작성자 이름")
    private final String name;

    public Writer(Long id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  /**
   * ReviewInfoDto 를 생성한다.
   * member 가 프로필을 등록한 경우, 프로필 정보를 담는다.
   * member 가 프로필을 등록하지 않은 경우, member 의 이름을 담는다.
   * @param review Review
   * @param member Review 작성자
   */
  public ReviewInfoDto(Review review, Member member) {
    this.reviewId = review.getId();
    this.content = review.getContent();

    if (hasProfile(member)) {
      this.writer = new Writer(member.getProfile().getId(), member.getProfile().getProfileName());
      this.hasProfile = true;
    } else {
      this.writer = new Writer(member.getId(), member.getName());
      this.hasProfile = false;
    }

    this.createdDate = review.getCreatedAt();
  }

  /**
   * member 가 프로필을 등록한지 여부를 반환한다.
   * @param member Review 작성자
   * @return member 가 프로필을 등록한지 여부
   */
  private boolean hasProfile(Member member) {
    return member.getProfile() != null;
  }
}
