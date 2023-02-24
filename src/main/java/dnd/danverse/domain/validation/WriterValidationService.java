package dnd.danverse.domain.validation;

import dnd.danverse.domain.profile.entity.Profile;

/**
 * API 요청자와 작성자가 동일한지 검증하는 서비스.
 * Interface 로 구현하면, 각 도메인 별로 구현체를 만들어서 사용할 수 있다.
 * @param <T> T 를 통해서 추후 Bean 을 주입받을 수 있다.
 */
public interface WriterValidationService<T> {

  /**
   * API 요청자와 작성자가 동일한지 검증한다.
   * @param targetId 검증할 대상 Id.
   * @param memberId API 요청자 Id.
   * @return 검증이 완료된 대상 객체.
   */
  default T validateWriter(Long targetId, Long memberId) {
    T target = getTarget(targetId);

    Profile targetProfile = getTargetProfile(target);

    Profile apiProfile = retrieveProfile(memberId);

    if (targetProfile.isNotSame(apiProfile)) {
      throwException();
    }

    return target;
  }

  /**
   * 검증할 대상 객체를 가져온다.
   * @param targetId 검증할 대상 Id.
   * @return 검증할 대상 객체.
   */
  T getTarget(Long targetId);

  /**
   * 검증할 대상 객체의 작성자를 가져온다.
   * @param target 검증할 대상 객체.
   * @return 검증할 대상 객체의 작성자.
   */
  Profile getTargetProfile(T target);

  /**
   * API 요청자의 프로필을 가져온다.
   * @param memberId API 요청자 Id.
   * @return API 요청자의 프로필.
   */
  Profile retrieveProfile(Long memberId);

  /**
   * 검증에 실패했을 때 발생시킬 예외를 던진다.
   */
  void throwException();

}
