package dnd.danverse.domain.profile.entity;


import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

/**
 * 포트폴리오 Embeddable 객체
 * 유튜브, 인스타그램, 트위터 링크를 담는다.
 */
@Embeddable
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

}
