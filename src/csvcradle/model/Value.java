package csvcradle.model;

import csvcradle.model.parser.Location;

public class Value
{
	private String text;
	private Location location;

	public Value(String text, Location location)
	{
		this.text = text;
		this.location = location;
	}

	public String getText()
	{
		return text;
	}

	public Location getLocation()
	{
		return location;
	}

	public String toString()
	{
		return text;
	}
}
