package com.github.tabulate4j.test;

import java.util.ArrayList;
import java.util.List;

import com.github.tabulate4j.Tabulate;

public class Main {
	
	private static String testAddColumnMode() {
		List<String> column1 = new ArrayList<>();
		List<String> column2 = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			column1.add("Value " + i);
			column2.add("Value " + i);
		}
		Tabulate tabulate = new Tabulate();
		tabulate.addColumn(column1, "Header 1");
		tabulate.addColumn(column2, "Header 2");
		
		return tabulate.format();
	}
	
	private static String testAddContentMode() {
		Tabulate tabulate = new Tabulate();
		tabulate.addContent("Value 1\tValue 1\nValue 2\tValue 2\nValue 3\tValue 3\n", "Header 1\tHeader 2\n");
		return tabulate.format();
	}
	
	public static void main(String[] args) {
		System.out.println("Testing column mode (with header):\n");
		System.out.println(testAddColumnMode());
		System.out.println("\nTesting content mode (with header):\n");
		System.out.println(testAddContentMode());
	}
}
