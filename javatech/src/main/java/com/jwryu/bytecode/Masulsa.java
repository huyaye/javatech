package com.jwryu.bytecode;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;

public class Masulsa {
	public static void main(String[] arg) {
//		manipulateByClass();
//		manipulateByName();
		manipulateByJavaAgent();
	}

	private static void manipulateByClass() {
		try {
			new ByteBuddy()
				.redefine(Moja.class)	// 클래스접근이 이미 되었기때문에 Moja는 로딩된 상태임. 
				.method(named("pullOut"))
				.intercept(FixedValue.value("Rabbit"))
				.make()
				.saveIn(new File("/Users/jungwook/study/Java/javatech/target/classes/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(new Moja().pullOut());	// 이시점에 변경된 클래스가 로딩되지는 않는다.
	}

	private static void manipulateByName() {
		ClassLoader classLoader = Masulsa.class.getClassLoader();
		TypePool typePool = TypePool.Default.of(classLoader);

		try {
			new ByteBuddy()
				.redefine(typePool.describe("com.jwryu.bytecode.Moja").resolve(), ClassFileLocator.ForClassLoader.of(classLoader)) 
				.method(named("pullOut"))
				.intercept(FixedValue.value("Rabbit"))
				.make()
				.saveIn(new File("/Users/jungwook/study/Java/javatech/target/classes/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 변경된 클래스가 로딩되어 실행. 하지만 다른곳에서 Moja클래스 조작전에 이미 로딩되었다면 소용없음. 클래스로딩 순서에 의존적.
		System.out.println(new Moja().pullOut());
	}
	
	public static void manipulateByJavaAgent() {
		System.out.println(new Moja().pullOut());
	}
}
