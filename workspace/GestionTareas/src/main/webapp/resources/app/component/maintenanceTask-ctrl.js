app.controller('maintenanceTask', function ($scope, $http) {

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


    $scope.getTarea = function () {
        console.log("Cargando tarea mantenimiento...")
        $http({method: 'GET', url: 'visortarea/getTareaMantenimiento'}).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Cargada tarea mantenimiento...")
    };
    //
    //$scope.employee = [];
    //$scope.loadData = function (employee) {
    //    console.log("Cargando datos iniciales tarea mantenimiento...")
    //    $scope.employee = JSON.parse(employee);
    //};


});