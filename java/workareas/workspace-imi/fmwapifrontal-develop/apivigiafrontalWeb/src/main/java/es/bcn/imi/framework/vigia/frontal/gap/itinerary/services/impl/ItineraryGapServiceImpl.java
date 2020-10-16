package es.bcn.imi.framework.vigia.frontal.gap.itinerary.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.itinerary.orm.dao.ItineraryGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.itinerary.services.ItineraryGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.ItineraryGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.ItineraryGap;
import es.bcn.vigia.fmw.libutils.convert.ItineraryGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_ITINERARY_GAP)
public class ItineraryGapServiceImpl implements ItineraryGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<ItineraryGapBDTO> getItinerariesGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<ItineraryGap> itineraries = myBatisTemplateGap.execute(ItineraryGapDao.class,
				new MyBatisDaoCallbackGap<List<ItineraryGap>>() {
				
					@Override
					public List<ItineraryGap> execute(MyBatisDaoGap dao) {
						return ((ItineraryGapDao) dao).getItinerariesGap(params);
					}

				});
		

		 return ItineraryGapConvert.object2bdto(itineraries);

	}

}