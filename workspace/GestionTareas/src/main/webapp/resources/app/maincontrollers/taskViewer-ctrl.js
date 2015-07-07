app.controller('taskviewer-ctrl', function ($scope, $http) {


    $scope.getInstallationData = function () {
        console.log("Loading Installation data...")
        $http({method: 'GET', url: 'visortarea/getInstallationData'}).
            success(function (data, status, headers, config) {
                $scope.installationData = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Installation data loaded...")
    };

    /*
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