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
})
;


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
app.service('CommonService', function ($rootScope) {
    this.square = function (a) {
        console.log("Multiplicando");
        return a * a;
    };

    this.suma = function (a) {
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