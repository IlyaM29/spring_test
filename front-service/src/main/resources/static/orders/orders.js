angular.module('market-front').controller('ordersController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555/gateway';

    $scope.loadOrders = function () {
        $http.get(contextPath + '/api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    };
    $scope.loadOrders();
});