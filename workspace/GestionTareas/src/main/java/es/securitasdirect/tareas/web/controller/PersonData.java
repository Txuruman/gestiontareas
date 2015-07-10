package es.securitasdirect.tareas.web.controller;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PersonData {
	
	private Integer personId;
	private String personName;
	
	private List<PersonData> personDataList;

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public List<PersonData> getPersonDataList() {
		return personDataList;
	}

	public void setPersonDataList(List<PersonData> personDataList) {
		this.personDataList = personDataList;
	}

}
