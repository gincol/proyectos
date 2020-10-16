package es.bcn.imi.framework.vigia.frontal.gap.planification.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.planification.orm.dao.PlanificationGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.planification.services.PlanificationGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlanificationGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PlanningGap;
import es.bcn.vigia.fmw.libutils.convert.PlanificationGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_PLANIFICATION_GAP)
public class PlanificationGapServiceImpl implements PlanificationGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	
	@Override
	public List<PlanificationGapBDTO> getPlanificationsGap(Map<String, Object> params) throws ImiException {
		logger.info(LogsConstants.LOG_START);
		
		 List<PlanningGap> planifications = myBatisTemplateGap.execute(PlanificationGapDao.class,
				new MyBatisDaoCallbackGap<List<PlanningGap>>() {
				
					@Override
					public List<PlanningGap> execute(MyBatisDaoGap dao) {
						return ((PlanificationGapDao) dao).getPlanificationsGap(params);
					}

				});
		

		 return PlanificationGapConvert.object2bdto(planifications);

	}

}