//Angular Maintenance Survey Controller start
app.controller('maintenancesurvey-ctrl', function ($scope, $http) {

    $scope.getTarea = function () {
        console.log("Loading Maintenance Survey Task...")
        $http({method: 'GET', url: 'visortarea/maintenancesurveytask/getMaintenanceSurveyTask'}).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded Maintenance Survey Task");

        console.log("Loading Excel Task Commons: Closing reason");
        $http({method: 'GET', url: 'visortarea/exceltaskcommon/getClosingReason'}).
            success(function (data, status, headers, config) {
                $scope.closingReasonList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Loaded Excel Task Commons: Closing reason")
    };
});
//Angular Maintenance Survey Controller end