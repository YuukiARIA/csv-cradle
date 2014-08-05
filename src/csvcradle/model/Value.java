package csvcradle.model;

import csvcradle.model.parser.Location;

public class Value
{
	private String text;
	private Location startLocation;
	private Location endLocation;

	public Value(String text, Location startLocation, Location endLocation)
	{
		this.text = text;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	public String getText()
	{
		return text;
	}

	public Location getStartLocation()
	{
		return startLocation;
	}

	public Location getEndLocation()
	{
		return endLocation;
	}

	public String toString()
	{
		return text;
	}
}
