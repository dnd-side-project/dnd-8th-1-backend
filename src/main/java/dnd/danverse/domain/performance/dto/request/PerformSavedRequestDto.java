package dnd.danverse.domain.performance.dto.request;

import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
   * Request Dto 에서 Performance Entity 로 변환.
   * profile 을 통해 공연 주최자를 설정.
   * Dto 가 가지는 데이터를 통해서 공연을 생성.
   * Image 객체는 값 타입이다. 값 타입은 Immutable 하게 설계해야 한다. 따라서 새로운 Image 객체를 생성하여 값을 넣어준다.
   * String 의 공연 장르 데이터를 통해 공연 장르를 생성 하는 책임은 Performance 에게 위임.
   *
   * @param profile 공연 주최자 프로필.
   * @return dto 에서 변환된 entity.
   */
  public Performance toPerform(Profile profile) {
    Performance performance = Performance.builder()
        .title(this.title)
        .performanceImg(new Image(this.imgUrl))
        .startDate(this.startDate)
        .startTime(this.startTime)
        .location(this.location)
        .description(this.description)
        .address(this.address)
        .profileHost(profile)
        .build();

    performance.addPerformGenres(this.genres);
    return performance;
  }


}
