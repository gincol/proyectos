package es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.GroupFurnitureGap;



public interface GroupFurnitureGapDao extends MyBatisDaoGap {
	 
	public abstract List<GroupFurnitureGap> getGroupsFurnitureGap(Map<String,Object> params);
}

