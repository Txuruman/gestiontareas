app.controller('notificationtask', function ($scope, $http, CommonService, $modal, $log) {

    $scope.logTarea = function () {
        $log.debug("Tarea: " + $scope.tarea);
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


    $scope.getTarea = function () {
        $log.debug('Loading NotificationTask');
        $log.debug("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'notificationtask/gettask',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded NotificationTask: ' + JSON.stringify(data.tarea));
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        $log.debug("NotificationTask loaded...")
    };


    $scope.init1 = function(){
        $log.debug("Charging page, combo lists");
        $http({
            method: 'GET',
            url: 'commons/getNotificationTypeList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Notification Type List', data);
                $scope.tipoAvisoList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });

        $http({
            method: 'GET',
            url: 'commons/getClosingList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Closing Type List', data);
                $scope.closingList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });


        $http({
            method: 'GET',
            url: 'commons/getTypeReasonList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Type Reason List', data);
                $scope.motivoList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });

        $http({
            method: 'GET',
            url: 'commons/getClosingAditionalDataList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Closing Aditional Data List', data);
                $scope.datosAdicionalesList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.init = function(){
        CommonService.getNotificationTypeList();
        CommonService.getClosingList();
        CommonService.getTypeReasonList();
        CommonService.getClosingAditionalDataList();
    }

    $scope.getInstallationAndTask = function () {
        $log.debug('Loading NotificationTask');
        $log.debug("Params: "
            + " installationId: " + $scope.installationId
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'notificationtask/getInstallationAndTask',
            params: {
                installationId: $scope.installationId,
                ccUserId: $scope.ccUserId,
                callingList: $scope.callingList,
                tareaId: $scope.tareaId
            }
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded NotificationTask Data' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;
                $scope.installationData = data.installationData;
                //TODO OBTENER DATOS DE COMBOS - Desconocido origen

            })


            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        $log.debug("NotificationTask loaded...")
    };


    $scope.getClosing = function () {
        $log.debug('Notification task - getClosing');
        $http({method: 'GET', url: 'notificationtask/getClosing'}).
            success(function (data, status, headers, config) {
                $scope.closingList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };


    $scope.aplazar = function (delayDate, recallType) {
        $log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' NotificationTask ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeNotificationTaskRequest = {
                recallType: recallType,
                delayDate: delayDate,
                task: $scope.tarea

            };

            $log.info("JSON DE LO QUE SE MANDA   " + JSON.stringify(postponeNotificationTaskRequest));

            $http({
                method: 'PUT',
                url: 'notificationtask/aplazar',
                data: postponeNotificationTaskRequest
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

    $scope.modificar = function () {
        $log.debug('Modificar Tarea, tarea: ' + $scope.tarea);
        var modifyNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        $log.debug('Modificar Tarea, request ' + JSON.stringify(modifyNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/modificar',
            data: modifyNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Modificación de la tarea realizada, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error en la modificación de la tarea, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.atras = function () {
        $log.debug('Atras, tarea: ' + JSON.stringify($scope.tarea));
        var backNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        $log.debug('Atras, request: ' + JSON.stringify(backNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/atras',
            data: backNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Atras realizado, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error en la realización de atras, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.crearmantenimiento = function () {
        $log.debug('Crear mantenimiento, tarea: ' + JSON.stringify($scope.tarea));
        var createMaintenanceNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        $log.debug('Crear mantenimiento, request: ' + JSON.stringify(createMaintenanceNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/crearmantenimiento',
            data: createMaintenanceNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Creación de mantenimiento realizada, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error en la creación de mantenimiento, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.descartar = function () {
        $log.debug('Descartar ' + $scope.tarea);
        var createMaintenanceNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        $log.debug('Descartar, request: ' + JSON.stringify(createMaintenanceNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/descartar',
            data: createMaintenanceNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Realización de descarte, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error en la realización del descarte, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.finalizar = function () {
        $log.debug('Finalizar, tarea: ' + $scope.tarea);
        var finalizeNotificationTaskRequest = {
            tarea: $scope.tarea,
            prueba: 'Hola'
        };
        $log.debug('Finalizar, request: ' + JSON.stringify(finalizeNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Finalización realizada, response: ' + JSON.stringify(finalizeNotificationTaskRequest));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error al finalizar, request: ' + JSON.stringify(finalizeNotificationTaskRequest));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }
})
;