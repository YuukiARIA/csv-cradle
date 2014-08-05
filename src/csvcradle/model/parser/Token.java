package csvcradle.model.parser;

public class Token
{
	public final Tag tag;
	public final String text;
	public final Location startLocation;
	public final Location endLocation;

	public Token(Tag tag, String text, Location startLocation, Location endLocation)
	{
		this.tag = tag;
		this.text = text;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}
}
