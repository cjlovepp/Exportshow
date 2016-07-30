package com.cj.base.utils;

import java.util.UUID;

public class UUIDUtils {

	/**
	 * 获取到32位的UUID
	 * author cj
     * test update
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		return uuid;
	}

}
