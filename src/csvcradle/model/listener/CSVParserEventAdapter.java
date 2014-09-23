package csvcradle.model.listener;

import csvcradle.model.parser.Location;
import csvcradle.model.parser.Token;

public abstract class CSVParserEventAdapter implements CSVParserEventListener
{
	@Override
	public void onUnexpectedToken(Token token)
	{
	}

	@Override
	public void onEmptyLine(Location location)
	{
	}
}
