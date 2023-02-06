package dnd.danverse.domain.jwt;

import lombok.Getter;

@Getter
public class AccessRefreshTokenDto {

  private final String newAccessToken;
  private final String newRefreshToken;

  public AccessRefreshTokenDto(String newAccessToken, String newRefreshToken) {
    this.newAccessToken = newAccessToken;
    this.newRefreshToken = newRefreshToken;
  }

}
