package es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao;

import java.util.List;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PlantWeighingGap;




public interface PlantWeighingGapDao extends MyBatisDaoGap {
	 
	public abstract List<PlantWeighingGap> getPlantWeighingsGap(String code);
}

