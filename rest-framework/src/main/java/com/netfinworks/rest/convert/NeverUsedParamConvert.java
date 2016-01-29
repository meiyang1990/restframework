package com.netfinworks.rest.convert;

public class NeverUsedParamConvert implements IParamConvert {
	public <T> T convert(String raw, Class<T> distClass) {
		throw new IllegalAccessError(
				"don't use NeverUserParamConvert for ever!!!");
	}
}
