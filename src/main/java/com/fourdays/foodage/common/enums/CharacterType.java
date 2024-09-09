package com.fourdays.foodage.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.member.exception.MemberNotSupportedCharacterTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CharacterType {

	PIGEON("비둘기", Type.DEFAULT, CollectionType.NONE),
	SPARROW("참새", Type.DEFAULT, CollectionType.NONE),
	CHICK("병아리", Type.DEFAULT, CollectionType.NONE),
	HAMSTER("햄스터", Type.DEFAULT, CollectionType.NONE),
	RACCOON("너구리", Type.DEFAULT, CollectionType.NONE),
	SEAL("물개", Type.DEFAULT, CollectionType.NONE),
	OTTER("수달", Type.DEFAULT, CollectionType.NONE),
	PANDA("팬더", Type.DEFAULT, CollectionType.NONE),

	H_PIGEON("용감한 비둘기", Type.HIDDEN, CollectionType.BADGE),
	H_SPARROW("새침한 참새", Type.HIDDEN, CollectionType.BADGE),
	H_CHICK("초롱한 병아리", Type.HIDDEN, CollectionType.BADGE),
	H_HAMSTER("세심한 햄스터", Type.HIDDEN, CollectionType.BADGE),
	H_RACCOON("근면한 너구리", Type.HIDDEN, CollectionType.BADGE),
	H_SEAL("인내심 있는 물개", Type.HIDDEN, CollectionType.BADGE),
	H_OTTER("명랑한 수달", Type.HIDDEN, CollectionType.BADGE),
	H_PANDA("행복한 팬더", Type.HIDDEN, CollectionType.BADGE);

	private final String krName;
	private final Type type;

	private final CollectionType collectionType;

	public static CharacterType getRandomOne() {
		CharacterType[] characters = CharacterType.values();
		CharacterType[] defaultCharacters = Arrays.stream(characters)
			.filter(character -> character.getType() == CharacterType.Type.DEFAULT)
			.toArray(CharacterType[]::new);

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
		throw new MemberNotSupportedCharacterTypeException(ExceptionInfo.ERR_NOT_SUPPORTED_CHARACTER_TYPE);
	}

	@JsonValue
	public String toLowerCase() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}

	public enum Type {
		DEFAULT,
		HIDDEN
	}

	public enum CollectionType {
		NONE,
		BADGE,
		EVENT
	}
}
