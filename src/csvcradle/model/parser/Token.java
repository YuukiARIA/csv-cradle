package csvcradle.model.parser;

public class Token
{
	public final Tag tag;
	public final String text;
	public final Location location;

	public Token(Tag tag, String text, Location location)
	{
		this.tag = tag;
		this.text = text;
		this.location = location;
	}
}
