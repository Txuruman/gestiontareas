var app = angular.module("myApp", []);

//create a service which defines a method square to return square of a number.
app.service('CommonService', function($rootScope){
    this.square = function(a) {
        console.log("Multiplicando");
        return a * a;
    };

    this.suma = function(a) {
        console.log("Suma");
        return a + a;
    };

    //Variable global para mostrar mensajes
    $rootScope.serverMessages = [];

    /** Funcion para processar las respuestas del servidor, eg: processBaseResponse(data,status,headers,config);  */
    this.processBaseResponse = function(data, status, headers, config) {
        console.log("Procesando BaseResponse....");
        $rootScope.serverMessages = data.messages;
        // TODO if($rootScope.serverMessages == )
        console.log("Fin Procesando BaseResponse....");
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

app.directive('jsonDate', function($filter) {
    return  {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {

            //format text going to user (model to view)
            ngModel.$formatters.push(function(value) {
                console.log("String To Date:" + value)
                var date = $filter('stringToDate')(value);
                return date.toString();
            });

            //format text from the user (view to model)
            ngModel.$parsers.push(function(value) {
                console.log("View to Model")
                var date = new Date(value);
                if (!isNaN( date.getTime())) {
                    return moment(date).format();
                }
            });
        }
    }
});