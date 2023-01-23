package dnd.danverse.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Member {

  private int age;
  private String name;

  public void updateName(String name) {
    this.name = name;
  }




}
