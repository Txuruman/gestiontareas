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
            url: 'visortarea/getTareaMantenimiento',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        }).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
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
        var maintenanceTaskCreate = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Creating Maintenance task " + JSON.stringify(maintenanceTaskCreate));
        $http({
            method: 'PUT',
            url: 'maintenancetask/create',
            data: maintenanceTaskCreate
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
        var maintenanceTaskFinalize = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        console.log("Finalize Maintenance task " + JSON.stringify(maintenanceTaskFinalize));
        $http({
            method: 'PUT',
            url: 'maintenancetask/finalize',
            data: maintenanceTaskFinalize
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