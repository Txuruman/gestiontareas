app.controller('taskCreation', function ($scope, $http, CommonService, $modal, $log) {

    $scope.createTask = function(){
        $scope.vm.appReady=false;
        $log.debug("Creating task");
        var createTaskRequest = {
            tarea:$scope.task,
            prueba:'Hola'
        };
        $log.debug("Creating task request: ",createTaskRequest);
        $http({
            method: 'PUT',
            url: '/createtask/createtask',
            data: createTaskRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            });
    }


    $scope.createMaintenance = function(){
        $scope.vm.appReady=false;
        $log.debug("Creating maintenance");
        var createMaintenanceRequest = {
            tarea:$scope.task,
            prueba:'Hola'
        };
        $log.debug("Creating maintenance request: ", createMaintenanceRequest );
        $http({
            method: 'PUT',
            url: '/createtask/createmaintenance',
            data: createMaintenanceRequest
        })
            .success(function (data, status, headers, config) {
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            });
    };

    $scope.init = function(data, status, heathers, config) {
        $scope.vm.appReady=false;
        $log.debug("Charging page, combo lists");
        CommonService.getNotificationTypeList(data, status, heathers, config);
        CommonService.getTypeReasonList(data, status, heathers, config);
        CommonService.loadInstallationData($scope.installationId,data, status, heathers, config);
        $log.debug("Charging page, combo lists");
        $scope.vm.appReady=true;
    };

    /**
     * Hace el set del installation id en el campo del scope y lanza la búsqueda de la instalación.
     */
    $scope.searchInstallationData = function(){
        $scope.vm.appReady=false;
        $scope.installationId = $scope.fieldInstallationId;
        CommonService.loadInstallationData($scope.installationId,data, status, heathers, config);
        $scope.vm.appReady=true;
    };

});