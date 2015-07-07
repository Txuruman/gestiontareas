//Fee cleaning ANGULARJS script START
app.controller('feecleaningtask-ctrl', function ($scope, $http) {

    $scope.getTarea = function () {
        console.log("Loading FeeCleaningTask...")
        $http({method: 'GET', url: 'visortarea/feecleaningtask/getfeecleaningtask'}).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded FeeCleaningTask")

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
//Fee cleaning ANGULARJS script END