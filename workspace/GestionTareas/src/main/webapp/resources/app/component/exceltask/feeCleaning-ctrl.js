//Fee cleaning ANGULARJS script START
app.controller('feecleaningtask-ctrl', function ($scope, $http, CommonService, $modal, $log) {

    $scope.getTarea = function () {
        console.log("Loading FeeCleaningTask...")
        console.log("Params: "
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({method: 'GET',
            url: '/feecleaningtask/gettarea',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded FeeCleaningTask")

        console.log("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                $scope.closingReasonList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded Excel Task Commons: Closing reason");
    };


    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        console.log("Loading Fee Cleaning Task...");
        console.log("Params: "
        + " installationId: " + $scope.installationId
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'feecleaningtask/getInstallationAndTask',
            params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                console.log("Loaded fee cleaning task:" + JSON.stringify(data.tarea));
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
            });
        console.log("List assistant task loaded...")
    }

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
    }

    $scope.aplazar = function(){
        console.log("Postpone Fee cleaning task, task: " + JSON.stringify($scope.tarea));
        var postponeFeeCleaningTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Postpone Fee Cleaning Task, request: " + JSON.stringify(postponeFeeCleaningTaskRequest));
        $http({
            method: 'PUT',
            url: 'feecleaningtask/aplazar',
            data: postponeFeeCleaningTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }

    $scope.descartar = function(){
        console.log("Discard Fee Cleaning task, task: " + JSON.stringify($scope.tarea));
        var discardFeeCleaningTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Discard Fee Clenaning Task, request: " + JSON.stringify(discardFeeCleaningTaskRequest));
        $http({
            method: 'PUT',
            url: 'feecleaningtask/descartar',
            data: discardFeeCleaningTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }

    $scope.finalizar = function(){
        console.log("Finalizar Fee cleaning task, task: " + JSON.stringify($scope.tarea));
        var finalizeFeeCleaningTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Finalizar Fee Cleaning Task, request: " + JSON.stringify(finalizeFeeCleaningTaskRequest));
        $http({
            method: 'PUT',
            url: 'feecleaningtask/finalizar',
            data: finalizeFeeCleaningTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
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
//Fee cleaning ANGULARJS script END