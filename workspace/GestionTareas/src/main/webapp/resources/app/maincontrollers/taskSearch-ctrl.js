app.controller('taskSearch', function ($scope, $http, CommonService) {

    $scope.searchTareaFromServer = function () {
        console.log('search Tareas ' + $scope.searchText +  ' ' + $scope.searchOption);
        var searchTaskRequest = {
            searchText: $scope.searchText,
            searchOption: $scope.searchOption
        };
        $http({
                method: 'PUT',
                url: 'searchtarea/query',
                data: searchTaskRequest
            })
            .success(function (data, status, headers, config) {
                $scope.taskList = data.taskList;
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    };
});