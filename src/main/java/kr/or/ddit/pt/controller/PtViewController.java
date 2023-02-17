package kr.or.ddit.pt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.commons.vo.PatientVO;
import kr.or.ddit.commons.vo.PtAssinmentVO;
import kr.or.ddit.commons.vo.PtDocumentVO;
import kr.or.ddit.commons.vo.ReceptionVO;
import kr.or.ddit.commons.vo.WaitHistoryVO;
import kr.or.ddit.pt.service.PtAssinmentService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 장예지
 * @since 2023. 2. 3.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 2. 3.      장예지       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */

@Slf4j
@RequestMapping("/pt")
@Controller
public class PtViewController {

	@Inject
	PtAssinmentService service;
	
	@GetMapping("/ptView")
	public String ptView(@ModelAttribute("ptAssinmentVOList") PtAssinmentVO ptAssinmentVO, Model model) {
		
		List<PtAssinmentVO> ptAssinmentVOList = service.retrievePtRoomList(ptAssinmentVO);
		List<PtAssinmentVO> ptBedList = service.retrievePtBedList();
		
		model.addAttribute("ptAssinmentList", ptAssinmentVOList);
		model.addAttribute("ptBedList", ptBedList);
		String viewName = null;
		viewName = "pt/ptView";

		return viewName;
	}

	/**
	 * Bed에 환자를 배정 
	 * (물리치료실 bed의 상태를) => update
	 * watingHistory에 현재 상태(대기중->치료중)를 Insert
	 * 
	 * @param ptAssinmentVO
	 * @return map
	 */
	@ResponseBody
	@PostMapping(value ="/ptBedFull", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> ptBedFull(@RequestBody PtAssinmentVO ptAssinmentVO) {
//		log.info("hi  ptAssinmentVO : {} ",ptAssinmentVO); //ok 확인
	
		int insertCnt = service.modifyptBedFull(ptAssinmentVO); //업데이트

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", insertCnt);
		map.put("rcpNo", ptAssinmentVO.getRcpNo());
		map.put("paName", ptAssinmentVO.getPaName());
		map.put("ptBedCd", ptAssinmentVO.getPtBedCd());

//		log.info("resultMap : {}" ,map);
		return map;
	}
	
	/**
	 * Bed에 환자의 데이터를 제거
	 * (물리치료실 bed의 상태를) => update
	 * watingHistory에 현재 상태(치료중->완료) Insert
	 * @param ptAssinmentVO
	 * @param waitHistoryVO
	 * @return map
	 */
	@ResponseBody
	@PostMapping(value = "/ptBedEmpty" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> ptBedEmpty(@RequestBody PtAssinmentVO ptAssinmentVO) {
		log.info("ptAssinmentVO : " + ptAssinmentVO);
		
		int insertCnt = service.modifyptBedEmpty(ptAssinmentVO); //엡데이트 문 호출
		
		Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", insertCnt);
			map.put("rcpNo", ptAssinmentVO.getRcpNo());
			map.put("paName", ptAssinmentVO.getPaName());
			map.put("ptBedCd", ptAssinmentVO.getPtBedCd());
		
		log.info("resultMap : {}" ,map);
		return map;
	}

	/**
	 * 환자 검색
	 * @param map
	 * @return
	 * @return
	 */
	@ResponseBody
	@PostMapping("/ptPatientSearch")
	public List<PatientVO> patientSearch(@RequestBody Map<String, String> map) {
		log.info("searchOption : {}", map.get("searchOption"));
		log.info("searchWord : {}", map.get("searchWord"));

		List<PatientVO> list = service.searchPatients(map);
		return list;
	}

	/**
	 * 환자 List 출력(초기화면)
	 * @return
	 */
	@ResponseBody
	@GetMapping("/patientList")
	public List<PatientVO> patientList() {
		List<PatientVO> list = service.selectPatientList();
		return list;

	}
	
	/**
	 * 물리치료기록서 List 출력
	 * @return
	 */
//	@GetMapping("/ptDocumentList")
//	public List<PtDocumentVO> ptDocumentList(
//			@ModelAttribute("ptAssinmentVOList") PtAssinmentVO ptAssinmentVO, Model model
//			) {
//		log.info("hello ptAssinmentVOList : {dsdsdsdsdsdsdsdsd}");
//		List<PtDocumentVO> ptDocumentList = service.retrievePtDocumentAgo();
//		return ptDocumentList;
//	}
	
}