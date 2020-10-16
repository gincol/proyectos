package es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.UbicationGap;

public interface UbicationGapDao extends MyBatisDaoGap {
	 
	public abstract List<UbicationGap> getUbicationsGap(Map<String, Object> params);

	public abstract List<UbicationGap> getUbicationsGapByCodeType(Map<String, Object> params);
}

