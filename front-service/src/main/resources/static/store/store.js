angular.module('market-front').controller('storeController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:5555';

    $scope.pages = [];
    $scope.loadProduct = function (p){
        $http({
            url: contextPath + '/gateway/api/v1/products',
            method: 'GET',
            params: {
                page: p,
                min_cost: $scope.min,
                max_cost: $scope.max,
                title: $scope.title
            }
        }).then(function (response) {
            $scope.productList = response.data.content;
            $scope.pages = [];
            for (i = 0; i < response.data.totalPages; i++) {
                $scope.pages.push(i+1)
            }
            console.log(response.data)
        });
    };
    $scope.loadProduct();

    $scope.addToCart = function (productId) {
        $http.post(contextPath + '/cart/api/v1/carts/add/' + productId, $localStorage.cartName)
            .then(function () {
            });
    };

});