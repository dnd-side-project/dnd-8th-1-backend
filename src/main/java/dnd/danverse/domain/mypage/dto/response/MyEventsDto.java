package dnd.danverse.domain.mypage.dto.response;

import dnd.danverse.domain.event.entitiy.Event;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * MyPage 에서 사용자가 참여한 이벤트 목록을 조회하기 위한 응답 DTO.
 */
@Getter
public class MyEventsDto {

  /**
   * 이벤트 고유 ID
   */
  @ApiModelProperty(value = "이벤트 고유 ID", example = "1")
  private final Long id;

  /**
   * 이벤트 생성 시간
   */
  @ApiModelProperty(value = "이벤트 글 생성 시간")
  private final LocalDateTime createdAt;

  /**
   * 이벤트 이미지 URL
   */
  @ApiModelProperty(value = "이벤트 이미지 URL")
  private final String imgUrl;

  /**
   * 이벤트 제목
   */
  @ApiModelProperty(value = "이벤트 제목")
  private final String title;

  /**
   * 이벤트에 한번이라도 다른 사용자가 참여하여 매칭 된 여부
   */
  @ApiModelProperty(value = "이벤트에 한번이라도 다른 사용자가 참여하여 매칭 된 여부")
  private final boolean isMatched;

  /**
   * 이벤트 타입
   */
  @ApiModelProperty(value = "이벤트 타입")
  private final String type;

  /**
   * 이벤트 주최자 정보
   */
  @ApiModelProperty(value = "이벤트 주최자 정보")
  private final ProfileNameInfo profile;

  @Getter
  static class ProfileNameInfo {

    @ApiModelProperty(value = "이벤트 주최자 프로필 이름")
    private final String name;

    public ProfileNameInfo(String name) {
      this.name = name;
    }
  }


  /**
   * MyEventsDto 생성자
   *
   * @param event 이벤트
   * @param profile 이벤트 주최자
   * @param isMatched 이벤트에 한번이라도 다른 사용자가 참여하여 매칭 된 여부
   */
  public MyEventsDto(Event event, Profile profile, boolean isMatched) {
    this.id = event.getId();
    this.createdAt = event.getCreatedAt();
    this.imgUrl = event.getEventImg().getImageUrl();
    this.title = event.getTitle();
    this.isMatched = isMatched;
    this.type = event.getEventType().getType();
    this.profile = new ProfileNameInfo(profile.getProfileName());
  }

}
