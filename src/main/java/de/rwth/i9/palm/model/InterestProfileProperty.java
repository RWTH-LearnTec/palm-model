package de.rwth.i9.palm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "interest_profile_property" )
public class InterestProfileProperty extends PersistableResource
{
	@Column( length = 50 )
	private String mainIdentifier;

	@Column( length = 50 )
	private String secondaryIdentifier;

	@Column
	@Lob
	private String value;

	@Column
	private java.sql.Timestamp lastModified;

	@Column( columnDefinition = "bit default 1" )
	private boolean valid = true;

	// relation
	@ManyToOne
	@JoinColumn( name = "interest_profile_id" )
	private InterestProfile interestProfile;

	public String getMainIdentifier()
	{
		return mainIdentifier;
	}

	public void setMainIdentifier( String mainIdentifier )
	{
		this.mainIdentifier = mainIdentifier;
	}

	public String getSecondaryIdentifier()
	{
		return secondaryIdentifier;
	}

	public void setSecondaryIdentifier( String secondaryIdentifier )
	{
		this.secondaryIdentifier = secondaryIdentifier;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue( String value )
	{
		this.value = value;
	}

	public java.sql.Timestamp getLastModified()
	{
		return lastModified;
	}

	public void setLastModified( java.sql.Timestamp lastModified )
	{
		this.lastModified = lastModified;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid( boolean valid )
	{
		this.valid = valid;
	}

	public InterestProfile getInterestProfile()
	{
		return interestProfile;
	}

	public void setInterestProfile( InterestProfile interestProfile )
	{
		this.interestProfile = interestProfile;
	}

}