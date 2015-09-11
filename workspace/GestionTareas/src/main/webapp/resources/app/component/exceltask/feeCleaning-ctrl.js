//Fee cleaning ANGULARJS script START
app.controller('feecleaningtask-ctrl', function ($scope, $http, CommonService, $modal, $log, $window) {


    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        //$log.debug("Loading Fee Cleaning Task...");

        var getInstallationAndTaskRequest = {
            callingList: $scope.callingList,
            taskId: $scope.tareaId,
            params: mapParams
        };
        //$log.debug("LO QUE ENVIAMOS", getInstallationAndTaskRequest);

        $http({
            method: 'PUT',
            url: 'feecleaningtask/getInstallationAndTask',
            data: getInstallationAndTaskRequest
        }).success(function (data, status, headers, config) {
            //$log.debug("Loaded fee cleaning task:",data.tarea);
            //$log.debug("Loaded isntallation data:",data.installationData);
            $scope.tarea = data.tarea;
            $scope.installationData = data.installationData;
            CommonService.processBaseResponse(data,status,headers,config);
            $scope.getClosingReason();
            $scope.vm.appReady=true;
        }).
        error(function (data, status, headers, config) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            CommonService.processBaseResponse(data,status,headers,config);
            $scope.vm.appReady=true;
            //$log.error("Error loading installation and task");
        });
    };

    $scope.getClosingReason = function(){
        //$log.debug("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.closingReasonList = data.pairList;
                //$log.debug("Closing reason list loaded: ", data.pairList);
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //$log.error("Error loading closint reason list");
            });
    };
    $scope.aplazar = function (delayDate, recallType) {
        //$log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea));
        if ($scope.tarea) {
            var postponeRequest = {
                recallType: recallType,
                delayDate:  delayDate,
                task: $scope.tarea
            };

            //$log.info("Json of Request " + JSON.stringify(postponeRequest));

            $http({
                method: 'PUT',
                url: 'feecleaningtask/aplazar',
                data: postponeRequest
            })
                .success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    /** Si no venimos de la pantalla de buscar cerramos la interacción,
                     *  en caso contrario volvemos a la pantalla de buscar
                     */  
                    if($scope.fromSearch!==true){
                    	CommonService.closeInteraction(data);
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

    /**
     * Método Descartar: Nos lleva a la página de buscar
     * Variable _contextPath inicializada en commonImports
     */
    $scope.descartar=function(){
    	$window.location.href= _contextPath + "/search";
    }    
    
    $scope.finalizar = function(){
        //$log.debug("Finalizar List Assistant task, task: ",$scope.tarea);
        var finalizeRequest = {
            task:$scope.tarea
        };
        //$log.debug("Finalizar  Task, request: ",finalizeRequest);
        $http({
            method: 'PUT',
            url: 'feecleaningtask/finalizar',
            data: finalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Finalized task");
                /** Si no venimos de la pantalla de buscar cerramos la interacción,
                 *  en caso contrario volvemos a la pantalla de buscar
                 */  
                if($scope.fromSearch!==true){
                	CommonService.closeInteraction(data);
                }else{
                	$scope.descartar();
                }
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
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
//Fee cleaning ANGULARJS script END