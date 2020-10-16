package es.bcn.imi.framework.vigia.frontal.gap.execution.services.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.execution.orm.dao.PlantWeighingGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.execution.services.PlantWeighingGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PlantWeighingGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PlantWeighingGap;
import es.bcn.vigia.fmw.libutils.convert.PlantWeighingGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_PLANT_WEIGHING_GAP)
public class PlantWeighingGapServiceImpl implements PlantWeighingGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<PlantWeighingGapBDTO> getPlantWeighingsGap(String code) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<PlantWeighingGap> plantWeighings = myBatisTemplateGap.execute(PlantWeighingGapDao.class,
				new MyBatisDaoCallbackGap<List<PlantWeighingGap>>() {
				
					@Override
					public List<PlantWeighingGap> execute(MyBatisDaoGap dao) {
						return ((PlantWeighingGapDao) dao).getPlantWeighingsGap(code);
					}

				});
		

		 return PlantWeighingGapConvert.object2bdto(plantWeighings);

	}

}