app.controller('taskviewer-ctrl', function ($scope, $http, CommonService) {
   $scope.getInstallationData = function () {
        console.log("Loading Installation data for installation number: " + $scope.installationId);

        $http({
            method: 'GET',
            url: 'installation/query',
            params: {installationId: $scope.installationId}
        })
        .success(function (data, status, headers, config) {
            CommonService.processBaseResponse(data, status, headers, config);


        })
        .error(function (data, status, headers, config) {
            CommonService.processBaseResponse(data, status, headers, config);
            // called asynchronously if an error occurs
            // or server returns response with an error stats.
        });
    };
});