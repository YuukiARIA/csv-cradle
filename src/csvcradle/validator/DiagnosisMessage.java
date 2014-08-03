package csvcradle.validator;

import java.util.Comparator;

import csvcradle.model.parser.Location;

public class DiagnosisMessage
{
	private static Comparator<DiagnosisMessage> comparator;

	private Severity severity;
	private Location location;
	private String message;

	public DiagnosisMessage(Severity severity, Location location, String message)
	{
		this.severity = severity;
		this.location = location;
		this.message = message;
	}

	public Severity getSeverity()
	{
		return severity;
	}

	public Location getLocation()
	{
		return location;
	}

	public String getMessage()
	{
		return message;
	}

	public String toString()
	{
		return "[" + severity + "] " + location + " - " + message;
	}

	public static DiagnosisMessage newInfo(Location location, String message)
	{
		return new DiagnosisMessage(Severity.INFO, location, message);
	}

	public static DiagnosisMessage newWarning(Location location, String message)
	{
		return new DiagnosisMessage(Severity.WARNING, location, message);
	}

	public static DiagnosisMessage newError(Location location, String message)
	{
		return new DiagnosisMessage(Severity.ERROR, location, message);
	}

	public static Comparator<DiagnosisMessage> getComparator()
	{
		if (comparator == null)
		{
			comparator = new Comparator<DiagnosisMessage>()
			{
				@Override
				public int compare(DiagnosisMessage d1, DiagnosisMessage d2)
				{
					return d1.location.compareTo(d2.location);
				}
			};
		}
		return comparator;
	}
}
