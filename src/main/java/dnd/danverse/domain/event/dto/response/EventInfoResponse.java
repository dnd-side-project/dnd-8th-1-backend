package dnd.danverse.domain.event.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import dnd.danverse.domain.event.entitiy.EventType;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

import lombok.Getter;

/**
 * 필터링된 이벤트 전체 조회시 사용되는 응답 DTO
 */
@Getter
public class EventInfoResponse {

  // 이벤트
  @ApiModelProperty(value = "이벤트 고유 ID")
  private final Long id;
  @ApiModelProperty(value = "이벤트 제목")
  private final String title;
  @ApiModelProperty(value = "이벤트 지역 ex) 서울, 경기")
  private final String location;
  @ApiModelProperty(value = "이벤트 타입 ex) 콜라보, 쉐어")
  private final String type;
  @ApiModelProperty(value = "이벤트 이미지 URL")
  private final String imgUrl;
  @ApiModelProperty(value = "이벤트 마감일자")
  private final LocalDateTime deadline;
  @ApiModelProperty(value = "이벤트 작성 일자")
  private final LocalDateTime createdAt;
  @ApiModelProperty(value = "이벤트 주최자 정보")
  private final ProfileSimpleInfo profile;

  // 이벤트 주최자
  @Getter
  static class ProfileSimpleInfo {

    @ApiModelProperty(value = "이벤트 주최자 멤버 고유 ID")
    private final Long id;
    @ApiModelProperty(value = "이벤트 주최자 프로필 이름")
    private final String name;
    @ApiModelProperty(value = "이벤트 주최자 프로필 이미지 URL")
    private final String imgUrl;

    @QueryProjection
    public ProfileSimpleInfo(Long memberId, String name, String imgUrl) {
      this.id = memberId;
      this.name = name;
      this.imgUrl = imgUrl;
    }
  }

  // TODO : 어디서 쓰고 있는지 확인 필요 (QueryDSL에서 사용 확인완료 및 수정 완료)
  @QueryProjection
  public EventInfoResponse(Long eventId, String title, String location, EventType type,
      String eventImg, LocalDateTime eventDeadline, LocalDateTime eventCreatedAt, Long memberId,
      String profileName, String profileImg) {
    this.id = eventId;
    this.title = title;
    this.location = location;
    this.type = type.getType();
    this.imgUrl = eventImg;
    this.deadline = eventDeadline;
    this.createdAt = eventCreatedAt;
    this.profile = new ProfileSimpleInfo(memberId, profileName, profileImg);
  }
}
