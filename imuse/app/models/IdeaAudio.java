package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sudocn.play.BasicModel;
@Entity
@Table(name = "idea_audio")
public class IdeaAudio extends BasicModel{
	
	@Column(name="title")
	public String title;
	@Column(name="text")
	public String text;
	@Column(name="track_id")
	public String TrackId;
	@Column(name="lrc_id")
	public String LrcId;
}
