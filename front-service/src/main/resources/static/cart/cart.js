angular.module('market-front').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555';

    $scope.loadCart = function () {
        $http.post(contextPath + '/cart/api/v1/carts', $localStorage.cartName)
            .then(function (response) {
                $scope.Cart = response.data;
                console.log($scope.Cart);
            });
    };
    $scope.loadCart();

    $scope.clearCart = function () {
        $http.post(contextPath + '/cart/api/v1/carts/clear', $localStorage.cartName)
            .then(function () {
                $scope.loadCart()
            });
    };

    $scope.removeFromCart = function (productId) {
        $http.post(contextPath + '/cart/api/v1/carts/remove/' + productId, $localStorage.cartName)
            .then(function () {
                $scope.loadCart()
            });
    };

    $scope.decreaseProduct = function (productId) {
        $http.post(contextPath + '/cart/api/v1/carts/decrease/' + productId, $localStorage.cartName)
            .then(function () {
                $scope.loadCart()
            });
    };

    $scope.checkOut = function () {
        $http({
            url: contextPath + '/cart/api/v1/carts/createOrder/' + $localStorage.cartName,
            method: 'POST',
            data: $scope.orderDetailsDto
        }).then(function () {
            $scope.loadCart();
            console.log($scope.orderDetailsDto);
            $scope.orderDetailsDto = null;
        });
    };

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись")
    };

});