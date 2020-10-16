package es.bcn.imi.framework.vigia.inventari.integration.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupport;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupportExpense;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface DocumentarySupportDao extends MyBatisDao {

	public abstract void insert(DocumentarySupport documentarySupport);
	
	public abstract void update(DocumentarySupport documentarySupport);
	
	public abstract void insertDocumentElement(DocumentarySupport documentarySupport);
	
	public abstract DocumentarySupport selectDocumentElement(DocumentarySupport documentarySupport);

	public abstract void deleteDocumentElement(DocumentarySupport documentarySupport);
	
	public abstract void insertDocumentExpense(DocumentarySupportExpense documentarySupportExpense);
	
	public abstract void deleteDocumentExpense(DocumentarySupportExpense documentarySupportExpense);

	public abstract List<DocumentarySupport> selectDocumentarySupport(Map<String, Object> map);
	
	public abstract DocumentarySupport selectDocumentExpense(DocumentarySupport documentarySupport);
}
