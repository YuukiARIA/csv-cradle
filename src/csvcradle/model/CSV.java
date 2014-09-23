package csvcradle.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csvcradle.model.parser.CSVLexer;
import csvcradle.model.parser.CSVParser;
import csvcradle.validator.DiagnosisMessage;

public class CSV
{
	private List<Row> rows;
	private List<DiagnosisMessage> parserDiagnoses = Collections.emptyList();

	public CSV(List<Row> rows)
	{
		this.rows = rows;
	}

	public List<DiagnosisMessage> getDiagnoses()
	{
		return parserDiagnoses;
	}

	public int countRows()
	{
		return rows.size();
	}

	public Row getRow(int i)
	{
		return rows.get(i);
	}

	public String[][] toStringTable()
	{
		String[][] rowsArray = new String[rows.size()][];
		for (int i = 0; i < rowsArray.length; i++)
		{
			rowsArray[i] = rows.get(i).toStringArray();
		}
		return rowsArray;
	}

	public List<Map<String, String>> toMapList()
	{
		List<Map<String, String>> mapList = new ArrayList<>();
		if (countRows() > 0)
		{
			Row header = getRow(0);
			for (int i = 1; i < countRows(); i++)
			{
				Row row = getRow(i);
				Map<String, String> map = new HashMap<>();
				for (int j = 0; j < Math.min(header.countValues(), row.countValues()); j++)
				{
					map.put(header.getValueText(j), row.getValueText(j));
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	public static CSV parse(CharSequence sourceText)
	{
		CSVParser parser = new CSVParser(new CSVLexer(sourceText));
		CSV csv = parser.parse();
		csv.parserDiagnoses = parser.getDiagnoses();
		return csv;
	}

	public static CSV read(String fileName) throws IOException
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
		{
			StringBuilder buf = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
			{
				buf.append(line).append('\n');
			}
			return parse(buf);
		}
	}
}
