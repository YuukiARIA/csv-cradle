package csvcradle.model.parser;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import csvcradle.model.CSV;
import csvcradle.model.Row;
import csvcradle.model.Value;
import csvcradle.validator.DiagnosisMessage;

public class CSVParser
{
	private CSVLexer lexer;
	private Token token;
	private List<DiagnosisMessage> diagnoses = new LinkedList<>();

	public CSVParser(CSVLexer lexer)
	{
		this.lexer = lexer;
	}

	public List<DiagnosisMessage> getDiagnoses()
	{
		List<DiagnosisMessage> allDiagnoses = new LinkedList<>();
		allDiagnoses.addAll(lexer.getDiagnoses());
		allDiagnoses.addAll(diagnoses);
		return allDiagnoses;
	}

	public List<String> getLines()
	{
		return lexer.getLines();
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
			diagnoses.add(DiagnosisMessage.newError(token.location, "予期しない入力: " + token.text));
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
		if (isNewLine())
		{
			next();
		}
		while (isNewLine())
		{
			diagnoses.add(DiagnosisMessage.newWarning(token.location, "空行が無視されました。"));
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

	public static CSVParser create(CharSequence sourceText)
	{
		return new CSVParser(new CSVLexer(sourceText));
	}
}
