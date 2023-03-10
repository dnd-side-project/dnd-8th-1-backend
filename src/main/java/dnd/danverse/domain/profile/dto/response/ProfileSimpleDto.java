package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 정보를 전달하기 위한 DTO.
 * - 프로필의 고유 Id
 * - 프로필의 이름
 * - 프로필 이미지
 */
@Getter
@NoArgsConstructor
public class ProfileSimpleDto {

  @ApiModelProperty(value = "프로필을 가지고 있는 멤버의 고유 ID")
  private Long id;
  @ApiModelProperty(value = "프로필 이름")
  private String name;
  @ApiModelProperty(value = "프로필 이미지 url")
  private String imgUrl;

  /**
   * 전체조회에서 간단히 보여주는 프로필의 정보를 담은 Dto.
   *
   * @param profile 반환하고자 하는 프로필 정보
   */
  @Builder
  public ProfileSimpleDto(Profile profile) {
    this.id = profile.getMember().getId();
    this.name = profile.getProfileName();
    this.imgUrl = profile.getProfileImg().getImageUrl();
  }


}
