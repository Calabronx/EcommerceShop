# EcommerceShop



>Applicacion de un Sistema E Commerce

Sistema realizado en Spring y java 11 en su totalidad.
Se realizo el patron de diseño MVC para manejar el control cliente servidor.
Se realizo un front end sencillo con Angular 9, implementando algunas funcionalidades de la aplicacion en el

# Las tecnologias que utiliza:

- Spring Framework
- Java 11
- Hibernate ORM (One to many Many to one)
- MySQL server
- Scripts SQL
- SpringFox y Swagger 2 para documentar API REST.
- SL4J framework y Logback para manejo de Logging por archivo *logback.xml*.
- JUnit5 y Mockito para testear el service.
- Angular 9, HTML, Bootstrap 5 y CSS para una simple vista del frontend. Esta parte estaria incompleta una parte.
- JPA Repository


# Aplicacion Rest

El link al projecto de postman se encuentra en el siguiente link:


>https://www.postman.com/lunar-module-geologist-54896819/workspace/6a202d85-cf22-4d56-a8c5-5e52d48753ab/collection/13665825-bae44d5d-d320-4326-9186-7b2808f1081f?action=share&creator=13665825

# Como ejecutar la aplicacion

>Primero se debe pasar los parametros de conexion( user y password local) de mysql Server.
En la primera ejecucion del systema en Spring java, la aplicacion va a ejecutar un script SQL para cargar 10 productos a la base, esto lo hace si esta la base sin registros.

La aplicacion tiene un scheduler que monitorea en base al tiempo los carritos inactivos, esto lo hace con el SystemTime.java

* Si pasan 40 minutos de un carrito creado y no es comprado por ningun cliente, se destruye.

# Estructura de la aplicacion Spring
El objetivo de la solucion de esta architectura de software, fue maqueteado para separar las clases en capas(packages)
Esta realizado en base a un patron de diseño MVC 
![image](https://user-images.githubusercontent.com/69681105/195393418-549386cb-2502-46ee-bf15-83120b142056.png)


# Requests de la aplicacion

Crear un carrito 
> POST http://localhost:8080/api/v1/create

Obtiene el status de un carrito
>GET http://localhost:8080/api/v1/cartstatus

Eliminar carrito existente por id
>DELETE http://localhost:8080/api/v1/delete/{id}

Agregar producto al carrito pasando el id del mismo
>POST http://localhost:8080/api/v1/addproduct/{id}

Eliminar producto por id
>DELETE http://localhost:8080/api/v2/delete/product/{id}

Comprar el carrito existente por id
>POST http://localhost:8080/api/v1/buy/{id}

Crear un cliente pasandole el body
>POST http://localhost:8080/api/v3/create

Agregar cantidad de un producto ya existente al carrito
>PUT http://localhost:8080/api/v2/add/quantity/{id}

Eliminar cantidad de un producto ya existente del carrito
>DELETE http://localhost:8080/api/v2/del/quantity/{id}

Obtener todos los carritos creados activos y inactivos
>GET http://localhost:8080/api/v1/getallcarts

Obtener todos los productos existentes en el mercado disponibles para comprar
>GET http://localhost:8080/api/v2/get

Agregar producto pasandole el nombre especifico al carrito
>POST http://localhost:8080/api/v1/add-name/{id}

Obtener todos los clientes por el tipo de membresia : COMUN,PROMOCIONABLE,VIP
>GET http://localhost:8080/api/v3/getlevel

Cambiar la membresia a mano de un cliente por request
>PUT http://localhost:8080/api/v3/changelevel/{id}

Eliminar todos los carritos y productos del systema
>DELETE http://localhost:8080/api/v1/deleteAll





Seguire realizando al documentacion que no he llegado por el tiempo, cualquier consulta porfavor comunicarme!

