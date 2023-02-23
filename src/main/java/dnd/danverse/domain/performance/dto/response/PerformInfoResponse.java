package dnd.danverse.domain.performance.dto.response;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.profile.dto.response.ProfileSimpleDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * 필터링 된 공연 전체 조회시 사용되는 응답 Dto
 */
@Getter
public class PerformInfoResponse {

  // 공연
  @ApiModelProperty(value = "공연 고유 ID")
  private final Long id;
  @ApiModelProperty(value = "공연 제목")
  private final String title;
  @ApiModelProperty(value = "공연 이미지 URL")
  private final String imgUrl;
  @ApiModelProperty(value = "공연 시작 날짜+시간 ex) 2021-01-01T12:00:00")
  private final LocalDateTime startDate;
  @ApiModelProperty(value = "공연 장르")
  private final List<String> genres;
  @ApiModelProperty(value = "공연 장소")
  private final String location;

  // 공연 주최자
  @ApiModelProperty(value = "공연 주최자 프로필")
  private final ProfileSimpleDto profile;


  /**
   * Entity to Dto
   * performance.getProfileHost() 시점에 N +1 문제가 발생하지만
   * Batch Size 를 통해 해결한다.
   * @param performance 공연
   */
  public PerformInfoResponse (Performance performance) {
    this.id = performance.getId();
    this.title = performance.getTitle();
    this.imgUrl = performance.getPerformanceImg().getImageUrl();
    this.startDate = performance.getStartTime();
    this.genres = performance.getStringOfPerformGenres();
    this.location = performance.getLocation();
    this.profile = new ProfileSimpleDto(performance.getProfileHost());
  }

  /**
   * Entity List to Dto List
   * @param performContent 공연 리스트
   * @return 공연 정보 응답 Dto 리스트
   */
  public static List<PerformInfoResponse> of(List<Performance> performContent) {
    return performContent.stream().map(PerformInfoResponse::new).collect(Collectors.toList());
  }
}
