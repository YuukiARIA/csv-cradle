package csvcradle.model;

import java.util.List;

public class Row
{
	private Value[] values;

	public Row(List<Value> values)
	{
		this.values = values.toArray(new Value[values.size()]);
	}

	public int countValues()
	{
		return values.length;
	}

	public Value getValue(int i)
	{
		return values[i];
	}

	public String getValueText(int i)
	{
		return getValue(i).getText();
	}

	public String[] toStringArray()
	{
		String[] texts = new String[values.length];
		for (int i = 0; i < values.length; i++)
		{
			texts[i] = values[i].getText();
		}
		return texts;
	}
}
