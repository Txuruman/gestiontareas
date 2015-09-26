app.controller('maintenancetask-ctrl', function ($scope, $http, CommonService, $log) {

    $scope.getDesplegableKey1 = function (){
        //$log.debug("Loading desplegable key1");
        $http({method: 'GET', url: 'maintenancetask/getDesplegableKey1'}).
            success(function (data, status, headers, config) {
                $scope.key1List = data.pairList;
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Desplegable key1 loaded");
                $scope.getDesplegableKey2();
            }).
            error(function (data, status, headers, config) {
                $scope.getDesplegableKey2();
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error loading desplegable key1");
            });
    };

    $scope.getDesplegableKey2 = function () {
        //$log.debug("Loading desplegable key2");
        if ($scope.tarea && $scope.tarea.key1) {
            // //$log.debug("Loading desplegable key2 for key1=" + $scope.tarea.key1);
            $http({method: 'GET', url: 'maintenancetask/getDesplegableKey2', params: {key1: $scope.tarea.key1}}).
                success(function (data, status, headers, config) {
                    $scope.key2List = data.pairList;
                    CommonService.processBaseResponse(data,status,headers,config);
                    //$log.debug("Desplegable key2 loaded");
                }).
                error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data,status,headers,config);
                    //$log.error("Error loading desplegable key1");
                });
        }
    };

    $scope.getCancelationType = function () {
        //$log.debug("Loading cancelation type list");
        $http({method: 'GET', url: 'maintenancetask/getCancelationType'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.cancelationTypeList = data.pairList;
                //$log.debug("Cancelation type list loaded");
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //$log.error("Error loading cancelation type list");
            });
    };


    $scope.init = function(){
        $scope.vm.appReady=false;
        this.getInstallationAndTask();
        $scope.vm.appReady=true;
    };


    $scope.getInstallationAndTask = function(){

        //$log.debug("Loading MaintenanceTask...");

        var getInstallationAndTaskRequest = {
            callingList: $scope.callingList,
            taskId: $scope.tareaId,
            params: mapParams
        };
        //$log.debug("LO QUE ENVIAMOS", getInstallationAndTaskRequest);

        $http({
            method: 'PUT',
            url: 'maintenancetask/getInstallationAndTask',
            data: getInstallationAndTaskRequest
        }).success(function (data, status, headers, config) {
            //$log.debug("Loaded maintenance task:",data.tarea);
            //$log.debug("Loaded installation data:", data.installationData);
            $scope.tarea = data.tarea;
            $scope.installationData = data.installationData;
            if(data.noInstallation==true){
            	$scope.noInstallation=data.noInstallation;
            	$scope.noInstallationMsg=data.noInstallationMsg;
            }
            CommonService.processBaseResponse(data,status,headers,config);
            $scope.getDesplegableKey1();
            $scope.getCancelationType();
        }).
        error(function (data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            CommonService.processBaseResponse(data,status,headers,config);
            //$log.error("Error loading maintenance task");
        });
    };



    $scope.interactionCreateMaintenance = function(){
        //$log.debug("Creating Maintenance task, task:", $scope.tarea);
        var maintenanceTaskCreateRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Creating Maintenance task, request:" + maintenanceTaskCreateRequest);
        $http({
            method: 'PUT',
            url: 'maintenancetask/create',
            data: maintenanceTaskCreateRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Maintenance task created");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error creating maintenance task");
            });
    };



    $scope.finalizar = function(){
        //$log.debug("Finalizar task: ",$scope.tarea);
        var finalizeRequest = {
            task:$scope.tarea,
            lastCalledPhone:$scope.lastCalledPhone
        };
        //$log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'maintenancetask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Finalized task");
                /**
                 * Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */
                if(data.result!=undefined && data.result.openMaintenanceWindow!=undefined && data.result.openMaintenanceWindow==true){
                	$scope.agent=data.result.agent;
                	var resultado = window.showModalDialog(data.result.openMaintenanceWindowURL, null, "center:yes; resizable:yes; dialogWidth:900px; dialogHeight:700px;");
                	alert($scope.agent +" - "+data.result.openMaintenanceWindowURL);

                    //Tras recibir el resultado de la otra ventana podemos cerrar la session de infopoint
                    $scope.closeInfopointSession();
                }
                //if (data.success) {
                if ($scope.fromSearch != "true") {
                    CommonService.closeInteraction(data);
                } else {
                	CommonService.gotoSearch();
                }
                //} else {
                    //Por errores no volvemos atras ni cerramos
               // }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error finalizing task");
            });
    };

    $scope.asignarTextoCancelacion=function(){
    	for (var i = 0; i < $scope.cancelationTypeList.length; i++) {
    		if($scope.cancelationTypeList[i].id==$scope.tarea.tipoCancelacion){
    			$scope.tarea.textoCancelacion=$scope.cancelationTypeList[i].description;
    		}
		} 
    	
    };
    
    /**
     * Cierra la session de infopoint tras ejecutar el mantenimiento
     */
    $scope.closeInfopointSession = function () {
        $http({
            method: 'GET',
            url: 'agent/closeInfopointSession'
        })
            .success(function (data, status, headers, config) {
                CommonService.logger('Agente obtenido: ' + data, "debug");
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.logger('Error en la creación de mantenimiento, response: ' + JSON.stringify(data), "debug");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };
    
    
    /** Llamada a la función externa para realizar llamadas
     * 	DoCall
     */
    $scope.doCall=function(phone){
    	var newCallConnid = null;
    	
    	function myDoCallHandler(eventName, connid) {
	   			newCallConnid = connid;
	   			alert("alberto!:"+eventName+": "+newCallConnid+": ");
	   	}
	
	   	function provideMockupObject() {
	   			var o = {idProspect:'12345', task:'TAKE_RDV', comments:'These are the comments of my prospect', otherelement:'tatata'};
	   			params = JSON.stringify(o);
	   			return JSON.stringify(o);
	   	}
	   	
	   	phone = "0999"+phone;
		//alert(phone);
		e = window.external.DoCall(phone, 'myDoCallHandler', provideMockupObject());
		//alert(JSON.stringify(e));

        var maintenanceTaskCallRequest={
            task: $scope.tarea
        };
        $http.put('maintenancetask/call', maintenanceTaskCallRequest)
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $scope.error = true;
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }
    $scope.compruebaTfno=function(){
    	if($scope.otroTelefono!=undefined && $scope.otroTelefono!="" && $scope.otroTelefono!=null){
//    		$scope.errorTel=false;
    		$scope.lastCalledPhone=angular.copy($scope.otroTelefono);
    		$scope.doCall($scope.otroTelefono);
    	}else{
//    		$scope.errorTel=true;
    	}
    }
    
});