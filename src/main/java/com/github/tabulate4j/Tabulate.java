/*
 * MIT License
 *
 * Copyright (c) 2023 Lorenzo Billi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.tabulate4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Pretty-print tabular data in Java.
 * @author Lorenzo Billi
 */
public class Tabulate {
	
	/**
	 * List containing the columns of the data. Each columns itself is represented as a list.
	 */
	private List<List<String>> columns;
	
	/**
	 * List containing the headers of the data.
	 */
	private List<String> headers;
	
	/**
	 * List containing the columns length.
	 */
	private List<Integer> columnsLength;
	
	/**
	 * Padding used to format the content of each column. Default value is 1.
	 */
	private int padding;
	
	/**
	 * Width of the resulting formatted table.
	 */
	private int width;
	
	/**
	 * Creates a new {@code Tabulate} object with the default padding value of 1.
	 */
	public Tabulate() {
		columns = new ArrayList<>();
		columnsLength = new ArrayList<>();
		headers = new ArrayList<>();
		padding = 1;
		width = 0;
	}

	/**
	 * Creates a new {@code Tabulate} object with the specified padding value.
	 * @param padding Value of the padding. Must be greater or equal than 1.
	 */
	public Tabulate(int padding) {
		this();
		if (padding < 1) {
			throw new IllegalArgumentException("Padding value cannot be less than 1");
		}
		this.padding = padding;
	}
	
	/**
	 * Adds a new data column to a {@code Tabulate} instance.
	 * @param column Data column to be added.
	 */
	public void addColumn(List<String> column) {
		int length = 0;
		for (String entry : column) {
			if (entry.length() > length) {
				length = entry.length();
			}
		}
		length += padding * 2;
		columns.add(column);
		columnsLength.add(length);
	}
	
	/**
	 * Adds a new data column with the specified header to a {@code Tabulate} instance.
	 * @param column Data column to be added.
	 * @param header Header of the data column that is being added.
	 */
	public void addColumn(List<String> column, String header) {
		addColumn(column);
		headers.add(header);
		if (header.length() + (padding * 2) > columnsLength.get(columnsLength.size() - 1)) {
			columnsLength.set(columnsLength.size() - 1, header.length() + (padding * 2));
		}
	}
	
	/**
	 * Transform a given string to a {@code List} of {@code List} representing the rows and the single entries of the
	 * final formatted table.
	 * @param content String to be parsed. See @see Tabulate#addContent(String) and 
	 * @see Tabulate#addContent(String, String) for how the string should be formatted in order to be parsed correctly.
	 * @return A {@code List} of {@code List} representing each table entry by row.
	 */
	private List<List<String>> parseContent(String content) {
		String[] parsedRows = content.split("\n");
		List<List<String>> rows = new ArrayList<>();
		for (String pr : parsedRows) {
			String[] parsedCells = pr.split("\t");
			List<String> cells = new ArrayList<>();
			for (String pc : parsedCells) {
				cells.add(pc);
			}
			rows.add(cells);
		}
		return rows;
	}
	
	/**
	 * Directly add the entire content of the table as a purposely formatted string.<br>
	 * Use {@code '\t'} to separate each column value, then use {@code '\n'} to create a new row.<br>
	 * <p>Example:</p>
	 * This string:
	 * <pre>"Value 1\tValue a\nValue 2\tValue b\nValue 3\tValue c\n"</pre>
	 * Is formatted as:
	 * <pre>
	 * +-------------------+
	 * | Value 1 | Value a |
	 * +-------------------+
	 * | Value 2 | Value b |
	 * +-------------------+
	 * | Value 3 | Value c |
	 * +-------------------+
	 * </pre>
	 * 
	 * @param content String containing the content of the table to be formatted.
	 */
	public void addContent(String content) {
		List<List<String>> rows = parseContent(content);
		
		for (int i = 0; i < rows.get(0).size(); i++) {
			List<String> column = new ArrayList<>();
			for (int j = 0; j < rows.size(); j++) {
				column.add(rows.get(j).get(i));
			}
			addColumn(column);
		}
	}
	
