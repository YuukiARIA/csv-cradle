package csvcradle.model.parser;

public final class Location implements Comparable<Location>
{
	public final int line;
	public final int column;

	public Location(int line, int column)
	{
		this.line = line;
		this.column = column;
	}

	public int hashCode()
	{
		return 17 * line + column;
	}

	public boolean equals(Object o)
	{
		return o == this || o instanceof Location
			&& line == ((Location)o).line && column == ((Location)o).column;
	}

	public int compareTo(Location l)
	{
		if (line == l.line)
		{
			if (column == l.column)
			{
				return 0;
			}
			return column < l.column ? -1 : 1;
		}
		return line < l.line ? -1 : 1;
	}

	public String toString()
	{
		return line + ":" + column;
	}

	public static Location of(int line, int column)
	{
		return new Location(line, column);
	}
}
