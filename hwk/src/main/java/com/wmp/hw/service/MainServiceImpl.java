package com.wmp.hw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wmp.hw.motel.HwVo;
import com.wmp.hw.motel.ResultVo;

@Service
public class MainServiceImpl implements MainService {

	@Override
	public ResultVo getProc(HwVo hwVo) {
		String resultStr = mergeStr(hwVo);
		return makeResult(resultStr, hwVo);
	}
	
	/**
	 * 숫자 영문자 추출 및 merge
	 * @param hwVo
	 * @return
	 */
	private String mergeStr(HwVo hwVo) {
		Predicate<String> numCheck = x -> x.matches("[0-9]");
		Predicate<String> enCheck = x -> x.matches("[A-z]");
		List<String> numArr = new ArrayList<>();
		List<String> enArr = new ArrayList<>();
		
		for(int i = 0; i < hwVo.getInpStr().length(); i++) {
			String c = hwVo.getInpStr().substring(i, i+1);
			if(numCheck.test(c)){
				numArr.add(c);
				continue;
			}
			if(enCheck.test(c)) {
				enArr.add(c);
				continue;
			}
		}
		
		Collections.sort(numArr);
		enArr = enArr.stream().sorted().sorted((e1, e2) -> e1.toUpperCase().compareTo(e2.toUpperCase())).collect(Collectors.toList());
		return mergeArr(numArr, enArr);
	}
	
	
	/**
	 * 숫자_리스트, 영문자_리스트 merge
	 * @param num 숫자_리스트
	 * @param en 영문자_리스트
	 * @return
	 */
	private String mergeArr(List<String> num, List<String> en) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> itNum = num.iterator();
		Iterator<String> itEn = en.iterator();
		while(itEn.hasNext() || itNum.hasNext()) {
			if(itEn.hasNext()) {
				sb.append(itEn.next());
			}
			if(itNum.hasNext()) {
				sb.append(itNum.next());
			}
		}
		return sb.toString();
	}
	
	/** 
	 * 결과값 생성
	 * @param inStr
	 * @param hwVo
	 * @return
	 */
	private ResultVo makeResult(String inStr, HwVo hwVo) {
		ResultVo resultVo = new ResultVo();
		StringBuilder sb = new StringBuilder();
        int startPoint = 0;
        while( (startPoint + hwVo.getGrpNum()) <= inStr.length() ){
            sb.append(inStr.substring(startPoint, startPoint + hwVo.getGrpNum()));
            sb.append(",");
            startPoint += hwVo.getGrpNum();
        }
        if(sb.length() > 0) {
        	resultVo.setResult1(sb.substring(0, sb.length() - 1));
        }
        resultVo.setResult2(inStr.substring(startPoint));
		return resultVo;
	}

}
