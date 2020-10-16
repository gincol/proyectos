package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.McfGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PhysicalModelAmortizationGap;

public interface PhysicalModelGapDao extends MyBatisDaoGap {

	public abstract List<McfGap> selectPhysicalModelMassive(Map<String, Object> map);
	
	public abstract List<McfGap> selectPhysicalModel(Map<String, Object> map);
	
	public abstract List<PhysicalModelAmortizationGap> selectPhysicalModelAmortization(Map<String, Object> map);
	
}