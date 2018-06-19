package com.ht.rule.common.api.vo;

import com.ht.rule.common.api.entity.VariableBind;

@SuppressWarnings("serial")
public class VariableBindVo extends VariableBind {
	/**
	 * 执行次数
	 */
	private int excuteTotal;
	
	/**
	 * 场景id
	 */
	private String senceId;
	
	/**
	 * 场景Code
	 */
	private String sceneIdentify;

	private String version; // 版本号

	private String testState;

	public String getTestState() {
		return testState;
	}

	public void setTestState(String testState) {
		this.testState = testState;
	}

	/**
	 * 取值方式 0-随机，1-倒序
	 */
	private String getWay ;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGetWay() {
		return getWay;
	}

	public void setGetWay(String getWay) {
		this.getWay = getWay;
	}

	public int getExcuteTotal() {
		return excuteTotal;
	}

	public void setExcuteTotal(int excuteTotal) {
		this.excuteTotal = excuteTotal;
	}

	public String getSenceId() {
		return senceId;
	}

	public void setSenceId(String senceId) {
		this.senceId = senceId;
	}

	public String getSceneIdentify() {
		return sceneIdentify;
	}

	public void setSceneIdentify(String sceneIdentify) {
		this.sceneIdentify = sceneIdentify;
	}
	
	
}
