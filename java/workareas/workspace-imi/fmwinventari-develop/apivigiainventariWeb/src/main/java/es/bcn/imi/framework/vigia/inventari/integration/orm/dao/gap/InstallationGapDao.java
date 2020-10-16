package es.bcn.imi.framework.vigia.inventari.integration.orm.dao.gap;

import java.util.List;
import java.util.Map;

import es.bcn.imi.framework.vigia.inventari.persistence.template.MyBatisDaoGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.AmortizationBaseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ApportionmentGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.DetailCertificationGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ExpenseGap;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.InstallationGap;

public interface InstallationGapDao extends MyBatisDaoGap {

	public abstract List<InstallationGap> selectInstallation(Map<String, Object> map);

	public abstract List<AmortizationBaseGap> selectAmortizationBasesByInstallation(Map<String, Object> map);

	public abstract List<ExpenseGap> selectExpensesByInstallation(Map<String, Object> map);

	public abstract List<ApportionmentGap> selectApportionmentsByInstallation(Map<String, Object> map);

	public abstract List<DetailCertificationGap> selectDetailCertificationAmortizationByInstallation(Map<String, Object> map);

	public abstract List<DetailCertificationGap> selectDetailCertificationExpensesByInstallation(Map<String, Object> map);
}