	/**
	 * Directly add the entire content of the table and its header as a purposely formatted string.<br>
	 * Use {@code '\t'} to separate each column value, then use {@code '\n'} to create a new row.<br>
	 * <p>Example:</p>
	 * These strings:
	 * <pre>"Value 1\tValue a\nValue 2\tValue b\nValue 3\tValue c\n"</pre>
	 * and <pre>"Header 1\tHeader 2\n"</pre>
	 * Are formatted as:
	 * <pre>
	 * +=====================+
	 * | Header 1 | Header 2 |
	 * +=====================+
	 * | Value 1  | Value a  |
	 * +---------------------+
	 * | Value 2  | Value b  |
	 * +---------------------+
	 * | Value 3  | Value c  |
	 * +---------------------+
	 * </pre>
	 * @param content String containing the content of the table to be formatted.
	 * @param headers String containing the headers of the table to be added to the columns.
	 */
	public void addContent(String content, String headers) {
		List<List<String>> header = parseContent(headers);
		List<List<String>> rows = parseContent(content);
		
		int n = 0;
			
		for (int i = 0; i < rows.get(0).size(); i++) {
			List<String> column = new ArrayList<>();
			for (int j = 0; j < rows.size(); j++) {
				column.add(rows.get(j).get(i));
			}
			addColumn(column, header.get(0).get(n));
			n++;
		}
	}
	
	/**
	 * Computes the length of the resulting table by summing all the content of the {@code columnsLength} list.
	 */
	private void computeLength() {
		for (int length : columnsLength) {
			width += length;
		}
		width++;
	}
	
	/**
	 * Gets an horizontal separator element of the correct length, depending on the table size.
	 * @param sepChar The separator character used to generate the horizontal separator.
	 * @return A {@code String} containing the horizontal separator element.
	 */
	private String getHorizontalSeparator(String sepChar) {
		String separator = "+";
		for (int n = 0; n < width; n++) {
			separator += sepChar;
		}
		separator += "+\n";
		return separator;
	}
	
	/**
	 * Gets a {@code String} containing the required amount of spaces in order to create the required padding.
	 * @return The {@code String} containing the padding.
	 */
	private String getPadding() {
		String padding = "";
		for (int n = 0; n < this.padding; n++) {
			padding += " ";
		}
		return padding;
	}
	
	/**
	 * Gets the data as a formatted table.
	 * @return A {@code String} object containing all the formatted data.
	 */
	@Override
	public String toString() {
		
		computeLength();
		boolean hasHeader = false;
		
		StringBuilder sb = new StringBuilder();
		
		if (columns.get(0).size() == 0) {
			return sb.toString();
		}
		
		String horizontalSeparator = getHorizontalSeparator("-");
		String padding = getPadding();
		
		if (headers.size() == columns.size()) {
			hasHeader = true;
			sb.append(getHorizontalSeparator("="));
			for (int i = 0; i < headers.size(); i++) {
				sb.append("|");
				String content = padding + headers.get(i) + padding;
				sb.append(content);
				int remaining = columnsLength.get(i) - content.length();
				while (remaining > 0) {
					sb.append(" ");
					remaining--;
				}
			}
			sb.append("|\n");
		}
	
		for (int i = 0; i < columns.get(0).size(); i++) {
			sb.append(hasHeader && i == 0 ? getHorizontalSeparator("=") : horizontalSeparator);
			for (int j = 0; j < columns.size(); j++) {
				sb.append("|");
				String content = padding + columns.get(j).get(i) + padding;
				sb.append(content);
				int remaining = columnsLength.get(j) - content.length();
				while (remaining > 0) {
					sb.append(" ");
					remaining--;
				}
			}
			sb.append("|\n");
		}
		sb.append(horizontalSeparator);
		
		return sb.toString();
	}
}
