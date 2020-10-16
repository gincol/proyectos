package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.EmployeeCatalog;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface EmployeeCatalogDao extends MyBatisDao {

	public abstract void insert(EmployeeCatalog employeeCatalog);

	public abstract List<EmployeeCatalog> selectEmployeeCatalog(Map<String, Object> map);

	public abstract List<EmployeeCatalog> selectEmployeeCatalogMassive(Map<String, Object> map);

}
