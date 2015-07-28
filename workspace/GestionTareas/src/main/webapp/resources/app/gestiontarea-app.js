var app = angular.module("myApp", ['ui.bootstrap']);


// Controlador de la ventana modal de aplazar
// Please note that $modalInstance represents a modal window (instance) dependency. It is not the same as the $modal service used above.
app.controller('DelayModalInstanceCtrl', function ($scope, $modalInstance, $log) {

    $scope.today = new Date();
    $scope.delayInfo = {
        delayDate: $scope.today,
        delayTime: $scope.today,
        recallType: ''
    };


    $scope.ok = function () {
        //Llama a la función de result.then de DelayModalCtrl
        if ($scope.delayDate && $scope.delayTime) {
            $scope.delayDate.setHours($scope.delayTime.getHours(), $scope.delayTime.getMinutes(), 0, 0);
        }
        $log.debug("Selected delay info :" + $scope.delayInfo );
        $modalInstance.close($scope.delayInfo);
    };

    $scope.cancel = function () {
        //Llama a la funcion result.then de DelayModalCtrl
        $modalInstance.dismiss('cancel');
    };
});


// Configure the $httpProvider by adding our date transformer
app.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.defaults.transformResponse.push(function(responseData){
        convertDateStringsToDates(responseData);
        return responseData;
    });
}]);

//Transoformación de Cadenas a Fecha(Javascript)
//El formato de fecha configurado en el servidor es: 2011-11-29T15:52:18.867Z  y  2020-02-18
var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)?$/;

function convertDateStringsToDates(input) {
    // Ignore things that aren't objects.
    if (typeof input !== "object") return input;

    for (var key in input) {
        if (!input.hasOwnProperty(key)) continue;

        var value = input[key];
        var match;
        // Check for string properties which look like dates.
        // TODO: Improve this regex to better match ISO 8601 date strings.
        if (typeof value === "string" && (match = value.match(regexIso8601))) {
            // Assume that Date.parse can parse ISO 8601 strings, or has been shimmed in older browsers to do so.
            console.log("Transformando fecha",value);
            var milliseconds = Date.parse(match[0]);
            if (!isNaN(milliseconds)) {
                input[key] = new Date(milliseconds);
            }
        } else if (typeof value === "object") {
            // Recurse into object
            convertDateStringsToDates(value);
        }
    }
}

////Transoformación de Cadenas a Fecha(Javascript)  - End



//Controller for the app:messages
app.controller('MessagesController', function ($scope, $rootScope) {
    //Remove a message with the X button
    $scope.closeMessage = function (index) {
        $rootScope.vm.serverMessages.splice(index, 1);
    };
});


app.controller('AccordionDemoCtrl', function ($scope) {
    $scope.oneAtATime = true;

    $scope.groups = [
        {
            title: 'Dynamic Group Header - 1',
            content: 'Dynamic Group Body - 1'
        },
        {
            title: 'Dynamic Group Header - 2',
            content: 'Dynamic Group Body - 2'
        }
    ];

    $scope.items = ['Item 1', 'Item 2', 'Item 3'];

    $scope.addItem = function () {
        var newItemNo = $scope.items.length + 1;
        $scope.items.push('Item ' + newItemNo);
    };

    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
});


app.controller('DatepickerDemoCtrl', function ($scope) {
    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabledW = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function () {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 2);
    $scope.events =
        [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];

    $scope.getDayClass = function (date, mode) {
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    };
});


//create a service which defines a method square to return square of a number.
app.service('CommonService', function ($rootScope, $log, $http) {
    this.square = function (a) {
        console.log("Multiplicando");
        return a * a;
    };

    $scope.suma = function (a) {
        console.log("Suma");
        return a + a;
    };


    //Objeto global para almacenar
    $rootScope.vm = {
        //Variable global para mostrar mensajes
        serverMessages: [],
        appReady: true,
        maxCaloriesPerDay: 2000,
        currentPage: 1,
        totalPages: 0,
        originalMeals: [],
        meals: [],
        isSelectionEmpty: true,
        errorMessages: [],
        infoMessages: []
    };

    //this.addAlert = function() {
    //    $scope.alerts.push({msg: 'Another alert!'});
    //};


    /** Funcion para processar las respuestas del servidor, eg: processBaseResponse(data,status,headers,config);  */
    this.processBaseResponse = function (data, status, headers, config) {
        console.log("Procesando BaseResponse....");
        if (data && data.messages) {
            for (var msg in data.messages) {
                $rootScope.vm.serverMessages.push(data.messages[msg]);
            }
        }
        //TODO Control status ,etc si hay error meter mensajes
        // TODO if($rootScope.serverMessages == )
    };

    // Disable weekend selection for calendar
    this.disabledWeekendSelection = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };


    this.getNotificationTypeList = function(data, status, heathers, config) {
        $log.debug("Load Notification Type List");
        $http({
            method: 'GET',
            url: 'commons/getNotificationTypeList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Notification Type List', data);
                $rootScope.tipoAvisoList = data.pairList;
                suma(5);
                this.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                this.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getClosingList = function(data, status, heathers, config) {
        $log.debug("Load Closing Type List");
        $http({
            method: 'GET',
            url: 'commons/getClosingList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Closing Type List', data);
                $rootScope.closingList = data.pairList;
                this.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                this.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getTypeReasonList = function(data, status, heathers, config) {
        $log.debug("Load Task Type Reason List");
        $http({
            method: 'GET',
            url: 'commons/getTypeReasonList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Type Reason List', data);
                $rootScope.motivoList = data.pairList;
                this.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                this.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.getClosingAditionalDataList = function(data, status, heathers, config) {
        $log.debug("Load Closing Aditional Data List");
        $http({
            method: 'GET',
            url: 'commons/getClosingAditionalDataList'
        })
            .success(function (data, status, headers, config) {
                $log.debug('Loaded Closing Aditional Data List', data);
                $rootScope.datosAdicionalesList = data.pairList;
                this.processBaseResponse(data, status, headers, config);
            })
            .error(function (data, status, headers, config) {
                this.processBaseResponse(data, status, headers, config);
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });
    };

    this.loadInstallationData = function(installationId,data, status, heathers, config){
        $log.debug("Search Installation. ID: " + installationId);
        $http({
            method: 'GET',
            url: 'commons/searchInstallation',
            params: {installationId: installationId}
        }).
            success(function (data, status, headers, config) {
                $log.debug("Installation data found: " ,data.installationData);
                $rootScope.installationData = data.installationData;
                this.processBaseResponse(data,status,headers,config);
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                this.processBaseResponse(data,status,headers,config);
                $log.debug("Error in Installation data search");
            });
        $log.debug("Installation data loaded...")
    }
});

app.filter('stringToDate', function () {
    return function (input) {
        console.log("input" + input);
        if (!input)
            return null;

        var date = moment(input);
        return date.isValid() ? date.toDate() : null;
    };
});

app.directive('jsonDate', function ($filter) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {

            //format text going to user (model to view)
            ngModel.$formatters.push(function (value) {
                console.log("String To Date:" + value)
                var date = $filter('stringToDate')(value);
                return date.toString();
            });

            //format text from the user (view to model)
            ngModel.$parsers.push(function (value) {
                console.log("View to Model")
                var date = new Date(value);
                if (!isNaN(date.getTime())) {
                    return moment(date).format();
                }
            });
        }
    }
});