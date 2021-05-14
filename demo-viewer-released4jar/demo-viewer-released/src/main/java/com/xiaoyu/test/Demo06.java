package com.xiaoyu.test;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Demo06 {
	
	
	public Demo06() {
		
	}
	public static void main(String[] args) {
		System.out.println("abb".substring(2, 3));
	}
	
	public void solve() {
		Set<String> set = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return sort(o1, o2);//Ωµ–Ú≈≈¡–
            }
        });
		set.add(".b");
		set.add("a");
		set.add("c");
		System.out.println(set);
	}
	
	public int sort(String str1, String str2) {
		if(str1.startsWith(".") && !str2.startsWith(".")) {
			return -1;
		}
		if(!str1.startsWith(".") && str2.startsWith(".")) {
			return 1;
		}
		return str1.compareTo(str2);
	}
}
