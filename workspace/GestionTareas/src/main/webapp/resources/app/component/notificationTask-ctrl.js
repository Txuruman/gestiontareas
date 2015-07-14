app.controller('notificationtask', function ($scope, $http, CommonService) {

    $scope.logTarea = function () {
        console.log("Tarea: " + $scope.tarea);
    };


    $scope.getTarea = function () {
        console.log("Loading NotificationTask");
        console.log("Params: "
            + " ccUserId: " + $scope.ccUserId
            + " callingList: " + $scope.callingList
            + " taskId: " + $scope.tareaId);
        $http({
            method: 'GET',
            url: 'visortarea/notificationtask/getnotificationtask',
            params: {ccUserId: $scope.ccUserId, callingList: $scope.callingList, tareaId: $scope.tareaId}
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.tarea = data;
            })
            .error(function (data, status, headers, config) {
                CommonService.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("NotificationTask loaded...")
    };

    $scope.getClosing = function () {
        console.log('Notification task - getClosing');
        $http({method: 'GET', url: 'visortarea/notificationtask/getClosing'}).
            success(function (data, status, headers, config) {
                $scope.closingList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };


    $scope.aplazar = function () {
        console.log('Aplazar Tareaa ' + $scope.tarea );
        $http({
            method: 'PUT',
            url: 'visortarea/aplazar',
            data: $scope.tarea
        })
            .success(function (data, status, headers, config) {
                console.log('Aplazada Tareaa ' + $scope.tarea );
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

});