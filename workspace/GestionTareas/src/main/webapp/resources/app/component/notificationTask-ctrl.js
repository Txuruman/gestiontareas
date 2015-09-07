app.controller('notificationtask', function ($scope, $http, CommonService, $modal, $log, $window) {

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

        //Funciones para recibir el cierre ok y el cancel
        modalInstance.result.then(function (delayInfo) {
            //Boton Ok del modal
            $scope.aplazar(delayInfo.delayDate, delayInfo.recallType);
        }, function (param) {
            //Boton cancelar del Modal
        });
    };
    //Ventana Aplazar - End
    $scope.openContentModal = function (size) {
        if (!angular.equals($scope.tarea, $scope.tareaOriginal)) {
            var modalInstance = $modal.open({
                animation: false, //Indica si animamos el modal
                templateUrl: 'ContentModal.html', //HTML del modal
                controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                size: size,
                resolve: {
                    //Creo que esto es para pasar parametros al controller interno
                    // items: function () {
                    //     return $scope.items;
                    // }
                }
            });

            //Funciones para recibir el cierre ok y el cancel
            modalInstance.result.then(function () {
                //Boton Ok del modal
                //Le mandamos la tarea sin los atributos de finalizar
//	        	var temp1=angular.copy($scope.tarea);
//	        	temp1.datosAdicionalesCierre=null;
//	        	temp1.closing=null;
                $scope.modificar($scope.tarea);
                //Si los atributos de finalizar no están nulos y hemos cambiado el tipo y el motivo 1 de la tarea --> Finalizamos y desmarcamos aviso de tarea
//	            if ($scope.tarea.datosAdicionalesCierre!=null && $scope.tarea.closing!=null && !angular.equals($scope.tarea.tipoAviso1, $scope.tareaOriginal.tipoAviso1) && !angular.equals($scope.tarea.motivo1, $scope.tareaOriginal.motivo1)) {
//					$scope.finalizar();
//					//TODO: Desmarcar aviso de tarea (otro WS)
//				}
                $scope.descartar();
            }, function (param) {
                //Boton cancelar del Modal
            });
        } else {
            $scope.descartar();
        }
    };

    $scope.init = function () {
        $scope.vm.appReady = false;
        $scope.getInstallationAndTask();
        $scope.vm.appReady = true;
    };

    $scope.getInstallationAndTask = function () {
        $log.debug('Loading NotificationTask');

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
                //clonamos el objeto tarea
                $scope.tareaOriginal = angular.copy($scope.tarea);

                $scope.installationData = data.installationData;
                $scope.getNotificationTypeList();
                $scope.getTypeReasonList1($scope.tarea.tipoAviso1);
                $scope.getTypeReasonList2($scope.tarea.tipoAviso2);
                $scope.getTypeReasonList3($scope.tarea.tipoAviso3);
                $log.debug("Motivo lists: ",$scope.motivoList1,$scope.motivoList2,$scope.motivoList3);
                $log.debug("SCOPE TAREA:", $scope.tarea);
                $log.debug("Get closing list params: " + $scope.tarea.tipoAviso1 + ", " + $scope.tarea.motivo1);
                $scope.getClosingList($scope.tarea.tipoAviso1, $scope.tarea.motivo1, $scope.tarea.closing);
                $scope.getClosingAditionalDataList();
                $scope.refeshDisabled = true;
                //Mensajes para los required de finalizar
                $scope.closingADAlert = false;
                $scope.closingAlert = false;
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        $log.debug("NotificationTask loaded...");
    };

    //Consultar Combo de Cierre
    $scope.getClosingList = function (idType, reasonId, closing) {
        $log.debug("Load Closing Type List for params: "+ idType + ", " + reasonId);
        var closingTypeRequest = {
            idType: idType,
            reasonId: reasonId
        };
        $log.debug("Load Closing Type List Request: ", closingTypeRequest);
        $http({
            method: 'PUT',
            url: 'commons/getNotificationClosingList',
            data: closingTypeRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Closing Type List Response', data);
                $scope.closingList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.error("Error loading Closing Type List");
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.getClosingAditionalDataList = function () {
        $log.debug("Load Closing Aditional Data List " );
        $http({
            method: 'GET',
            url: 'commons/getClosingAditionalDataList'
        })
            .success(function (data, status, headers, config) {
                $scope.datosAdicionalesList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.getNotificationTypeList = function () {
        $log.debug("Load Notification Type List");
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
    };

    //Consulta Motivo 1
    $scope.getTypeReasonList1 = function (typeId, data, status, headers, config) {
        $log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Type Reason List', data);
                $scope.motivoList1 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 2
    $scope.getTypeReasonList2 = function (typeId) {
        $log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Type Reason List', data);
                $scope.motivoList2 = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 3
    $scope.getTypeReasonList3 = function (typeId) {
        $log.debug("Load Task Type Reason List");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Type Reason List', data);
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
        $log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            //Creamos dos objetos temporales, nulleamos los atributos y comparamos
//        	var temp1=angular.copy($scope.tarea);
//        	var temp2=angular.copy($scope.tareaOriginal);
//        	temp1.datosAdicionalesCierre=null;
//        	temp2.datosAdicionalesCierre=null;
//        	temp1.closing=null;
//        	temp2.closing=null;
//        	//Si son diferentes modificamos, utilizamos el objeto temporal para no sobreescribir los atributos de finalizar
//        	if (!angular.equals(temp1, temp2)) {
//        		$scope.modificar(temp1);
//			}
            var postponeRequest = {
                recallType: recallType,
                delayDate: delayDate,
                task: $scope.tarea
            };

            $log.info("Json of Request " + JSON.stringify(postponeRequest));

            $http({
                method: 'PUT',
                url: 'notificationtask/aplazar',
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

    $scope.modificar = function (tarea) {
        $log.debug('Modificar Tarea, tarea: ' + $scope.tarea);
        var modifyNotificationTaskRequest = {
            task: tarea,
            installation:$scope.installationData
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
    };

    $scope.refrescar = function () {
        $scope.getInstallationAndTask();
    };

    /**
     * Crear mantenimiento:
     * 1. obtenemos el agente
     * 2. llamamos a la nueva ventana pasandole los parametros
     * 3. cerramos el agente
     */
    $scope.crearmantenimiento = function () {
        //Obtenemos el agente
        $http({
            method: 'GET',
            url: 'agent/prepareInfopointSession'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Agente obtenido: ' + JSON.stringify(data));
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.openMaintenaceWindow(data.agent);
                $scope.closeAgent();
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error en la creación de mantenimiento, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

    /** Abre la ventana de Mantenimiento de la aplicación externa */
    $scope.openMaintenaceWindow = function (agent) {
        //En IE8 hay que utilizar showdialog para poder ver los datos de vuelta, en Chome esta deprecado
        // prompt("aa","windowCreateMaintenanceFrame?InstallationNumber="+$scope.installationData.numeroInstalacion+"&PanelTypeId="+$scope.installationData.panel+"&TicketNumber="+$scope.tarea.idAviso+"&RequestedBy="+$scope.tarea.requeridoPor+"&Operator="+agent.agentIBS+"&ContactPerson="+$scope.tarea.personaContacto+"&ContactPhone="+$scope.tarea.telefonoAviso+"&Text="+$scope.tarea.observaciones+"&SessionToken="+agent.infopointSession+"&type="+$scope.tarea.tipoAviso1+"&motive="+$scope.tarea.motivo1);
    	CreateMaintenanceAppRequest={
    			installationData: $scope.installationData,
    		    tareaAviso: $scope.tarea
    	}
    	$http.put('commons/getCreateMaintenanceApp',CreateMaintenanceAppRequest)
    	.then(function(data, status, headers, config){
    		CommonService.processBaseResponse(data, status, headers, config);
    		var url = "windowCreateMaintenanceFrame"+data.data.app+"?InstallationNumber=" + $scope.installationData.numeroInstalacion + "&PanelTypeId=" + $scope.installationData.panel + "&TicketNumber=" + $scope.tarea.idAviso + "&RequestedBy=" + $scope.tarea.requeridoPor + "&Operator=" + agent.agentIBS + "&ContactPerson=" + $scope.tarea.personaContacto + "&ContactPhone=" + $scope.tarea.telefono + "&Text=" + $scope.tarea.observaciones + "&SessionToken=" + agent.infopointSession + "&type=" + $scope.tarea.tipoAviso1 + "&motive=" + $scope.tarea.motivo1;

            var resultado = window.showModalDialog(url, null, "center:yes; resizable:yes; dialogWidth:900px; dialogHeight:700px;");
            alert(resultado);
            //TODO BOrrar, es para probar un resultado concreto
            //resultado='{"AppointmentNumber":"1234","Status":0,"Message":"correcto"}';

            if (resultado) {
                $scope.finalizarDesdeMantenimiento(JSON.parse(resultado));
            } else {
                $scope.finalizarDesdeMantenimiento(null);
            }
    	},function(data, status, headers, config){
    		CommonService.processBaseResponse(data, status, headers, config);
    	});
        


    };

    /**
     * Cierra la session de infopoint tras ejecutar el mantenimiento
     */
    $scope.closeAgent = function () {
        $http({
            method: 'GET',
            url: 'agent/closeInfopointSession'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Agente obtenido: ' + data);
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                $log.debug('Error en la creación de mantenimiento, response: ' + JSON.stringify(data));
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

    /**
     * Método Descartar: Nos lleva a la página de buscar
     * Variable _contextPath inicializada en commonImports
     */
    $scope.descartar = function () {
        $window.location.href = _contextPath + "/entry";
    };


    /**
     * Finalizar desde el botón de finalizar
     */
    $scope.finalizar = function () {
        $log.debug("Finalizar task, task: ",$scope.tarea);
        //Comparamos la tarea con el originar y si ha habido cambios modificamos.
//    	if (!angular.equals($scope.tarea, $scope.tareaOriginal)) {
//			$scope.modificar();
//		}
        var finalizeRequest = {
            task: $scope.tarea,
            installation: $scope.installationData
        };
        $log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $log.debug("Finalized task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                $log.error("Error finalizing task");
            });
    };

    //Mostramos los mensajes para rellenar los combos de finalizar
    $scope.muestraFinalizarRequired = function () {
        if ($scope.tarea.datosAdicionalesCierre == null) {
            $scope.closingADAlert = true;
        }
        if ($scope.tarea.closing == null) {
            $scope.closingAlert=true;
        }
    };

    /**
     * Ejemplo de objetos JSON que se reciben:
     * {"AppointmentNumber":"","Status":2,"Message":"Error generico creando actividad en TOA"}
     *
     * @param resultado
     */
        //Finalizar mantenimiento al volver de la pantalla emergente
    $scope.finalizarDesdeMantenimiento = function (resultado) {
        $log.debug("Finalizar task: ",$scope.tarea);
        var finalizeRequest = {
            task: $scope.tarea,
            lastCalledPhone: $scope.lastCalledPhone,
            //  finalizedByCreateMaintenance
            finalizedByCreateMaintenance: true
        };

        if (resultado) { //Si hay resultado añadimos los datos
            // appointmentNumber
            finalizeRequest.appointmentNumber = resultado.AppointmentNumber;
            //  status
            finalizeRequest.status = resultado.Status;
            // message
            finalizeRequest.message = resultado.Message;
        }

        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $log.debug("Finalized task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                $log.error("Error finalizing task");
            });
    };

});