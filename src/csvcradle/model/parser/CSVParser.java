package csvcradle.model.parser;


public class CSVParser
{
	private CSVLexer lexer;
	private Token token;

	public CSVParser(CSVLexer lexer)
	{
		this.lexer = lexer;
	}

	private boolean isEnd()
	{
		return token.tag == Tag.END;
	}

	private boolean isComma()
	{
		return token.tag == Tag.COMMA;
	}

	private boolean isNewLine()
	{
		return token.tag == Tag.NEWLINE;
	}

	private boolean isText()
	{
		return token.tag == Tag.TEXT;
	}

	private void next()
	{
		token = lexer.lex();
	}
}
