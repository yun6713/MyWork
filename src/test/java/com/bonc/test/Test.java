package com.bonc.test;

import org.springframework.util.DigestUtils;

public class Test {

	public static void main(String[] args) {
		String s = DigestUtils.md5DigestAsHex("123456".getBytes());
		
		System.out.println(s);

		System.out.println(DigestUtils.md5DigestAsHex(s.getBytes()));
	}

}
