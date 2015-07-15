//Angular KeyboxTask controller
app.controller('keyboxtask-ctrl', function ($scope, $http, CommonService) {
        $scope.getTarea = function () {
            console.log("Loading Keybox  Task...")
            console.log("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
            $http({method: 'GET',
                url: '/keyboxtask/gettarea',
                params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
            }).
                success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    $scope.tarea = data.tarea;
                }).
                error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            console.log("Loaded Keybox Task")

            console.log("Loading Excel Task Commons: Closing reason");
            $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
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