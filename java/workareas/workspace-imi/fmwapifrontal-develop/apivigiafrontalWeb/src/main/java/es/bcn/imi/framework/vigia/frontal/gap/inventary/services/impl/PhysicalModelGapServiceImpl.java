package es.bcn.imi.framework.vigia.frontal.gap.inventary.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.PhysicalModelGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.PhysicalModelGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.PhysicalModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.PhysicalModelGap;
import es.bcn.vigia.fmw.libutils.convert.PhysicalModelGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_PHYSICAL_MODEL_GAP)
public class PhysicalModelGapServiceImpl implements PhysicalModelGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<PhysicalModelGapBDTO> getPhysicalModelsGap(String codeMCF) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeMCF", codeMCF);
		
		List<PhysicalModelGap> physicalModels = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
				new MyBatisDaoCallbackGap<List<PhysicalModelGap>>() {
				
					@Override
					public List<PhysicalModelGap> execute(MyBatisDaoGap dao) {
						return ((PhysicalModelGapDao) dao).getPhysicalModelsGap(params);
					}

				});
		

		return PhysicalModelGapConvert.object2bdto(physicalModels);		

	}

	
	@Override
	public List<PhysicalModelGapBDTO> getPhysicalModelsLogicalModelGap(String codeMCL) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
		
		params.put("codeMCL", codeMCL);
		
		List<PhysicalModelGap> physicalModels = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
				new MyBatisDaoCallbackGap<List<PhysicalModelGap>>() {
				
					@Override
					public List<PhysicalModelGap> execute(MyBatisDaoGap dao) {
						return ((PhysicalModelGapDao) dao).getPhysicalModelsGap(params);
					}

				});

		return PhysicalModelGapConvert.object2bdto(physicalModels);
		
	}

	
	@Override
	public List<PhysicalModelGapBDTO> getPhysicalModelsGap(String codeUbication, String codeMCL, String codeMCF)
			throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		Map<String, Object> params = new HashMap<>();
			
		params.put("codeMCL", codeMCL);
		params.put("codeMCF", codeMCF);
		params.put("codeUbication", codeUbication);
		
		List<PhysicalModelGap> physicalModels = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
				new MyBatisDaoCallbackGap<List<PhysicalModelGap>>() {
				
					@Override
					public List<PhysicalModelGap> execute(MyBatisDaoGap dao) {
						return ((PhysicalModelGapDao) dao).getPhysicalModelsGap(params);
					}

				});
		

		return PhysicalModelGapConvert.object2bdto(physicalModels);

	}

	@Override
	public List<PhysicalModelGapBDTO> getPhysicalModelsGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		List<PhysicalModelGap> physicalModels = myBatisTemplateGap.execute(PhysicalModelGapDao.class,
				new MyBatisDaoCallbackGap<List<PhysicalModelGap>>() {
				
					@Override
					public List<PhysicalModelGap> execute(MyBatisDaoGap dao) {
						return ((PhysicalModelGapDao) dao).getPhysicalModelsGap(params);
					}

				});
		

		return PhysicalModelGapConvert.object2bdto(physicalModels);		

	}


}