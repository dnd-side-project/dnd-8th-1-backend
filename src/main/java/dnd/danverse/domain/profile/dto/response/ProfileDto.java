package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Profile;
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
public class ProfileDto {
  private Long id;
  private String name;
  private String imgUrl;

  @Builder
  public ProfileDto(Profile profile) {
    this.id = profile.getId();
    this.name = profile.getProfileName();
    this.imgUrl = profile.getProfileImg().getImageUrl();
  }


}
