package es.bcn.imi.framework.vigia.frontal.inventary.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupport;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface DocumentarySupportDao extends MyBatisDao {
	
	public abstract List<DocumentarySupport> getDocumentarySupports(Map<String, Object> params);
}

