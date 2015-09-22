//Controlador List Assistant - Start
app.controller('listAssistant-ctrl', function ($scope, $http, CommonService, $modal, $log, $timeout, $window) {

    $scope.getClosingReason = function () {
        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'exceltaskcommon/getClosingReason'}).
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


    $scope.aplazar = function (delayDate, recallType) {
        //$log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' NotificationTask ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate: delayDate,
                task: $scope.tarea
            };

            $http({
                method: 'PUT',
                url: 'listassistanttask/aplazar',
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
//    	alert("entrando en descartartarea");
        if ($scope.installationData == null || $scope.installationData == undefined) {
            var discardRequest = {
                task: $scope.tarea,
                installation: $scope.installation
            }
            $http.put("listassistanttask/descartar", discardRequest).then(function (data, status, headers, config) {
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
//            	alert("Descartamos por javascript");
            	CommonService.excellDiscard();
            } else {
                CommonService.gotoSearch();
            }
        }
    }
    
    $scope.closeInteraction = function () {
        CommonService.closeInteraction({success: true});
    }

    $scope.finalizar = function () {
        //$log.debug("Finalizar List Assistant task, task: ",$scope.tarea);
        var finalizeRequest = {
            task: $scope.tarea
        };
        //$log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'listassistanttask/finalizar',
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


    $scope.getInstallationAndTask = function () {
        $scope.vm.appReady = false;

        //$log.debug("Loading List Assistant Task...");

        var getInstallationAndTaskRequest = {
            callingList: $scope.callingList,
            taskId: $scope.tareaId,
            params: mapParams
        };
        //$log.debug("LO QUE ENVIAMOS", getInstallationAndTaskRequest);

        $http({
            method: 'PUT',
            url: 'listassistanttask/getInstallationAndTask',
            data: getInstallationAndTaskRequest
        }).success(function (data, status, headers, config) {
            //$log.debug("Loaded list assistant task:", data.tarea);
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
                //$log.debug("Error loading list assistant task:", data.tarea);
                $scope.vm.appReady = true;
            });
        //$log.debug("List assistant task loaded...")
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
//Controlador List Assistant - End