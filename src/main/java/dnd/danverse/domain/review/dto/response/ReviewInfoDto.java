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
  @Getter
  static class Writer {

    /**
     * 작성자 고유 ID
     * 상황에 따라서 member 의 ID 또는 profile 의 ID 가 담긴다.
     */
    // TODO : 무조권 Member 의 ID 를 담도록 수정 (수정완료)
    @ApiModelProperty(value = "작성자 멤버 고유 ID")
    private final Long id;

    /**
     * 작성자 이름
     * 상황에 따라서 member 의 이름 또는 profile 의 이름이 담긴다.
     */
    @ApiModelProperty(value = "작성자 이름")
    private final String name;

    public Writer(Long memberId, String name) {
      this.id = memberId;
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
    // TODO : 이젠 Writer 에는 Member 의 ID 만 담도록 수정 (수정완료)
    // TODO : 따라서, 87번째 줄은 필요가 없다. (필요하다. 프로필이 있으면, 프로필 이름으로 반환해야 하기 때문에, (수정완료)
    if (hasProfile(member)) {
      this.writer = new Writer(member.getId(), member.getProfile().getProfileName());
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


  /**
   * 해당 생성자는 공연 후기 조회 시, 응답에 필요한 데이터만을 조회하기 위해 만들었다.
   * @param reviewId 후기 ID
   * @param content 후기 내용
   * @param createdDate 후기 작성 시간
   * @param memberId 후기 작성자 ID
   * @param memberName 후기 작성자 이름
   * @param profileId 후기 작성자 프로필 ID
   * @param profileName 후기 작성자 프로필 이름
   */
  public ReviewInfoDto(Long reviewId, String content, LocalDateTime createdDate,
      Long memberId, String memberName, Long profileId, String profileName) {
    this.reviewId = reviewId;
    this.content = content;
    this.createdDate = createdDate;
    // TODO : 이젠 Writer 에는 Member 의 ID 만 담도록 수정 (수정완료)
    // TODO : 따라서, 125 번째 줄은 필요가 없다. 아니다, 필요하다. 프로필이 있으면, 프로필 이름으로 반환해야 하기 때문에, (수정완료)
    if (hasProfile(profileId)) {
      this.writer = new Writer(memberId, profileName);
      this.hasProfile = true;
    } else {
      this.writer = new Writer(memberId, memberName);
      this.hasProfile = false;
    }
  }

  /**
   * profileId 가 null 이 아닌지 여부를 반환한다.
   * @param profileId 후기 작성자 프로필 ID
   * @return profileId 가 null 이 아닌지 여부
   */
  private static boolean hasProfile(Long profileId) {
    return profileId != null;
  }
}
