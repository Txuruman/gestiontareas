app.controller('taskviewer-ctrl', function ($scope, $http) {


    $scope.getInstallationData = function () {
        console.log("Loading Installation data for installation id: " + $scope.installationId);
        $http({method: 'GET',
                url: 'visortarea/getInstallationData',
                params: {installationId: $scope.installationId}
        })
            .success(function (data, status, headers, config) {
                $scope.installationData = data;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Installation data loaded...")
    };
});