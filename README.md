
# BLOGS app

Aplicación que permite capturar y almacenar posts de blogs en base de datos,
además de brindar la posibilidad de realizar consultas sobre diversos posts y comentarios realizados.

## Requisitos previos

Asegúrate de tener instaladas las siguientes herramientas antes de comenzar:

- Java Development Kit (JDK) 8 o superior
- Maven (opcional, si prefieres construir el proyecto manualmente)
- SQLite (generalmente, ya está preinstalado en la mayoría de los sistemas)

## Configuración del proyecto

1. Clona el repositorio de GitHub:

git clone https://github.com/JosefinaJapaze/BLOGS.git

2. Abre el proyecto en tu entorno de desarrollo favorito (por ejemplo, IntelliJ IDEA, Eclipse, Visual Studio Code).

3. Asegúrate de configurar tu entorno con la versión adecuada de Java (JDK).

## Configuración de la base de datos SQLite

1. La aplicación utiliza SQLite como base de datos por defecto. No es necesario configurar nada adicional, 
ya que Spring Boot lo maneja automáticamente. 
La base de datos se creará automáticamente en un archivo llamado `sqlite.db` en el directorio de trabajo.

## Ejecución de la aplicación

1. Desde la línea de comandos (con Maven):

mvn spring-boot:run

2. Una vez levantada la aplicación, se deberá realizar un curl localhost:8080/blog/update para guardar los posts en la base de datos.

3. Se puede acceder al swagger de la aplicación en http://localhost:8080/swagger-ui.html