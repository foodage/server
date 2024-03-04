package com.fourdays.foodage.enums;

import org.junit.jupiter.api.Test;

import com.fourdays.foodage.common.enums.CharacterType;

/**
 * author         : ebkim <br/>
 * date           : 24-02-13 <br/>
 * description    : 지원 캐릭터 종류  <br/>
 */
public class CharacterTypePickTest {

	@Test
	void pickOne() {
		CharacterType character = CharacterType.getRandomOne();
		System.out.println("# character : " + character.name());
	}

	@Test
	void pickMany() {
		for (int i = 0; i < 10; i++) {
			CharacterType character = CharacterType.getRandomOne();
			System.out.println("# character : " + character.name());
		}
	}
}
