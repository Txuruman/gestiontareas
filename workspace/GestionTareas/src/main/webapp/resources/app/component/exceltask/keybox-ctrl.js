//Angular KeyboxTask controller
app.controller('keyboxtask-ctrl', function ($scope, $http, CommonService, $modal, $log, $window) {

    $scope.getInstallationAndTask = function () {
        $scope.vm.appReady = false;

        //$log.debug("Loading Keybox Task...");

        var getInstallationAndTaskRequest = {
            callingList: $scope.callingList,
            taskId: $scope.tareaId,
            params: mapParams
        };
        //$log.debug("LO QUE ENVIAMOS", getInstallationAndTaskRequest);

        $http({
            method: 'PUT',
            url: 'keyboxtask/getInstallationAndTask',
            data: getInstallationAndTaskRequest
        }).success(function (data, status, headers, config) {
            //$log.debug("Loaded keybox task:",data.tarea);
            //$log.debug("Loaded installation data:",data.installationData);
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
                //$log.error("Error loading keybox task and installation data");
            });
    };

    $scope.getClosingReason = function () {
        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                //$log.debug("Closing reason list loaded");
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.error("Error loeading closing reason list");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };
    $scope.aplazar = function (delayDate, recallType) {
        //$log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate: delayDate,
                task: $scope.tarea,
                fromSearch: $scope.fromSearch
            };

            //$log.info("Json of Request " + JSON.stringify(postponeRequest));

            $http({
                method: 'PUT',
                url: 'keyboxtask/aplazar',
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
	                    if (data.success) {
	                        if ($scope.fromSearch != "true") {
	                            CommonService.closeInteraction(data);
	                        } else {
	                            CommonService.gotoSearch($scope.desktopDepartment);
	                        }
	                    } else {
	                        //Por errores no volvemos atras ni cerramos
	                    }
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
                installation: $scope.installation,
                fromSearch: $scope.fromSearch
            };
            $http.put("keyboxtask/descartar", discardRequest).then(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                
                /**
                 * Si venimos de la pantalla de buscar y la tarea está en retrieved,
                 * no permitimos hacer nada y volvemos al buscador
                 */
                if (data.data.tareaRetrieved){
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
                
	                if ($scope.fromSearch != 'true') {
	                	//[AJT] Si no hay instalación, hay que cerrar el registro en genesys y cerrar interacción
	                	CommonService.closeInteraction({success: true});
	                } else {
	                    CommonService.gotoSearch($scope.desktopDepartment);
	                }
                }
            }, function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
            })
        } else {
            if ($scope.fromSearch != 'true') {
            	CommonService.excellDiscard();
            } else {
                CommonService.gotoSearch($scope.desktopDepartment);
            }
        }
    }
   
    $scope.closeInteraction = function () {
        CommonService.closeInteraction({success: true});
    }
    $scope.finalizar = function () {
        //$log.debug("Finalizar List Assistant task, task: ",$scope.tarea);
        var finalizeRequest = {
            task: $scope.tarea,
            fromSearch: $scope.fromSearch
        };
        //$log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'keyboxtask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.debug("Finalized task");
                
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