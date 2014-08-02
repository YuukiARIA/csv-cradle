package csvcradle.model;

import org.junit.Test;

import csvcradle.model.parser.CSVLexer;
import csvcradle.model.parser.CSVParser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CSVTest
{
	@Test
	public void toStringTable()
	{
		CSV csv = new CSVParser(new CSVLexer("a,b,c,d\n1,2,3,4")).parse();
		String[][] table = csv.toStringTable();
		String[][] expected = { { "a", "b", "c", "d" }, { "1", "2", "3", "4" } };
		assertEquals(expected.length, table.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], table[i]);
		}
	}
}
