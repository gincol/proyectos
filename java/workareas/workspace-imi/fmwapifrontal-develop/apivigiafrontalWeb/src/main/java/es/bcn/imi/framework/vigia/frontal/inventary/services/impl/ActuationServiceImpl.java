package es.bcn.imi.framework.vigia.frontal.inventary.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.ActuationDao;
import es.bcn.imi.framework.vigia.frontal.inventary.services.ActuationService;
import es.bcn.vigia.fmw.libcommons.business.dto.ActuationBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.Actuation;
import es.bcn.vigia.fmw.libutils.convert.ActuationConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_ACTUATION)
public class ActuationServiceImpl implements ActuationService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	
	@Override
	public List<ActuationBDTO> getActuations(Map<String, Object> params) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		
		List<Actuation> actuations = myBatisTemplate.execute(ActuationDao.class,
				new MyBatisDaoCallback<List<Actuation>>() {
				
					@Override
					public List<Actuation> execute(MyBatisDao dao) {
						return ((ActuationDao) dao).getActuations(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return ActuationConvert.object2bdto(actuations);
	}
}