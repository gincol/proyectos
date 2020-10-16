package es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao;

import java.util.List;

import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.TypeSubtypeExpenseGap;


public interface TypeSubtypeExpenseGapDao extends MyBatisDaoGap {
	 
	public abstract List<TypeSubtypeExpenseGap> getTypeSubtypesExpenseGap(String codeType);
}

