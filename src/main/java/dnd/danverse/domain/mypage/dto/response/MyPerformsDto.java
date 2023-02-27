package dnd.danverse.domain.mypage.dto.response;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 작성자가 등록한 공연 정보들을 반환하는 응답 Dto.
 */
@Getter
public class MyPerformsDto {

  /**
   * 공연 고유 Id.
   */
  @ApiModelProperty("공연 고유 Id")
  private final Long id;

  /**
   * 공연 등록 날짜.
   */
  @ApiModelProperty("공연 등록 날짜")
  private final LocalDateTime createdDate;

  /**
   * 공연 이미지.
   */
  @ApiModelProperty("공연 이미지")
  private final String imgUrl;

  /**
   * 공연 제목.
   */
  @ApiModelProperty("공연 제목")
  private final String title;

  /**
   * 공연 주최자 프로필.
   */
  @ApiModelProperty("공연 주최자 프로필")
  private final ProfileNameInfo profile;

  /**
   * 공연 주최자 프로필 응답 Dto.
   */
  @Getter
  public static class ProfileNameInfo {

    /**
     * 공연 주최자 프로필 이름.
     */
    @ApiModelProperty("공연 주최자 프로필 이름")
    private final String name;

    public ProfileNameInfo(String name) {
      this.name = name;
    }
  }

  public MyPerformsDto(Performance performance, Profile profile) {
    this.id = performance.getId();
    this.createdDate = performance.getCreatedAt();
    this.imgUrl = performance.getPerformanceImg().getImageUrl();
    this.title = performance.getTitle();
    this.profile = new ProfileNameInfo(profile.getProfileName());

  }


}
