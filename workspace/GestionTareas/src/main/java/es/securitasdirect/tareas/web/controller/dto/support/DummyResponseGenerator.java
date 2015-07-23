package es.securitasdirect.tareas.web.controller.dto.support;

import es.securitasdirect.tareas.web.controller.util.MessageUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by Roberto on 14/07/2015.
 */
@Named(value = "dummyResponseGenerator")
@Singleton
public class DummyResponseGenerator {

    @Inject
    protected MessageUtil messageUtil;

    /** TAREA MANTENIMIENTO**/

    public BaseResponse dummyCreateMaintenanceTaskSuccess() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.warning("Dummy");
        baseResponse.success(messageUtil.getProperty("tareamantenimiento.create.success"));
        return baseResponse;
    }

    public BaseResponse dummyFinalizeSuccess() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.warning("Dummy");
        baseResponse.success(messageUtil.getProperty("tareamantenimiento.finalize.success"));
        return baseResponse;
    }


    public BaseResponse dummyCustomSuccess(String customName){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.warning("Dummy");
        baseResponse.success(messageUtil.getProperty(customName));
        return baseResponse;
    }

}
