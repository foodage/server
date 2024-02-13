package com.fourdays.foodage.common.enums;

import java.util.Random;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CharacterType {

	DUCK("오리"),
	SPARROW("참새"),
	RACCOON("너구리"),
	SEAOTTER("해달"),
	PIGEON("비둘기");

	private final String krName;

	public static CharacterType getRandomOne() {
		CharacterType[] characters = CharacterType.values();
		Random random = new Random();
		int randomIndex = random.nextInt(characters.length);
		return characters[randomIndex];
	}

	public String getNameToResponseFormat() {
		return this.name().toLowerCase();
	}
}
