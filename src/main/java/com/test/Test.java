package com.test;

import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Test {

	private static void bSearch() {

		int[] arr = new int[] { 1, 5, 8, 3, 6, 9, 10, 11, 23, 55, 16 };
		Arrays.sort(arr);

		for (int i : arr) {
			// System.out.println(i);
		}
		doSearch(arr, 23);

	}

	private static int doSearch(int[] arr, int find) {

		if (arr.length < 2) {
			return 0;
		}
		int index = arr.length / 2;
		int middle = arr[index];
		if (middle == find) {
			return index;
		}
		if (find < middle) {
			int left[] = Arrays.copyOf(arr, index);
			return doSearch(left, find);
		} else {
			int right[] = Arrays.copyOfRange(arr, index + 1, arr.length);
			return doSearch(right, find);
		}

	}
	
	private static void encry() {
		String str = "iii";
		byte[] bytes = str.getBytes();
		 
        //Base64 加密
        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println("Base 64 加密后：" + encoded);
 
        //Base64 解密
        byte[] decoded = Base64.getDecoder().decode(encoded);
 
        String decodeStr = new String(decoded);
        System.out.println("Base 64 解密后：" + decodeStr);
 
        System.out.println();
	}

	public static void main(String[] args) {
		
		Map<Integer, Integer> lm = new LinkedHashMap<Integer, Integer>(10, 0.75f, true);
		lm.put(3, 11);
		lm.put(1, 12);
		lm.put(5, 23);
		lm.put(2, 22);
		
		lm.put(3, 26);
		lm.get(5);
		
		for (Entry<Integer, Integer> entry : lm.entrySet()) {
			System.out.println(entry.getKey());
		}
	}
}
