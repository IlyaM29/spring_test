angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://127.0.0.1:8189/app';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.pages = [];
    $scope.loadProduct = function (p){
        $http({
            url: contextPath + '/api/v1/products',
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

    $scope.tryToAuth = function () {
        $http.post('http://127.0.0.1:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.loadProduct();
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.springWebUser;
    };
});