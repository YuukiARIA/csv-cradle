package csvcradle.validator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import csvcradle.model.CSV;
import csvcradle.model.Row;
import csvcradle.model.Value;
import csvcradle.model.parser.Location;

public class CSVValidator
{
	private enum Type
	{
		UNSPECIFIED, TEXT, NUMERIC
	}

	private CSVValidator() { }

	public static List<DiagnosisMessage> validate(CSV csv)
	{
		List<DiagnosisMessage> diagnoses = new LinkedList<>();
		if (validateColumnCounts(csv, diagnoses))
		{
			validateSimpleValueTypes(csv, diagnoses);
		}
		return diagnoses;
	}

	private static boolean validateColumnCounts(CSV csv, List<DiagnosisMessage> diagnoses)
	{
		if (csv.countRows() == 0) return true;

		boolean valid = true;
		Row header = csv.getRow(0);
		for (int i = 1; i < csv.countRows(); i++)
		{
			Row row = csv.getRow(i);
			Location location = row.getValue(0).getLocation();
			if (row.countValues() < header.countValues())
			{
				diagnoses.add(DiagnosisMessage.newWarning(location, "カラム数が少なすぎます。"));
				valid = false;
			}
			else if (row.countValues() > header.countValues())
			{
				diagnoses.add(DiagnosisMessage.newWarning(location, "カラム数が多すぎます。"));
				valid = false;
			}
		}
		return valid;
	}

	private static void validateSimpleValueTypes(CSV csv, List<DiagnosisMessage> diagnoses)
	{
		if (csv.countRows() == 0) return;

		Row header = csv.getRow(0);
		Type[][] types = getTypes(csv);
		for (int c = 0; c < header.countValues(); c++)
		{
			Type mode = getModeType(types[c]);

			if (mode != Type.NUMERIC) continue;

			for (int r = 1; r < csv.countRows(); r++)
			{
				if (types[c][r] != mode)
				{
					Value value = csv.getRow(r).getValue(c);
					String text = value.getText();
					if (!text.isEmpty())
					{
						diagnoses.add(DiagnosisMessage.newWarning(value.getLocation(), "数値であるべき？"));
					}
				}
			}
		}
	}

	private static Type[][] getTypes(CSV csv)
	{
		Type[][] types = new Type[csv.getRow(0).countValues()][csv.countRows()];
		for (int i = 1; i < csv.countRows(); i++)
		{
			Row row = csv.getRow(i);
			for (int j = 0; j < row.countValues(); j++)
			{
				Value value = row.getValue(j);
				String text = value.getText();
				if (!text.isEmpty())
				{
					if (isNumeric(text))
					{
						types[j][i] = Type.NUMERIC;
					}
					else
					{
						types[j][i] = Type.TEXT;
					}
				}
				else
				{
					types[j][i] = Type.UNSPECIFIED;
				}
			}
		}
		return types;
	}

	private static Type getModeType(Type[] typesOfColumn)
	{
		Map<Type, Integer> counts = new HashMap<>();
		for (Type type : typesOfColumn)
		{
			Integer count = counts.get(type);
			if (count == null)
			{
				count = 0;
			}
			counts.put(type, count + 1);
		}
		int max = 0;
		Type mode = Type.UNSPECIFIED;
		for (Map.Entry<Type, Integer> ent : counts.entrySet())
		{
			int c = ent.getValue();
			if (max < c)
			{
				max = c;
				mode = ent.getKey();
			}
			else if (max == c)
			{
				mode = Type.UNSPECIFIED;
			}
		}
		return mode;
	}

	private static boolean isNumeric(String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (!isNumericChar(c))
			{
				return false;
			}
		}
		return true;
	}

	private static boolean isNumericChar(char c)
	{
		return '0' <= c && c <= '9' || c == '.' || c == '+' || c == '-';
	}
}