package csvcradle.model.parser;

public class Token
{
	public final Tag tag;
	public final String text;
	public final Location location;
	public final Location endLocation;

	public Token(Tag tag, String text, Location location, Location endLocation)
	{
		this.tag = tag;
		this.text = text;
		this.location = location;
		this.endLocation = endLocation;
	}
}
