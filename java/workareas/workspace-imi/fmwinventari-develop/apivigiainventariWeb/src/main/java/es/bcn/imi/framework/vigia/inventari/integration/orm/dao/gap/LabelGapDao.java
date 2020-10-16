package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.LabelGap;

public interface LabelGapDao extends MyBatisDaoGap {

	public abstract List<LabelGap> selectLabel(Map<String, Object> map);	
}