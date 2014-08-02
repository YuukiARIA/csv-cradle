package csvcradle.model.parser;

public final class Location
{
	public final int line;
	public final int column;

	public Location(int line, int column)
	{
		this.line = line;
		this.column = column;
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
