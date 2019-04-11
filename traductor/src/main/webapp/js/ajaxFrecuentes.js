$('#inputText').keyup(
		
		function(){
				
			var textoBuscar=$('#inputText').val().split(' ');
			var ultima=textoBuscar[textoBuscar.length-1];
			if(ultima!=null){
				$('#traduccionesfrecuentes').children().remove();
				$.post('/traduccionesfrecuentes/{ultima}', {"ultima" : ultima}, function(diccionario){
					
					$('#traduccionesfrecuentes').css("visibility", "visible");
					$('#labelTraducciones').css("visibility", "visible");
					for(var i=0;i<diccionario.length;i++){
						var anadir="<option value=" + i +">"+diccionario[i]+"</option>";
						$('#traduccionesfrecuentes').append(anadir);
					}
					
				});
			}
		} 		
  
  );

