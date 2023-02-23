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
   * 후기 작성자 id 값
   */
  @ApiModelProperty(value = "후기 작성자 고유 Id 값")
  private final Long writerId;


  /**
   * 후기 작성자 이름
   */
  @ApiModelProperty(value = "후기 작성자 이름")
  private final String writerName;


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
      this.writerId = member.getProfile().getId();
      this.writerName = member.getProfile().getProfileName();
      this.hasProfile = true;
    } else {
      this.writerId = review.getMember().getId();
      this.writerName = review.getMember().getName();
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
