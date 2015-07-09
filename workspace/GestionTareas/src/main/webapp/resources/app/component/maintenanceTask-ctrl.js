app.controller('maintenancetask-ctrl', function ($scope, $http) {

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
        console.log("Loading MaintenanceTask...")
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
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("MaintenanceTask loaded...")
    };
    //
    //$scope.employee = [];
    //$scope.loadData = function (employee) {
    //    console.log("Cargando datos iniciales tarea mantenimiento...")
    //    $scope.employee = JSON.parse(employee);
    //};


});