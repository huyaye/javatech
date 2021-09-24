package com.jwryu.javaapt;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@AutoService(Processor.class)
public class MagicMojaProcessor extends AbstractProcessor {

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Collections.singleton(Magic.class.getName());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);
		for (Element element : elements) {
			/*
			 * 어노테이션 검증
			 */
			if (element.getKind() != ElementKind.INTERFACE) {
				// Magic 어노테이션을 interface 가 아닌 곳에 붙이면 컴파일에러 발생
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
						"Magic annotation can not be used on " + element.getSimpleName());
			} else {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + element.getSimpleName());
			}
			
			/*
			 * 클래스 생성
			 */
			TypeElement typeElement = (TypeElement)element;
			ClassName className = ClassName.get(typeElement);

			MethodSpec pullOut = MethodSpec.methodBuilder("pullOutAPT")
					.addModifiers(Modifier.PUBLIC)
					.returns(String.class)
					.addStatement("return $S", "Rabbit made by annotation process")
					.build();

			TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
					.addModifiers(Modifier.PUBLIC)
					.addSuperinterface(className)
					.addMethod(pullOut)
					.build();

			// 소스 파일 생성
			Filer filer = processingEnv.getFiler();

			try {
				JavaFile.builder(className.packageName(), magicMoja)
					.build()
					.writeTo(filer);
			} catch (IOException e) {
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + e);
			}
		}

		return true; // True 리턴하면 애노테이션 처리를 종료시킨다. 다른 프로세서에 처리하지 않는다.
	}

}
