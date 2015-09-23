app.controller('taskSearch', function ($scope, $http, CommonService, $modal, $log ) {
	$scope.searchTareaFromServer = function () {
        //$log.debug('search Tareas ' + $scope.searchText +  ' ' + $scope.searchOption);
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
      //Ventana Aplazar - Start
        //Abre la ventana, posibles tamaños '', 'sm', 'lg'
        $scope.openDelayModal = function (size,t) {
        	$scope.tareaActiva=t;
            var modalInstance = $modal.open({
                animation: false, //Indica si animamos el modal
                templateUrl: 'deplayModalContent.html', //HTML del modal
                controller: 'DelayModalInstanceCtrl',  //Referencia al controller especifico para el modal
                size: size,
                resolve: {
                	items: function () {
                		if(t.typeName==='TASK_TYPE_AVISO'){
                			return true;
                		}else{
                			return false;
                		}
                    }
                }
            });

            //Funciones para recivir el cierre ok y el cancel
            modalInstance.result.then(function (delayInfo) {
                //Boton Ok del modal
                $scope.aplazar(delayInfo.delayDate, delayInfo.recallType);
            }, function (param) {
                //Boton cancelar del Modal
            });
        };
        
        $scope.aplazar = function (delayDate, recallType) {
            //$log.info('Delay to ' + delayDate + ' with recallType ' + recallType + ' task ' + JSON.stringify($scope.tarea));
            if ($scope.tareaActiva) {
                var postponeRequest = {
                    recallType: recallType,
                    delayDate:  delayDate,
                    task: $scope.tareaActiva
                };

                //$log.info("Json of Request " + JSON.stringify(postponeRequest));

                $http({
                    method: 'PUT',
                    url: 'searchtarea/aplazar',
                    data: postponeRequest
                })
                    .success(function (data, status, headers, config) {
                        CommonService.processBaseResponse(data, status, headers, config);
                        $scope.searchTareaFromServer();
                    })
                    .error(function (data, status, headers, config) {
                        // called asynchronously if an error occurs
                        // or server returns response with an error status.
                        CommonService.processBaseResponse(data, status, headers, config);
                    });
            }
        };
        //Ventana Aplazar - End
        
        //Comprobamos si tenemos que realizar una búsqueda de inicio
        $scope.getSearch=function(){
        	//Realizamos la búsqueda cuando rebimos valores de inicio
        	if ($scope.searchText!=null && $scope.searchText!="" && $scope.searchOption!=null && $scope.searchOption!="") {
        		$scope.searchTareaFromServer();
			}
        }
});