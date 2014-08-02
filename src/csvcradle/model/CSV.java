package csvcradle.model;

import java.util.List;

public class CSV
{
	private List<Row> rows;

	public CSV(List<Row> rows)
	{
		this.rows = rows;
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
}
