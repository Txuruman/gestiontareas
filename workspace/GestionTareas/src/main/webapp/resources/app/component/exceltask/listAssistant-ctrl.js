//Controlador List Assistant - Start
app.controller('listAssistant-ctrl', function ($scope, $http, CommonService, $modal, $log, $timeout, $window) {

    $scope.getClosingReason = function(){
        $log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                $log.debug("Closing reason list loaded", data.pairList);
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $log.error("Error loading closing reason list");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };


    $scope.aplazar = function (delayDate, recallType) {
        $log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' NotificationTask ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate:  delayDate ,
                task: $scope.tarea
            };

            $log.info("JSON DE LO QUE SE MANDA   " + JSON.stringify(postponeRequest));

            $http({
                method: 'PUT',
                url: 'listassistanttask/aplazar',
                data: postponeRequest
            })
                .success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                })
                .error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    CommonService.processBaseResponse(data, status, headers, config);
                });
        }
    };

    /**
     * Método Descartar: Nos lleva a la página de buscar
     * Variable _contextPath inicializada en commonImports
     */
    $scope.descartar=function(){
    	$window.location.href= _contextPath + "/entry";
    };

    
//	Antiguo método descartar
//    $scope.descartar = function(){
//        $log.debug("Discard List Assistant task, task: ", $scope.tarea);
//        var discardListAssistantTaskRequest = {
//            tarea:$scope.tarea,
//            prueba:'Hola'
//        };
//        $log.debug("Discard List Assistant Task, request: " ,discardListAssistantTaskRequest);
//        $http({
//            method: 'PUT',
//            url: 'listassistanttask/descartar',
//            data: discardListAssistantTaskRequest
//        })
//            .success(function (data, status, headers, config) {
//                CommonService.processBaseResponse(data,status,headers,config);
//                $log.debug("Discarded list assistant task");
//            })
//            .error(function (data, status, headers, config) {
//                // called asynchronously if an error occurs
//                // or server returns response with an error status.
//                CommonService.processBaseResponse(data,status,headers,config);
//                $log.error("Error discarding list assistant task");
//            });
//    };


    $scope.finalizar = function(){
        $log.debug("Finalizar List Assistant task, task: ",$scope.tarea);
        var finalizeRequest = {
            task:$scope.tarea
        };
        $log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'listassistanttask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                $log.debug("Finalized task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $log.error("Error finalizing task");
            });
    };


    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        console.log("Loading List Assistant Task...");
        console.log("Params: "
        //+ " installationId: " + $scope.installationId
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'listassistanttask/getInstallationAndTask',
            params: {callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                $log.debug("Loaded list assistant task:",data.tarea);
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
                $log.debug("Error loading list assistant task:",data.tarea);
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