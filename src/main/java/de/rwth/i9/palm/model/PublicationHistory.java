package de.rwth.i9.palm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "publication_history" )
public class PublicationHistory extends PersistableResource
{
	@Column
	private String title;

	/* comma separated author list */
	@Column
	private String authorString;

	/* comma separated author list */
	@Column
	private String authorAffiliation;

	@Column
	@Lob
	private String abstractText;

	@Column
	@Lob
	private String fulltext;

	@Column
	@Lob
	private String citation;

	@Column
	private String venue;

	@Column( length = 4 )
	private String year;

	@Column( length = 10 )
	private String month;

	@Column( length = 10 )
	private int citationNumber;

	@Column
	private Date modifiedAt;

	// relations
	@ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn( name = "user_id" )
	private User user;

	@ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JoinColumn( name = "publication_id" )
	private Publication publication;

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getAbstractText()
	{
		return abstractText;
	}

	public void setAbstractText( String abstractText )
	{
		this.abstractText = abstractText;
	}

	public String getFulltext()
	{
		return fulltext;
	}

	public void setFulltext( String fulltext )
	{
		this.fulltext = fulltext;
	}

	public String getAuthorString()
	{
		return authorString;
	}

	public void setAuthorString( String authorString )
	{
		this.authorString = authorString;
	}

	public Date getModifiedAt()
	{
		return modifiedAt;
	}

	public void setModifiedAt( Date modifiedAt )
	{
		this.modifiedAt = modifiedAt;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser( User user )
	{
		this.user = user;
	}

	public String getAuthorAffiliation()
	{
		return authorAffiliation;
	}

	public void setAuthorAffiliation( String authorAffiliation )
	{
		this.authorAffiliation = authorAffiliation;
	}

	public String getCitation()
	{
		return citation;
	}

	public void setCitation( String citation )
	{
		this.citation = citation;
	}

	public String getVenue()
	{
		return venue;
	}

	public void setVenue( String venue )
	{
		this.venue = venue;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear( String year )
	{
		this.year = year;
	}

	public String getMonth()
	{
		return month;
	}

	public void setMonth( String month )
	{
		this.month = month;
	}

	public int getCitationNumber()
	{
		return citationNumber;
	}

	public void setCitationNumber( int citationNumber )
	{
		this.citationNumber = citationNumber;
	}

	public Publication getPublication()
	{
		return publication;
	}

	public void setPublication( Publication publication )
	{
		this.publication = publication;
	}

}

