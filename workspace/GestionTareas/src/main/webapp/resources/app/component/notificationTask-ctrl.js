app.controller('notificationtask', function ($scope, $http, CommonService, $modal, $log, $window) {
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
                $scope.descartaraviso($scope.tarea);
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

    	CommonService.logger("Loading NotificationTask", "debug");
        var getInstallationAndTaskRequest = {
            callingList: $scope.callingList,
            taskId: $scope.tareaId,
            params: mapParams
        };
        CommonService.logger("LO QUE ENVIAMOS", "debug", getInstallationAndTaskRequest);

        $http({
            method: 'PUT',
            url: 'notificationtask/getInstallationAndTask',
            data: getInstallationAndTaskRequest
        }).success(function (data, status, headers, config) {
        	CommonService.logger('Loaded NotificationTask Data' + JSON.stringify(data),"debug");
            CommonService.processBaseResponse(data, status, headers, config);

            $scope.tarea = data.tarea;
            //clonamos el objeto tarea
            $scope.tareaOriginal = angular.copy($scope.tarea);

            $scope.installationData = data.installationData;
            $scope.getNotificationTypeList();
            $scope.getTypeReasonList1($scope.tarea.tipoAviso1);
            $scope.getTypeReasonList2($scope.tarea.tipoAviso2);
            $scope.getTypeReasonList3($scope.tarea.tipoAviso3);
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
        CommonService.logger("NotificationTask loaded...","debug");
    };

    //Consultar Combo de Cierre
    $scope.getClosingList = function (idType, reasonId, closing) {
    	CommonService.logger("Load Closing Type List for params: " + idType + ", " + reasonId,"debug");
        var closingTypeRequest = {
            idType: idType,
            reasonId: reasonId
        };
        CommonService.logger("Load Closing Type List Request: ", "debug", closingTypeRequest);
        $http({
            method: 'PUT',
            url: 'commons/getNotificationClosingList',
            data: closingTypeRequest
        })
            .success(function (data, status, headers, config) {
            	CommonService.logger('Loaded Closing Type List Response',"debug", data);
                $scope.closingList = data.pairList;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
            	CommonService.logger("Error loading Closing Type List","error");
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.getClosingAditionalDataList = function () {
    	CommonService.logger("Load Closing Aditional Data List ","debug");
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
    	CommonService.logger("Load Notification Type List","debug");
        $http({
            method: 'GET',
            url: 'commons/getNotificationTypeList'
        })
            .success(function (data, status, headers, config) {
            	CommonService.logger('Loaded Notification Type List', "debug", data);
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
    	CommonService.logger("Load Task Type Reason List","debug");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
            	CommonService.logger('Loaded Type Reason List', "debug",data);
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
    	CommonService.logger("Load Task Type Reason List","debug");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
            	CommonService.logger('Loaded Type Reason List', "debug",data);
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
    	CommonService.logger("Load Task Type Reason List","debug");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
            	CommonService.logger("Loaded Type Reason List","debug", data);
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
        
    	CommonService.logger('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea),"info");
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

            CommonService.logger("Json of Request " + JSON.stringify(postponeRequest),"info");

            $http({
                method: 'PUT',
                url: 'notificationtask/aplazar',
                data: postponeRequest
            })
                .success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    /** Si no venimos de la pantalla de buscar cerramos la interacción,
                     *  en caso contrario volvemos a la pantalla de buscar
                     */  
                    if($scope.fromSearch!==true){
                    	CommonService.closeInteraction();
                    }else{
                    	$scope.descartar();
                    }
                })
                .error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    CommonService.processBaseResponse(data, status, headers, config);
                });
        }
    };

    $scope.descartaraviso = function (tarea) {
    	CommonService.logger('Descartar Tarea, tarea: ' + $scope.tarea,"debug");
        var modifyNotificationTaskRequest = {
            task: tarea,
            installation: $scope.installationData
        };
        CommonService.logger('Descartar Tarea, request ' + JSON.stringify(modifyNotificationTaskRequest),"debug");
        $http({
            method: 'PUT',
            url: 'notificationtask/descartaraviso',
            data: modifyNotificationTaskRequest
        })
            .success(function (data, status, headers, config) {
            	CommonService.logger('Modificación de la tarea realizada, response: ' + JSON.stringify(data),"debug");
                CommonService.processBaseResponse(data, status, headers, config);
                /** Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */  
                if($scope.fromSearch!==true){
                	CommonService.closeInteraction();
                }else{
                	$scope.descartar();
                }
            })
            .error(function (data, status, headers, config) {
            	CommonService.logger('Error en la modificación de la tarea, response: ' + JSON.stringify(data),"debug");
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
            	CommonService.logger('Agente obtenido: ' + JSON.stringify(data),"debug");
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.openMaintenaceWindow(data.agent);
                $scope.closeAgent();
            })
            .error(function (data, status, headers, config) {
            	CommonService.logger('Error en la creación de mantenimiento, response: ' + JSON.stringify(data),"debug");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

    /** Abre la ventana de Mantenimiento de la aplicación externa */
    $scope.openMaintenaceWindow = function (agent) {
        //En IE8 hay que utilizar showdialog para poder ver los datos de vuelta, en Chome esta deprecado
        // prompt("aa","windowCreateMaintenanceFrame?InstallationNumber="+$scope.installationData.numeroInstalacion+"&PanelTypeId="+$scope.installationData.panel+"&TicketNumber="+$scope.tarea.idAviso+"&RequestedBy="+$scope.tarea.requeridoPor+"&Operator="+agent.agentIBS+"&ContactPerson="+$scope.tarea.personaContacto+"&ContactPhone="+$scope.tarea.telefonoAviso+"&Text="+$scope.tarea.observaciones+"&SessionToken="+agent.infopointSession+"&type="+$scope.tarea.tipoAviso1+"&motive="+$scope.tarea.motivo1);
        CreateMaintenanceAppRequest = {
            installationData: $scope.installationData,
            tareaAviso: $scope.tarea
        };
        $http.put('commons/getCreateMaintenanceApp', CreateMaintenanceAppRequest)
            .then(function (data, status, headers, config) {
                //CommonService.processBaseResponse(data.data, status, headers, config);
                
                if (data.app===undefined) {
                	CommonService.processBaseResponse(data.data, status, headers, config);
				}
                //alert("Lo que devuelve createMaintenance" + JSON.stringify(data));
                //TODO Aqui hay que controlar la excepción
                else{
	               var url = "windowCreateMaintenanceFrame" + data.app;
	
	
	                //Parametros para TOA
	                url += "?InstallationNumber=" + $scope.installationData.numeroInstalacion +
	                    "&PanelTypeId=" + $scope.installationData.panel +
	                    "&TicketNumber=" + $scope.tarea.idAviso +
	                    "&RequestedBy=" + $scope.tarea.requeridoPor +
	                    "&Operator=" + agent.agentIBS +
	                    "&ContactPerson=" + $scope.tarea.personaContacto +
	                    "&ContactPhone=" + $scope.tarea.telefono +
	                    "&Text=" + $scope.tarea.observaciones +
	                    "&SessionToken=" + agent.infopointSession +
	                    "&type=" + $scope.tarea.tipoAviso1 +
	                    "&motive=" + $scope.tarea.motivo1;
	                //Parametros para MMS
	                url += "&t=" + agent.infopointSession +
	                    "&NINSTALACION=" + $scope.installationData.numeroInstalacion +
	                    "&TIPOPANEL=" + $scope.installationData.panel +
	                    "&PAIS=" + agent.agentCountryJob +
	                    "&IDIOMA=" + agent.currentLanguage +
	                    "&FINANCIACION=0" +
	                    "&AVISO=" + $scope.tarea.idAviso +
	                    "&MATRICULA=" + agent.agentIBS +
	                    "&TIPOCIERRE=" + $scope.tarea.closing +
	                    "&NOTACIERRE=" + $scope.tarea.nota +
	                    "&STATUSDESTINO=3" +
	                    "&TIPODEUDA=PENDIENTE" +
	                    "&DATOSADIC=" + $scope.tarea.datosAdicionalesCierre +
	                    "&REPNAME=" + $scope.tarea.requeridoPor +
	                    "&NOMBRE=" + $scope.installationData.personaContacto +
	                    "&TELEFONO=" + $scope.installationData.telefono +
	                    "&CALLTYPEPROBLEM=" + $scope.tarea.tipoAviso1 + "|" + $scope.tarea.motivo1 + "|1|" +
	                    "&TEXTO=" + $scope.tarea.observaciones;
	                var resultado = window.showModalDialog(url, null, "center:yes; resizable:yes; dialogWidth:900px; dialogHeight:700px;");
	                alert(resultado);
	                //TODO BOrrar, es para probar un resultado concreto
	                //resultado='{"AppointmentNumber":"1234","Status":0,"Message":"correcto"}';
	
	                if (resultado) {
	                    $scope.finalizarDesdeMantenimiento(JSON.parse(resultado));
	                } else {
	                    $scope.finalizarDesdeMantenimiento(null);
	                }
                }
            }, function (data, status, headers, config) {
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
            	CommonService.logger(('Agente obtenido: ' + data,"debug");
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
            	CommonService.logger('Error en la creación de mantenimiento, response: ' + JSON.stringify(data),"debug");
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
        $window.location.href = _contextPath + "/search";
    };


    /**
     * Finalizar desde el botón de finalizar
     */
    $scope.finalizar = function () {
    	CommonService.logger("Finalizar task, task: ", $scope.tarea,"debug");
        //Comparamos la tarea con el originar y si ha habido cambios modificamos.
//    	if (!angular.equals($scope.tarea, $scope.tareaOriginal)) {
//			$scope.modificar();
//		}
        var finalizeRequest = {
            task: $scope.tarea,
            installation: $scope.installationData
        };
        CommonService.logger("Finalizar  Task, request: ", finalizeRequest,"debug");
        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                CommonService.logger("Finalized task","debug");
                /** Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */  
                if($scope.fromSearch!==true){
                	CommonService.closeInteraction();
                }else{
                	$scope.descartar();
                }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                CommonService.logger("Error finalizing task","error");
            });
    };

    //Mostramos los mensajes para rellenar los combos de finalizar
    $scope.muestraFinalizarRequired = function () {
        if ($scope.tarea.datosAdicionalesCierre == null) {
            $scope.closingADAlert = true;
        }
        if ($scope.tarea.closing == null) {
            $scope.closingAlert = true;
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
    	CommonService.logger(("Finalizar task: ","debug", $scope.tarea);
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
                CommonService.logger("Finalized task","debug");
                /** Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */  
                if($scope.fromSearch!==true){
                	CommonService.closeInteraction();
                }else{
                	$scope.descartar();
                }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                CommonService.logger("Error finalizing task","error");
            });
    };
});