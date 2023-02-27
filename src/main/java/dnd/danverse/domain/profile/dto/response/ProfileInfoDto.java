package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Getter;

/**
 * 프로필 정보를 반환하기 위한 Dto.
 */
@Getter
public class ProfileInfoDto {

  /**
   * 프로필의 고유 ID.
   */
  // TODO : 프로필 ID가 아니라, 멤버 고유 ID를 반환해야 할 것 같다. (수정완료)
  @ApiModelProperty(value = "프로필 소유자의 멤버 고유 ID")
  private final Long id;

  /**
   * 프로필의 타입. (댄스팀, 댄서)
   */
  @ApiModelProperty(value = "이벤트 모집 유형 ex) 댄스팀, 댄서")
  private final String type;

  /**
   * 프로필의 이름. (팀명, 개인명)
   */
  @ApiModelProperty(value = "프로필의 이름 (팀명, 개인명)")
  private final String name;

  /**
   * 프로필의 이미지 URL.
   */
  @ApiModelProperty(value = "프로필의 이미지 URL")
  private final String imgUrl;

  /**
   * 프로필의 작성자의 주 활동 지역.
   */
  @ApiModelProperty(value = "프로필의 작성자의 주 활동 지역")
  private final String location;

  /**
   * 프로필의 작성자의 활동 시작일.
   */
  @ApiModelProperty(value = "프로필의 작성자의 활동 시작일")
  private final LocalDate careerStartDay;

  /**
   * 프로필의 작성자의 자기소개.
   */
  @ApiModelProperty(value = "프로필의 작성자의 자기소개")
  private final String description;

  /**
   * 프로필의 작성자의 카카오톡 오픈채팅 URL.
   */
  @ApiModelProperty(value = "프로필의 작성자의 카카오톡 오픈채팅 URL")
  private final String openChatUrl;

  /**
   * 프로필의 작성자의 포트폴리오 URL Dto.
   */
  @ApiModelProperty(value = "프로필의 포트폴리오 정보")
  private final PortfolioUrl portfolioUrl;


  /**
   * Profile 객체를 이용하여 ProfileInfoDto 를 생성한다.
   *
   * @param profile 프로필 정보를 담은 객체
   */
  public ProfileInfoDto(Profile profile) {
    // TODO : profile Id 가 아닌 Member Id로 변경될 필요성이 있다. (수정완료)
    this.id = profile.getMember().getId();
    this.type = profile.getProfileType().getType();
    this.name = profile.getProfileName();
    this.imgUrl = profile.getProfileImg().getImageUrl();
    this.location = profile.getLocation();
    this.careerStartDay = profile.getCareerStartDay();
    this.description = profile.getDescription();
    this.openChatUrl = profile.getOpenChatUrl().getOpenChatUrl();
    this.portfolioUrl = new PortfolioUrl(profile.getPortfolioUrl());
  }

}
