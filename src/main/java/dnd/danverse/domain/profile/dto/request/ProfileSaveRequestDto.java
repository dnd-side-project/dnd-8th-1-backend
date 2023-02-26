package dnd.danverse.domain.profile.dto.request;

import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.TeamType;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.profile.dto.response.PortfolioUrl;
import dnd.danverse.domain.profile.entity.OpenChat;
import dnd.danverse.domain.profile.entity.Portfolio;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 등록을 위한 요청 Dto.
 */
@Getter
@NoArgsConstructor
public class ProfileSaveRequestDto {

  @ApiModelProperty(value = "프로필 타입 ex) 댄서, 댄스팀")
  private String type;

  @ApiModelProperty(value = "프로필 이미지 url")
  private String imgUrl;

  @ApiModelProperty(value = "프로필 이름")
  private String name;

  @ApiModelProperty(value = "프로필 관심 장르")
  @Size(max = 3, min = 1, message = "장르는 최대 3개, 최소 1개까지 선택 가능합니다.")
  private Set<String> genres;

  @ApiModelProperty(value = "프로필 활동지역")
  private String location;

  @ApiModelProperty(value = "댄스 경력")
  private LocalDate careerStartDate;

  @ApiModelProperty(value = "프로필 상세 소개")
  private String description;

  @ApiModelProperty(value = "프로필 오픈챗 url")
  private String openChatUrl;

  /**
   * 프로필의 작성자의 포트폴리오 URL Dto.
   */
  @ApiModelProperty(value = "프로필의 포트폴리오 정보")
  private PortfolioUrl portfolioUrl;

  /**
   * request dto를 profile entity로 변환.
   *
   * @param member 프로필 저장하려는 member.
   * @return 변환된 profile entity.
   */
  public Profile toEntity(Member member) {
    Profile profile = Profile.builder()
        .member(member)
        .profileType(TeamType.of(this.type))
        .profileImg(new Image(this.imgUrl))
        .profileName(this.name)
        .location(this.location)
        .careerStartDay(this.careerStartDate)
        .description(this.description)
        .openChatUrl(new OpenChat(this.openChatUrl))
        .portfolioUrl(new Portfolio(this.portfolioUrl.getYoutube(),
            this.portfolioUrl.getInstagram(), this.portfolioUrl.getTiktok()))
        .build();

    this.genres.stream()
        .map(ProfileGenre::new)
        .forEach(profileGenre -> profileGenre.addProfile(profile));

    return profile;
  }

}
