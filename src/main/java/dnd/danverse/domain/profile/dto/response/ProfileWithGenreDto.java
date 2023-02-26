package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Portfolio;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Set;
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

  @ApiModelProperty(name = "프로필의 고유 Id")
  private final Long id;

  @ApiModelProperty(name = "프로필 이름")
  private final String name;

  @ApiModelProperty(name = "프로필 유형")
  private final String type;

  @ApiModelProperty(name = "프로필 이미지 url")
  private final String imgUrl;

  @ApiModelProperty(name = "프로필 활동 지역")
  private final String location;

  @ApiModelProperty(name = "프로필 관심 장르")
  private final Set<String> genres;

  @ApiModelProperty(name = "커리어 시작 날짜")
  private final LocalDate startDate;

  @ApiModelProperty(name = "프로필 상세 설명")
  private final String description;

  @ApiModelProperty(name = "프로필 오픈채팅 url")
  private final String openChatUrl;

  @ApiModelProperty(name = "프로필의 포트폴리오 url")
  private final Portfolio portfolio;

  /**
   * profile entity를 응답 dto로 변환하는 생성자.
   *
   * @param profile 프로필.
   */
  public ProfileWithGenreDto(Profile profile) {
    this.id = profile.getId();
    this.name = profile.getProfileName();
    this.type = profile.getProfileType().getType();
    this.imgUrl = profile.getProfileImg().getImageUrl();
    this.location = profile.getLocation();
    this.genres = profile.toStringProfileGenre();
    this.startDate = profile.getCareerStartDay();
    this.description = profile.getDescription();
    this.openChatUrl = profile.getOpenChatUrl().getOpenChatUrl();
    this.portfolio = profile.getPortfolioUrl();
  }
}
