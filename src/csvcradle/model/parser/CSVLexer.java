package csvcradle.model.parser;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import csvcradle.model.listener.CSVLexerEventListener;

public class CSVLexer
{
	private CharSequence cs;
	private StringBuilder lineBuf = new StringBuilder();
	private List<String> lines = new LinkedList<>();
	private int p;
	private int line;
	private int column;
	private Location startLocation;
	private LineDelimiter lineDelimiter;
	private StringBuilder buf = new StringBuilder();
	private List<CSVLexerEventListener> listeners = new LinkedList<>();

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
		lineBuf.setLength(0);
		lines.clear();
	}

	public List<String> getLines()
	{
		return Collections.unmodifiableList(lines);
	}

	public Token lex()
	{
		startLocation = Location.of(line, column);

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
			if (peek() == '"')
			{
				dispatchUnescapedDoubleQuotationEvent(currentLocation());
			}
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
		boolean closed = false;
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
					closed = true;
					break;
				}
			}
			else
			{
				buf.append(peek());
				succ();
			}
		}
		if (!closed)
		{
			dispatchUnclosedDoubleQuotationEvent(startLocation);
		}
		return buf.toString();
	}

	private void setLineDelimiter(LineDelimiter lineDelimiter)
	{
		if (this.lineDelimiter != lineDelimiter)
		{
			if (this.lineDelimiter == null)
			{
				dispatchLindelimiterDeterminedEvent(lineDelimiter);
			}
			else
			{
				dispatchLineDelimiterChangedEvent(currentLocation(), this.lineDelimiter, lineDelimiter);
			}
			this.lineDelimiter = lineDelimiter;
		}
	}

	private Location currentLocation()
	{
		return Location.of(line, column);
	}

	private Token createToken(Tag tag, String text)
	{
		return new Token(tag, text, startLocation, currentLocation());
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
				lines.add(lineBuf.toString());
				lineBuf.setLength(0);
			}
			else
			{
				++column;
				lineBuf.append(peek());
			}

			++p;

			if (end())
			{
				lines.add(lineBuf.toString());
				lineBuf.setLength(0);
			}
		}
	}

	private boolean end()
	{
		return p >= cs.length();
	}

	public void addListener(CSVLexerEventListener listener)
	{
		listeners.add(listener);
	}

	public void removeListener(CSVLexerEventListener listener)
	{
		listeners.remove(listener);
	}

	private void dispatchLindelimiterDeterminedEvent(LineDelimiter lineDelimiter)
	{
		for (CSVLexerEventListener l : listeners)
		{
			l.onLineDelimiterDetermined(lineDelimiter);
		}
	}

	private void dispatchLineDelimiterChangedEvent(Location location, LineDelimiter oldLineDelimiter, LineDelimiter newLineDelimiter)
	{
		for (CSVLexerEventListener l : listeners)
		{
			l.onLineDelimiterChanged(location, oldLineDelimiter, newLineDelimiter);
		}
	}

	private void dispatchUnclosedDoubleQuotationEvent(Location location)
	{
		for (CSVLexerEventListener l : listeners)
		{
			l.onUnclosedDoubleQuotation(location);
		}
	}

	private void dispatchUnescapedDoubleQuotationEvent(Location location)
	{
		for (CSVLexerEventListener l : listeners)
		{
			l.onUnescapedDoubleQuotation(location);
		}
	}
}
