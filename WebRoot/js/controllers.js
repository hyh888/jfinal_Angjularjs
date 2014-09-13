'use strict';
var myApp = angular.module("myApp", []);
myApp.controller("ShoppingController", function ($scope, $http,$filter) {
		 $http.get('/item').success(function(data, status, headers, config) {
			 $scope.myStr="";
		  $scope.items = data.items;  
		  //$scope.items[0]["myStatus"]="E";
		        $scope.items.push({});
			        //$scope.items.splice($scope.items.length,0,jEmptyItem);
		      // $scope.items.splice(0,0,jEmptyItem);
		        //$scope.items.splice($scope.items.length,0,"");
		      //"id":"","des":"","title":"" 
		 });
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
			
		 $scope.judLast=function(myIndex,myLast){
				if (true==myLast){
				  $scope.items.push({});
				  };
			};	
			
			 $scope.judChange=function(myIndex,e){
				 e.item["myStatus"]="U";
				// var colattr = angular.element(this).data();
		    		//console.log(colattr);

				 console.log(myIndex); 
					 console.log("change", e.item["id"]); 
				};	
				 $scope.saveRecord=function($event,myThis){
					var p= $event.currentTarget.id;
					//var resultItems=$scope.items|filter:{'name':'iphone'};
					var resultItems=$filter('filter')($scope.items,{myStatus:'U'});
					//console.log(resultItems);
					var url='/item';
					$http.get(url, {params: resultItems }).success(function(data){
					    
					});
				 };
				
	});
	

function hyhClick(e){
	console.log(e.html());
	console.log(e.outerHTML());
	
}