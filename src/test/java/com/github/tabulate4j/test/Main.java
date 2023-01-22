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
		
		return tabulate.toString();
	}
	
	private static String testAddColumnModeWithHeader() {
		List<String> column1 = new ArrayList<>();
		List<String> column2 = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			column1.add("Value " + i);
			column2.add("Value " + i);
		}
		Tabulate tabulate = new Tabulate();
		tabulate.addColumn(column1);
		tabulate.addColumn(column2);
		
		return tabulate.toString();
	}
	
	private static String testAddContentMode() {
		Tabulate tabulate = new Tabulate();
		tabulate.addContent("Value 1\tValue a\nValue 2\tValue b\nValue 3\tValue c\n");
		return tabulate.toString();
	}
	
	private static String testAddContentModeWithHeader() {
		Tabulate tabulate = new Tabulate();
		tabulate.addContent("Value 1\tValue a\nValue 2\tValue b\nValue 3\tValue c\n", "Header 1\tHeader 2\n");
		return tabulate.toString();
	}
	
	public static void main(String[] args) {
		System.out.println("Testing column mode:\n");
		System.out.println(testAddColumnMode());
		System.out.println("Testing column mode (with header):\n");
		System.out.println(testAddColumnModeWithHeader());
		System.out.println("Testing content mode:\n");
		System.out.println(testAddContentMode());
		System.out.println("Testing content mode (with header):\n");
		System.out.println(testAddContentModeWithHeader());
	}
}
