package dnd.danverse.domain.performance.dto.response;

import dnd.danverse.domain.performance.entity.Performance;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 임박한 공연 데이터를 전달하기 위한 DTO.
 * - 공연 제목
 * - 공연 시작 날짜
 * - 공연 이미지
 */
@Getter
@RequiredArgsConstructor
public class ImminentPerformsDto {

  /**
   * 공연 ID
   */
  @ApiModelProperty(value = "공연 고유 ID")
  private final Long id;

  /**
   * 공연 제목
   */
  @ApiModelProperty(value = "공연 제목")
  private final String title;
  /**
   * 공연 시작 날짜
   */
  @ApiModelProperty(value = "공연 시작 날짜 , ex) 2021-01-01")
  private final LocalDate startDate;
  /**
   * 공연 이미지
   */
  @ApiModelProperty(value = "공연 이미지 URL")
  private final String imgUrl;

  public ImminentPerformsDto(Performance performance) {
    this.id = performance.getId();
    this.title = performance.getTitle();
    this.startDate = performance.getStartDate();
    this.imgUrl = performance.getPerformanceImg().getImageUrl();
  }
}
