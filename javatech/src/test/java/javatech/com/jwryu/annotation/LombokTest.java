package javatech.com.jwryu.annotation;

import org.junit.Test;

import com.jwryu.annotation.LombokItem;

public class LombokTest {

	@Test
	public void lombok() {
		LombokItem lombokItem = new LombokItem();

		lombokItem.setName("jungwook");

		org.junit.Assert.assertEquals("jungwook", lombokItem.getName());
	}
}
