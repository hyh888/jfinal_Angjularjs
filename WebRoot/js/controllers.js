'use strict';

function ShoppingController0($scope, $http) {
	 $http.get('/item').success(function(data, status, headers, config) {
		 $scope.myStr="";
	  $scope.items = data.items;  
      
	        $scope.items.push({});

		        //$scope.items.splice($scope.items.length,0,jEmptyItem);
	      // $scope.items.splice(0,0,jEmptyItem);
	        //$scope.items.splice($scope.items.length,0,"");
	      //"id":"","des":"","title":"" 
	 });
	 $scope.delRecord=function(myIndex,myId){
			$scope.items.splice(myIndex,1);
		};
	 $scope.judLast=function(myIndex,myLast){
			if (true==myLast){
			  $scope.items.push({});
			  };
		};	
		
}

