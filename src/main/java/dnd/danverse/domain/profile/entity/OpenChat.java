package dnd.danverse.domain.profile.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카카오 오픈 채팅 Embeddable 객체
 */
@Embeddable
@Getter
@NoArgsConstructor
public class OpenChat {

  /**
   * 카카오 오픈 채팅 URL
   */
  @Column(nullable = false)
  private String openChatUrl;

  public OpenChat(String openChatUrl) {
    this.openChatUrl = openChatUrl;
  }

}
