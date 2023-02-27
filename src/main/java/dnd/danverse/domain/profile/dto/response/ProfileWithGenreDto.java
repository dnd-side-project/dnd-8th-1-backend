package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 프로필 상세 조회 정보를 담은 응답 Dto.
 * - 프로필 고유 id
 * - 프로필 이름
 * - 프로필 이미지 url
 * - 프로필 활동지역
 * - 프로필 장르
 * - 프로필 커리어 시작 날짜
 * - 프로필 상세설명
 * - 오픈 채팅 url
 * - 포트폴리오 url(유튜브, 인스타, 트위터)
 */
@Getter
public class ProfileWithGenreDto {

  @ApiModelProperty(value = "사용자의 멤버 고유 ID")
  private final Long id;

  /**
   * 사용자의 이름.
   */
  @ApiModelProperty(value = "사용자의 이름")
  private final String name;

  /**
   * 사용자의 이메일.
   */
  @ApiModelProperty(value = "사용자의 이메일")
  private final String email;

  /**
   * 사용자의 소셜 프로필 이미지.
   */
  @ApiModelProperty(value = "사용자의 소셜 프로필 이미지")
  private final String imgUrl;

  /**
   * 사용자의 프로필
   */
  @ApiModelProperty(value = "사용자의 프로필")
  private final ProfileDetailResponseDto profile;




  /**
   * profile entity를 응답 dto로 변환하는 생성자.
   *
   * @param profile 프로필.
   */
  public ProfileWithGenreDto(Member member, Profile profile) {
    this.id = member.getId();
    this.name = member.getName();
    this.email = member.getEmail();
    this.imgUrl = member.getSocialImg();
    this.profile = profile != null ? new ProfileDetailResponseDto(profile) : null;
  }
}
