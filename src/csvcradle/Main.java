package csvcradle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import csvcradle.model.CSV;
import csvcradle.model.parser.CSVParser;
import csvcradle.model.parser.Location;
import csvcradle.validator.CSVValidator;
import csvcradle.validator.DiagnosisMessage;

public class Main
{
	private static final int UTF8_BOM = 0xFEFF;
	private static final Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args)
	{
		for (String arg : args)
		{
			validateCSV(arg);
		}
	}

	private static void validateCSV(String fileName)
	{
		System.out.println("ファイル: " + fileName);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset)))
		{
			skipUtf8Bom(reader);

			StringBuilder text = new StringBuilder();
			char[] buf = new char[4096];
			int n;
			while ((n = reader.read(buf)) != -1)
			{
				text.append(buf, 0, n);
			}

			CSVParser parser = CSVParser.create(text);
			CSV csv = parser.parse();
			List<String> lines = parser.getLines();
			List<DiagnosisMessage> diagnoses = new LinkedList<>();
			diagnoses.addAll(csv.getDiagnoses());
			diagnoses.addAll(CSVValidator.validate(csv));
			Collections.sort(diagnoses, DiagnosisMessage.getComparator());
			for (DiagnosisMessage diagnosis : diagnoses)
			{
				System.out.println(diagnosis);
				Location l = diagnosis.getLocation();
				System.out.println(lines.get(l.line - 1));
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("--------");
	}

	private static boolean skipUtf8Bom(BufferedReader reader) throws IOException
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
