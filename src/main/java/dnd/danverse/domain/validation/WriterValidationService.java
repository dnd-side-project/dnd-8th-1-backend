package dnd.danverse.domain.validation;

import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.global.exception.ErrorCode;

public interface WriterValidationService<T> {

  default T validateWriter(Long targetId, Long memberId) {
    T target = getTarget(targetId);

    Profile targetProfile = getTargetProfile(target);

    Profile profile = retrieveProfile(memberId);

    if (targetProfile.isNotSame(profile)) {
      throw new IllegalArgumentException("작성자가 아닙니다.");
    }

    return target;
  }

  T getTarget(Long targetId);

  Profile getTargetProfile(T target);

  Profile retrieveProfile(Long memberId);

  void throwException(ErrorCode errorCode);

}
