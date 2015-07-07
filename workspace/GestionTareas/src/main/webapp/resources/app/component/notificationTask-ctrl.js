app.controller('notificationtask', function ($scope, $http) {

    $scope.logTarea = function () {
        console.log("Tarea: " + $scope.tarea);
    };


    $scope.getTarea = function () {
        console.log("Loading NotificationTask...")
        $http({method: 'GET', url: 'visortarea/notificationtask/getnotificationtask'}).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
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