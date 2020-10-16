package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.AmortizationBaseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ApportionmentGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.DetailCertificationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ExpenseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.MaterialResourceGap;

public interface MaterialResourceGapDao extends MyBatisDaoGap {

	public abstract List<MaterialResourceGap> selectMaterialResourceVehicleMassive(Map<String, Object> map);

	public abstract List<MaterialResourceGap> selectMaterialResourceVehicle(Map<String, Object> map);

	public abstract List<DetailCertificationGap> selectDetailCertificationAmortizationByRRMM(Map<String, Object> map);

	public abstract List<DetailCertificationGap> selectDetailCertificationExpensesByRRMM(Map<String, Object> map);

	public abstract List<ApportionmentGap> selectApportionmentsByRRMM(Map<String, Object> map);

	public abstract List<AmortizationBaseGap> selectAmortizationBasesByRRMM(Map<String, Object> map);

	public abstract List<ExpenseGap> selectExpensesByRRMM(Map<String, Object> map);
	
}