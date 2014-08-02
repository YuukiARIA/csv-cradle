package csvcradle.model.parser;

import org.junit.Test;

import csvcradle.model.CSV;
import csvcradle.model.Row;

import static org.junit.Assert.assertEquals;

public class CSVParserTest
{
	@Test
	public void parseCSVWithLF()
	{
		CSVParser parser = new CSVParser(new CSVLexer("a,b,c,d\n1,2,3,4"));
		CSV csv = parser.parse();

		String[][] expected = { { "a", "b", "c", "d" }, { "1", "2", "3", "4" } };

		assertEquals(2, csv.countRows());
		for (int j = 0; j < csv.countRows(); j++)
		{
			Row row = csv.getRow(j);
			assertEquals(4, row.countValues());
			for (int i = 0; i < row.countValues(); i++)
			{
				String text = row.getValueText(i);
				assertEquals(expected[j][i], text);
			}
		}
	}

	@Test
	public void parseCSVWithCRLF()
	{
		CSVParser parser = new CSVParser(new CSVLexer("a,b,c,d\r\n1,2,3,4"));
		CSV csv = parser.parse();

		String[][] expected = { { "a", "b", "c", "d" }, { "1", "2", "3", "4" } };

		assertEquals(2, csv.countRows());
		for (int j = 0; j < csv.countRows(); j++)
		{
			Row row = csv.getRow(j);
			assertEquals(4, row.countValues());
			for (int i = 0; i < row.countValues(); i++)
			{
				String text = row.getValueText(i);
				assertEquals(expected[j][i], text);
			}
		}
	}

	@Test
	public void parseCSVWithTrailingLF()
	{
		CSVParser parser = new CSVParser(new CSVLexer("a,b,c,d\r\n1,2,3,4\n"));
		CSV csv = parser.parse();

		String[][] expected = { { "a", "b", "c", "d" }, { "1", "2", "3", "4" } };

		assertEquals(2, csv.countRows());
		for (int j = 0; j < csv.countRows(); j++)
		{
			Row row = csv.getRow(j);
			assertEquals(4, row.countValues());
			for (int i = 0; i < row.countValues(); i++)
			{
				String text = row.getValueText(i);
				assertEquals(expected[j][i], text);
			}
		}
	}

	@Test
	public void parseCSVContainingEmptyLine()
	{
		CSVParser parser = new CSVParser(new CSVLexer("a,b,c,d\n\n\n\n1,2,3,4"));
		CSV csv = parser.parse();

		String[][] expected = { { "a", "b", "c", "d" }, { "1", "2", "3", "4" } };

		assertEquals(2, csv.countRows());
		for (int j = 0; j < csv.countRows(); j++)
		{
			Row row = csv.getRow(j);
			assertEquals(4, row.countValues());
			for (int i = 0; i < row.countValues(); i++)
			{
				String text = row.getValueText(i);
				assertEquals(expected[j][i], text);
			}
		}
	}

	@Test
	public void parseCSVContainingEmptyCell()
	{
		CSVParser parser = new CSVParser(new CSVLexer("a,b,,d\n1,2,3,"));
		CSV csv = parser.parse();

		String[][] expected = { { "a", "b", "", "d" }, { "1", "2", "3", "" } };

		assertEquals(2, csv.countRows());
		for (int j = 0; j < csv.countRows(); j++)
		{
			Row row = csv.getRow(j);
			assertEquals(4, row.countValues());
			for (int i = 0; i < row.countValues(); i++)
			{
				String text = row.getValueText(i);
				assertEquals(expected[j][i], text);
			}
		}
	}
}
