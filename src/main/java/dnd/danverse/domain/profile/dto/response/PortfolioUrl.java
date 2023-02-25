package dnd.danverse.domain.profile.dto.response;

import dnd.danverse.domain.profile.entity.Portfolio;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포트폴리오 url 들을 담은 Dto.
 */
@Getter
@NoArgsConstructor
public class PortfolioUrl {

  /**
   * 프로필의 작성자의 유튜브 URL.
   */
  @ApiModelProperty(value = "포트폴리오의 유튜브 URL")
  private String youtube;

  /**
   * 프로필의 작성자의 인스타그램 URL.
   */
  @ApiModelProperty(value = "포트폴리오의 인스타 URL")
  private String instagram;

  /**
   * 프로필의 작성자의 트위터 URL.
   */
  @ApiModelProperty(value = "포트폴리오의 트위터 URL")
  private String twitter;

  /**
   * 포트폴리오의 실제 url 데이터를 dto 데이터로 매핑한다.
   *
   * @param portfolio dto 로 변환하는 포트폴리오 객체.
   */
  public PortfolioUrl(Portfolio portfolio) {
    this.youtube = portfolio.getYoutubeUrl();
    this.instagram = portfolio.getInstagramUrl();
    this.twitter = portfolio.getTwitterUrl();
  }
}
