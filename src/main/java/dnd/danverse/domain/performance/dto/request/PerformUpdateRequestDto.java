package dnd.danverse.domain.performance.dto.request;

import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performgenre.entity.PerformGenre;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공연 수정 요청 Dto.
 */
@Getter
@NoArgsConstructor
public class PerformUpdateRequestDto {

  /**
   * 공연 고유 Id.
   */
  @ApiModelProperty(value = "공연 id")
  private Long id;
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
  private Set<String> genres;

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
   * 기존의 요청 Dto 에서는 Set<String> 형태로 장르가 담겨있다.
   * 그러나 실제 엔티티에서는 Set<PerformGenre>이기 때문에, 이를 변환한다.
   * String -> PerformGenre
   *
   * @param performance 장르를 바꾸려고 하는 공연 객체.
   * @return Set<PerformGenre>
   */
  public Set<PerformGenre> stringToGenre(Performance performance) {
    return this.getGenres().stream()
        .map(genre -> new PerformGenre(genre, performance))
        .collect(Collectors.toSet());
  }

}
