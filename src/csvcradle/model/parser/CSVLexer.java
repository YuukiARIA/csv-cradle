package csvcradle.model.parser;

public class CSVLexer
{
	private CharSequence cs;
	private int p;
	private int line;
	private int column;
	private Location curLocation;
	private LineDelimiter lineDelimiter;
	private StringBuilder buf = new StringBuilder();

	public CSVLexer(CharSequence cs)
	{
		this.cs = cs;
		reset();
	}

	public void reset()
	{
		p = 0;
		line = 1;
		column = 1;
		lineDelimiter = null;
	}

	public Token lex()
	{
		curLocation = Location.of(line, column);

		if (end())
		{
			return createToken(Tag.END, "$END");
		}

		if (peek() == '"')
		{
			String text = lexQuoted();
			return createToken(Tag.TEXT, text);
		}
		else if (peek() == ',')
		{
			succ();
			return createToken(Tag.COMMA, ",");
		}
		else if (peek() == '\r')
		{
			succ();
			if (peek() == '\n')
			{
				succ();
				setLineDelimiter(LineDelimiter.CRLF);
				return createToken(Tag.NEWLINE, "CRLF");
			}
			setLineDelimiter(LineDelimiter.CR);
			return createToken(Tag.NEWLINE, "CR");
		}
		else if (peek() == '\n')
		{
			succ();
			setLineDelimiter(LineDelimiter.LF);
			return createToken(Tag.NEWLINE, "LF");
		}
		else
		{
			String text = lexText();
			return createToken(Tag.TEXT, text);
		}
	}

	private String lexText()
	{
		buf.setLength(0);
		do
		{
			buf.append(peek());
			succ();
		}
		while (!end() && peek() != ',' && peek() != '\r' && peek() != '\n');
		return buf.toString();
	}

	private String lexQuoted()
	{
		if (peek() != '"') return "";
		succ();

		buf.setLength(0);
		while (!end())
		{
			if (peek() == '"')
			{
				succ();
				if (peek() == '"')
				{
					succ();
					buf.append('"');
				}
				else
				{
					break;
				}
			}
			else
			{
				buf.append(peek());
				succ();
			}
		}
		return buf.toString();
	}

	private void setLineDelimiter(LineDelimiter lineDelimiter)
	{
		this.lineDelimiter = lineDelimiter;
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
			char newlineChar = lineDelimiter == LineDelimiter.CR ? '\r' : '\n';
			if (peek() == newlineChar)
			{
				++line;
				column = 1;
			}
			else
			{
				++column;
			}
			++p;
		}
	}

	private boolean end()
	{
		return p >= cs.length();
	}
}
