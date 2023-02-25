package dnd.danverse.domain.profile.entity;


import dnd.danverse.domain.profile.dto.request.ProfileSaveRequestDto.PortfolioUrl;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포트폴리오 Embeddable 객체
 * 유튜브, 인스타그램, 트위터 링크를 담는다.
 */
@Embeddable
@Getter
@NoArgsConstructor
public class Portfolio {

  private String youtubeUrl;
  private String instagramUrl;
  private String twitterUrl;

  public Portfolio(String youtubeUrl, String instagramUrl, String twitterUrl) {
    this.youtubeUrl = youtubeUrl;
    this.instagramUrl = instagramUrl;
    this.twitterUrl = twitterUrl;
  }

  /**
   * 포트폴리오 정보를 담는 dto를 엔티티로 변환한다.
   *
   * @param portfolio 포트폴리오 정보를 담은 Dto.
   */
  public Portfolio(PortfolioUrl portfolio) {
    this.youtubeUrl = portfolio.getYoutube();
    this.instagramUrl = portfolio.getInstagram();
    this.twitterUrl = portfolio.getTwitter();
  }
}
