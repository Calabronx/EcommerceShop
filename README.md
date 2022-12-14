# EcommerceShop



# Applicacion FullStack de un Sistema E Commerce

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

>Se guardan las clases de modelo como entidades que luego son mapeadas directamente a la base con la anotacion Entity del ORM Hibernate

![image](https://user-images.githubusercontent.com/69681105/195394629-ce600561-dac2-4a9f-8657-1fd7c2ffabd0.png)

>Se guardan las clases de Controladores tipo Rest, para manejar las request de los endpoints realizados por el cliente del  lado del Web server

![image](https://user-images.githubusercontent.com/69681105/195394934-97711d9b-5c24-4216-a9e5-84be00150ed6.png)

>Se guardan las clases De servicio (Business Logic) donde se implementan los metodos que procesan la informacion

![image](https://user-images.githubusercontent.com/69681105/195395018-7a637040-f9be-404d-a307-910c2d491432.png)

>Se guardan las interfaces de repositorio que extienden la clase JPARepository para realizar las transacciones con la base de datos

![image](https://user-images.githubusercontent.com/69681105/195395261-850003db-b583-4970-b020-b0652f5e2a52.png)

>Contiene las clases de utilidad, que almacenan constantes, metodos utiles para el procesamiento logico del sistema que luego implementa el >servicio, programas java que el systema usa en su totalidad.

![image](https://user-images.githubusercontent.com/69681105/195396103-9ce1c206-ad91-48eb-aab2-b33a042edcbb.png)

>Contiene clases de configuracion del sistema

![image](https://user-images.githubusercontent.com/69681105/195396332-fc4e5c3c-38c8-4b4d-89d0-5129a2f12a59.png)

>Contiene excepciones customizadas

![image](https://user-images.githubusercontent.com/69681105/195396460-e2e91a49-c73d-49cf-83dd-992b7b4fdcb3.png)

# Front End

>Estructura del proyecto con Angular 9

![image](https://user-images.githubusercontent.com/69681105/195396972-12434aa5-4f38-4828-b96e-799c699fd02c.png)


>Componente para agregar producto 

![image](https://user-images.githubusercontent.com/69681105/195397831-5c35bd6f-d2e7-4ec8-b4e6-c5d84ac26daa.png)

>Componente para agregar carrito 
>
![image](https://user-images.githubusercontent.com/69681105/195398019-18c1e345-7627-44d3-bc10-4e48b0f03b9e.png)

>Componente para listar carritos

![image](https://user-images.githubusercontent.com/69681105/195398115-463f0809-0ff7-4e80-9abe-69a23190afa7.png)

>Componente para listar un carrito especifico por id

![image](https://user-images.githubusercontent.com/69681105/195398221-9c5dcb90-8c8d-43ab-8816-0cd26d69cd2c.png)

>Componente para listar todos los productos de la base 

![image](https://user-images.githubusercontent.com/69681105/195398296-058017ad-0256-44f7-a03a-e0932369ae35.png)


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

Requests de proyecto Angular


>GET http://localhost:4200/get-cart/{id}

>GET http://localhost:4200/list-shopping-cart

>POST http://localhost:4200/create

>POST http://localhost:4200/add-product/{id} -> no logre hacerlo funcionar en el service de add-product.ts


Seguire realizando al documentacion que no he llegado por el tiempo, cualquier consulta porfavor comunicarme!

