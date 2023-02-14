package dnd.danverse.domain.performance.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import dnd.danverse.domain.performance.entity.Performance;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * 필터링 된 공연 전체 조회시 사용되는 응답 Dto
 */
@Getter
public class PerformInfoResponse {

  // 공연
  private final Long performId;
  private final String performTitle;
  private final String performImg;
  private final LocalDate performStartDate;
  private final List<String> performGenres;
  private final String performLocation;

  // 공연 주최자
  private final Long profileId;
  private final String profileImg;
  private final String profileName;

  @QueryProjection
  public PerformInfoResponse(Long performId, String performTitle, String performImg, LocalDate performStartDate, List<String> performGenres, String performLocation, Long profileId, String profileImg, String profileName) {
    this.performId = performId;
    this.performTitle = performTitle;
    this.performImg = performImg;
    this.performStartDate = performStartDate;
    this.performGenres = performGenres;
    this.performLocation = performLocation;
    this.profileId = profileId;
    this.profileImg = profileImg;
    this.profileName = profileName;
  }

  /**
   * Entity to Dto
   * @param performance 공연
   */
  public PerformInfoResponse (Performance performance) {
    this.performId = performance.getId();
    this.performTitle = performance.getTitle();
    this.performImg = performance.getPerformanceImg().getImageUrl();
    this.performStartDate = performance.getStartDate();
    this.performGenres = performance.getPerformGenres();
    this.performLocation = performance.getLocation();
    this.profileId = performance.getProfileHost().getId();
    this.profileImg = performance.getProfileHost().getProfileImg().getImageUrl();
    this.profileName = performance.getProfileHost().getProfileName();
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
