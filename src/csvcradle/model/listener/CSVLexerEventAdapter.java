package csvcradle.model.listener;

import csvcradle.model.parser.LineDelimiter;
import csvcradle.model.parser.Location;

public abstract class CSVLexerEventAdapter implements CSVLexerEventListener
{
	@Override
	public void onLineDelimiterDetermined(LineDelimiter lineDelimiter)
	{
	}

	@Override
	public void onLineDelimiterChanged(Location location, LineDelimiter oldLineDelimiter, LineDelimiter newLineDelimiter)
	{
	}

	@Override
	public void onUnclosedDoubleQuotation(Location location)
	{
	}

	@Override
	public void onUnescapedDoubleQuotation(Location location)
	{
	}
}
