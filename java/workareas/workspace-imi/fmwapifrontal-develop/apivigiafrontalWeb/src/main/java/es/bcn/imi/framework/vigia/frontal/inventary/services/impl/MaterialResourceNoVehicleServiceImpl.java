package es.bcn.imi.framework.vigia.frontal.inventary.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import es.bcn.imi.framework.vigia.frontal.inventary.orm.dao.MaterialResourceDao;
import es.bcn.imi.framework.vigia.frontal.inventary.services.MaterialResourceNoVehicleService;
import es.bcn.vigia.fmw.libcommons.business.dto.MaterialResourceBDTO;
import es.bcn.vigia.fmw.libcommons.constants.ImiConstants;
import es.bcn.vigia.fmw.libcommons.constants.ServicesConstants;
import es.bcn.vigia.fmw.libcommons.integration.orm.model.MaterialResource;
import es.bcn.vigia.fmw.libutils.convert.MaterialResourceConvert;
import es.bcn.vigia.fmw.libutils.exception.ImiException;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDao;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisDaoCallback;
import net.opentrends.openframe.services.persistence.mybatis.template.MyBatisTemplate;

@Lazy
@Service(ServicesConstants.SRV_RM_NO_VEHICLES)
public class MaterialResourceNoVehicleServiceImpl implements MaterialResourceNoVehicleService {
	
	@Autowired
	@Qualifier(ImiConstants.MY_BATIS_TEMPLATE)
	private MyBatisTemplate myBatisTemplate;
	
	@Override
	public List<MaterialResourceBDTO> getMaterialResourcesNoVehicleByCode(Map<String, Object> params) throws ImiException {
		List<MaterialResource> materialResources = myBatisTemplate.execute(MaterialResourceDao.class,
				new MyBatisDaoCallback<List<MaterialResource>>() {
				
					@Override
					public List<MaterialResource> execute(MyBatisDao dao) {
						return ((MaterialResourceDao) dao).selectNoVehiclesByCode(params);
					}

				});

		
		return MaterialResourceConvert.object2bdto(materialResources);
	}	
}