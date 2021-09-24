package com.jwryu.annotation;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LombokItem {
	private String name;

	private int age;

	public boolean isSameAge2(LombokItem member) {
		return this.age == member.age;
	}
}
