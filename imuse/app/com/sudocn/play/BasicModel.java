package com.sudocn.play;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;

import play.db.jpa.GenericModel;
import play.db.jpa.JPABase;
import play.libs.Codec;

@MappedSuperclass
public class BasicModel extends GenericModel {

	@Id
	public String id;

	@Column(name = "create_time")
	public Long createTime;

	@Column(name = "update_time")
	public Long updateTime;

	void beforeSave(){
		long now = System.currentTimeMillis();
		if (createTime == null) {
			createTime = now;
		}
		if (StringUtils.isEmpty(id)) {
			id = generateId(); 
		}
		updateTime = now;
	}

	@Override
	public <T extends JPABase> T save() {
		beforeSave();
		return super.save();
	}
	
	@Override
	public void _save() {
		beforeSave();
		super._save();
	}

	public String getId() {
		return id;
	}

	@Override
	public Object _key() {
		return getId();
	}
	
	protected String generateId(){
		return Codec.UUID().replace("-", "").toUpperCase();
	}

}
