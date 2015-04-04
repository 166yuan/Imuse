package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "idea_Text")
public class IdeaText extends BasicModel{
	
	@Column(name="title")
	public String title;
	@Column(name="text")
	public String text;

}
