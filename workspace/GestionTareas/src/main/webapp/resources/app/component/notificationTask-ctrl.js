app.controller('notificationTask', function ($scope, $http) {


    $scope.getClosing = function () {

        $http({method: 'GET', url: 'visortarea/notificationttask/getClosing'}).
            success(function (data, status, headers, config) {
                $scope.closingList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    /* EJEMPLO
    $scope.searchTareaFromServer = function () {
        console.log('search Tareas ' + $scope.searchText +  ' ' + $scope.searchOption);
        $http({
                method: 'GET',
                url: 'searchtarea/query',
                params: {searchText: $scope.searchText, searchOption: $scope.searchOption}
            })
            .success(function (data, status, headers, config) {
                $scope.tareas = data;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };*/
});