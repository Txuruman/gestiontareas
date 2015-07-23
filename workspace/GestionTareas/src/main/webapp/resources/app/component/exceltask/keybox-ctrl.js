//Angular KeyboxTask controller
app.controller('keyboxtask-ctrl', function ($scope, $http, CommonService, $modal, $log) {
        $scope.getTarea = function () {
            console.log("Loading Keybox  Task...")
            console.log("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
            $http({method: 'GET',
                url: '/keyboxtask/gettarea',
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
            console.log("Loaded Keybox Task")

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

        console.log("Loading Keybox Task...");
        console.log("Params: "
        + " installationId: " + $scope.installationId
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'keyboxtask/getInstallationAndTask',
            params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                console.log("Loaded keybox task:" + JSON.stringify(data.tarea));
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
        console.log("Postpone Keybox task, task: " + JSON.stringify($scope.tarea));
        var postponeKeyboxTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Postpone Keybox Task, request: " + JSON.stringify(postponeKeyboxTaskRequest));
        $http({
            method: 'PUT',
            url: 'keyboxtask/aplazar',
            data: postponeKeyboxTaskRequest
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
        console.log("Discard Keybox task, task: " + JSON.stringify($scope.tarea));
        var discardKeyboxTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Discard Keybox Task, request: " + JSON.stringify(discardKeyboxTaskRequest));
        $http({
            method: 'PUT',
            url: 'keyboxtask/descartar',
            data: discardKeyboxTaskRequest
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
        console.log("Finalizar Keybox task, task: " + JSON.stringify($scope.tarea));
        var finalizeKeyboxTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Finalizar Keybox Task, request: " + JSON.stringify(finalizeKeyboxTaskRequest));
        $http({
            method: 'PUT',
            url: 'keyboxtask/finalizar',
            data: finalizeKeyboxTaskRequest
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