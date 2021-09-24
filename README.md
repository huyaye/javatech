# Java 기술 샘플 코드 
bytecode 조작, reflection, proxy, annotation processor
## 프로젝트 구성
### javatech
	샘플 클래스 구현
	주로 test 패키지의 클래스들에서 테스트.
	javaapt 와 javaagent 를 사용하는 부분도 있음. 
### javaagent
	클래스로딩 시점에 특정메소드의 바이트코드를 변경시키는 java-agent
	mvn clean package 로 jar 생성
	javatech 프로젝트의 com.jwryu.bytecode.Masulsa 클래스실행시 vm 옵션으로 생성된 jar를 사용한다. -javaagent:{jar경로}
### javaapt
	annotation processor 구성 (테스트용 Magic 어노테이션)
	Magic 어노테이션을 사용하는 프로젝트의 컴파일시점에 MagicMoja.java 파일을 Generation
	mvn clean install 로 maven repository 에 설치
	javatech 프로젝트의 com.jwryu.annotation.MasulsaAPT 클래스실행 테스트
		target/generated-sources/annotations 에 MagicMoja.java 파일 생성된것을 확인.
