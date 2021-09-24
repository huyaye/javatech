package javatech.com.jwryu.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.jwryu.proxy.DefaultItemService;
import com.jwryu.proxy.ItemService;

public class ItemServiceTest {

	ItemService itemService = (ItemService) Proxy.newProxyInstance(
		ItemService.class.getClassLoader(), 
		new Class[] {ItemService.class},
		new InvocationHandler() {
			ItemService itemService = new DefaultItemService();
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("Before " + method.getName() + " ===============");
				Object invoke = method.invoke(itemService, args);
				System.out.println("After =======================");
				return invoke;
			}
		}
	);
			
	@Test
	public void proxy() {
		itemService.rent();
		System.out.println();
		itemService.returnItem();
	}
}
