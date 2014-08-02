package csvcradle.model.parser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CSVLexerTest
{
	@Test
	public void lexComma()
	{
		CSVLexer lexer = new CSVLexer(",");
		Token t = lexer.lex();
		assertEquals(Tag.COMMA, t.tag);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexCR()
	{
		CSVLexer lexer = new CSVLexer("\r");
		Token t = lexer.lex();
		assertEquals(Tag.NEWLINE, t.tag);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexLF()
	{
		CSVLexer lexer = new CSVLexer("\n");
		Token t = lexer.lex();
		assertEquals(Tag.NEWLINE, t.tag);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexCRLF()
	{
		CSVLexer lexer = new CSVLexer("\r\n");
		Token t = lexer.lex();
		assertEquals(Tag.NEWLINE, t.tag);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexText()
	{
		CSVLexer lexer = new CSVLexer("text");
		Token t = lexer.lex();
		assertEquals(Tag.TEXT, t.tag);
		assertEquals("text", t.text);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexQuotedText()
	{
		CSVLexer lexer = new CSVLexer("\"quoted text\"");
		Token t = lexer.lex();
		assertEquals(Tag.TEXT, t.tag);
		assertEquals("quoted text", t.text);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexEmptyQuotedText()
	{
		CSVLexer lexer = new CSVLexer("\"\"");
		Token t = lexer.lex();
		assertEquals(Tag.TEXT, t.tag);
		assertEquals("", t.text);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexQuotedTextContainingDoubleQuotation()
	{
		CSVLexer lexer = new CSVLexer("\"this is \"\"quoted text\"\"\"");
		Token t = lexer.lex();
		assertEquals(Tag.TEXT, t.tag);
		assertEquals("this is \"quoted text\"", t.text);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexQuotedTextContainingNewline()
	{
		CSVLexer lexer = new CSVLexer("\"this is the first line.\r\n\r\nhere is the third line.\"");
		Token t = lexer.lex();
		assertEquals(Tag.TEXT, t.tag);
		assertEquals("this is the first line.\r\n\r\nhere is the third line.", t.text);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexQuotedTextContainingComma()
	{
		CSVLexer lexer = new CSVLexer("\"cat, dog, monkey\"");
		Token t = lexer.lex();
		assertEquals(Tag.TEXT, t.tag);
		assertEquals("cat, dog, monkey", t.text);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexEmptyInput()
	{
		CSVLexer lexer = new CSVLexer("");
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void lexAfterEndToken()
	{
		CSVLexer lexer = new CSVLexer("");
		assertEquals(Tag.END, lexer.lex().tag);
		assertEquals(Tag.END, lexer.lex().tag);
	}

	@Test
	public void reset()
	{
		CSVLexer lexer = new CSVLexer("a,b,c\nd,e,f\n");
		assertEquals(Tag.TEXT, lexer.lex().tag);
		assertEquals(Tag.COMMA, lexer.lex().tag);

		lexer.reset();

		Token token = lexer.lex();
		assertEquals(Tag.TEXT, token.tag);
		assertEquals("a", token.text);
	}
}
