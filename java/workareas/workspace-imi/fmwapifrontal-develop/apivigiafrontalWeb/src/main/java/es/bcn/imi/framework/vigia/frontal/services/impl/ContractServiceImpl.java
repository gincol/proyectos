package es.bcn.imi.framework.vigia.frontal.services.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.orm.dao.ContractDao;
import es.bcn.imi.framework.vigia.frontal.services.ContractService;
import es.bcn.vigia.fmw.libcommons.business.dto.ContractBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.LogsConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.Contract;
import es.bcn.vigia.fmw.libutils.convert.ContractConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_CONTRACT)
public class ContractServiceImpl implements ContractService {

	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;

	
	@Override
	public List<ContractBDTO> getContract(Map<String, String> params) throws ImiException {
		logger.info(LogsConstants.LOG_START);		
		
		List<Contract> contracts = myBatisTemplate.execute(ContractDao.class,
				new MyBatisDaoCallback<List<Contract>>() {
				
					@Override
					public List<Contract> execute(MyBatisDao dao) {
						return ((ContractDao) dao).getContract(params);
					}

				});

		logger.info(LogsConstants.LOG_END);
		
		return ContractConvert.object2bdto(contracts);
	}
}