app.controller('marketingsurveytask-ctrl', function ($scope, $http) {
    //Angular Maintenance Survey Controller start
    $scope.getTarea = function () {
        console.log("Loading Marketing Survey Task...")
        $http({method: 'GET', url: 'visortarea/marketingsurveytask/getMarketingSurveyTask'}).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded Marketing Survey Task")

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