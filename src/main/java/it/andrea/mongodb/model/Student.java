package it.andrea.mongodb.model;

import java.util.Collection;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by andrea on 12/8/15.
 */
@Document
public class Student
{

	@Id
	private String uid;
	private String name;
	private Collection<Address> addresses;
	private Map<String, Education> education;
	private Education phd;

	public Student(final String uid, final String name, final Collection<Address> addresses, final Map<String, Education> education, final Education phd)
	{
		this.uid = uid;
		this.name = name;
		this.addresses = addresses;
		this.education = education;
		this.phd = phd;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public Collection<Address> getAddresses()
	{
		return addresses;
	}

	public void setAddresses(final Collection<Address> addresses)
	{
		this.addresses = addresses;
	}

	public Map<String, Education> getEducation()
	{
		return education;
	}

	public void setEducation(final Map<String, Education> education)
	{
		this.education = education;
	}

	public Education getPhd()
	{
		return phd;
	}

	public void setPhd(final Education phd)
	{
		this.phd = phd;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(final String uid)
	{
		this.uid = uid;
	}
}
