angular.module('market-front').controller('ordersController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://127.0.0.1:8189/app';

    $scope.loadOrders = function () {
        $http.get(contextPath + '/api/v1/orders', $localStorage.cartName)
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    };
    $scope.loadOrders();
});