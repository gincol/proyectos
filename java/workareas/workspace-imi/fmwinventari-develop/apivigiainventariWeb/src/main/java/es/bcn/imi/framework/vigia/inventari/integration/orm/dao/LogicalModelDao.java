package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.LogicalModel;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface LogicalModelDao extends MyBatisDao{

	public abstract void insert(LogicalModel logicalModel);

	public abstract void update(LogicalModel logicalModel);

	public abstract void delete(LogicalModel logicalModel);
	
	public abstract LogicalModel select(LogicalModel logicalModel);

	public abstract List<LogicalModel> selectLogicalModel(Map<String, Object> map);
	
}