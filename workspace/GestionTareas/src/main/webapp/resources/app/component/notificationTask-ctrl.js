app.controller('notificationtask', function ($scope, $http, CommonService, $modal, $log) {

    $scope.logTarea = function () {
        console.log("Tarea: " + $scope.tarea);
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
        console.log('Loading NotificationTask');
        console.log("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'notificationtask/gettask',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        })
            .success(function (data, status, headers, config) {
                console.log('Loaded NotificationTask: ' + JSON.stringify(data.tarea));
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("NotificationTask loaded...")
    };


    $scope.getInstallationAndTask = function () {
        console.log('Loading NotificationTask');
        console.log("Params: "
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
                console.log('Loaded NotificationTask Data' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;
                $scope.installationData = data.installationData;
                //TODO OBTENER DATOS DE COMBOS - Desconocido origen
                $scope.tipoAvisoList = data.tipoAvisoList;
                $scope.motivoList = data.motivoAvisoList;
                $scope.closingList = data.closingList;
                $scope.datosAdicionalesList = data.closingListAditionalData;
            })


            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("NotificationTask loaded...")
    };


    $scope.getClosing = function () {
        console.log('Notification task - getClosing');
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
        console.log('Modificar Tarea, tarea: ' + $scope.tarea);
        var modifyNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        console.log('Modificar Tarea, request ' + JSON.stringify(modifyNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/modificar',
            data: modifyNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                console.log('Modificación de la tarea realizada, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                console.log('Error en la modificación de la tarea, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.atras = function () {
        console.log('Atras, tarea: ' + JSON.stringify($scope.tarea));
        var backNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        console.log('Atras, request: ' + JSON.stringify(backNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/atras',
            data: backNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                console.log('Atras realizado, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                console.log('Error en la realización de atras, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.crearmantenimiento = function () {
        console.log('Crear mantenimiento, tarea: ' + JSON.stringify($scope.tarea));
        var createMaintenanceNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        console.log('Crear mantenimiento, request: ' + JSON.stringify(createMaintenanceNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/crearmantenimiento',
            data: createMaintenanceNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                console.log('Creación de mantenimiento realizada, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                console.log('Error en la creación de mantenimiento, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.descartar = function () {
        console.log('Descartar ' + $scope.tarea);
        var createMaintenanceNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        console.log('Descartar, request: ' + JSON.stringify(createMaintenanceNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/descartar',
            data: createMaintenanceNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                console.log('Realización de descarte, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                console.log('Error en la realización del descarte, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.finalizar = function () {
        console.log('Finalizar, tarea: ' + $scope.tarea);
        var finalizeNotificationTaskRequest = {
            tarea: $scope.tarea,
            prueba: 'Hola'
        };
        console.log('Finalizar, request: ' + JSON.stringify(finalizeNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                console.log('Finalización realizada, response: ' + JSON.stringify(finalizeNotificationTaskRequest));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                console.log('Error al finalizar, request: ' + JSON.stringify(finalizeNotificationTaskRequest));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }
})
;