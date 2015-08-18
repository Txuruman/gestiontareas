app.controller('maintenancetask-ctrl', function ($scope, $http, CommonService, $log) {

    $scope.getDesplegableKey1 = function (){
        //$log.debug("Loading desplegable key1");
        $http({method: 'GET', url: 'maintenancetask/getDesplegableKey1'}).
            success(function (data, status, headers, config) {
                $scope.key1List = data.pairList;
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Desplegable key1 loaded");
                $scope.getDesplegableKey2();
            }).
            error(function (data, status, headers, config) {
                $scope.getDesplegableKey2();
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error loading desplegable key1");
            });
    };

    $scope.getDesplegableKey2 = function () {
        //$log.debug("Loading desplegable key2");
        if ($scope.tarea && $scope.tarea.key1) {
            //$log.debug("Loading desplegable key2 for key1=" + $scope.tarea.key1);
            $http({method: 'GET', url: 'maintenancetask/getDesplegableKey2', params: {key1: $scope.tarea.key1}}).
                success(function (data, status, headers, config) {
                    $scope.key2List = data.pairList;
                    CommonService.processBaseResponse(data,status,headers,config);
                    //$log.debug("Desplegable key2 loaded");
                }).
                error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data,status,headers,config);
                    //$log.error("Error loading desplegable key1");
                });
        }
    };

    $scope.getCancelationType = function () {
        //$log.debug("Loading cancelation type list");
        $http({method: 'GET', url: 'maintenancetask/getCancelationType'}).
            success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.cancelationTypeList = data.pairList;
                //$log.debug("Cancelation type list loaded");
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                //$log.error("Error loading cancelation type list");
            });
    };


    $scope.getTarea = function () {
        $scope.vm.appReady=false;

        //$log.debug("Loading MaintenanceTask...");
        //console.log("Params: "
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'maintenancetask/gettarea',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                //$log.debug("Loaded maintenance task:" + JSON.stringify(data.tarea));
                $scope.tarea = data.tarea;
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.getDesplegableKey1();
                $scope.getDesplegableKey2();
                $scope.vm.appReady=true;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
                //$log.error("Error loading maintenance task");
            });
    };

    $scope.init = function(){
        $scope.vm.appReady=false;
        this.getInstallationAndTask();
        $scope.vm.appReady=true;
    };


    $scope.getInstallationAndTask = function(){

        //$log.debug("Loading MaintenanceTask...");
        //$log.debug("Params: "
        //+ " installationId: " + $scope.installationId
        //+ " ccUserId: " + $scope.ccUserId
        //+ " callingList: " + $scope.callingList
        //+ " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'maintenancetask/getInstallationAndTask',
            params: {callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                //$log.debug("Loaded maintenance task:",data.tarea);
                //$log.debug("Loaded installation data:", data.installationData);
                $scope.tarea = data.tarea;
                $scope.installationData = data.installationData;
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.getDesplegableKey1();
                $scope.getCancelationType();
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error loading maintenance task");
            });
    };



    $scope.interactionCreateMaintenance = function(){
        //$log.debug("Creating Maintenance task, task:", $scope.tarea);
        var maintenanceTaskCreateRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Creating Maintenance task, request:" + maintenanceTaskCreateRequest);
        $http({
            method: 'PUT',
            url: 'maintenancetask/create',
            data: maintenanceTaskCreateRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Maintenance task created");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error creating maintenance task");
            });
    }

    $scope.finalizar = function(){
        //$log.debug("Finalizing Maintenance task");
        var maintenanceTaskFinalizeRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        //$log.debug("Finalize Maintenance task ",maintenanceTaskFinalizeRequest);
        $http({
            method: 'PUT',
            url: 'maintenancetask/finalize',
            data: maintenanceTaskFinalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.debug("Finalized maintenance task");
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                //$log.error("Error finalizing maintenance task");
            });
    }
});