package es.bcn.imi.framework.vigia.frontal.inventary.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.DocumentarySupportDao;
import es.bcn.imi.framework.vigia.frontal.inventary.services.DocumentarySupportService;
import es.bcn.vigia.fmw.libcommons.business.dto.DocumentarySupportBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.DocumentarySupport;
import es.bcn.vigia.fmw.libutils.convert.DocumentarySupportConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_DOCUMENTARY_SUPPORT)
public class DocumentarySupportServiceImpl implements DocumentarySupportService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	


	@Override
	public List<DocumentarySupportBDTO> getDocumentarySupports(Map<String, Object> params) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		
		List<DocumentarySupport> documentarySupporta = myBatisTemplate.execute(DocumentarySupportDao.class,
				new MyBatisDaoCallback<List<DocumentarySupport>>() {
				
					@Override
					public List<DocumentarySupport> execute(MyBatisDao dao) {
						return ((DocumentarySupportDao) dao).getDocumentarySupports(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return DocumentarySupportConvert.object2bdto(documentarySupporta);

	}
}