package csvcradle.model.listener;

import csvcradle.model.parser.LineDelimiter;
import csvcradle.model.parser.Location;

public interface CSVLexerEventListener
{
	public void onLineDelimiterDetermined(LineDelimiter lineDelimiter);

	public void onLineDelimiterChanged(LineDelimiter oldLineDelimiter, LineDelimiter newLineDelimiter);

	public void onUnclosedDoubleQuotation(Location location);

	public void onUnescapedDoubleQuotation(Location location);
}
