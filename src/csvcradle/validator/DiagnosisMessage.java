package csvcradle.validator;

import csvcradle.model.parser.Location;

public class DiagnosisMessage
{
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
}
