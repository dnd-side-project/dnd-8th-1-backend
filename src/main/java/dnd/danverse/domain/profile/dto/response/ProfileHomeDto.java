package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * 홈 화면에서 보여줄 프로필 정보를 담은 Dto.
 */
@Getter
public class ProfileHomeDto {

  /**
   * 프로필 ID
   */
  // TODO : 프로필 ID가 아니라, 멤버 고유 ID를 반환해야 할 것 같다. (수정완료)
  @ApiModelProperty(value = "프로필을 소유하고 있는 멤버 고유 ID")
  private final Long id;
  /**
   * 프로필 이름
   */
  @ApiModelProperty(value = "프로필 이름")
  private final String name;
  /**
   * 프로필 이미지 URL
   */
  @ApiModelProperty(value = "프로필 이미지 URL")
  private final String imgUrl;
  /**
   * 프로필 타입
   */
  @ApiModelProperty(value = "프로필 타입")
  private final String type;

  public ProfileHomeDto(Profile profile) {
    // TODO : profile Id 가 아닌 Member Id로 변경될 필요성이 있다. (수정완료)
    this.id = profile.getMember().getId();
    this.name = profile.getProfileName();
    this.imgUrl = profile.getProfileImg().getImageUrl();
    this.type = profile.getProfileType().getType();
  }

  public static List<ProfileHomeDto> toDtoList(List<Profile> profiles) {
    return profiles.stream()
        .map(ProfileHomeDto::new)
        .collect(Collectors.toList());
  }

}
