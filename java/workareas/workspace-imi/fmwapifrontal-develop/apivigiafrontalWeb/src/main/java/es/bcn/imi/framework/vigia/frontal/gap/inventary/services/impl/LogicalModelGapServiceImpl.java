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

import es.bcn.imi.framework.vigia.frontal.gap.inventary.orm.dao.LogicalModelGapDao;
import es.bcn.imi.framework.vigia.frontal.gap.inventary.services.LogicalModelGapService;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisDaoCallbackGap;
import es.bcn.imi.framework.vigia.frontal.persistence.mybatis.template.MyBatisTemplateGap;
import es.bcn.vigia.fmw.libcommons.business.dto.view.gap.LogicalModelGapBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.gap.LogicalModelGap;
import es.bcn.vigia.fmw.libutils.convert.LogicalModelGapConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

@Lazy(true)
@Service(ServicesConstants.SRV_LOGICAL_MODEL_GAP)
public class LogicalModelGapServiceImpl implements LogicalModelGapService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE_GAP)
	private MyBatisTemplateGap myBatisTemplateGap;

	@Override
	public List<LogicalModelGapBDTO> getLogicalModelsGap(Map<String,Object> params) throws ImiException {
		
		logger.info(LogsConstants.LOG_START);
		
		 List<LogicalModelGap> logicalModels = myBatisTemplateGap.execute(LogicalModelGapDao.class,
				new MyBatisDaoCallbackGap<List<LogicalModelGap>>() {
				
					@Override
					public List<LogicalModelGap> execute(MyBatisDaoGap dao) {
						return ((LogicalModelGapDao) dao).getLogicalModelsGap(params);
					}

				});
		

		 return LogicalModelGapConvert.object2bdto(logicalModels);		

	}
	
	@Override
	public List<LogicalModelGapBDTO> getLogicalModelsUbicationGap(String codeUbication) throws ImiException {
		 List<LogicalModelGap> logicalModels = myBatisTemplateGap.execute(LogicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<LogicalModelGap>>() {
					
						@Override
						public List<LogicalModelGap> execute(MyBatisDaoGap dao) {
							return ((LogicalModelGapDao) dao).getLogicalModelsUbicationGap(codeUbication);
						}

					});
			

		 return LogicalModelGapConvert.object2bdto(logicalModels);
	}

	@Override
	public List<LogicalModelGapBDTO> getLogicalModelsPositionUbicationGap(String codeUbication, long position,String codeMCL) throws ImiException {
		 List<LogicalModelGap> logicalModels = myBatisTemplateGap.execute(LogicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<LogicalModelGap>>() {
					
						@Override
						public List<LogicalModelGap> execute(MyBatisDaoGap dao) {
							return ((LogicalModelGapDao) dao).getLogicalModelsPositionUbicationGap(codeUbication, position, codeMCL);
						}

					});
			

		 return LogicalModelGapConvert.object2bdto(logicalModels);
	}

	@Override
	public List<LogicalModelGapBDTO> getVacantLogicalModelsGap(String codeMCL, String codeMCF) throws ImiException {
		Map<String, Object> params = new HashMap<>();
		params.put("codeMCL", codeMCL);
		params.put("codeMCF", codeMCF);

		 List<LogicalModelGap> logicalModels = myBatisTemplateGap.execute(LogicalModelGapDao.class,
					new MyBatisDaoCallbackGap<List<LogicalModelGap>>() {
					
						@Override
						public List<LogicalModelGap> execute(MyBatisDaoGap dao) {
							return ((LogicalModelGapDao) dao).getVacantLogicalModelsGap(params);
						}

					});
			

		 return LogicalModelGapConvert.object2bdto(logicalModels);
	}
			

}