package dnd.danverse.domain.oauth.dto;

import dnd.danverse.domain.member.dto.response.MemberResponse;
import lombok.Getter;

@Getter
public class OAuth2LoginResponseDTO {
  private final String accessToken;
  private final String refreshToken;
  private final MemberResponse memberResponse;

  public OAuth2LoginResponseDTO(String accessToken, String refreshToken, MemberResponse memberResponse) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.memberResponse = memberResponse;
  }
}
