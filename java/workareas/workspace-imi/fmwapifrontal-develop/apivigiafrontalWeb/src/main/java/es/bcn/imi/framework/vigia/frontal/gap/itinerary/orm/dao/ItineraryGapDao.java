package es.bcn.imi.framework.vigia.frontal.gap.itinerary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ItineraryGap;



public interface ItineraryGapDao extends MyBatisDaoGap {
	 
	public abstract List<ItineraryGap> getItinerariesGap(Map<String,Object> params);
}

