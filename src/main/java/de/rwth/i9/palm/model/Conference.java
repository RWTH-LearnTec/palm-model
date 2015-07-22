package de.rwth.i9.palm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "conference" )
@Indexed
public class Conference extends PersistableResource
{
	@Column
	private Date date;

	@Column
	@Field( index = Index.YES, analyze = Analyze.NO, store = Store.YES )
	private String thema;

	@Column( length = 4 )
	@Field( index = Index.YES, analyze = Analyze.NO, store = Store.YES )
	private String year;

	public String getYear()
	{
		return year;
	}

	public void setYear( String year )
	{
		this.year = year;
	}

	public void setThema( String thema )
	{
		this.thema = thema;
	}

	public ConferenceGroup getConferenceGroup()
	{
		return conferenceGroup;
	}

	public void setConferenceGroup( ConferenceGroup conferenceGroup )
	{
		this.conferenceGroup = conferenceGroup;
	}

	@ManyToOne
	@JoinColumn( name = "conference_group_id" )
	@IndexedEmbedded
	@Boost( 2.0f )
	private ConferenceGroup conferenceGroup;

	@OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private Location location;

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}

	public Location getLocation()
	{
		return location;
	}

	public void setLocation( Location location )
	{
		this.location = location;
	}

	public String getThema()
	{
		return thema;
	}

	public void setType( String thema )
	{
		this.thema = thema;
	}
}
