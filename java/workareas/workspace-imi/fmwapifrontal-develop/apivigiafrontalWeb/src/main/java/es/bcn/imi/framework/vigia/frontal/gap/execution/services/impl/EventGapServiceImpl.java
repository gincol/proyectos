package es.bcn.imi.framework.vigia.frontal.gap.execution.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao.EventGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.EventGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.EventGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.EventGap;
import es.bcn.vigia.fmw.libutils.convert.EventGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_EVENT_GAP)
public class EventGapServiceImpl implements EventGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<EventGapBDTO> getEventsGap(String code) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<EventGap> events = myBatisTemplateGap.execute(EventGapDao.class,
				new MyBatisDaoCallbackGap<List<EventGap>>() {
				
					@Override
					public List<EventGap> execute(MyBatisDaoGap dao) {
						return ((EventGapDao) dao).getEventsGap(code);
					}

				});
		

		 return EventGapConvert.object2bdto(events);

	}

}