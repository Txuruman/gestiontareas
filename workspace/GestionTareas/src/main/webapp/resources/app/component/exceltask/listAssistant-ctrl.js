//Controlador List Assistant - Start
app.controller('listAssistant-ctrl', function ($scope, $http, CommonService, $modal, $log) {
        $scope.getTarea = function () {
            console.log("Loading List Assistant Task...")
            console.log("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
            $http({
                method: 'GET',
                url: '/listassistanttask/gettarea',
                params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
            })
                .success(function (data, status, headers, config) {
                    console.log("ListAssistantTask: " + JSON.stringify(data));
                    $scope.tarea = data.tarea;
                    CommonService.processBaseResponse(data, status, headers, config);

                })
                .error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            console.log("List assistant task loaded...")

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
    }


    $scope.aplazar = function(){
        console.log("Postpone List Assistant task, task: " + JSON.stringify($scope.tarea));
        var postponeListAssistantTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Postpone List Assistant Task, request: " + JSON.stringify(postponeListAssistantTaskRequest));
        $http({
            method: 'PUT',
            url: 'listassistanttask/aplazar',
            data: postponeListAssistantTaskRequest
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
        console.log("Discard List Assistant task, task: " + JSON.stringify($scope.tarea));
        var discardListAssistantTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Discard List Assistant Task, request: " + JSON.stringify(discardListAssistantTaskRequest));
        $http({
            method: 'PUT',
            url: 'listassistanttask/descartar',
            data: discardListAssistantTaskRequest
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
        console.log("Finalizar List Assistant task, task: " + JSON.stringify($scope.tarea));
        var finalizeListAssistantTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Finalizar List Assistant Task, request: " + JSON.stringify(finalizeListAssistantTaskRequest));
        $http({
            method: 'PUT',
            url: 'listassistanttask/finalizar',
            data: finalizeListAssistantTaskRequest
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


    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        console.log("Loading List Assistant Task...");
        console.log("Params: "
        + " installationId: " + $scope.installationId
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'listassistanttask/getInstallationAndTask',
            params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                console.log("Loaded list assistant task:" + JSON.stringify(data.tarea));
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