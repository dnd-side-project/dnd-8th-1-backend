package dnd.danverse.domain.performance.dto.response;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.profile.dto.response.ProfileSimpleDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * 공연 상세조회 정보를 담은 Dto.
 */
@Getter
public class PerformDetailResponse {

  /**
   * 공연 정보글의 고유 Id.
   */
  @ApiModelProperty(value = "공연 고유 ID")
  private final Long id;

  /**
   * 공연 정보글의 제목.
   */
  @ApiModelProperty(value = "공연 제목")
  private final String title;

  /**
   * 공연 정보글에 담기는 이미지 url.
   */
  @ApiModelProperty(value = "공연의 imgUrl")
  private final String imgUrl;

  /**
   * 공연의 시작 날짜.
   */
  @ApiModelProperty(value = "공연 시작 날짜")
  private final LocalDate startDate;

  /**
   * 공연 시작 시간.
   */
  @ApiModelProperty(value = "공연 시작 시간")
  private final LocalDateTime startTime;

  /**
   * 공연 진행 지역.
   */
  @ApiModelProperty(value = "공연 지역")
  private final String location;

  /**
   * 공연 장르.
   */
  @ApiModelProperty(value = "공연 장르")
  private final List<String> genres;

  /**
   * 공연에 대한 상세설명.
   */
  @ApiModelProperty(value = "공연 상세설명")
  private final String description;

  /**
   * 공연 진행 장소.
   */
  @ApiModelProperty(value = "공연 장소")
  private final String address;

  /**
   * 공연 주최자의 프로필.
   */
  @ApiModelProperty(value = "공연 주최자의 프로필 정보를 담은 dto")
  private final ProfileSimpleDto profile;

  @Builder
  public PerformDetailResponse(Performance perform) {
    this.id = perform.getId();
    this.title = perform.getTitle();
    this.imgUrl = perform.getPerformanceImg().getImageUrl();
    this.startDate = perform.getStartDate();
    this.startTime = perform.getStartTime();
    this.location = perform.getLocation();
    this.genres = perform.getPerformGenres();
    this.description = perform.getDescription();
    this.address = perform.getAddress();
    this.profile = new ProfileSimpleDto(perform.getProfileHost());
  }



}
