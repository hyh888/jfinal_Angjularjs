'use strict';
//var myApp = angular.module("myApp", []);
var myApp = angular.module("myApp", [],function($httpProvider) {
	  // Use x-www-form-urlencoded Content-Type
	  $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
	 
	  /**
	   * The workhorse; converts an object to x-www-form-urlencoded serialization.
	   * @param {Object} obj
	   * @return {String}
	   */
	  var param = function(obj) {
	    var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
	      
	    for(name in obj) {
	      value = obj[name];
	        
	      if(value instanceof Array) {
	        for(i=0; i<value.length; ++i) {
	          subValue = value[i];
	          fullSubName = name + '[' + i + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value instanceof Object) {
	        for(subName in value) {
	          subValue = value[subName];
	          fullSubName = name + '[' + subName + ']';
	          innerObj = {};
	          innerObj[fullSubName] = subValue;
	          query += param(innerObj) + '&';
	        }
	      }
	      else if(value !== undefined && value !== null)
	        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
	    }
	      
	    return query.length ? query.substr(0, query.length - 1) : query;
	  };
	 
	  // Override $http service's default transformRequest
	  $httpProvider.defaults.transformRequest = [function(data) {
	    return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
	  }];
	});
myApp.controller("ShoppingController", function ($scope, $http,$filter) {
		 $http.get('/item').success(function(data, status, headers, config) {
			 $scope.myStr="";
			 $scope.items = data.items;  
             $scope.items.push({});

		 });
		 $scope.uploadExcel=function(){
				$http.get('/item/uploadExcel').success(function(data, status, headers, config) {
					alert(data);
					 $scope.items = data.items;  
		             $scope.items.push({});
					}); 
		 
		 };
		 
		 $scope.createExcel=function(){
				$http.get('/item/createExcel').success(function(data, status, headers, config) {
					alert(data);
					}); 
		 
		 }	;
		 
		 $scope.delRecord=function(myIndex,myId){
				$scope.items.splice(myIndex,1);
				alert(myId);
				if(myId!=undefined){
					$http.get('/item/delete/'+myId).success(function(data, status, headers, config) {
						$scope.myStr=data;
						alert(myStr);
						});
				};
			};
			$scope.changeStatus=function(myIndex,myId){
				$scope.items[myIndex]["myStatus"]="E";
				};
				$scope.changeToDel=function(myIndex,myId){
					$scope.items[myIndex]["myStatus"]="D";
					};			
				 $scope.judLast=function(myIndex,myLast){
						if (true==myLast){
						  $scope.items.push({});
						  };
			};	
			
			 $scope.judChange=function(myIndex,e){
				 e.item["myStatus"]="U";
				 console.log(myIndex); 
					 console.log("change", e.item["id"]); 
				};	
				 $scope.saveRecord=function($event,myThis){
					//var p= $event.currentTarget.id;
					var resultItems=$filter('filter')($scope.items,{myStatus:'U'});
					var url='/item/batchCrud';
					$http.post(url, {params: JSON.stringify(resultItems) }).success(function(data){
						 $scope.items = data.items;  
			             $scope.items.push({});
					});
				 };
				
	});
	
//$scope.items[0]["myStatus"]="E";
//$scope.items.splice($scope.items.length,0,jEmptyItem);
// $scope.items.splice(0,0,jEmptyItem);
  //$scope.items.splice($scope.items.length,0,"");
//"id":"","des":"","title":"" 