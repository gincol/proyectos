package es.bcn.imi.framework.vigia.frontal.business.certification;

import org.springframework.web.multipart.MultipartFile;

import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCleaningServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationCollectionServiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExpenseInstallationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationExtraordinaryRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInspectionRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentFurnitureRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvestmentRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationInvoiceRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationOthersRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationPersonalRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.QueryParameterCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnCertificationProposalsRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.CertificationRegularizationRDTO;
import es.bcn.vigia.fmw.libcommons.web.rest.rdto.out.returns.ReturnRDTO;
import es.bcn.vigia.fmw.libutils.exception.ImiException;

public interface CertificationService {
	
	public abstract ReturnRDTO sendVersionAndHeader(CertificationRDTO certificationRDTO) throws ImiException;
	public abstract ReturnRDTO sendOthersExpensesDetails(CertificationOthersRDTO certificationOthersRDTO) throws ImiException;
	public abstract ReturnRDTO sendExtraordinaryConceptsDetails(CertificationExtraordinaryRDTO certificationExtraordinaryRDTO) throws ImiException;
	public abstract ReturnRDTO sendInvoicesDetails(CertificationInvoiceRDTO certificationInvoiceRDTO, MultipartFile attachedFile) throws ImiException;
	public abstract ReturnRDTO sendInspectionsDetails(CertificationInspectionRDTO certificationInspectionRDTO) throws ImiException;
	public abstract ReturnRDTO sendInstallationExpensesDetails(CertificationExpenseInstallationRDTO certificationExpenseInstallationRDTO) throws ImiException;
	public abstract ReturnRDTO sendInvestmentFurnitureDetails(CertificationInvestmentFurnitureRDTO certificationInvestmentFurnitureDTO) throws ImiException;
	public abstract ReturnRDTO sendCleaningServiceDetails(CertificationCleaningServiceRDTO certificationCleaningServiceRDTO) throws ImiException;
	public abstract ReturnRDTO sendStaffExpensesDetails(CertificationPersonalRDTO certificationPersonalRDTO) throws ImiException;
	public abstract ReturnRDTO sendCollectionServiceDetails(CertificationCollectionServiceRDTO certificationCollectionServiceRDTO) throws ImiException;
	public abstract ReturnRDTO sendRegularizationsDetails(CertificationRegularizationRDTO certificationRegularizationRDTO) throws ImiException;
	public abstract ReturnRDTO sendInvestmentRRMMDetails(CertificationInvestmentRDTO certificationInvestmentRRMMRDTO) throws ImiException;
	public abstract ReturnCertificationProposalsRDTO selectCertificationProposals(QueryParameterCertificationProposalsRDTO queryParameterCertificationProposalsRDTO) throws ImiException;
	

	
}