app.controller('maintenancetask-ctrl', function ($scope, $http, CommonService) {

    $scope.getDesplegableKey1 = function () {
        $http({method: 'GET', url: 'visortarea/getDesplegableKey1'}).
            success(function (data, status, headers, config) {
                $scope.key1List = data;
                CommonService.processBaseResponse(data,status,headers,config);
            }).
            error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            });
    };

    $scope.getDesplegableKey2 = function () {
        if ($scope.tarea && $scope.tarea.key1) {
            console.log("Queriying Key2 for key1=" + $scope.tarea.key1);
            $http({method: 'GET', url: 'visortarea/getDesplegableKey2', params: {key1: $scope.tarea.key1}}).
                success(function (data, status, headers, config) {
                    $scope.key2List = data;
                    CommonService.processBaseResponse(data,status,headers,config);
                }).
                error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data,status,headers,config);
                });
        }
    };

    $scope.getCancelationType = function () {
        $http({method: 'GET', url: 'visortarea/getCancelationType'}).
            success(function (data, status, headers, config) {
                $scope.cancelationTypeList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };


    $scope.getTarea = function () {
        $scope.vm.appReady=false;

        console.log("Loading MaintenanceTask...");
        console.log("Params: "
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'maintenancetask/gettarea',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                console.log("Loaded maintenance task:" + JSON.stringify(data.tarea));
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
            });
        console.log("MaintenanceTask loaded...")
    };

    $scope.getInstallationAndTask = function(){
        $scope.vm.appReady=false;

        console.log("Loading MaintenanceTask...");
        console.log("Params: "
        + " installationId: " + $scope.installationId
        + " ccUserId: " + $scope.ccUserId
        + " callingList: " + $scope.callingList
        + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'maintenancetask/getInstallationAndTask',
            params: {installationId: $scope.installationId, ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                console.log("Loaded maintenance task:" + JSON.stringify(data.tarea));
                $scope.tarea = data.tarea;
                $scope.installationData = data.installationData;
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
            });
        console.log("MaintenanceTask loaded...")
    };



    $scope.interactionCreateMaintenance = function(){
        console.log("Creating Maintenance task, task:" + JSON.stringify($scope.tarea));
        var maintenanceTaskCreateRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Creating Maintenance task, request:" + JSON.stringify(maintenanceTaskCreateRequest));
        $http({
            method: 'PUT',
            url: 'maintenancetask/create',
            data: maintenanceTaskCreateRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }

    $scope.finalizar = function(){
        console.log("Finalizing Maintenance task");
        var maintenanceTaskFinalizeRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Finalize Maintenance task " + JSON.stringify(maintenanceTaskFinalizeRequest));
        $http({
            method: 'PUT',
            url: 'maintenancetask/finalize',
            data: maintenanceTaskFinalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }


});