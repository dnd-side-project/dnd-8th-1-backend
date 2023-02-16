package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Portfolio;
import dnd.danverse.domain.profile.entity.Profile;
import java.time.LocalDate;
import lombok.Getter;

/**
 * 프로필 정보를 반환하기 위한 Dto
 */
@Getter
public class ProfileInfoDto {

  /**
   * 프로필의 고유 ID.
   */
  private final Long id;
  /**
   * 프로필의 타입. (팀, 개인)
   */
  private final String type;
  /**
   * 프로필의 이름. (팀명, 개인명)
   */
  private final String name;
  /**
   * 프로필의 이미지 URL.
   */
  private final String imgUrl;
  /**
   * 프로필의 작성자의 주 활동 지역
   */
  private final String location;
  /**
   * 프로필의 작성자의 활동 시작일
   */
  private final LocalDate careerStartDay;
  /**
   * 프로필의 작성자의 자기소개
   */
  private final String description;
  /**
   * 프로필의 작성자의 카카오톡 오픈채팅 URL
   */
  private final String openChatUrl;
  /**
   * 프로필의 작성자의 포트폴리오 URL Dto
   */
  private final PortfolioUrl portfolioUrl;

  /**
   * 프로필의 작성자의 포트폴리오 URL Dto
   */
  @Getter
  public static class PortfolioUrl {

    /**
     * 프로필의 작성자의 유튜브 URL
     */
    private final String youtube;
    /**
     * 프로필의 작성자의 인스타그램 URL
     */
    private final String instagram;
    /**
     * 프로필의 작성자의 트위터 URL
     */
    private final String twitter;

    public PortfolioUrl(Portfolio portfolio) {
      this.youtube = portfolio.getYoutubeUrl();
      this.instagram = portfolio.getInstagramUrl();
      this.twitter = portfolio.getTwitterUrl();
    }
  }

  /**
   * Profile 객체를 이용하여 ProfileInfoDto 를 생성한다.
   * @param profile 프로필 정보를 담은 객체
   */
  public ProfileInfoDto(Profile profile) {
    this.id = profile.getId();
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
