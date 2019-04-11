$('#inputText').keyup(
		
		function(){
			
			var textotraduccir=$('#inputText').val();
			
			
			$('#outputText').children().remove();
			
				$.post('/traducir/{textoBuscar}', {"textotraduccir" : textotraduccir}, function(textoTraducido){
					$('#outputText').val(textoTraducido);
					
				});
			
			}  		
  
  );

