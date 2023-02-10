package dnd.danverse.domain.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이미지 Embeddable 객체
 * 이미지 확장자 검사 할 수 있다.
 * 이벤트, 공연, 프로필에서 사용된다.
 */
@Embeddable
@NoArgsConstructor
@Getter
public class Image {

  /**
   * 이미지 URL
   */
  @Column(nullable = false)
  private String imageUrl;

}
