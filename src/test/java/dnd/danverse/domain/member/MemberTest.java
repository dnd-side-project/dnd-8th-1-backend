package dnd.danverse.domain.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemberTest {

  @Test
  void updateName() {
    Member member = new Member();
    member.updateName("test");
    assertEquals("test", member.getName());
  }
}