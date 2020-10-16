package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MclGap;

public interface LogicalModelGapDao extends MyBatisDaoGap {

	public abstract List<MclGap> selectMclMassive(Map<String, Object> map);
	
	public abstract List<MclGap> selectMcl(Map<String, Object> map);
	
}