package org.example;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

import net.bytebuddy.matcher.ElementMatchers;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.FixedValue;

public class MasulsaAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        new AgentBuilder.Default()
                .type(ElementMatchers.any())
				.transform((builder, typeDescription, classLoader, javaModule) -> builder
						.method(named("pullOut")).intercept(FixedValue.value("Rabbit made by java-agent"))).installOn(inst);
    }
}
