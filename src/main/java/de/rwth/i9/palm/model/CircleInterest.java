package de.rwth.i9.palm.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity( name = "circle_interest" )
public class CircleInterest extends PersistableResource
{
	@ElementCollection
	@MapKeyColumn( name = "term" )
	@Column( name = "weight" )
	@CollectionTable( name = "circle_term_weight" )
	Map<Interest, Double> termWeights;

	@ElementCollection
	@MapKeyColumn( name = "term" )
	@Column( name = "weight" )
	@CollectionTable( name = "circle_term_weight_string" )
	Map<String, Double> termWeightsString;

	@Column
	Date year;

	@Column( columnDefinition = "bit default 1" )
	private boolean valid = true;

	@Column
	private String language;

	// relation
	@ManyToOne
	@JoinColumn( name = "circle_interest_profile_id" )
	private CircleInterestProfile circleInterestProfile;

	// getter & setter

	public Map<Interest, Double> getTermWeights()
	{
		return termWeights;
	}

	public void setTermWeights( Map<Interest, Double> termWeights )
	{
		this.termWeights = termWeights;
	}

	public CircleInterest addTermWeight( Interest term, double weight )
	{
		if ( termWeights == null )
			termWeights = new LinkedHashMap<Interest, Double>();

		termWeights.put( term, weight );

		return this;
	}

	public Map<String, Double> getTermWeightsString()
	{
		return termWeightsString;
	}

	public void setTermWeightsString( Map<String, Double> termWeightsString )
	{
		this.termWeightsString = termWeightsString;
	}

	public CircleInterest addTermWeight( String term, double weight )
	{
		if ( termWeightsString == null )
			termWeightsString = new LinkedHashMap<String, Double>();

		termWeightsString.put( term, weight );

		return this;
	}

	public Date getYear()
	{
		return year;
	}

	public void setYear( Date year )
	{
		this.year = year;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid( boolean valid )
	{
		this.valid = valid;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage( String language )
	{
		this.language = language;
	}

	public CircleInterestProfile getCircleInterestProfile()
	{
		return circleInterestProfile;
	}

	public void setCircleInterestProfile( CircleInterestProfile circleInterestProfile )
	{
		this.circleInterestProfile = circleInterestProfile;
	}

}
