//Angular Maintenance Survey Controller start
app.controller('maintenancesurvey-ctrl', function ($scope, $http, CommonService,$modal, $log) {

    $scope.getTarea = function () {
        console.log("Loading Maintenance Survey Task...")
        console.log("Params: "
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({method: 'GET',
            url: '/maintenancesurveytask/gettarea',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded Maintenance Survey Task");

       $scope.getClosingReason();
    };

    $scope.getClosingReason = function(){
        console.log("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data;
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded Excel Task Commons: Closing reason");
    };


    $scope.getInstallationAndTask = function() {
            $scope.vm.appReady = false;

            console.log("Loading Maintenance Survey Task...");
            console.log("Params: "
                + " installationId: " + $scope.installationId
                + " ccUserId: " + $scope.ccUserId
                + " callingList: " + $scope.callingList
                + " taskId: " + $scope.tareaId);
            $http({
                method: 'GET',
                url: 'maintenancesurveytask/getInstallationAndTask',
                params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
            }).
                success(function (data, status, headers, config) {
                    console.log("Loaded maintenance survey task:" + JSON.stringify(data.tarea));
                    $scope.tarea = data.tarea;
                    $scope.installationData = data.installationData;
                    CommonService.processBaseResponse(data, status, headers, config);
                    $scope.getClosingReason();
                    $scope.vm.appReady = true;
                }).
                error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    CommonService.processBaseResponse(data, status, headers, config);
                    $scope.vm.appReady = true;
                });
            console.log("Maintenance survey Task loaded...")
    };

    $scope.aplazar = function(){
        console.log("Postpone Maintenance Survey task, task: " + JSON.stringify($scope.tarea));
        var postponeMaintenanceSurveyTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Postpone Maintenance Survey Task, request: " + JSON.stringify(postponeMaintenanceSurveyTaskRequest));
        $http({
            method: 'PUT',
            url: 'maintenancesurveytask/aplazar',
            data: postponeMaintenanceSurveyTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    };

    $scope.descartar = function(){
        console.log("Discard Maintenance Survey task, task: " + JSON.stringify($scope.tarea));
        var discardMaintenanceSurveyTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Discard List Assistant Task, request: " + JSON.stringify(discardMaintenanceSurveyTaskRequest));
        $http({
            method: 'PUT',
            url: 'maintenancesurveytask/descartar',
            data: discardMaintenanceSurveyTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    };

    $scope.finalizar = function(){
        console.log("Finalizar Maintenance Survey task, task: " + JSON.stringify($scope.tarea));
        var finalizeMaintenanceSurveyTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Finalizar Maintenance Survey Task, request: " + JSON.stringify(finalizeMaintenanceSurveyTaskRequest));
        $http({
            method: 'PUT',
            url: 'maintenancesurveytask/finalizar',
            data: finalizeMaintenanceSurveyTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
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
//Angular Maintenance Survey Controller end