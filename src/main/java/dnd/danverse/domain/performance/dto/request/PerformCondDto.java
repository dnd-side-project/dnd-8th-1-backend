package dnd.danverse.domain.performance.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 공연 필터링 조건을 담는 DTO
 * 날짜, 지역, 장르를 통해 공연을 필터링한다.
 * ModelAttribute 로써 사용되기 때문에 2가지 중 하나의 방법을 선택하면 된다.
 * 1. Setter + NoArgsConstructor
 * 2. Getter + AllArgsConstructor
 */
@Getter
@AllArgsConstructor
public class PerformCondDto {

  /**
   * 공연 시작하는 년도
   */
  @ApiModelProperty(value = "공연 시작하는 년도")
  private Integer year;

  /**
   * 공연 시작하는 월
   */
  @ApiModelProperty(value = "공연 시작하는 월")
  private Integer month;
  /**
   * 공연 시작하는 일
   */
  @ApiModelProperty(value = "공연 시작하는 일")
  private Integer day;
  /**
   * 공연 지역
   */
  @ApiModelProperty(value = "공연 지역")
  private String location;
  /**
   * 공연 장르
   */
  @ApiModelProperty(value = "공연 장르")
  private String genre;

  /**
   * 공연 페이지
   */
  @ApiModelProperty(value = "공연 page 페이지")
  private int page;

}
