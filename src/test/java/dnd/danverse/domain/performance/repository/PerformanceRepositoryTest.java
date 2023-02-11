package dnd.danverse.domain.performance.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.TeamType;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.entity.Role;
import dnd.danverse.domain.member.repository.MemberRepository;
import dnd.danverse.domain.oauth.info.OAuth2Provider;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performgenre.PerformGenreRepository;
import dnd.danverse.domain.performgenre.entity.PerformGenre;
import dnd.danverse.domain.profile.ProfileRepository;
import dnd.danverse.domain.profile.entity.OpenChat;
import dnd.danverse.domain.profile.entity.Portfolio;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profilegenre.ProfileGenreRepository;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class PerformanceRepositoryTest {

  @Autowired
  private PerformanceRepository performanceRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private ProfileGenreRepository profileGenreRepository;
  @Autowired
  private ProfileRepository profileRepository;
  @Autowired
  private PerformGenreRepository performGenreRepository;

  private List<Member> members;
  private List<ProfileGenre> profileGenres;
  private List<Profile> profiles;
  private List<PerformGenre> performGenres;
  private List<Performance> performances;
  private LocalDate localDate;
  private LocalTime localTime;

  @BeforeEach
  void setUp() {
    members = saveMembers(createMembers(2));
    profileGenres = saveProfileGenres(createProfileGenres(2));
    performGenres = savePerformGenres(createPerformGenres(2));

    // 각각의 member 는 만들어진 profileGenre 장르 모두 갖게 된다.
    profiles = saveProfiles(createProfiles(members, profileGenres));
  }


  @Test
  void findImminentPerforms() {
    //given
    // 현재 시간보다 항상 7일 뒤, 13시 시간을 기준으로 한다.
    localDate = LocalDate.now().plusDays(7);
    localTime = LocalTime.of(13,0);

    performances = savePerformances(createPerformances(localDate, localTime, profiles,
        performGenres));

    // when
    List<Performance> imminentPerforms = performanceRepository.findImminentPerforms(
        LocalDate.now());

    //then
    assertEquals(4, imminentPerforms.size());
  }



  /**
   * Members 를 모두 DB 에 저장한다.
   * @param members 저장할 Member List
   * @return 최종적으로 members 의 size 만큼 저장된 Member List
   */
  private List<Member> saveMembers(List<Member> members) {
    return memberRepository.saveAll(members);
  }

  /**
   * ProfileGenre 를 모두 DB 에 저장한다.
   * @param profileGenres 저장할 ProfileGenre List
   * @return 저장된 ProfileGenre List
   */
  private List<ProfileGenre> saveProfileGenres(List<ProfileGenre> profileGenres) {
    return profileGenreRepository.saveAll(profileGenres);
  }

  /**
   * Profile 을 모두 DB 에 저장한다.
   * @param profiles 저장할 Profile List
   * @return 저장된 Profile List
   */
  private List<Profile> saveProfiles(List<Profile> profiles) {
    return profileRepository.saveAll(profiles);
  }

  /**
   * PerformGenre 를 모두 DB 에 저장한다.
   * @param performGenres 저장할 PerformGenre List
   * @return 저장된 PerformGenre List
   */
  private List<PerformGenre> savePerformGenres(List<PerformGenre> performGenres) {
    return performGenreRepository.saveAll(performGenres);
  }

  /**
   * Performance 를 모두 DB 에 저장한다.
   * @param performances 저장할 Performance List
   * @return 저장된 Performance List
   */
  private List<Performance> savePerformances(List<Performance> performances) {
    return performanceRepository.saveAll(performances);
  }


  /**
   * Performance 들을 객체로 생성한다.
   * 공연 데이터에는 1 Profile(주최자) 에 1 PerformGenre(공연 장르) 를 가진다.
   * 최종적으로 profiles(= member) * performGenres 개수만큼 공연 객체가 만들어진다.
   * @param localDate 현재 시간보다 항상 7일 뒤의 시간
   * @param localTime 13시
   * @param profiles 객체로 만들어진 Profile List
   * @param performGenres 객체로 만들어진 PerformGenre List
   * @return 객체로 생성된 Performance List
   */
  private List<Performance> createPerformances(LocalDate localDate, LocalTime localTime,
      List<Profile> profiles, List<PerformGenre> performGenres) {

    return profiles.stream()
        .flatMap(profile -> performGenres.stream()
            .map(performGenre -> new Performance(profile, List.of(performGenre), "공연제목",
                "지역", "상세주소", localDate, LocalDateTime.of(localDate, localTime),
                new Image("imageUrl"),
                "공연 소개글")
            )
        ).collect(Collectors.toList());
  }

  /**
   * PerformGenre 객체들을 생성한다.
   * @param count 생성할 PerformGenre 객체의 개수
   * @return 객체로 생성된 PerformGenre List
   */
  private List<PerformGenre> createPerformGenres(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new PerformGenre("테스트공연장르" + i))
        .collect(Collectors.toList());
  }


  /**
   * Profile 객체들을 생성한다. 내부적으로 필요한 객체들을 포함한다.
   * 프로필 데이터에는 1 Member(프로필 주최자) 에 , 객체로 생성된 여러 ProfileGenre(프로필 장르) 한꺼번에 가진다.
   * profiles 의 개수는 member count 의 개수와 같게 나온다.
   * @param members 객체로 생서된 Member List
   * @param profileGenres 객체로 생성된 ProfileGenre List
   * @return 객체로 생성된 Profile List
   */
  private List<Profile> createProfiles(List<Member> members, List<ProfileGenre> profileGenres) {
    return members.stream()
        .map(member -> new Profile(member,
                profileGenres,
                TeamType.TEAM,
                "테스트프로필이름",
                new Image("imageUrl"),
                "지역",
                LocalDate.now(),
                "소개글",
                new OpenChat("openChatUrl"),
                new Portfolio("youtubeUrl",
                    "instagramUrl",
                    "twitterUrl"))
        )
        .collect(Collectors.toList());
  }


  /**
   * ProfileGenre 객체들을 생성한다.
   * @param count 생성할 ProfileGenre 객체의 개수
   * @return 객체로 생성된 ProfileGenre List
   */
  private List<ProfileGenre> createProfileGenres(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new ProfileGenre("테스트프로필장르" + i))
        .collect(Collectors.toList());
  }

  /**
   * Member 객체들을 생성한다.
   * @param count 생성할 Member 객체의 개수
   * @return 객체로 생성된 Member List
   */
  private List<Member> createMembers(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Member("testName" + i,
            "email" + i,
            "username" + i,
            "password" + i,
            "imgUrl" + i,
            Role.USER_PROFILE_NO,
            OAuth2Provider.GOOGLE))
        .collect(Collectors.toList());
  }



}

