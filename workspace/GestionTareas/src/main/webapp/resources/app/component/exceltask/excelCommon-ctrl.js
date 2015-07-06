app.controller('excelcommon', function ($scope, $http) {


    $scope.getClosingReason = function () {

        $http({method: 'GET', url: 'visortarea/getClosingReason'}).
            success(function (data, status, headers, config) {
                $scope.closingReasonList = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    /* EJEMPLOS
     $scope.getDesplegableKey1 = function () {

     $http({method: 'GET', url: 'visortarea/getDesplegableKey1'}).
     success(function (data, status, headers, config) {
     $scope.key1List = data;
     }).
     error(function (data, status, headers, config) {
     // called asynchronously if an error occurs
     // or server returns response with an error status.
     });
     };

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