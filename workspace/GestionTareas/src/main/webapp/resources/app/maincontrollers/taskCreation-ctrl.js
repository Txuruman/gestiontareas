app.controller('taskCreation', function ($scope, $http, CommonService, $modal, $log) {
	//variabled para desabilitar los input de crear tarea si no se ha hecho la busqueda de la instalacion
	$scope.installationNotSearched=true;
	$scope.mostrarAvisos=false;
	$scope.getIntallation = function (event) {
        //$log.debug('Loading NotificationTask');
        if(event!=undefined){
        	event.preventDefault();
        }
        $http({
            method: 'GET',
            url: 'installation/query',
            params: {
            	installationId: $scope.tarea.numeroInstalacion
            }
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.installationData = data.installationData;
                if ($scope.installationData==null || $scope.installationData==undefined || $scope.installationData=="") {
                	$scope.installationNotSearched=true;
				}else{
					$scope.installationNotSearched=false;
					$scope.mostrarAvisos=true;
				}
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.installationNotSearched=true;
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        //$log.debug("NotificationTask loaded...");
    };
    $scope.createTask = function(){
        $scope.vm.appReady=false;
        //$log.debug("Creating task");
        //$scope.tarea.fechaCreacion=new Date();
        var createTaskRequest = {
        		task:$scope.tarea,
        	    installationData:$scope.installationData
        };
        //$log.debug("Creating task request: ",createTaskRequest);
        $http({
            method: 'PUT',
            url: 'createtask/createtask',
            data: createTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                var inst=$scope.tarea.numeroInstalacion;
                $scope.tarea={};
                $scope.tarea.numeroInstalacion=inst;
                $scope.vm.appReady=true;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            });
    };



    $scope.init = function(data, status, heathers, config) {
        $scope.vm.appReady=false;
        //$log.debug("Charging page, combo lists");
        $scope.getNotificationTypeList();
        //CommonService.getNotificationTypeList(data, status, heathers, config);
        //CommonService.getTypeReasonList(data, status, heathers, config);
        CommonService.loadInstallationData($scope.installationId,data, status, heathers, config);
        //$log.debug("Charging page, combo lists");
        $scope.vm.appReady=true;
    };

    /**
     * Hace el set del installation id en el campo del scope y lanza la búsqueda de la instalación.
     */
    $scope.searchInstallationData = function(){
        $scope.vm.appReady=false;
        $scope.installationId = $scope.fieldInstallationId;
        CommonService.loadInstallationData($scope.installationId,data, status, heathers, config);
        $scope.vm.appReady=true;
    };
    
    
    /**
     * Obtenemos los datos de los combos
     */
    $scope.getNotificationTypeList = function() {
        //$log.debug("Load Notification Type List");
        $http({
            method: 'GET',
            url: 'commons/getNotificationTypeList'
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Notification Type List', data);
                $scope.tipoAvisoList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
                
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 1
    $scope.getTypeReasonList1 = function(typeId,data, status, headers, config) {
        //$log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Type Reason List', data);
                $scope.motivoList1 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
                
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 2
    $scope.getTypeReasonList2 = function(typeId) {
        //$log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Type Reason List', data);
                $scope.motivoList2 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
                
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 3
    $scope.getTypeReasonList3 = function(typeId) {
        //$log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Type Reason List', data);
                $scope.motivoList3 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
                
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };
    /** Fin datos combos***/
    
    /** Cierre de interacción
     * 	Función externa CloseInteractionPushPreview
     */
    $scope.closeInteraction=function(){
    	 e = window.external.CloseInteractionPushPreview(connID);
         alert(JSON.stringify(e));
    }
});


