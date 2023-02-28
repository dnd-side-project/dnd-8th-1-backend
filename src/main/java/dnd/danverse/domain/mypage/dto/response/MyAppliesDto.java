package dnd.danverse.domain.mypage.dto.response;

import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 활동 내역에서 사용자가 지원한 이벤트 목록을 조회하기 위한 응답 DTO.
 */
@Getter
public class MyAppliesDto {

  /**
   * 이벤트 고유 ID.
   */
  @ApiModelProperty(value = "이벤트 고유 ID", example = "1")
  private final Long id;

  /**
   * 이벤트 지원 날짜.
   */
  @ApiModelProperty(value = "이벤트 지원 날짜")
  private final LocalDateTime appliedAt;

  /**
   * 지원한 이벤트 이미지 url.
   */
  @ApiModelProperty(value = "지원한 이벤트 이미지 url")
  private final String imgUrl;

  @ApiModelProperty(value = "지원한 이벤트 매칭 여부")
  private final boolean isMatched;

  /**
   * 지원한 이벤트 제목.
   */
  @ApiModelProperty(value = "지원한 이벤트 제목")
  private final String title;

  /**
   * 이벤트 주최자 프로필 정보 Dto.
   */
  @ApiModelProperty(value = "이벤트 주최자 프로필 정보 Dto")
  private final ProfileNameInfo profile;

  @Getter
  static class ProfileNameInfo {

    /**
     * 이벤트 주최자 프로필 이름.
     */
    @ApiModelProperty(value = "이벤트 주최자 프로필 이름")
    private final String name;

    public ProfileNameInfo(String name) {
      this.name = name;
    }
  }

  public MyAppliesDto(EventMatch appliedEvent, Profile profile) {
    this.id = appliedEvent.getEvent().getId();
    this.appliedAt = appliedEvent.getCreatedAt();
    this.imgUrl = appliedEvent.getEvent().getEventImg().getImageUrl();
    this.isMatched = appliedEvent.isMatched();
    this.title = appliedEvent.getEvent().getTitle();
    this.profile = new ProfileNameInfo(profile.getProfileName());
  }

}
