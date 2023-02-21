package dnd.danverse.domain.performance.dto.request;

import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performgenre.entity.PerformGenre;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공연 등록 요청 Dto.
 */
@Getter
@NoArgsConstructor
public class PerformSavedRequestDto {

  /**
   * 공연 제목.
   */
  @ApiModelProperty(value = "공연 제목")
  private String title;

  /**
   * 공연 지역.
   */
  @ApiModelProperty(value = "공연 지역")
  private String location;

  /**
   * 공연 장소.
   */
  @ApiModelProperty(value = "공연 장소")
  private String address;

  /**
   * 공연 시작 날짜.
   */
  @ApiModelProperty(value = "공연 시작 날짜")
  private LocalDate startDate;

  /**
   * 공연 시작 시간.
   */
  @ApiModelProperty(value = "공연 시작 시간")
  private LocalDateTime startTime;

  /**
   * 공연 장르 리스트(최대 3개까지).
   */
  @ApiModelProperty(value = "공연 장르 리스트")
  @Size(max = 3, min = 1, message = "장르는 최대 3개, 최소 1개까지 선택 가능합니다.")
  private List<String> genres;

  /**
   * 공연 포스터 Url.
   */
  @ApiModelProperty(value = "공연 포스터 Url")
  private String imgUrl;

  /**
   * 공연 상세설명.
   */
  @ApiModelProperty(value = "공연 상세 설명")
  private String description;

  /**
   * dto to Entity.
   *
   * @param profile 공연 주최자 프로필.
   * @return dto에서 변환된 entity.
   */
  public Performance toEntity(Profile profile) {
    return Performance.builder()
        .title(this.title)
        .performanceImg(new Image(this.imgUrl))
        .startDate(this.startDate)
        .startTime(this.startTime)
        .location(this.location)
        .performGenres(this.getGenres().stream()
            .map(PerformGenre::new)
            .collect(Collectors.toList()))
        .description(this.description)
        .address(this.address)
        .profileHost(profile)
        .build();
  }


}
