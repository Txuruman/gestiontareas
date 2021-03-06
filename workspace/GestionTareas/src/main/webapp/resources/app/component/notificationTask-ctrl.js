app.controller('notificationtask', function ($scope, $http, CommonService, $modal, $log, $window) {

    $scope.init = function () {
        $scope.vm.appReady = false;
        $scope.getInstallationAndTask();
        $scope.vm.appReady = true;
    };


    /**** MODALES **/
        //Ventana Aplazar - Start
        //Abre la ventana, posibles tamaños '', 'sm', 'lg'
    $scope.openDelayModal = function (size) {
    	$scope.mostrarErrorAplazarC=false;
    	$scope.mostrarErrorAplazarT=false;
    	var modalInstance = $modal.open({
            animation: false, //Indica si animamos el modal
            templateUrl: 'deplayModalContent.html', //HTML del modal
            controller: 'DelayModalInstanceCtrl',  //Referencia al controller especifico para el modal
            size: size,
            resolve: {
                //Creo que esto es para pasar parametros al controller interno
                 items: function () {
                     return true;
                 }
            }
        });

        //Funciones para recibir el cierre ok y el cancel
        modalInstance.result.then(function (delayInfo) {
            //Boton Ok del modal
            $scope.aplazar(delayInfo.delayDate, delayInfo.recallType, delayInfo.motive);
        }, function (param) {
            //Boton cancelar del Modal
        });
    };
    //Ventana Aplazar - End


    /****
     * Modales de descartar
     * Función que se lanza al pulsar el BOTÓN DESCARTAR
     **/
    $scope.openContentModal = function (size) {
        //alert("Vamos a llamar a isCallDone");
        $scope.iscalldone = window.external.IsCallDone(mapParams.bp_interactionId);
        //TODO PARA DESARROLLO, QUITAR
//        $scope.iscalldone = true;
        //alert("Tras llamar a isCallDone el resultado ha sido " + $scope.iscalldone);

        /*
         * Errores
         * Si no hay tarea o hay error al finalizar aviso , vuelve para atrás,
         * Si no hay instalación y si hay tarea, finalizamos la tarea
         */
        if ($scope.tarea == undefined || $scope.tarea == null || $scope.error == true) {
            if ($scope.fromSearch != "true") {
                CommonService.closeInteraction({success: true});
            } else {
                CommonService.gotoSearch($scope.desktopDepartment);
            }
        } else if ($scope.installationData == null || $scope.installationData == undefined) {
            //Si no hay instalación llamamos a descartar aviso directamente porque entendemos que es un error
        	if ($scope.tarea.personaContacto == "" || $scope.tarea.personaContacto == null || $scope.tarea.telefonoAviso == "" || $scope.tarea.telefonoAviso == null || $scope.tarea.tipoAviso1 == "" || $scope.tarea.tipoAviso1 == null || $scope.tarea.motivo1 == "" || $scope.tarea.motivo1 == null || $scope.compruebaMotivos()) {
                var modalInstance = $modal.open({
                    animation: false, //Indica si animamos el modal
                    templateUrl: 'alertModal.html', //HTML del modal
                    controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                    size: size,
                    resolve: {
                        //Creo que esto es para pasar parametros al controller interno
                        // items: function () {
                        //     return $scope.items;
                        // }
                    }
                });

                //	            //Funciones para recibir el cierre ok y el cancel
                modalInstance.result.then(function () {
                    $scope.verErrores = true;
                }, function (param) {
                    //Boton cancelar del Modal
                });
            }
            else {
        		$scope.descartaraviso();
            }
        } else {
            if (!angular.equals($scope.tarea, $scope.tareaOriginal)) {
                /**Si hay cambios los siguientes campos son obligatorios:
                 * Persona, teléfono, tipo 1 y motivo 1.
                 * */
                if ($scope.tarea.personaContacto == "" || $scope.tarea.personaContacto == null || $scope.tarea.telefonoAviso == "" || $scope.tarea.telefonoAviso == null || $scope.tarea.tipoAviso1 == "" || $scope.tarea.tipoAviso1 == null || $scope.tarea.motivo1 == "" || $scope.tarea.motivo1 == null || $scope.compruebaMotivos()) {
                    var modalInstance = $modal.open({
                        animation: false, //Indica si animamos el modal
                        templateUrl: 'alertModal.html', //HTML del modal
                        controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                        size: size,
                        resolve: {
                            //Creo que esto es para pasar parametros al controller interno
                            // items: function () {
                            //     return $scope.items;
                            // }
                        }
                    });

                    //	            //Funciones para recibir el cierre ok y el cancel
                    modalInstance.result.then(function () {
                        $scope.verErrores = true;
                    }, function (param) {
                        //Boton cancelar del Modal
                    });
                }
                else {
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
                        $scope.descartaraviso();
                    }, function (param) {
                        $scope.descartaravisosinsalvartarea();
                    });
                }
            }
            else {
                /*
                 *  Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */
                if ($scope.fromSearch != "true") {
                	if ($scope.tarea.personaContacto == "" || $scope.tarea.personaContacto == null || $scope.tarea.telefonoAviso == "" || $scope.tarea.telefonoAviso == null || $scope.tarea.tipoAviso1 == "" || $scope.tarea.tipoAviso1 == null || $scope.tarea.motivo1 == "" || $scope.tarea.motivo1 == null || $scope.compruebaMotivos()) {
                        var modalInstance = $modal.open({
                            animation: false, //Indica si animamos el modal
                            templateUrl: 'alertModal.html', //HTML del modal
                            controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                            size: size,
                            resolve: {
                                //Creo que esto es para pasar parametros al controller interno
                                // items: function () {
                                //     return $scope.items;
                                // }
                            }
                        });

                        //	            //Funciones para recibir el cierre ok y el cancel
                        modalInstance.result.then(function () {
                            $scope.verErrores = true;
                        }, function (param) {
                            //Boton cancelar del Modal
                        });
                    }
                    else {
                		$scope.descartaraviso();
                    }
                } else {
                    CommonService.gotoSearch($scope.desktopDepartment);
                }
            }
        }
    };


    /**** APLAZAR **/
    $scope.aplazar = function (delayDate, recallType, motive) {

        CommonService.logger('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea), "info");
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate: delayDate,
                motive:motive,
                task: $scope.tarea,
                fromSearch: $scope.fromSearch
            };

            CommonService.logger("Json of Request " + JSON.stringify(postponeRequest), "info");

            $http({
                method: 'PUT',
                url: 'notificationtask/aplazar',
                data: postponeRequest
            })
                .success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    
                    /**
                     * Si venimos de la pantalla de buscar y la tarea está en retrieved,
                     * no permitimos hacer nada y volvemos al buscador
                     */
                    if (data.tareaRetrieved){
                    	var modalInstance = $modal.open({
                            animation: false, //Indica si animamos el modal
                            templateUrl: 'alertModalTareaRetrieved.html', //HTML del modal
                            controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                            size: null,
                            resolve: {
                                //Creo que esto es para pasar parametros al controller interno
                                // items: function () {
                                //     return $scope.items;
                                // }
                            }
                        });

                        //Funciones para recibir el cierre ok y el cancel
                        modalInstance.result.then(function () {
                        	CommonService.gotoSearch($scope.desktopDepartment);
                        }, function (param) {
                            //Boton cancelar del Modal
                        });
                        
                    	
                    }
                    else{
                    
	                    /** Si no venimos de la pantalla de buscar cerramos la interacción,
	                     *  en caso contrario volvemos a la pantalla de buscar
	                     */
	                    if ($scope.fromSearch != "true") {
	                        CommonService.closeInteraction(data);
	                    } else {
	                        CommonService.gotoSearch($scope.desktopDepartment);
	                    }
	                    
                    }
                })
                .error(function (data, status, headers, config) {
                    $scope.error = true;
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    CommonService.processBaseResponse(data, status, headers, config);
                });
        }
    };


    /** Llamada al Servidor a la funcion descartaraviso */
    $scope.descartaraviso = function () {
        $scope.callDescartarAvisoServer(false);
    };

    /** Llamada al Servidor a la funcion descartaraviso */
    $scope.descartaravisosinsalvartarea = function () {
        $scope.callDescartarAvisoServer(true);
    };


    /**
     * Unica llamada REST al servidor para Descartar Aviso
     */
    $scope.callDescartarAvisoServer = function (sinSalvarTarea) {
//        CommonService.logger('Descartar Tarea, tarea: ' + $scope.tarea, "debug");
        var requestdata = {
            task: $scope.tarea,
            installation: $scope.installationData,
            callDone: $scope.iscalldone,
            withInteaction:!$scope.fromSearch,
            fromSearch: $scope.fromSearch
        };
        
        //CommonService.logger('Descartar Tarea, request ' + JSON.stringify(requestdata), "debug");

        var urlfinal = 'notificationtask/descartaraviso';
        if (sinSalvarTarea) {
            urlfinal = 'notificationtask/descartaravisosinsalvartarea';
        }
        $http({
            method: 'PUT',
            url: urlfinal,
            data: requestdata
        })
            .success(function (data, status, headers, config) {
                CommonService.logger('Modificación de la tarea realizada, response: ' + JSON.stringify(data), "debug");
                CommonService.processBaseResponse(data, status, headers, config);
                
                /**
                 * Si venimos de la pantalla de buscar y la tarea está en retrieved,
                 * no permitimos hacer nada y volvemos al buscador
                 */
                if (data.tareaRetrieved){
                	var modalInstance = $modal.open({
                        animation: false, //Indica si animamos el modal
                        templateUrl: 'alertModalTareaRetrieved.html', //HTML del modal
                        controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                        size: null,
                        resolve: {
                            //Creo que esto es para pasar parametros al controller interno
                            // items: function () {
                            //     return $scope.items;
                            // }
                        }
                    });

                    //Funciones para recibir el cierre ok y el cancel
                    modalInstance.result.then(function () {
                    	CommonService.gotoSearch($scope.desktopDepartment);
                    }, function (param) {
                        //Boton cancelar del Modal
                    });
                    
                	
                }
                else{
                
	                /** Si no venimos de la pantalla de buscar cerramos la interacción,
	                 *  en caso contrario volvemos a la pantalla de buscar
	                 */
	                    //variable error para poder volver atras en el descartar
	                $scope.error = !data.success;
	//                alert(data.result.ommitedCallToDiscard);
	                if(data.result.ommitedCallToDiscard){
	                    //alert("RejectCloseInteractionPushPreview ", data.result.ommitedCallToDiscard);
	                	e = window.external.RejectCloseInteractionPushPreview(mapParams.bp_connid);
	                    //alert(resultado);
	                }else{
		                if ($scope.fromSearch != "true") {
		                    CommonService.closeInteraction(data);
		                } else {
		                    CommonService.gotoSearch($scope.desktopDepartment);
		                }
	                }
                }
            })
            .error(function (data, status, headers, config) {
                CommonService.logger('Error en la modificación de la tarea, response: ' + JSON.stringify(data), "debug");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.error = true;
            });
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
                CommonService.logger('Agente obtenido: ' + JSON.stringify(data), "debug");
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.openMaintenaceWindow(data.agent);
            })
            .error(function (data, status, headers, config) {
                $scope.error = true;
                CommonService.logger('Error en la creación de mantenimiento, response: ' + JSON.stringify(data), "debug");
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
            tareaAviso: $scope.tarea,
            fromSearch: $scope.fromSearch
        };
        $http.put('commons/getCreateMaintenanceApp', CreateMaintenanceAppRequest)
            .success(function (data, status, headers, config) {
                //CommonService.processBaseResponse(data.data, status, headers, config);

                if (data.app === undefined) { //Si no sabemos a que aplicacion ir no abrimos nada
                    CommonService.processBaseResponse(data, status, headers, config);
                } else {
                    var url = "windowCreateMaintenanceFrame" + data.app;
                    var req=$scope.tarea.requeridoPor.substring(0, $scope.tarea.requeridoPor.indexOf(" "));
                    //Parametros para TOA
                    url = url + "?InstallationNumber=" + $scope.installationData.numeroInstalacion +
                        "&PanelTypeId=" + $scope.installationData.panel +
                        "&TicketNumber=" + $scope.tarea.idAviso +
                        "&RequestedBy=" + req +
                        "&Operator=" + agent.agentIBS +
                        "&ContactPerson=" + $scope.tarea.personaContacto +
                        "&ContactPhone=" + $scope.tarea.telefono +
                        "&Text=" + $scope.tarea.observaciones +
                        "&SessionToken=" + agent.infopointSession +
                        "&type=" + $scope.tarea.tipoAviso1 +
                        "&motive=" + $scope.tarea.motivo1;
                    //Parametros para MMS
                    //url = "&t=" + agent.infopointSession +
                    //    "&NINSTALACION=" + $scope.installationData.numeroInstalacion +
                    //    "&TIPOPANEL=" + $scope.installationData.panel +
                    //    "&PAIS=" + agent.agentCountryJob +
                    //    "&IDIOMA=" + agent.currentLanguage +
                    //    "&FINANCIACION=0" +
                    //    "&AVISO=" + $scope.tarea.idAviso +
                    //    "&MATRICULA=" + agent.agentIBS +
                    //    "&TIPOCIERRE=" + $scope.tarea.closing +
                    //    "&NOTACIERRE=" + $scope.tarea.nota +
                    //    "&STATUSDESTINO=3" +
                    //    "&TIPODEUDA=PENDIENTE" +
                    //    "&DATOSADIC=" + $scope.tarea.datosAdicionalesCierre +
                    //    "&REPNAME=" + $scope.tarea.requeridoPor +
                    //    "&NOMBRE=" + $scope.installationData.personaContacto +
                    //    "&TELEFONO=" + $scope.installationData.telefono +
                    //    "&CALLTYPEPROBLEM=" + $scope.tarea.tipoAviso1 + "|" + $scope.tarea.motivo1 + "|1|" +
                    //    "&TEXTO=" + $scope.tarea.observaciones;
                    
                    var resultado = window.showModalDialog(url, null, "center:yes; resizable:yes; dialogWidth:900px; dialogHeight:700px;");
//                    alert(resultado);

                    //Tras recibir el resultado de la otra ventana podemos cerrar la session de infopoint
                    $scope.closeInfopointSession();

                    //TODO BOrrar, es para probar un resultado concreto
                    //resultado='{"AppointmentNumber":"1234","Status":0,"Message":"correcto"}';

                    if (resultado) {
//                    	alert(JSON.parse(resultado));
                        $scope.finalizarDesdeMantenimiento(JSON.parse(resultado));
                    } else {
//                    	alert("resultado no es true");
                        $scope.finalizarDesdeMantenimiento(null);
                    }
                }
            })
            .error(function (data, status, headers, config) {
            $scope.error = true;
            CommonService.processBaseResponse(data, status, headers, config);
        })


    };


    /**
     * Finalizar desde el botón de finalizar
     */
    $scope.finalizar = function () {
        CommonService.logger("Finalizar task, task: ", "debug", $scope.tarea);
        var finalizeRequest = {
            task: $scope.tarea,
            installation: $scope.installationData,
            fromSearch: $scope.fromSearch
        };
        CommonService.logger("Finalizar  Task, request: ", "debug", finalizeRequest);
        $http({
            method: 'PUT',
            url: 'notificationtask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                CommonService.logger("Finalized task", "debug");
                
                /**
                 * Si venimos de la pantalla de buscar y la tarea está en retrieved,
                 * no permitimos hacer nada y volvemos al buscador
                 */
                if (data.tareaRetrieved){
                	var modalInstance = $modal.open({
                        animation: false, //Indica si animamos el modal
                        templateUrl: 'alertModalTareaRetrieved.html', //HTML del modal
                        controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                        size: null,
                        resolve: {
                            //Creo que esto es para pasar parametros al controller interno
                            // items: function () {
                            //     return $scope.items;
                            // }
                        }
                    });

                    //Funciones para recibir el cierre ok y el cancel
                    modalInstance.result.then(function () {
                    	CommonService.gotoSearch($scope.desktopDepartment);
                    }, function (param) {
                        //Boton cancelar del Modal
                    });
                    
                	
                }
                else{
	                /** Si no venimos de la pantalla de buscar cerramos la interacción,
	                 *  en caso contrario volvemos a la pantalla de buscar
	                 */
	                $scope.error = !data.success;
	                if (data.success) {
	                    if ($scope.fromSearch != "true") {
	                        CommonService.closeInteraction(data);
	                    } else {
	                        CommonService.gotoSearch($scope.desktopDepartment);
	                    }
	                } else {
	                    //No cerramos o volvemos atras porque hay errores
	                }
                }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
                CommonService.logger("Error finalizing task", "error");
                $scope.error = true;
            });
    };


    /**
     * Ejemplo de objetos JSON que se reciben:
     * {"AppointmentNumber":"","Status":2,"Message":"Error generico creando actividad en TOA"}
     *
     * @param resultado
     */
        //Finalizar mantenimiento al volver de la pantalla emergente
    $scope.finalizarDesdeMantenimiento = function (resultado) {
        CommonService.logger("Finalizar task: ", "debug", $scope.tarea);
        var finalizeRequest = {
            task: $scope.tarea,
            lastCalledPhone: $scope.lastCalledPhone,
            //  finalizedByCreateMaintenance
            finalizedByCreateMaintenance: true,
            fromSearch: $scope.fromSearch
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
                CommonService.logger("Finalized task", "debug");
                
                /**
                 * Si venimos de la pantalla de buscar y la tarea está en retrieved,
                 * no permitimos hacer nada y volvemos al buscador
                 */
                if (data.tareaRetrieved){
                	var modalInstance = $modal.open({
                        animation: false, //Indica si animamos el modal
                        templateUrl: 'alertModalTareaRetrieved.html', //HTML del modal
                        controller: 'ContentModalCtrl',  //Referencia al controller especifico para el modal
                        size: null,
                        resolve: {
                            //Creo que esto es para pasar parametros al controller interno
                            // items: function () {
                            //     return $scope.items;
                            // }
                        }
                    });

                    //Funciones para recibir el cierre ok y el cancel
                    modalInstance.result.then(function () {
                    	CommonService.gotoSearch($scope.desktopDepartment);
                    }, function (param) {
                        //Boton cancelar del Modal
                    });
                    
                	
                }
                else{
                
	                /** Si no venimos de la pantalla de buscar cerramos la interacción,
	                 *  en caso contrario volvemos a la pantalla de buscar
	                 */
	                if ($scope.fromSearch != "true") {
	                    CommonService.closeInteraction(data);
	                } else {
	                    CommonService.gotoSearch($scope.desktopDepartment);
	                }
                }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope.error = true;
                CommonService.processBaseResponse(data, status, headers, config);
                CommonService.logger("Error finalizing task", "error");
            });
    };

    //Mostramos los mensajes para rellenar los combos de finalizar
    $scope.muestraFinalizarRequired = function () {
        $scope.verErrores = true;
        if ($scope.tarea.datosAdicionalesCierre == null) {
            $scope.closingADAlert = true;
        }
        if ($scope.tarea.closing == null) {
            $scope.closingAlert = true;
        }
    };

    /**
     * Cierra la session de infopoint tras ejecutar el mantenimiento
     */
    $scope.closeInfopointSession = function () {
        $http({
            method: 'GET',
            url: 'agent/closeInfopointSession'
        })
            .success(function (data, status, headers, config) {
                CommonService.logger('Agente obtenido: ' + data, "debug");
                $scope.agent = data.agent;
                CommonService.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                CommonService.logger('Error en la creación de mantenimiento, response: ' + JSON.stringify(data), "debug");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };


    /** OBTENCION DE DATOS*/
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
            CommonService.logger('Loaded NotificationTask Data' + JSON.stringify(data), "debug");
            CommonService.processBaseResponse(data, status, headers, config);

            $scope.tarea = data.tarea;
            //clonamos el objeto tarea
            $scope.tareaOriginal = angular.copy($scope.tarea);

            $scope.installationData = data.installationData;
            //Si no llega instalación mostramos mensaje error perpetuo
            if(data.noInstallation==true){
            	$scope.noInstallation=data.noInstallation;
            	$scope.noInstallationMsg=data.noInstallationMsg;
            }
            //Si no llega tarea también mostramos el mensaje perpetuo
            if(data.noTicked==true){
            	$scope.noTicked=data.noTicked;
            	$scope.noInstallationMsg=data.noInstallationMsg;
            }
            $scope.getNotificationTypeList();
            $scope.getTypeReasonList1($scope.tarea.tipoAviso1);
            $scope.getTypeReasonList2($scope.tarea.tipoAviso2);
            $scope.getTypeReasonList3($scope.tarea.tipoAviso3);
            //$scope.getClosingList($scope.tarea.tipoAviso1, $scope.tarea.motivo1, $scope.tarea.closing);
            $scope.getClosingAditionalDataList();
            $scope.refeshDisabled = true;
            //Mensajes para los required de finalizar
            $scope.closingADAlert = false;
            $scope.closingAlert = false;
        })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.error = true;
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        CommonService.logger("NotificationTask loaded...", "debug");
    };


    /**** COMBOS *****/
    
        /**
         * Consultar Combo de Cierre
         * Al hacer click en el combo se llama a getNotificationClosingList del back que hace:
         * si se ha modificado algún tipo y motivo se actualiza el aviso
         * Consulta los motivos con estos nuevos datos
         */
    $scope.getClosingList = function(){
    	
    	if ($scope.tarea.tipoAviso1!=undefined && $scope.tarea.tipoAviso1!=null && $scope.tarea.tipoAviso1!="" && 
    			$scope.tarea.motivo1!=undefined && $scope.tarea.motivo1!=null && $scope.tarea.motivo1!=""){
    			var flag = true;
    			if (($scope.tarea.tipoAviso2!=undefined && $scope.tarea.tipoAviso2!=null && $scope.tarea.tipoAviso2!="") &&
    					($scope.tarea.motivo2==undefined || $scope.tarea.motivo2==null || $scope.tarea.motivo2=="")){
    				flag = false;
    			}
    			if (($scope.tarea.tipoAviso3!=undefined && $scope.tarea.tipoAviso3!=null && $scope.tarea.tipoAviso3!="") &&
    					($scope.tarea.motivo3==undefined || $scope.tarea.motivo3==null || $scope.tarea.motivo3=="")){
    				flag = false;
    			}
    			if (flag){
			        CommonService.logger("Load Closing Type List for params: " + $scope.tarea.tipoAviso1 + ", " + $scope.tarea.motivo1, "debug");
			        var closingTypeRequest = {
			            idType: $scope.tarea.tipoAviso1,
			            reasonId: $scope.tarea.motivo1,
			            tarea:$scope.tarea
			        };
			        CommonService.logger("Load Closing Type List Request: ", "debug", closingTypeRequest);
			        $http({
			            method: 'PUT',
			            url: 'commons/getNotificationClosingList',
			            data: closingTypeRequest
			        })
			            .success(function (data, status, headers, config) {
			                CommonService.logger('Loaded Closing Type List Response', "debug", data);
			                $scope.closingList = data.pairList;
			                CommonService.processBaseResponse(data, status, headers, config);
			            })
			            .error(function (data, status, headers, config) {
			                CommonService.logger("Error loading Closing Type List", "error");
			                CommonService.processBaseResponse(data, status, headers, config);
			                $scope.error = true;
			                // called asynchronously if an error occurs
			                // or server returns response with an error status.
			            });
    			}
    	}
    	else{
    		//El tipo1 y el motivo1 no están rellenos y por lo tanto no se debe lanzar el getMotivosCierreFiltrado
    	}
    };

    $scope.getClosingAditionalDataList = function () {
        CommonService.logger("Load Closing Aditional Data List ", "debug");
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
                $scope.error = true;
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    $scope.getNotificationTypeList = function () {
        CommonService.logger("Load Notification Type List", "debug");
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
                $scope.error = true;
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 1
    $scope.getTypeReasonList1 = function (typeId, data, status, headers, config) {
        CommonService.logger("Load Task Type Reason List", "debug");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.logger('Loaded Type Reason List', "debug", data);
                $scope.motivoList1 = data.pairList;
                $scope.getClosingList();
                CommonService.processBaseResponse(data, status, headers, config);

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.error = true;
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    //Consulta Motivo 2
    $scope.getTypeReasonList2 = function (typeId) {
        CommonService.logger("Load Task Type Reason List", "debug");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.logger('Loaded Type Reason List', "debug", data);
                $scope.motivoList2 = data.pairList;
                $scope.getClosingList();
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
        CommonService.logger("Load Task Type Reason List", "debug");
        var taskTypeReasonRequest = {
            idType: typeId
        };
        $http({
            method: 'PUT',
            url: 'commons/getTypeReasonList',
            data: taskTypeReasonRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.logger("Loaded Type Reason List", "debug", data);
                $scope.motivoList3 = data.pairList;
                $scope.getClosingList();
                CommonService.processBaseResponse(data, status, headers, config);

            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    /****REFRESCAR****/
    $scope.refrescar = function () {
        $scope.getInstallationAndTask();
    };

    /**
     * mostrarAvisosAplazar
     * comprueba si están rellenos los campos de persona de contacto y telefono antes de aplazar
     * también comprueba que los campos horario desde y hasta sean correctos
     */
    $scope.mostrarAvisosAplazar=function(){
    	var err=$scope.compruebaMotivos();
    	if($scope.tarea.personaContacto!='' && $scope.tarea.personaContacto!=null && $scope.tarea.personaContacto!=undefined && $scope.tarea.telefonoAviso!=null && $scope.tarea.telefonoAviso!='' && $scope.tarea.telefonoAviso!=undefined && !err){
    		if ($scope.tarea.horarioDesde== undefined || ($scope.tarea.horarioDesde!= undefined && $scope.tarea.horarioDesde!="" && (parseInt($scope.tarea.horarioDesde)<0 || parseInt($scope.tarea.horarioDesde)>23))) {
				$scope.errorHorarioDesde=true;
			}else if ($scope.tarea.horarioHasta==undefined || ($scope.tarea.horarioHasta!=undefined && $scope.tarea.horarioHasta!="" && (parseInt($scope.tarea.horarioHasta)<0 || parseInt($scope.tarea.horarioHasta)>23))) {
				$scope.errorHorarioDesde=false;
				$scope.errorHorarioHasta=true;
			}else{
				$scope.errorHorarioDesde=false;
				$scope.errorHorarioHasta=false;
				$scope.openDelayModal();
			}
    	}else{
    		if($scope.tarea.personaContacto=='' || $scope.tarea.personaContacto==null || $scope.tarea.personaContacto==undefined){
    			$scope.mostrarErrorAplazarC=true;
        	}else{
        	    $scope.mostrarErrorAplazarC=false;
        	}
        	if($scope.tarea.telefonoAviso=='' || $scope.tarea.telefonoAviso==null || $scope.tarea.telefonoAviso==undefined){
        		$scope.mostrarErrorAplazarT=true;
        	}else{
        	    $scope.mostrarErrorAplazarT=false;
        	}
    	}
    	
    }
    
    /**
     * Comprobar motivos
     * Si alguno de los tres tipos está rellenado tiene que tener el motivo relleno
     * también comprueba que los horario desde y hasta sean correctos
     */
    $scope.compruebaMotivos=function(){
    	var err=false;
    	if ($scope.tarea.tipoAviso1!=null && $scope.tarea.tipoAviso1!="" && $scope.tarea.tipoAviso1!=undefined) {
			if($scope.tarea.motivo1==null || $scope.tarea.motivo1=="" || $scope.tarea.motivo1==undefined){
				$scope.errorMotivo1=true;
				err=true;
			}else{
				$scope.errorMotivo1=false;
			}
		}
    	if ($scope.tarea.tipoAviso2!=null && $scope.tarea.tipoAviso2!="" && $scope.tarea.tipoAviso2!=undefined) {
			if($scope.tarea.motivo2==null || $scope.tarea.motivo2=="" || $scope.tarea.motivo2==undefined){
				$scope.errorMotivo2=true;
				err=true;
			}else{
				$scope.errorMotivo2=false;
			}
		}
    	if ($scope.tarea.tipoAviso3!=null && $scope.tarea.tipoAviso3!="" && $scope.tarea.tipoAviso3!=undefined) {
			if($scope.tarea.motivo3==null || $scope.tarea.motivo3=="" || $scope.tarea.motivo3==undefined){
				$scope.errorMotivo3=true;
				err=true;
			}else{
				$scope.errorMotivo3=false;
			}
		}
    	if ($scope.tarea.horarioDesde== undefined || ($scope.tarea.horarioDesde!= undefined && $scope.tarea.horarioDesde!="" && (parseInt($scope.tarea.horarioDesde)<0 || parseInt($scope.tarea.horarioDesde)>23))) {
			$scope.errorHorarioDesde=true;
			err=true;
		}else if ($scope.tarea.horarioHasta==undefined || ($scope.tarea.horarioHasta!=undefined && $scope.tarea.horarioHasta!="" && (parseInt($scope.tarea.horarioHasta)<0 || parseInt($scope.tarea.horarioHasta)>23))) {
			$scope.errorHorarioDesde=false;
			$scope.errorHorarioHasta=true;
			err=true;
		}else{
			$scope.errorHorarioDesde=false;
			$scope.errorHorarioHasta=false;
			err=false;
		}
    	return err;
    }
    
    /**
     * Comprobación boton mantenimiento
     */
    $scope.botonMantenimiento=function(){
    	//Antigua funcion --> (formVisorTarea.$valid && tarea.closing!=null)? crearmantenimiento() : muestraFinalizarRequired()
    	var err=$scope.compruebaMotivos();
    	if($scope.formVisorTarea.$valid && $scope.tarea.closing!=null && !err){
    		$scope.crearmantenimiento();
    	}else{
    		$scope.muestraFinalizarRequired();
    	}
    }
    /**
     * Comprobación boton finalizar
     */
    $scope.botonFinalizar=function(){
    	//Antigua funcion --> (formVisorTarea.$valid && tarea.closing!=null)? finalizar() : muestraFinalizarRequired()
    	var err=$scope.compruebaMotivos();
    	if ($scope.formVisorTarea.$valid && $scope.tarea.closing!=null && !err) {
			$scope.finalizar();
		}else{
			$scope.muestraFinalizarRequired();
		}
    }
});