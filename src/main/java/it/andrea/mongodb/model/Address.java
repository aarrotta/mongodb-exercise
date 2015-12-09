package it.andrea.mongodb.model;

import java.lang.String;import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by andrea on 12/8/15.
 */
@Document
public class Address
{

	private String street;
	private String number;
	private String country;

	public Address(final String uid, final String street, final String number, final String country)
	{
		this.street = street;
		this.number = number;
		this.country = country;
	}

	public Address()
	{
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(final String street)
	{
		this.street = street;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(final String number)
	{
		this.number = number;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(final String country)
	{
		this.country = country;
	}

}
