package csvcradle.model.listener;

import csvcradle.model.parser.Location;
import csvcradle.model.parser.Token;

public interface CSVParserEventListener
{
	public void onUnexpectedToken(Token token);

	public void onEmptyLine(Location location);
}
