//Angular KeyboxTask controller
app.controller('keyboxtask-ctrl', function ($scope, $http, CommonService, $modal, $log) {
    $scope.getTarea = function () {
        //$log.debug("Loading Keybox  Task...");
        //$log.debug("Params: "
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({method: 'GET',
            url: '/keyboxtask/gettarea',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;
                //$log.debug("Keybox task loaded");
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //$log.error("Error loading keybox task");
            });

        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                $scope.closingReasonList = data.pairList;
                //$log.debug("Closing reason list loaded: ", data.pairList);
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //$log.error("Error loading closing reason list");
            });
    };


    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        //$log.debug("Loading Keybox Task...");
        //$log.debug("Params: "
        //+ " installationId: " + $scope.installationId
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'keyboxtask/getInstallationAndTask',
            params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                //console.log("Loaded keybox task:",data.tarea);
                //console.log("Loaded installation data:",data.installationData);
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
                $scope.vm.appReady=true;
                //$log.error("Error loading keybox task and installation data");
            });
    };

    $scope.getClosingReason = function(){
        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                //$log.debug("Closing reason list loaded");
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.error("Error loeading closing reason list");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.aplazar = function(){
        //$log.debug("Postpone Keybox task, task: ", $scope.tarea);
        var postponeKeyboxTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Postpone Keybox Task, request: ",postponeKeyboxTaskRequest);
        $http({
            method: 'PUT',
            url: 'keyboxtask/aplazar',
            data: postponeKeyboxTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Postponed keybox task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error postponing keybox task");
            });
    };

    $scope.descartar = function(){
        //$log.debug("Discard Keybox task, task: " ,$scope.tarea);
        var discardKeyboxTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Discard Keybox Task, request: ",discardKeyboxTaskRequest);
        $http({
            method: 'PUT',
            url: 'keyboxtask/descartar',
            data: discardKeyboxTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Discarded keybox task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error discarding keybox task");
            });
    };

    $scope.finalizar = function(){
        //$log.debug("Finalizar Keybox task, task: ",$scope.tarea);
        var finalizeKeyboxTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Finalizar Keybox Task, request: " ,finalizeKeyboxTaskRequest);
        $http({
            method: 'PUT',
            url: 'keyboxtask/finalizar',
            data: finalizeKeyboxTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Finalized keybox task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error finalizing keybox task");
            });
    };

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