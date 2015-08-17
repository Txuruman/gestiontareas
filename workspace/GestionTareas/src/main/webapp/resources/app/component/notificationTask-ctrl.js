app.controller('notificationtask', function ($scope, $http, CommonService, $modal, $log) {
    var controllerVar = this;

    $scope.logTarea = function () {
        //$log.debug("Tarea: " + $scope.tarea);
    };


    //Ventana Aplazar - Start
    //Abre la ventana, posibles tamaños '', 'sm', 'lg'
    $scope.openDelayModal = function (size) {
        var modalInstance = $modal.open({
            animation: false, //Indica si animamos el modal
            templateUrl: 'deplayModalContent.html', //HTML del modal
            controllerVar: 'DelayModalInstanceCtrl',  //Referencia al controller especifico para el modal
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
        //$log.debug('Loading NotificationTask');
        $scope.vm.appReady=false;
        //$log.debug("Params: "
        //    + " ccUserId: " + $scope.ccUserId
        //    + " callingList: " + $scope.callingList
        //    + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'notificationtask/gettask',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded NotificationTask: ' + JSON.stringify(data.tarea));
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;
                $scope.vm.appReady=true;
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope.vm.appReady=true;
            });
        //$log.debug("NotificationTask loaded...")
    };

    $scope.init = function(){
        $scope.vm.appReady=false;
        this.getInstallationAndTask();
        $scope.vm.appReady=true;
    };

    $scope.getInstallationAndTask = function () {
        //$log.debug('Loading NotificationTask');
        //$log.debug("Params: "
        //    + " installationId: " + $scope.installationId
        //    + " ccUserId: " + $scope.ccUserId
        //    + " callingList: " + $scope.callingList
        //    + " taskId: " + $scope.tareaId);
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
                //$log.debug('Loaded NotificationTask Data' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data.tarea;
                $scope.installationData = data.installationData;
                controllerVar.getNotificationTypeList();
                controllerVar.getTypeReasonList1($scope.tarea.tipoAviso1);
                controllerVar.getTypeReasonList2($scope.tarea.tipoAviso2);
                controllerVar.getTypeReasonList3($scope.tarea.tipoAviso3);
                //$log.debug("Motivo lists: ",$scope.motivoList1,$scope.motivoList2,$scope.motivoList3);
                //$log.debug("SCOPE TAREA:", $scope.tarea);
                //$log.debug("Get closing list params: " + $scope.tarea.tipoAviso1 + ", " + $scope.tarea.motivo1);
                var groupId; //Definir de donde se saca groupId
                controllerVar.getClosingList($scope.tarea.tipoAviso1,
                    $scope.tarea.motivo1,
                    $scope.tarea.closing,
                    groupId
                );
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        //$log.debug("NotificationTask loaded...");
    };

    this.getClosingList = function(idType, reasonId, closing, groupId) {
        //$log.debug("Load Closing Type List for params: "+ idType + ", " + reasonId);
        var closingTypeRequest = {
            idType: idType,
            reasonId: reasonId,
            groupId: groupId
        };
        //$log.debug("Load Closing Type List Request: ", closingTypeRequest);
        $http({
            method: 'PUT',
            url: 'commons/getClosingList',
            data: closingTypeRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Closing Type List Response', data);
                $scope.closingList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
                controllerVar.getClosingAditionalDataList(closing);
            })
            .error(function (data, status, headers, config) {
                //$log.error("Error loading Closing Type List");
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getClosingAditionalDataList = function(closingTypeId) {
        //$log.debug("Load Closing Aditional Data List for param:", closingTypeId);
        var closingAditionalDataRequest = {
            closingTypeId: closingTypeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getClosingAditionalDataList',
            data: closingAditionalDataRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Closing Aditional Data List', data);
                $scope.datosAdicionalesList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getNotificationTypeList = function() {
        //$log.debug("Load Notification Type List");
        $http({
            method: 'GET',
            url: 'commons/getNotificationTypeList'
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Notification Type List', data);
                $scope.tipoAvisoList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getTypeReasonList1 = function(typeId,data, status, headers, config) {
        //$log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Type Reason List', data);
                $scope.motivoList1 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getTypeReasonList2 = function(typeId) {
        //$log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Type Reason List', data);
                $scope.motivoList2 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getTypeReasonList3 = function(typeId) {
        //$log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Loaded Type Reason List', data);
                $scope.motivoList3 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.aplazar = function (delayDate, recallType) {
        //$log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' NotificationTask ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeNotificationTaskRequest = {
                recallType: recallType,
                delayDate: delayDate,
                task: $scope.tarea

            };

            //$log.info("JSON DE LO QUE SE MANDA   " + JSON.stringify(postponeNotificationTaskRequest));

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
        //$log.debug('Modificar Tarea, tarea: ' + $scope.tarea);
        var modifyNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        //$log.debug('Modificar Tarea, request ' + JSON.stringify(modifyNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/modificar',
            data: modifyNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Modificación de la tarea realizada, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                //$log.debug('Error en la modificación de la tarea, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    }

    $scope.atras = function () {
        //$log.debug('Atras, tarea: ' + JSON.stringify($scope.tarea));
        var backNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        //$log.debug('Atras, request: ' + JSON.stringify(backNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/atras',
            data: backNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Atras realizado, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                //$log.debug('Error en la realización de atras, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

    $scope.crearmantenimiento = function () {
        //$log.debug('Crear mantenimiento, tarea: ' + JSON.stringify($scope.tarea));
        var createMaintenanceNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        //$log.debug('Crear mantenimiento, request: ' + JSON.stringify(createMaintenanceNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/crearmantenimiento',
            data: createMaintenanceNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Creación de mantenimiento realizada, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                //$log.debug('Error en la creación de mantenimiento, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

    $scope.descartar = function () {
        //$log.debug('Descartar ' + $scope.tarea);
        var createMaintenanceNotificationTaskRequest = {
            task: $scope.tarea,
            prueba: 'Hola'
        };
        //$log.debug('Descartar, request: ' + JSON.stringify(createMaintenanceNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/descartar',
            data: createMaintenanceNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Realización de descarte, response: ' + JSON.stringify(data));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                //$log.debug('Error en la realización del descarte, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

    $scope.finalizar = function () {
        //$log.debug('Finalizar, tarea: ' + $scope.tarea);
        var finalizeNotificationTaskRequest = {
            tarea: $scope.tarea,
            prueba: 'Hola'
        };
        //$log.debug('Finalizar, request: ' + JSON.stringify(finalizeNotificationTaskRequest));
        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
                //$log.debug('Finalización realizada, response: ' + JSON.stringify(finalizeNotificationTaskRequest));
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                //$log.debug('Error al finalizar, request: ' + JSON.stringify(finalizeNotificationTaskRequest));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };
});