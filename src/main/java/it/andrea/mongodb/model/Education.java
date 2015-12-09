package it.andrea.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by andrea on 12/8/15.
 */
@Document
public class Education
{

	private String university;
	private String year;

	public Education(final String uid, final String university, final String year)
	{
		this.university = university;
		this.year = year;
	}

	public Education()
	{
	}

	public String getUniversity()
	{
		return university;
	}

	public void setUniversity(final String university)
	{
		this.university = university;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(final String year)
	{
		this.year = year;
	}
}
