package com.bonc.test;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Test {

	public static void main(String[] args) {
		String password = "123456";
		String salt = "customer8d78869f470951332959580424d4bf4f";//d3c59d25033dbf980d29554025c23a75
		password = new SimpleHash("MD5",password,salt.getBytes(),2).toHex();
		
		

		System.out.println(password);
	}

}
