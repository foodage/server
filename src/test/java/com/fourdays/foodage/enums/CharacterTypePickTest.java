package com.fourdays.foodage.enums;

import static org.testng.Assert.*;

import java.util.Arrays;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import com.fourdays.foodage.common.enums.CharacterType;

/**
 * author         : ebkim <br/>
 * date           : 24-09-09 <br/>
 * description    : 지원 캐릭터 종류  <br/>
 */
public class CharacterTypePickTest {

	@RepeatedTest(50)
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

	@RepeatedTest(100)
	void getRandomOne_메소드에서_기본_캐릭터만_선택되는지_확인() {
		// 전체 캐릭터 배열을 가져온 후, 기본 캐릭터만 필터링
		CharacterType[] characters = CharacterType.values();
		CharacterType[] defaultCharacters = Arrays.stream(characters)
			.filter(character -> character.getType() == CharacterType.Type.DEFAULT)
			.toArray(CharacterType[]::new);

		// 무작위로 선택된 캐릭터가 기본 캐릭터 배열에 포함되는지 확인
		CharacterType randomCharacter = CharacterType.getRandomOne();

		assertTrue(Arrays.asList(defaultCharacters)
				.contains(randomCharacter),
			"The selected character should be a DEFAULT type character");
	}
}
