package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceDetailedGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceElementGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.CommerceGap;

public interface CommerceGapDao extends MyBatisDaoGap {

	public abstract List<CommerceDetailedGap> selectCommerce(Map<String, Object> map);

	public abstract List<CommerceGap> selectCommerceMassive(Map<String, Object> map);

	public abstract List<CommerceElementGap> selectElementsByCommerce(Map<String, Object> map);
}