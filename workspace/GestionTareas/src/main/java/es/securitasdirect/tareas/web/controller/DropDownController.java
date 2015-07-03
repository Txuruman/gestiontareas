package es.securitasdirect.tareas.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DropDownController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DropDownController.class);

	@RequestMapping(value = "/dropDown.web", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		LOGGER.info("Redirigiendo a dropdown");
		return "dropDown";
	}

	@RequestMapping(value = "/populatePersonDataFromServer.web", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	List<PersonData> populateActivePSwapBasket() {
        LOGGER.info("Cargando datos Bascket");
		PersonData personData = new PersonData();
		personData.setPersonId(10);
		personData.setPersonName("Java Honk");

		List<PersonData> personDatas = new ArrayList<PersonData>();
		personDatas.add(personData);

		personData = new PersonData();
		personData.setPersonId(11);
		personData.setPersonName("AngularJS combo box");
		personDatas.add(personData);

		personData = new PersonData();
		personData.setPersonId(11);
		personData.setPersonName("Select box list");
		personDatas.add(personData);

		return personDatas;
	}

}
