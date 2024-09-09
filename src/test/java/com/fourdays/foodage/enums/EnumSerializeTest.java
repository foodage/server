package com.fourdays.foodage.enums;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fourdays.foodage.common.enums.CharacterType;
import com.google.gson.Gson;

class EnumSerializeTest {

	@Test
	@DisplayName("CharacterType")
	public void characterTypeSerialize() throws JsonProcessingException {

		List<CharacterType> characters = CharacterType.getAll();
		List<Temp> temps = new ArrayList<>();
		for (CharacterType character : characters) {
			Temp temp = new Temp(character.name(), character.getKrName());
			temps.add(temp);
		}
		Gson gson = new Gson();
		String json = gson.toJson(temps);
		System.out.println(json);
	}

	class Temp {

		final String key;
		final String lowerCaseKey;
		final String value;

		public Temp(String key, String value) {
			this.key = key;
			this.lowerCaseKey = key.toLowerCase();
			this.value = value;
		}
	}
}
