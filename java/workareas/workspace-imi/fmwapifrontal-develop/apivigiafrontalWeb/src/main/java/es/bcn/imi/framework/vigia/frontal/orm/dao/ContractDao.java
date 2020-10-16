package es.bcn.imi.framework.vigia.frontal.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.Contract;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface ContractDao extends MyBatisDao {
	
	public abstract List<Contract> getContract(Map<String, String> params);
}

