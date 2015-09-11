var app = angular.module("myApp", ['ui.bootstrap','angular-loading-bar']);


// Controlador de la ventana modal de aplazar
// Please note that $modalInstance represents a modal window (instance) dependency. It is not the same as the $modal service used above.
app.controller('DelayModalInstanceCtrl', function ($scope, $modalInstance, $log) {
	
    $scope.today = new Date();
    $scope.delayInfo = {
        delayDate: $scope.today,
        delayTime: $scope.today,
        recallType: '5'
    };
    //$scope.withoutChanges=true;

    $scope.ok = function () {
            //Llama a la función de result.then de DelayModalCtrl
            if ($scope.delayInfo.delayDate && $scope.delayInfo.delayTime) {
                $scope.delayInfo.delayDate.setHours($scope.delayInfo.delayTime.getHours(), $scope.delayInfo.delayTime.getMinutes(), 0, 0);
            }
            //$log.debug("Selected delay info :" + $scope.delayInfo );
            $modalInstance.close($scope.delayInfo);
        };

        $scope.cancel = function () {
            //Llama a la funcion result.then de DelayModalCtrl
            $modalInstance.dismiss('cancel');
    };
});
//Controlador de la ventana modal de descartar
//Please note that $modalInstance represents a modal window (instance) dependency. It is not the same as the $modal service used above.
app.controller('ContentModalCtrl', function ($scope, $modalInstance, $log) {
	
 $scope.ok = function () {
         //Llama a la función de result.then de DelayModalCtrl
         //$log.debug("Selected delay info :" + $scope.delayInfo );
         $modalInstance.close();
     };

     $scope.cancel = function () {
         //Llama a la funcion result.then de DelayModalCtrl
         $modalInstance.dismiss('cancel');
 };
});

// Configure the $httpProvider by adding our date transformer
app.config(['cfpLoadingBarProvider','$httpProvider', function (cfpLoadingBarProvider,$httpProvider) {
    $httpProvider.defaults.transformResponse.push(function(responseData){
        convertDateStringsToDates(responseData);
        return responseData;
    });
    cfpLoadingBarProvider.includeSpinner = false;
}]);

/* Directiva para que funcionen las modales en IE8
 * En vez de utilizar script usamos div para el contenedor de las modales.
 * Esta directiva se encarga de hacer que funcione.
 * Ejemplo: WEB-INF/tags/invoiceDetailModalContent.tag
 */
app.directive('cachedTemplate', ['$templateCache', function ($templateCache) {
	  "use strict";
	  return {
	    restrict: 'A',
	    terminal: true,
	    compile: function (element, attr) {
	      if (attr.type === 'text/ng-template') {
	        var templateUrl = attr.cachedTemplate,
	            text = element.html();
	        $templateCache.put(templateUrl, text);
	      }
	    }
	  };
	}])

