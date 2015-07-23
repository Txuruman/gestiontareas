app.controller('taskCreation', function ($scope, $http, $log) {

    $scope.crearTarea = function(){
        $log.debug("Creating task");
        var createTaskRequest = {
            tarea:$scope.tarea,
            prueba:'Hola'
        };
        $log.debug("Creating task request: " + createTaskRequest);
        $http({
            method: 'PUT',
            url: 'createtask/finalize',
            data: maintenanceTaskFinalizeRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }
});