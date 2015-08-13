//Controlador List Assistant - Start
app.controller('listAssistant-ctrl', function ($scope, $http, CommonService, $modal, $log) {
    $scope.getTarea = function () {
        //$log.debug("Loading List Assistant Task...")
        //console.log("Params: "
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: '/listassistanttask/gettarea',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        })
            .success(function (data, status, headers, config) {
                //$log.debug("ListAssistantTask: " ,data);
                $scope.tarea = data.tarea;
                CommonService.processBaseResponse(data, status, headers, config);

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.error("Error loading list assistant task");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        $scope.getClosingReason();
    };

    $scope.getClosingReason = function(){
        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                //$log.debug("Closing reason list loaded", data.pairList);
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.error("Error loading closing reason list");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };


    $scope.aplazar = function(){
        //$log.debug("Postpone List Assistant task, task: ",$scope.tarea);
        var postponeListAssistantTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //console.log("Postpone List Assistant Task, request: ", postponeListAssistantTaskRequest);
        $http({
            method: 'PUT',
            url: 'listassistanttask/aplazar',
            data: postponeListAssistantTaskRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug("Postpone done");
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error executing postpone");
            });
    };

    $scope.descartar = function(){
        //$log.debug("Discard List Assistant task, task: ", $scope.tarea);
        var discardListAssistantTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Discard List Assistant Task, request: " ,discardListAssistantTaskRequest);
        $http({
            method: 'PUT',
            url: 'listassistanttask/descartar',
            data: discardListAssistantTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Discarded list assistant task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error discarding list assistant task");
            });
    };

    $scope.finalizar = function(){
        //$log.debug("Finalizar List Assistant task, task: ",$scope.tarea);
        var finalizeListAssistantTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Finalizar List Assistant Task, request: ",finalizeListAssistantTaskRequest);
        $http({
            method: 'PUT',
            url: 'listassistanttask/finalizar',
            data: finalizeListAssistantTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Finalized list assistant task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error finalizing list assistant task");
            });
    };


    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        //console.log("Loading List Assistant Task...");
        //console.log("Params: "
        //+ " installationId: " + $scope.installationId
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'listassistanttask/getInstallationAndTask',
            params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                //$log.debug("Loaded list assistant task:",data.tarea);
                $scope.tarea = data.tarea;
                $scope.installationData = data.installationData;
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.getClosingReason();
                $scope.vm.appReady=true;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Error loading list assistant task:",data.tarea);
                $scope.vm.appReady=true;
            });
        //console.log("List assistant task loaded...")
    }


    //Ventana Aplazar - Start
    //Abre la ventana, posibles tamaños '', 'sm', 'lg'
    $scope.openDelayModal = function (size) {
        var modalInstance = $modal.open({
            animation: false, //Indica si animamos el modal
            templateUrl: 'deplayModalContent.html', //HTML del modal
            controller: 'DelayModalInstanceCtrl',  //Referencia al controller especifico para el modal
            size: size,
            resolve: {
                //Creo que esto es para pasar parametros al controller interno
                // items: function () {
                //     return $scope.items;
                // }
            }
        });

        //Funciones para recivir el cierre ok y el cancel
        modalInstance.result.then(function (delayInfo) {
            //Boton Ok del modal
            $scope.aplazar(delayInfo.delayDate, delayInfo.recallType);
        }, function (param) {
            //Boton cancelar del Modal
        });
    };
    //Ventana Aplazar - End


});
//Controlador List Assistant - End