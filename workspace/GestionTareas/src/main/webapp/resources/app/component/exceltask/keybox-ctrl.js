//Angular KeyboxTask controller
app.controller('keyboxtask-ctrl', function ($scope, $http) {
        $scope.getTarea = function () {
            console.log("Loading Keybox  Task...")
            $http({method: 'GET', url: 'visortarea/keybox/getkeyboxtask'}).
                success(function (data, status, headers, config) {
                    $scope.tarea = data;
                }).
                error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            console.log("Loaded Keybox Task")

            console.log("Loading Excel Task Commons: Closing reason");
            $http({method: 'GET', url: 'visortarea/exceltaskcommon/getClosingReason'}).
                success(function (data, status, headers, config) {
                    $scope.closingReasonList = data;
                }).
                error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            console.log("Loaded Excel Task Commons: Closing reason");
        };
});