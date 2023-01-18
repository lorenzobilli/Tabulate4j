package com.github.tabulate4j.test;

import java.util.ArrayList;
import java.util.List;

import com.github.tabulate4j.Tabulate;

public class Main {
	public static void main(String[] args) {
		List<String> column1 = new ArrayList<>();
		List<String> column2 = new ArrayList<>();
		for (int i = 1; i <= 5; i++) {
			column1.add("Value " + i);
			column2.add("Value " + i);
		}
		Tabulate tabulate = new Tabulate();
		tabulate.addColumn(column1, "Header 1");
		tabulate.addColumn(column2, "Header 2");
		System.out.println(tabulate.format());
	}
}
