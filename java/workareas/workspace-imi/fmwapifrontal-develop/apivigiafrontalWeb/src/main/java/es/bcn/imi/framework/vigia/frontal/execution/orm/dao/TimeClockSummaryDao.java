package es.bcn.imi.framework.vigia.frontal.execution.orm.dao;

import java.util.List;
import java.util.Map;

import es.bcn.vigia.fmw.libcommons.integration.orm.model.TimeClockSummary;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;

public interface TimeClockSummaryDao extends MyBatisDao {
	
	public abstract List<TimeClockSummary> getTimeClockSummaries(Map<String,Object> params);

	
}

