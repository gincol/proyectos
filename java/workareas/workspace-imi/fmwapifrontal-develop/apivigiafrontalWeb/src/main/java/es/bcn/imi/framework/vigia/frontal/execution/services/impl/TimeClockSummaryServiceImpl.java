package es.bcn.imi.framework.vigia.frontal.execution.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.execution.orm.dao.TimeClockSummaryDao;
import es.bcn.imi.framework.vigia.frontal.execution.services.TimeClockSummaryService;
import es.bcn.vigia.fmw.libcommons.business.dto.TimeClockSummaryBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.TimeClockSummary;
import es.bcn.vigia.fmw.libutils.convert.TimeClockSummaryConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_TIME_CLOCK_SUMMARY)
public class TimeClockSummaryServiceImpl implements TimeClockSummaryService {
	
	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;
	

	@Override
	public List<TimeClockSummaryBDTO> getTimeClockSummaries(Map<String, Object> params) throws ImiException {
		List<TimeClockSummary> timeClockSummaries = myBatisTemplate.execute(TimeClockSummaryDao.class,
				new MyBatisDaoCallback<List<TimeClockSummary>>() {
				
					@Override
					public List<TimeClockSummary> execute(MyBatisDao dao) {
						return ((TimeClockSummaryDao) dao).getTimeClockSummaries(params);
					}

				});

		
		return TimeClockSummaryConvert.object2bdto(timeClockSummaries);

	}	
}