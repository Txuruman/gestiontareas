app.controller('taskviewer-ctrl', function ($scope, $http, CommonService) {
    $scope.misMensajes = CommonService.serverMessages;

    console.log("Initial serverMessages:" + $scope.misMensajes);
    console.log("Commons serverMessages:" + CommonService.serverMessages);

    $scope.getInstallationData = function () {
        console.log("Loading Installation data for installation id: " + $scope.installationId);

        $scope.cuadrado = CommonService.square(3);
        console.log("cuadrado" + $scope.cuadrado);

        $scope.suma = CommonService.square(5);
        console.log("suma" + $scope.suma);

        $http({
            method: 'GET',
            url: 'visortarea/getInstallationData',
            params: {installationId: $scope.installationId}
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.installationData = data;
                console.log("serverMessages OK:" + $scope.serverMessages);
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope.installationData = data;
                console.log("serverMessages ERROR:" + $scope.serverMessages);
            });
        console.log("Installation data loaded...")
    };


    // PRUEBAS

    $scope.getMessages = function () {
        console.log("Getting messages");
        $http({method: 'GET', url: 'test/message'}).
            success(function (data, status, headers, config) {
                $scope.allData=data;
               // $scope.serverMessages=data.messages;
                CommonService.processBaseResponse(data, status, headers, config);
            }).
            error(function (data, status, headers, config) {
                $scope.allData=data;
               // $scope.serverMessages=data.messages;
                CommonService.processBaseResponse(data, status, headers, config);
            });
    };

});