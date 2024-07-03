package com.fourdays.foodage.common.enums;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fourdays.foodage.member.exception.MemberNotSupportedCharacterTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CharacterType {

	PIGEON("비둘기"),
	SPARROW("참새"),
	CHICK("병아리"),
	HAMSTER("햄스터"),
	RACCOON("너구리"),
	SEAL("물개"),
	SEAOTTER("해달"), // 수달?
	PANDA("팬더");

	private final String krName;

	public static CharacterType getRandomOne() {
		CharacterType[] characters = CharacterType.values();
		Random random = new Random();
		int randomIndex = random.nextInt(characters.length);
		return characters[randomIndex];
	}

	public static List<CharacterType> getAll() {
		return List.of(CharacterType.values());
	}

	@JsonCreator
	public static CharacterType of(String jsonValue) {
		for (CharacterType character : values()) {
			if (character.name().equals(jsonValue.toUpperCase())) {
				return character;
			}
		}
		throw new MemberNotSupportedCharacterTypeException(ResultCode.ERR_NOT_SUPPORTED_CHARACTER_TYPE);
	}

	@JsonValue
	public String toLowerCase() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
