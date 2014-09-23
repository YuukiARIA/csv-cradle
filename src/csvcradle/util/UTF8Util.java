package csvcradle.util;

import java.io.BufferedReader;
import java.io.IOException;

public final class UTF8Util
{
	private static final int UTF8_BOM = 0xFEFF;

	private UTF8Util() { }

	public static boolean skipUTF8Bom(BufferedReader reader) throws IOException
	{
		reader.mark(3);
		if (reader.read() != UTF8_BOM)
		{
			reader.reset();
			return false;
		}
		return true;
	}
}
