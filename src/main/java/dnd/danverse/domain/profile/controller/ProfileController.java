package dnd.danverse.domain.profile.controller;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.profile.dto.request.ProfileSaveRequestDto;
import dnd.danverse.domain.profile.dto.request.ProfileUpdateRequestDto;
import dnd.danverse.domain.profile.dto.response.ProfileDetailResponseDto;
import dnd.danverse.domain.profile.dto.response.ProfileHomeDto;
import dnd.danverse.domain.profile.dto.response.MemberWithProfileDto;
import dnd.danverse.domain.profile.service.ProfileDeleteComplexService;
import dnd.danverse.domain.profile.service.ProfileDetailService;
import dnd.danverse.domain.profile.service.ProfileSaveService;
import dnd.danverse.domain.profile.service.ProfileSearchService;
import dnd.danverse.domain.profile.service.ProfileUpdateService;
import dnd.danverse.global.response.DataResponse;
import dnd.danverse.global.response.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 프로필 컨트롤러.
 */
@Controller
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
@Api(tags = "ProfileController : 프로필 관련 API")
public class ProfileController {

  private final ProfileSearchService profileSearchService;
  private final ProfileDetailService profileDetailService;
  private final ProfileSaveService profileSaveService;
  private final ProfileUpdateService profileUpdateService;
  private final ProfileDeleteComplexService profileDeleteComplexService;

  /**
   * 서비스 사용자는 실제 사용자의 프로필 6개를 랜덤으로 조회할 수 있습니다.
   *
   * @return 랜덤 프로필 6개를 반환한다.
   */
  @GetMapping("/home")
  @ApiOperation(value = "프로필 6개 랜덤 조회", notes = "프로필 6개 랜덤 조회")
  public ResponseEntity<DataResponse<List<ProfileHomeDto>>> searchProfileForHome() {
    List<ProfileHomeDto> profileHomes = profileSearchService.searchProfileForHome();
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 6개 랜덤 조회 성공", profileHomes), HttpStatus.OK);
  }

  /**
   * 서비스 사용자는 다른 사용자의 프로필을 조회할 수 있습니다.
   */
  @GetMapping("/{memberId}")
  @ApiOperation(value = "프로필 상세 조회", notes = "다른 사용자의 프로필 상세 조회")
  @ApiImplicitParam(name = "memberId", value = "멤버 고유 ID", required = true)
  public ResponseEntity<DataResponse<MemberWithProfileDto>> getProfile(@PathVariable("memberId") Long memberId) {
    MemberWithProfileDto memberWithProfileDto = profileDetailService.getProfile(memberId);
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 상세 조회 성공", memberWithProfileDto), HttpStatus.OK);
  }

  /**
   * 로그인한 사용자는 프로필을 등록할 수 있습니다.
   */
  @PostMapping()
  @ApiOperation(value = "프로필 등록", notes = "로그인한 사용자에 한하여 프로필 등록")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<ProfileDetailResponseDto>> saveProfile(@RequestBody ProfileSaveRequestDto request,
      @AuthenticationPrincipal SessionUser sessionUser) {
    ProfileDetailResponseDto profileResponse = profileSaveService.saveProfile(request, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "프로필 등록 성공", profileResponse), HttpStatus.CREATED);
  }

  /**
   * 프로필을 등록한 사용자는 프로필을 수정할 수 있습니다.
   */
  @PatchMapping()
  @ApiOperation(value = "프로필 수정", notes = "프로필을 등록한 사용자에 한하여 프로필 수정")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<ProfileDetailResponseDto>> updateProfile(@RequestBody @Validated ProfileUpdateRequestDto profileUpdateRequest,
      @AuthenticationPrincipal SessionUser sessionUser) {
    ProfileDetailResponseDto detailResponse = profileUpdateService.updateProfile(profileUpdateRequest, sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "프로필 수정 성공", detailResponse), HttpStatus.OK);
  }

  /**
   * 프로필을 등록한 사용자는 자신의 프로필을 삭제할 수 있습니다.
   *
   * @param sessionUser API 호출 사용자
   * @return 프로필 삭제 성공에 대한 메시지를 응답한다.
   */
  @DeleteMapping()
  @ApiOperation(value = "프로필 삭제", notes = "자신의 프로필을 삭제 할 수 있다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<MessageResponse> deleteProfile(@AuthenticationPrincipal SessionUser sessionUser) {
    profileDeleteComplexService.deleteProfile(sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "프로필 삭제 성공"), HttpStatus.OK);
  }


}
