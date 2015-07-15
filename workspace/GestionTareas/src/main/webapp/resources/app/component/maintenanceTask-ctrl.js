app.controller('maintenancetask-ctrl', function ($scope, $http, CommonService) {

    $scope.getDesplegableKey1 = function () {
        $http({method: 'GET', url: 'visortarea/getDesplegableKey1'}).
            success(function (data, status, headers, config) {
                $scope.key1List = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
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
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
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