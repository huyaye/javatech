package com.jwryu.proxy;

public class DefaultItemService implements ItemService {

	@Override
	public void rent() {
		System.out.println("rent");
	}

	@Override
	public void returnItem() {
		System.out.println("returnItem");
	}

}
