<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- metemos en pomp las dependencias_ maven dependencies spring boot web starter 2.1.3. . En total, añadir 3 dependencias. -->

<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %> <!-- añadido para formato de decimales -->
<!-- PONEMOS taglib PARA .JSTL (COGIDO DE INTERNET) -->

<html>

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<!-- Bootstrap CSS -->
<!-- PONEMOS PARA ESTILOS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<!-- nuevos para las figuritas del formulario -->
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">


<!-- PONEMOS PARA ESTILOS    fin-->


<body style="background-color: #0B79F2">

	<div class="container" style="background-color: #9CD94b">
	<!--  -->
			<!-- Card ini 1-->
			
	<div class="card card-image" style="background-color: #C3B996">
	<!-- 		style="background-image: url(https://cdn.pixabay.com/photo/2017/03/25/17/55/color-2174045_960_720.png);">
			<!-- ./imagenes/FondoVerdeCuchillas.jpg"> -->
			<!-- Content -->

			<div
				class="text-black text-left d-flex align-items-center rgba-black-strong py-5 px-4">
				<div>
		<!-- Card fin 1-->

					<!--  -->

					<p>
					<p>
					<h1 class="font-weight-light text-center text-lg-left mt-4 mb-0">CARGA MASIVA</h1>
					


					<hr class="mt-2 mb-5">
					<p>
						<a href="/administrar" class="badge badge-primary">Administrar</a>
												
						<h4><p class="text-danger">${mensaje}</h4> <br>
					<hr class="mt-2 mb-5">
	


				<!-- carga fichero en red y texto -->
		
					<!-- <form class="form-signin" id="leerfichero" name="leerfichero" method="post" action="leer">  -->
					<!-- <form action="/accionañadirAnuncio" method="POST" id="contact_form" class="contact_form" enctype="multipart/form-data">  -->
					
					<form action="/file/accioncargarfileenred" method="POST" id="formfichero" class="contact_form" enctype="multipart/form-data">   
						
						<div>   	<!-- fichero -->						            
        					<input type="file" name="file"> 
        					<br>
						</div>
						<button class="button contact_button"><span>Añadir fichero CON TODO en red, validar y mostrar</span></button>
						
						<br><br><br>
						
					   
					</form>
					
					<form action="/file/accionconvertirfileenredNUM" method="POST" id="formficheroNUM" class="contact_form" enctype="multipart/form-data">   
						
						<div>   	<!-- fichero -->						            
        					<input type="file" name="fileNUM"> 
        					<br>
						</div>
						<button class="button contact_button"><span>Añadir fichero CON NUM INGLES ESPANOL</span></button>
						
						<br><br><br>
						
					   
					</form>
						
									
					<form action="/file/accioncargarfileenbbdd" method="POST" id="formcargar" class="contact_form" >
												
						 <div class="form-group">
							<label for="descripcion" class="control-label">Area comprobacion</label>
							<textarea class="form-control" rows="10" id="areacarga" name="areacarga" type="text" placeholder="descripcion" value="${areacarga}">${areacarga}</textarea>
						</div>
												
						<div class="form-group">
							<!-- Submit Button -->
							<button class="button contact_button"  type="submit"><span>GRABAR EN BASE DE DATOS</span></button>
							<!-- <button type="submit" class="btn btn-primary">GRABAR EN BASE DE DATOS</button>   -->
						</div>
					</form>
					
					
					 
					<!-- Card ini 1-->
				</div>
			</div>
			<!-- Content -->
		</div>
		<!-- Card fin 1-->


	</div>
	<!-- /.container -->


</body>
</html>