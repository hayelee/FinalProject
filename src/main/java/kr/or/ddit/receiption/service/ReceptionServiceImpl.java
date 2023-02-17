package kr.or.ddit.receiption.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.vo.PatientVO;
import kr.or.ddit.commons.vo.WaitHistoryVO;
import kr.or.ddit.receiption.dao.ReceiptionDAO;
import kr.or.ddit.receiption.vo.ReadRegistVO;
import kr.or.ddit.receiption.vo.ReceiptionVO;
import kr.or.ddit.receiption.vo.RegistVO;
import kr.or.ddit.receiption.vo.SelectPatientVO;

@Service
public class ReceptionServiceImpl implements ReceptionService {

	@Inject
	ReceiptionDAO DAO;

	@Override
	public List<ReceiptionVO> searchPatientList(Map<String, String> map) {
			List<ReceiptionVO> seekReceptionList = DAO.seekReceptionList(map);
		return seekReceptionList;
	}

	@Override
	public int createReceiption(RegistVO registVO) {
		int rowcnt = DAO.insertReceiption(registVO);
		return rowcnt;
	}

	@Override
	public int createWaitHistory(WaitHistoryVO waitHistoryVO) {
		int rowcnt = DAO.insertWaitHistory(waitHistoryVO);
		return rowcnt;
	}

	@Override
	public int createPatient(PatientVO patientVO) {
		int rowcnt = DAO.insertPatient(patientVO);
		return rowcnt;
	}

	@Override
	public ReadRegistVO selectRegistVO(String paNo) {
		ReadRegistVO registVO = DAO.selectRegistVO(paNo);
		return registVO;
	}

	@Override
	public SelectPatientVO selectPatient() {
		SelectPatientVO selectPatient = DAO.selectPatient();
		return selectPatient;
	}


	
	
	

}
