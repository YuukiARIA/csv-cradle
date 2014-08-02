package csvcradle.model.parser;


public class CSVLexer
{
	private CharSequence cs;
	private int p;
	private int line;
	private int column;
	private Location curLocation;
	private LineDelimiter lineDelimiter;

	public CSVLexer(CharSequence cs)
	{
		this.cs = cs;
		reset();
	}

	public Token lex()
	{
		curLocation = Location.of(line, column);

		if (end())
		{
			return createToken(Tag.END, "$END");
		}

		throw new RuntimeException("!!!");
	}

	public void reset()
	{
		p = 0;
		lineDelimiter = null;
	}

	private Token createToken(Tag tag, String text)
	{
		return new Token(tag, text, curLocation);
	}

	private char peek()
	{
		return !end() ? cs.charAt(p) : 0;
	}

	private void succ()
	{
		if (!end())
		{
			++p;
		}
	}

	private boolean end()
	{
		return p >= cs.length();
	}
}
