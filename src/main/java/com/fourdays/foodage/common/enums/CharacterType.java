package com.fourdays.foodage.common.enums;

import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CharacterType {

	DUCK("오리"),
	SPARROW("참새"),
	RACCOON("너구리"),
	SEAOTTER("해달"),
	PIGEON("비둘기");

	private final String krName;

	@JsonValue
	public String getKrName() {
		return krName;
	}

	public static CharacterType getRandomOne() {
		CharacterType[] characters = CharacterType.values();
		Random random = new Random();
		int randomIndex = random.nextInt(characters.length);
		return characters[randomIndex];
	}

	public static List<CharacterType> getAll() {
		return List.of(CharacterType.values());
	}

	public String getNameToResponseFormat() {
		return this.name().toLowerCase();
	}
}