//Transoformación de Cadenas a Fecha(Javascript)
//El formato de fecha configurado en el servidor es: 2011-11-29T15:52:18.867Z  y  2020-02-18
//var regexIso8601 = /^(\d{4}|\+\d{6})(?:-(\d{2})(?:-(\d{2})(?:T(\d{2}):(\d{2}):(\d{2})\.(\d{1,})(Z|([\-+])(\d{2}):(\d{2}))?)?)?)$/;
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
        if (typeof value === "string" && value.length>4 && (match = value.match(regexIso8601)) ) {
            // Assume that Date.parse can parse ISO 8601 strings, or has been shimmed in older browsers to do so.
            //$log.debug("Transformando fecha",value);
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


//Servicio con funciones de utilidad comunes
app.service('CommonService', function ($rootScope, $log, $http, $timeout) {
    var service=this;

    
    /**
     * Gestión del log de javascript
     * msg: mensaje a mostrar
     * tipo: debug, info, error
     * variable: variable a mostrar
     * _IE: inicializada al principio, si abrimos con IE estará con valor true
     */
    this.logger=function(msg, tipo, variable){
    	if (_IE===false) {
    		if (variable!=undefined) {
        		$log[tipo](msg, variable);
    		}else{
    			$log[tipo](msg);
    		}
		}
    }
    this.logDebug=function(msg,variable) {
    	this.logger(msg,"debug",variable);
    }
    this.logInfo=function(msg,variable) {
    	this.logger(msg,"info",variable);
    }
    this.logError=function(msg,variable) {
    	this.logger(msg,"error",variable);
    }
    /**FIN Gestión Error*/
    
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
    
    /** Cierre de interacción
     * 	Función externa CloseInteractionPushPreview
     */
    this.closeInteraction=function(data){
    	//alert(data.success);
//    	alert("map" + mapParams);
//    	alert("conn" + mapParams.bp_auth_connid);
//    	alert(JSON.strinify(data));
    	if (data.success) {
    		alert("A continuación se cerrará la interacción");
    		e = window.external.CloseInteractionPushPreview(mapParams.bp_auth_connid);
		}
//        alert(JSON.stringify(e));
    }
    
    /** Funcion para processar las respuestas del servidor, eg: processBaseResponse(data,status,headers,config);  */
    /* quitado this. */
    this.processBaseResponse = function (data, status, headers, config) {
        //$log.debug("Procesando BaseResponse....");
        if (data && data.messages) {
        	/**
        	 * For modificado para correcto funcionamiento en IE8
        	 * Original: for (var msg in data.messages) {
        	 */
        	for (var msg=0;msg<data.messages.length; msg++) {
                $rootScope.vm.serverMessages.push(data.messages[msg]);
            }
        	//Necesario para asignar las clases de la directiva alert (IE8)
        	$timeout(function(){
            	$('div[type="success"]').addClass("alert-success alert-dismissable");
            	$('div[type="warning"]').addClass("alert-warning alert-dismissable");
            	$('div[type="danger"]').addClass("alert-danger alert-dismissable");
            },0);
        }
        //TODO Control status ,etc si hay error meter mensajes
        // TODO if($rootScope.serverMessages == )
    };

    // Disable weekend selection for calendar
    this.disabledWeekendSelection = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    this.loadInstallationData = function(installationId,data, status, headers, config){
        //$log.debug("Search Installation. ID: " + installationId);
        $http({
            method: 'GET',
            url: 'installation/query',
            params: {installationId: installationId}
        }).
            success(function (data, status, headers, config) {
                //$log.debug("Installation data found: " ,data.installationData);
                $rootScope.installationData = data.installationData;
                service.processBaseResponse(data, status, headers, config);
            }).
            error(function (data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                service.processBaseResponse(data, status, headers, config);
                //$log.debug("Error in Installation data search");
            });
        //$log.debug("Installation data loaded...")
    }
});

app.filter('stringToDate', function () {
    return function (input) {
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
                //$log.debug("String To Date:" + value);
                var date = $filter('stringToDate')(value);
                return date.toString();
            });

            //format text from the user (view to model)
            ngModel.$parsers.push(function (value) {
                //$log.debug("View to Model");
                var date = new Date(value);
                if (!isNaN(date.getTime())) {
                    return moment(date).format();
                }
            });
        }
    }
});

/**
 * Directiva para admitir sólo numeros en un input text
 * podemos asignar una longitud máxima con el atributo number-size
 */
app.directive('onlyNumber', function() {
    return {
        restrict: 'A',
        scope: {
          numberSize: '=',
          hour:'='
        },
        link:function link(scope, element){
        	element.keypress(function(event){
        		if (scope.hour==true) {				
	        		if (element.val().length==0) {
	        			if(event.keyCode<48 || event.keyCode>50){
	        				event.preventDefault();
	        			}
					}else if (element.val().length==1) {
						if (element.val()=="0") {
							if(event.keyCode<48 || event.keyCode>57){
		        				event.preventDefault();
		        			}
						}else if (element.val()=="1") {
							if(event.keyCode<48 || event.keyCode>50){
		        				event.preventDefault();
		        			}
						}else if (element.val()=="2") {
							if(event.keyCode<48 || event.keyCode>51){
		        				event.preventDefault();
		        			}
						}	
					}else if (element.val().length==2) {
						if(event.keyCode!=58){
	        				event.preventDefault();
	        			}
					}else if (element.val().length==3){
						if(event.keyCode<48 || event.keyCode>53){
	        				event.preventDefault();
	        			}
					}else if (element.val().length==4){
						if(event.keyCode<48 || event.keyCode>57){
	        				event.preventDefault();
	        			}
					}else{
						event.preventDefault();
					}
        		}else{
        			if(event.keyCode<48 || event.keyCode>57){
                		event.preventDefault();
                		//element.parent().append("<div class='avisoInput'>Sólo números</div>");
                	}
                	if (element.val().length == scope.numberSize) {
                		event.preventDefault();
            		}
        		}
        		element.focus();
        		
        	})
        }
      }
    });