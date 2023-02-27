package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트에 신청한 사용자의 프로필 정보를 담은 Dto.
 */
@Getter
@NoArgsConstructor
public class MatchProfileDto {

  // TODO : Swagger 문서에는 profile Id가 아니라, 멤버 고유 ID를 반환해야 할 것 같다. (수정완료)
  @ApiModelProperty(value = "신청자의 멤버 고유 Id")
  private Long id;

  @ApiModelProperty(value = "신청자 프로필 이름")
  private String name;

  @ApiModelProperty(value = "신청자 프로필 이미지")
  private String imgUrl;

  @ApiModelProperty(value = "신청자 프로필 자기소개(상세설명)")
  private String description;

  @ApiModelProperty(value = "신청자의 오픈 채팅방 주소")
  private String openChatUrl;

  @ApiModelProperty(value = "신청자의 활동 지역")
  private String location;


  @Builder
  public MatchProfileDto(Profile profile) {
    // TODO : profile Id 가 아닌 Member Id로 변경될 필요성이 있다. (수정완료)
    this.id = profile.getMember().getId();
    this.name = profile.getProfileName();
    this.imgUrl = profile.getProfileImg().getImageUrl();
    this.description = profile.getDescription();
    this.openChatUrl = profile.getOpenChatUrl().getOpenChatUrl();
    this.location = profile.getLocation();
  }
}
