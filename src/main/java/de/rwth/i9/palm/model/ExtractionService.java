package de.rwth.i9.palm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "extraction_service" )
public class ExtractionService extends PersistableResource
{
	@Enumerated( EnumType.STRING )
	@Column( length = 20 )
	private ExtractionServiceType extractionServiceType;

	@Column
	private String purpose;

	@Column
	private Date lastQueryDate;

	@Column
	@Lob
	private String description;

	@Column( columnDefinition = "bit default 1" )
	private boolean active = true;

	@Column( columnDefinition = "int default 0" )
	private int countQueryThisDay;

	@Column( columnDefinition = "int default 1000" )
	private int maxQueryPerDay;

	@Column( columnDefinition = "int default 5000" )
	private int maxTextLength;

	@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "extractionService", orphanRemoval = true )
	private List<ExtractionServiceProperty> extractionServiceProperties;

	// getter & setter

	public String getPurpose()
	{
		return purpose;
	}

	public void setPurpose( String purpose )
	{
		this.purpose = purpose;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public ExtractionServiceType getExtractionServiceType()
	{
		return extractionServiceType;
	}

	public void setExtractionServiceType( ExtractionServiceType extractionServiceType )
	{
		this.extractionServiceType = extractionServiceType;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive( boolean active )
	{
		this.active = active;
	}

	public int getCountQueryThisDay()
	{
		return countQueryThisDay;
	}

	public void setCountQueryThisDay( int countQueryThisDay )
	{
		this.countQueryThisDay = countQueryThisDay;
	}

	public int getMaxQueryPerDay()
	{
		return maxQueryPerDay;
	}

	public void setMaxQueryPerDay( int maxQueryPerDay )
	{
		this.maxQueryPerDay = maxQueryPerDay;
	}

	public int getMaxTextLength()
	{
		return maxTextLength;
	}

	public void setMaxTextLength( int maxTextLength )
	{
		this.maxTextLength = maxTextLength;
	}

	public Date getLastQueryDate()
	{
		return lastQueryDate;
	}

	public void setLastQueryDate( Date lastQueryDate )
	{
		this.lastQueryDate = lastQueryDate;
	}

	public List<ExtractionServiceProperty> getExtractionServiceProperties()
	{
		return extractionServiceProperties;
	}

	public void setExtractionServiceProperties( List<ExtractionServiceProperty> extractionServiceProperties )
	{
		this.extractionServiceProperties = extractionServiceProperties;
	}
	
}
