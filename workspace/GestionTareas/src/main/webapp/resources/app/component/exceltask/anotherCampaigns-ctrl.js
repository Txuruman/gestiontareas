//Another Campaigns ANGULARJS script START
app.controller('anotherCampaigns', function ($scope, $http, CommonService, $modal, $log) {

    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        //$log.debug("Loading Another Campaigns Task...");
        //$log.debug("Params: "
        //+ " installationId: " + $scope.installationId
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'anothercampaignstask/getInstallationAndTask',
            params: {callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                //$log.debug("Loaded fee cleaning task:",data.tarea);
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
                //$log.debug("Closing reason list loaded", data.pairList);
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                //$log.error("Error loading closing reason list");
                // called asynchronously if an error occurs
                // or server returns response with an error status.
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
                url: 'anothercampaignstask/aplazar',
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
    //Ventana Aplazar - End

    $scope.finalizar = function(){
        //$log.debug("Finalizar Fee cleaning task, task: ",$scope.tarea);
        var finalizeFeeCleaningTaskRequest = {
            tarea:$scope.tarea
        };
        //$log.debug("Finalizar Fee Cleaning Task, request: ",finalizeFeeCleaningTaskRequest);
        $http({
            method: 'PUT',
            url: 'anothercampaignstask/finalizar',
            data: finalizeFeeCleaningTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Finalized fee cleaning task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error finalizing fee cleaning task");
            });
    };



});
//Another Campaigns ANGULARJS script END