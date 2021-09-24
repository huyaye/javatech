package com.jwryu.di;

import java.util.Arrays;

public class ContainerService {

	public static <T> T getObject(Class<T> classType) {
		T instance = createInstance(classType);

		Arrays.stream(classType.getDeclaredFields()).forEach(field -> {
			if (field.getAnnotation(Inject.class) != null) {
				Object fieldInstance = getObject(field.getType());
				field.setAccessible(true);
				try {
					field.set(instance, fieldInstance);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});

		return instance;
	}

	private static <T> T createInstance(Class<T> classType) {
		try {
			return classType.getConstructor(null).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
