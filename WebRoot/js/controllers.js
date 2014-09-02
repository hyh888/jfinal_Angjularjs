'use strict';

function ShoppingController0($scope, $http) {
	 $http.get('/item').success(function(data, status, headers, config) {
		 $scope.myStr="";
	  $scope.items = data.items;  
	//  	  var jEmptyItem= clone($scope.items[0]);
	       // var newJson = $scope.items.splice(0);

	 //       for(var key in $scope.items[0])
	   //     {
	     //   	jEmptyItem[key]="";
	       // 	$scope.myStr +='"' + key +'"' +':"",';
	        //}
	      
	        $scope.items.push({});

		        //$scope.items.splice($scope.items.length,0,jEmptyItem);
	      // $scope.items.splice(0,0,jEmptyItem);
	        //$scope.items.splice($scope.items.length,0,"");
	      //"id":"","des":"","title":"" 
	 });
	 
	}

