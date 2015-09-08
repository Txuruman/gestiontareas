package es.securitasdirect.tareas.web.controller.dto.request;

import java.util.Map;

public class GetInstallationAndTaskRequest {
	private String callingList;
	private String taskId;
	private Map<String,String> params;
	
	public GetInstallationAndTaskRequest(String callingList, String taskId, Map<String, String> params) {
		super();
		this.callingList = callingList;
		this.taskId = taskId;
		this.params = params;
	}

	public GetInstallationAndTaskRequest() {
		super();
	}

	public String getCallingList() {
		return callingList;
	}

	public void setCallingList(String callingList) {
		this.callingList = callingList;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	
}
