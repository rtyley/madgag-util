package com.madgag.io.csv;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class CSVFileParser {
	public <T> List<T> parse(Reader reader, CSVLineParser<T> lineParser) throws IOException {
		List<T> list = new ArrayList<T>();
		BufferedReader bufferedReader = new BufferedReader(reader);
		String line =null;
		while ((line=bufferedReader.readLine())!=null) {
			if (!line.isEmpty()) {
				List<String> values = asList(line.split(","));
				list.add(lineParser.parse(values));
			}
		}
		return list;
	}
}
