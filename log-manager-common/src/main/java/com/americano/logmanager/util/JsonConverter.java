package com.americano.logmanager.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonConverter {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T fromJson(String json, Class<T> type) {
		try {
			return objectMapper.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static final TypeReference<Map<String, Object>> typeOfMap = new TypeReference<Map<String, Object>>() {};

	public static Map<String, Object> toMap(String json) {
		try {
			return objectMapper.readValue(json, typeOfMap);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
