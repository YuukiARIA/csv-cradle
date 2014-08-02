package csvcradle.model.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import csvcradle.model.CSV;
import csvcradle.model.Row;
import csvcradle.model.Value;

public class CSVParser
{
	private CSVLexer lexer;
	private Token token;

	public CSVParser(CSVLexer lexer)
	{
		this.lexer = lexer;
	}

	//
	// <<CSV>> ::= { <<Row>> } <End>
	//
	public CSV parse()
	{
		List<Row> rows = new LinkedList<Row>();

		next();
		while (isText() || isComma())
		{
			rows.add(parseRow());
		}
		if (!isEnd())
		{
			throw new RuntimeException("unexpected token: " + token.text);
		}

		return new CSV(rows);
	}

	//
	// <<Row>> ::= [ <Text> ] { <Comma> [ <Text> ] } { <NewLine> }
	//
	private Row parseRow()
	{
		List<Value> values = new ArrayList<Value>();

		String value = "";
		Location location = token.location;
		if (isText())
		{
			value = token.text;
			next();
		}
		values.add(new Value(value, location));
		while (isComma())
		{
			next();
			value = "";
			location = token.location;
			if (isText())
			{
				value = token.text;
				next();
			}
			values.add(new Value(value, location));
		}
		while (isNewLine())
		{
			next();
		}
		return new Row(values);
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
