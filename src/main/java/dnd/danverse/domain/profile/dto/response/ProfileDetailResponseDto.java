package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;

/**
 * 프로필 정보를 반환하는 응답 Dto.
 */
@Getter
public class ProfileDetailResponseDto {

  @ApiModelProperty(name = "프로필을 가지고 있는 멤버의 고유 Id")
  private final Long id;

  @ApiModelProperty(name = "프로필 유형")
  private final String type;

  @ApiModelProperty(name = "프로필 이름")
  private final String name;

  @ApiModelProperty(name = "프로필 이미지 url")
  private final String imgUrl;

  @ApiModelProperty(name = "프로필 활동 지역")
  private final String location;

  @ApiModelProperty(name = "프로필 관심 장르")
  private final Set<String> genres;

  @ApiModelProperty(name = "커리어 시작 날짜")
  private final LocalDate careerStartDate;

  @ApiModelProperty(name = "프로필 상세 설명")
  private final String description;

  @ApiModelProperty(name = "프로필 오픈채팅 url")
  private final String openChatUrl;

  @ApiModelProperty(name = "프로필의 포트폴리오 url")
  private final PortfolioUrl portfolio;

  /**
   * 입력받은 프로필을 통해서 프로필 정보를 반환하는 dto 생성자.
   *
   * @param profile 정보 반환을 원하는 프로필 객체.
   */
  public ProfileDetailResponseDto(Profile profile) {
    this.id = profile.getMember().getId();
    this.type = profile.getProfileType().getType();
    this.name = profile.getProfileName();
    this.imgUrl = profile.getProfileImg().getImageUrl();
    this.location = profile.getLocation();
    this.genres = profile.toStringProfileGenre();
    this.careerStartDate = profile.getCareerStartDay();
    this.description = profile.getDescription();
    this.openChatUrl = profile.getOpenChatUrl().getOpenChatUrl();
    this.portfolio = new PortfolioUrl(profile.getPortfolioUrl());
  }

}
