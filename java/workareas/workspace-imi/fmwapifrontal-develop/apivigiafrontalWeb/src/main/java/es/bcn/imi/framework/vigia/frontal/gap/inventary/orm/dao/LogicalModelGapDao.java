package es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.LogicalModelGap;

public interface LogicalModelGapDao extends MyBatisDaoGap {
	 
	public abstract List<LogicalModelGap> getLogicalModelsGap(Map<String, Object> params);
	
	public abstract List<LogicalModelGap> getLogicalModelsUbicationGap(String codeUbication);

	public abstract List<LogicalModelGap> getLogicalModelsPositionUbicationGap(String codeUbication, long position, String codeMCL);
	
	public abstract List<LogicalModelGap> getVacantLogicalModelsGap(Map<String,Object> params);
}

