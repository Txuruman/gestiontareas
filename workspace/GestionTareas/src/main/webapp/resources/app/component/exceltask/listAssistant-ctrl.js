//Controlador List Assistant - Start
app.controller('listAssistant-ctrl', function ($scope, $http, CommonService) {
        $scope.getTarea = function () {
            console.log("Loading List Assistant Task...")
            console.log("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
            $http({
                method: 'GET',
                url: '/listassitanttask/gettarea',
                params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
            })
                .success(function (data, status, headers, config) {
                    console.log("ListAssistantTask: " + JSON.stringify(data));
                    $scope.tarea = data.tarea;
                    CommonService.processBaseResponse(data, status, headers, config);

                })
                .error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            console.log("List assistant task loaded...")


            console.log("Loading Excel Task Commons: Closing reason");
            $http({method: 'GET', url: '/exceltaskcommon/getClosingReason'}).
                success(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    $scope.closingReasonList = data;
                }).
                error(function (data, status, headers, config) {
                    CommonService.processBaseResponse(data, status, headers, config);
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
            console.log("Loaded Excel Task Commons: Closing reason");

        };


});
//Controlador List Assistant - End