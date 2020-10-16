package es.bcn.imi.framework.vigia.frontal.business.domains;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterDomainsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainValuesRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnDomainsRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface DomainsService {
	
	public abstract ReturnDomainsRDTO selectDomains() throws ImiException;

	public abstract ReturnDomainValuesRDTO selectDomainValues(QueryParameterDomainsRDTO queryParameterDomainsRDTO) throws ImiException;	
	
}
