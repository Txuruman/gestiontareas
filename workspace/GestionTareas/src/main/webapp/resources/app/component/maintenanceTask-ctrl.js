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

});