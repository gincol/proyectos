package es.bcn.imi.framework.vigia.frontal.gap.execution.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao.GroupEventGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.GroupEventGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.GroupEventGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.GroupEventGap;
import es.bcn.vigia.fmw.libutils.convert.GroupEventGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_GROUP_EVENT_GAP)
public class GroupEventGapServiceImpl implements GroupEventGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<GroupEventGapBDTO> getGroupEventsGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<GroupEventGap> groupEvents = myBatisTemplateGap.execute(GroupEventGapDao.class,
				new MyBatisDaoCallbackGap<List<GroupEventGap>>() {
				
					@Override
					public List<GroupEventGap> execute(MyBatisDaoGap dao) {
						return ((GroupEventGapDao) dao).getGroupEventsGap(params);
					}

				});
		

		 return GroupEventGapConvert.object2bdto(groupEvents);

	}

}