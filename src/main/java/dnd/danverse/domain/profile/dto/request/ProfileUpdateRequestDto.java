package dnd.danverse.domain.profile.dto.request;

import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 수정 요청 dto.
 */
@Getter
@NoArgsConstructor
public class ProfileUpdateRequestDto {

  /**
   * 멤버 고유 Id.
   */
  @ApiModelProperty(value = "멤버 고유 Id")
  private Long id;

  /**
   * 프로필 유형.
   */
  @ApiModelProperty(value = "프로필 타입 ex) 댄서, 댄스팀")
  private String type;

  /**
   * 프로필 이미지 url.
   */
  @ApiModelProperty(value = "프로필 이미지 url")
  private String imgUrl;

  /**
   * 프로필 이름.
   */
  @ApiModelProperty(value = "프로필 이름")
  private String name;

  /**
   * 프로필 관심 장르 목록.
   */
  @ApiModelProperty(value = "프로필 관심 장르")
  @Size(max = 3, min = 1, message = "장르는 최대 3개, 최소 1개까지 선택 가능합니다.")
  private Set<String> genres;

  /**
   * 프로필 활동 지역.
   */
  @ApiModelProperty(value = "프로필 활동지역")
  private String location;

  /**
   * 프로필 댄스 경력.
   */
  @ApiModelProperty(value = "댄스 경력")
  private LocalDate careerStartDate;

  /**
   * 프로필 상세 소개.
   */
  @ApiModelProperty(value = "프로필 상세 소개")
  private String description;

  /**
   * 프로필 오픈챗 url.
   */
  @ApiModelProperty(value = "프로필 오픈챗 url")
  private String openChatUrl;

  /**
   * 요청 dto 가 가지고 있는 String 장르 값을 profile 과 연관 관계 매핑이 이루어진
   * ProfileGenre 객체로 변환하여 반환.
   * @param profile 연관 관계 매핑을 위한 profile 객체
   * @return Profile 과 연관 관계 매핑이 이루어진 ProfileGenre 객체로 변환된 Set
   */
  public Set<ProfileGenre> getSetOfGenres(Profile profile) {
    return this.getGenres().stream()
        .map(genre -> new ProfileGenre(genre, profile))
        .collect(Collectors.toSet());
  }

}
