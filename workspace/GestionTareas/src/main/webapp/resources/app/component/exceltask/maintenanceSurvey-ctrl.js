//Angular Maintenance Survey Controller start
app.controller('maintenancesurvey-ctrl', function ($scope, $http, CommonService, $modal, $log, $window) {


    $scope.getClosingReason = function () {
        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                //$log.debug("Loaded closing reason list", data.pairList)
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //$log.error("Error loading closing reason");
            });
    };


    $scope.getInstallationAndTask = function () {
        $scope.vm.appReady = false;

        //$log.debug("Loading Maintenance Survey Task...");

        var getInstallationAndTaskRequest = {
            callingList: $scope.callingList,
            taskId: $scope.tareaId,
            params: mapParams
        };
        //$log.debug("LO QUE ENVIAMOS", getInstallationAndTaskRequest);

        $http({
            method: 'PUT',
            url: 'maintenancesurveytask/getInstallationAndTask',
            data: getInstallationAndTaskRequest
        }).success(function (data, status, headers, config) {
            //$log.debug("Loaded maintenance survey task:" ,data.tarea);
            //$log.debug("Loaded installation data: ",data.installationData);
            $scope.tarea = data.tarea;
            $scope.installationData = data.installationData;
            if(data.noInstallation==true){
            	$scope.noInstallation=data.noInstallation;
            	$scope.noInstallationMsg=data.noInstallationMsg;
            }
            CommonService.processBaseResponse(data, status, headers, config);
            $scope.getClosingReason();
            $scope.vm.appReady = true;
        }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.vm.appReady = true;
                //$log.error("Error loading maintenance survey task and/or installation data");
            });
    };
    $scope.aplazar = function (delayDate, recallType) {
        //$log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate: delayDate,
                task: $scope.tarea
            };

            //$log.info("Json of Request " + JSON.stringify(postponeRequest));

            $http({
                method: 'PUT',
                url: 'maintenancesurveytask/aplazar',
                data: postponeRequest
            })
                .success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    /** Si no venimos de la pantalla de buscar cerramos la interacción,
                     *  en caso contrario volvemos a la pantalla de buscar
                     */
                    if (data.success) {
                        if ($scope.fromSearch != "true") {
                            CommonService.closeInteraction(data);
                        } else {
                            CommonService.gotoSearch();
                        }
                    } else {
                        //Por errores no volvemos atras ni cerramos
                    }
                })
                .error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    CommonService.processBaseResponse(data, status, headers, config);
                });
        }
    };

    /**
     * Si no hay instalacion finalizamos la tarea
     */
    $scope.descartarTarea = function () {
        if ($scope.installationData == null || $scope.installationData == undefined) {
            var discardRequest = {
                task: $scope.tarea,
                installation: $scope.installation
            }
            $http.put("maintenancesurveytask/descartar", discardRequest).then(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                if ($scope.fromSearch != 'true') {
                	CommonService.excellDiscard();
                } else {
                    CommonService.gotoSearch();
                }
            }, function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
            })
        } else {
            if ($scope.fromSearch != 'true') {
            	CommonService.excellDiscard(tarea, installation);
            } else {
                CommonService.gotoSearch();
            }
        }
    }
    
    $scope.closeInteraction = function () {
        CommonService.closeInteraction({success: true});
    }
    $scope.finalizar = function () {
        //$log.debug("Finalizar task: ",$scope.tarea);
        var finalizeRequest = {
            task: $scope.tarea
        };
        //$log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'maintenancesurveytask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.debug("Finalized task");
                /** Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */
                if ($scope.fromSearch != "true") {
                    CommonService.closeInteraction(data);
                } else {
                    CommonService.gotoSearch();
                }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.error("Error finalizing task");
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
            	items: function () {
                    return false;
                }
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