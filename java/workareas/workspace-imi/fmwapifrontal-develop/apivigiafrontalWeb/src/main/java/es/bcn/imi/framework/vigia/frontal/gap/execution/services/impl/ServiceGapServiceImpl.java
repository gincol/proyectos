package es.bcn.imi.framework.vigia.frontal.gap.execution.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao.ServiceGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.ServiceGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ServiceGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ServiceGap;
import es.bcn.vigia.fmw.libutils.convert.ServiceGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_SERVICE_GAP)
public class ServiceGapServiceImpl implements ServiceGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<ServiceGapBDTO> getServicesGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<ServiceGap> services = myBatisTemplateGap.execute(ServiceGapDao.class,
				new MyBatisDaoCallbackGap<List<ServiceGap>>() {
				
					@Override
					public List<ServiceGap> execute(MyBatisDaoGap dao) {
						return ((ServiceGapDao) dao).getServicesGap(params);
					}

				});
		

		 return ServiceGapConvert.object2bdto(services);

	}

}