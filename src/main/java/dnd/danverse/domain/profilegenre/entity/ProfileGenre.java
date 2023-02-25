package dnd.danverse.domain.profilegenre.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.profile.entity.Profile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

/**
 * 프로필 - 장르, 중간 테이블 Entity.
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "PROFILE_GENRE_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "PROFILE_GENRE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class ProfileGenre extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_GENRE_SEQ_GENERATOR")
  private Long id;

  @Column(nullable = false)
  private String genre;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PROFILE_GENRE_PROFILE"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Profile profile;


  public ProfileGenre(String genre) {
    this.genre = genre;
  }

  /**
   * 양방향 관계 편의 메서드.
   *
   * @param profile 장르를 저장하려는 프로필.
   */
  public void addProfile(Profile profile) {
    this.profile = profile;
    profile.getProfileGenres().add(this);
  }

}
