app.controller('taskSearch', function ($scope, $http, CommonService, $modal) {
	//Valor por defecto del radioButton de la búsqueda
	$scope.searchOption="phone";
	
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
        
      //Ventana Aplazar - Start
        //Abre la ventana, posibles tamaños '', 'sm', 'lg'
        $scope.openDelayModal = function (size) {
            var modalInstance = $modal.open({
                animation: false, //Indica si animamos el modal
                templateUrl: 'deplayModalContent.html', //HTML del modal
                controller: 'DelayModalInstanceCtrl',  //Referencia al controller especifico para el modal
                size: size,
                resolve: {
                    //Creo que esto es para pasar parametros al controller interno
                    // items: function () {
                    //     return $scope.items;
                    // }
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
        //Ventana Aplazar - End
    };
});