package de.rwth.i9.palm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.rwth.i9.palm.persistence.PersistableResource;

@Entity
@Table( name = "user_widget" )
public class UserWidget extends PersistableResource
{
	@Column( name = "position_" )
	private int position;

	@Enumerated( EnumType.STRING )
	@Column( columnDefinition = "VARCHAR(16) DEFAULT 'NORMAL'" )
	private WidgetCondition witgetCondition;

	@Enumerated( EnumType.STRING )
	@Column( length = 8 )
	private WidgetWidth widgetWidth;

	@Enumerated( EnumType.STRING )
	@Column( length = 16 )
	private Color widgetColor;

	// relationships
	@OneToOne
	@JoinColumn( name = "widget_id" )
	private Widget widget;

	// getter / setter

	public int getPosition()
	{
		return position;
	}

	public void setPosition( int position )
	{
		this.position = position;
	}

	public WidgetCondition getWitgetCondition()
	{
		return witgetCondition;
	}

	public void setWitgetCondition( WidgetCondition witgetCondition )
	{
		this.witgetCondition = witgetCondition;
	}

	public Color getWidgetColor()
	{
		return widgetColor;
	}

	public void setWidgetColor( Color widgetColor )
	{
		this.widgetColor = widgetColor;
	}

	public Widget getWidget()
	{
		return widget;
	}

	public void setWidget( Widget widget )
	{
		this.widget = widget;
	}

	public WidgetWidth getWidgetWidth()
	{
		return widgetWidth;
	}

	public void setWidgetWidth( WidgetWidth widgetWidth )
	{
		this.widgetWidth = widgetWidth;
	}
}
