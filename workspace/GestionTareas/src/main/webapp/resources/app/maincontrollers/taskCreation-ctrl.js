app.controller('taskCreation', function ($scope, $http, CommonService, $modal, $log) {

    $scope.createTask = function(){
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
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }


    $scope.createMaintenance = function(){
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
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
            });
    }


    $scope.getInstallation = function(){
        $scope.vm.appReady=false;

        $log("Loading Installation...");
        $log("Params: installationId: " + $scope.installationId);
        $http({
            method: 'GET',
            url: 'createtask/getInstallation',
            params: {installationId: $scope.installationId}
        }).
            success(function (data, status, headers, config) {
                $log.debug("Loaded installation data: " ,data.tarea);
                $scope.installationData = data.installationData;
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.getTipoList();
                $scope.getMotivoList();
                $scope.vm.appReady=true;
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            });
        $log.debug("Installation data loaded...")
    };

    $scope.getTypeList = function(){
        $scope.vm.appReady=false;
        $log.debug("Loading Option Types");
        $http({
            method: 'GET',
            url: 'createtask/gettypelist'
        })
            .success(function(data, status, headers, config){
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.vm.appReady=true;
            })
            .error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    CommonService.processBaseResponse(data,status,headers,config);
                    $scope.vm.appReady=true;
            });
        $log.debug("Option type loaded.");
    }

    $scope.getReasonList = function(){
        $scope.vm.appReady=false;
        $log.debug("Loading Option Types");
        $http({
            method: 'GET',
            url: 'createtask/getreasonlist'
        })
            .success(function(data, status, headers, config){
                CommonService.processBaseResponse(data, status, headers, config);
                $scope.vm.appReady=true;
            })
            .error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                CommonService.processBaseResponse(data,status,headers,config);
                $scope.vm.appReady=true;
            });
        $log.debug("Reason types loaded.");
    }

});