//Controlador List Assistant - Start
    app.controller('listAssistant-ctrl', function ($scope, $http) {

    $scope.getTarea = function () {
        console.log("Cargando tarea listAssistant...")
        $http({method: 'GET', url: 'visortarea/getTareaListadoAssistant'}).
            success(function (data, status, headers, config) {
                $scope.tarea = data;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
        console.log("Cargada tarea listAssistant...")
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
//Controlador List Assistant - End