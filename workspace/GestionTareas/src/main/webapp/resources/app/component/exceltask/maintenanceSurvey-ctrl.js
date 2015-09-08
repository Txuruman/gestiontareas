//Angular Maintenance Survey Controller start
app.controller('maintenancesurvey-ctrl', function ($scope, $http, CommonService,$modal, $log, $window) {


    $scope.getClosingReason = function(){
        $log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                $log.debug("Loaded closing reason list", data.pairList)
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $log.error("Error loading closing reason");
            });
    };


    $scope.getInstallationAndTask = function() {
            $scope.vm.appReady = false;

            $log.debug("Loading Maintenance Survey Task...");

            var getInstallationAndTaskRequest = {
                callingList: $scope.callingList,
                taskId: $scope.tareaId,
                params: mapParams
            };
            $log.debug("LO QUE ENVIAMOS", getInstallationAndTaskRequest);

            $http({
                method: 'PUT',
                url: 'maintenancesurveytask/getInstallationAndTask',
                data: getInstallationAndTaskRequest
            }).success(function (data, status, headers, config) {
                $log.debug("Loaded maintenance survey task:" ,data.tarea);
                $log.debug("Loaded installation data: ",data.installationData);
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
                $log.error("Error loading maintenance survey task and/or installation data");
            });
    };
    $scope.aplazar = function (delayDate, recallType) {
        $log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate:  delayDate,
                task: $scope.tarea
            };

            $log.info("Json of Request " + JSON.stringify(postponeRequest));

            $http({
                method: 'PUT',
                url: 'maintenancesurveytask/aplazar',
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
    }    
    
//Antiguo método descartar    
//    $scope.descartar = function(){
//        $log.debug("Discard Maintenance Survey task, task: ",$scope.tarea);
//        var discardMaintenanceSurveyTaskRequest = {
//            tarea:$scope.tarea,
//            prueba:'Hola'
//        };
//        $log.debug("Discard List Assistant Task, request: " ,discardMaintenanceSurveyTaskRequest);
//        $http({
//            method: 'PUT',
//            url: 'maintenancesurveytask/descartar',
//            data: discardMaintenanceSurveyTaskRequest
//        })
//            .success(function (data, status, headers, config) {
//                CommonService.processBaseResponse(data,status,headers,config);
//                $log.debug("Discarded maintenance survey task");
//            })
//            .error(function (data, status, headers, config) {
//                // called asynchronously if an error occurs
//                // or server returns response with an error status.
//                CommonService.processBaseResponse(data,status,headers,config);
//                $log.error("Error discarding maintenance survey task");
//            });
//    };


    $scope.finalizar = function(){
        $log.debug("Finalizar task: ",$scope.tarea);
        var finalizeRequest = {
            task:$scope.tarea
        };
        $log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'maintenancesurveytask/finalizar',
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