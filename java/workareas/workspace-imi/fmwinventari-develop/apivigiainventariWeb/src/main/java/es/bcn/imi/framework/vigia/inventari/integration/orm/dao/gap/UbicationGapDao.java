package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.UbicationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MclGap;

public interface UbicationGapDao extends MyBatisDaoGap {

	public abstract List<UbicationGap> selectUbication(Map<String, Object> map);

	public abstract List<UbicationGap> selectUbicationMassive(Map<String, Object> map);

	public abstract List<MclGap> selectMclByUbication(Map<String, Object> map);
	
}