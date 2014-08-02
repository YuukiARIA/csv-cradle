package csvcradle.model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CSVTest
{
	@Test
	public void toStringTable()
	{
		CSV csv = CSV.parse("a,b,c,d\n1,2,3,4");
		String[][] table = csv.toStringTable();
		String[][] expected = { { "a", "b", "c", "d" }, { "1", "2", "3", "4" } };
		assertEquals(expected.length, table.length);
		for (int i = 0; i < expected.length; i++)
		{
			assertArrayEquals(expected[i], table[i]);
		}
	}
}
