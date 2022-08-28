angular.module('market-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://127.0.0.1:8189/app';

    $scope.loadCart = function () {
        $http.post(contextPath + '/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
                console.log($scope.Cart);
            });
    };
    $scope.loadCart();

    $scope.clearCart = function () {
        $http.post(contextPath + '/api/v1/carts/clear', $localStorage.cartName)
            .then(function () {
                $scope.loadCart()
            });
    };

    $scope.removeFromCart = function (productId) {
        $http.post(contextPath + '/api/v1/carts/remove/' + productId, $localStorage.cartName)
            .then(function () {
                $scope.loadCart()
            });
    };

    $scope.decreaseProduct = function (productId) {
        $http.post(contextPath + '/api/v1/carts/decrease/' + productId, $localStorage.cartName)
            .then(function () {
                $scope.loadCart()
            });
    };

    $scope.checkOut = function () {
        $http.post(contextPath + '/api/v1/orders', $scope.orderDetails)
        .then(function () {
            $scope.loadCart();
            $scope.orderDetails = null
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись")
    };

});