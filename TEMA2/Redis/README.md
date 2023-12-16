# REDIS

## Descripción del Proyecto

Este proyecto es un servicio de acortamiento de URLs. Consiste en dos programas principales: un cliente y un servicio. El cliente toma URLs de los usuarios, las acorta y las almacena en una base de datos Redis. El servicio se ejecuta en un bucle infinito, buscando constantemente nuevas URLs para acortar en la base de datos.

El cliente acepta tres comandos:

- `shorten <URL>`: Acorta la URL proporcionada y la almacena en la base de datos.
- `url <shortened URL>`: Devuelve la URL original a partir de la URL acortada proporcionada.
- `exit`: Sale del programa.

El servicio busca constantemente nuevas URLs para acortar en la base de datos. Cuando encuentra una, genera una cadena aleatoria de 8 caracteres para representar la URL acortada y la almacena en la base de datos.

## Configuración

Para ejecutar este proyecto, necesitarás tener instalado Java y Maven en tu máquina.

Además, necesitarás tener acceso a una base de datos Redis. Puedes configurar la dirección del host y el puerto de tu base de datos Redis en el archivo `Constantes.java`. Por defecto, el host está configurado como "34.228.162.124" y el puerto como 6379. Asegúrate de cambiar estos valores a los de tu propia base de datos Redis antes de ejecutar el programa.

```java
public class Constantes {
    public static final String HOST = "34.228.162.124"; // Cambia esto a tu host de Redis
    public static final int PORT = 6379; // Cambia esto a tu puerto de Redis
}
```

## Dependencias

Este proyecto depende de la biblioteca Jedis para interactuar con la base de datos Redis. Puedes agregar esta dependencia a tu proyecto Maven añadiendo el siguiente código a tu archivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>3.7.0</version>
    </dependency>
</dependencies>
```

## Ejecución

Para ejecutar este proyecto, primero compila el código con Maven usando el comando `mvn compile`. Luego, puedes ejecutar el programa cliente con el comando `mvn exec:java -Dexec.mainClass="org.example.Client"` y el programa de servicio con el comando `mvn exec:java -Dexec.mainClass="org.example.Service"`.

## Cómo funciona

Cuando ejecutas el programa cliente, te pedirá que introduzcas un comando. Si introduces el comando `shorten` seguido de una URL, el programa acortará la URL y la almacenará en la base de datos. Si introduces el comando `url` seguido de una URL acortada, el programa buscará la URL original en la base de datos y la imprimirá. Si introduces el comando `exit`, el programa terminará.

Mientras tanto, el programa de servicio se ejecuta en un bucle infinito, buscando constantemente nuevas URLs para acortar en la base de datos. Cuando encuentra una, genera una cadena aleatoria de 8 caracteres para representar la URL acortada y la almacena en la base de datos.

## Estructura del Proyecto

El proyecto consta de tres archivos principales:

- `Client.java`: Este es el programa cliente que interactúa con el usuario. Acepta comandos del usuario, acorta las URLs y las almacena en la base de datos.

- `Service.java`: Este es el programa de servicio que se ejecuta en un bucle infinito, buscando constantemente nuevas URLs para acortar en la base de datos.

- `Constantes.java`: Este archivo contiene las constantes utilizadas en el proyecto, como la dirección del host y el puerto de la base de datos Redis.

## Contribuciones

Las contribuciones a este proyecto son bienvenidas. Si encuentras un error o tienes una sugerencia para mejorar el proyecto, por favor abre un issue o envía un pull request.





## Licencia


Este proyecto está licenciado bajo los términos de la licencia MIT. Para más detalles, consulta el archivo [MIT](https://choosealicense.com/licenses/mit/) en el directorio raíz del proyecto.


## Instalación

Para instalar este proyecto, necesitarás tener Java y Maven instalados en tu sistema. Aquí están los pasos para instalar y ejecutar el proyecto:

1. Clona el repositorio en tu máquina local usando Git:

```bash
git clone https://github.com/yourusername/your-repo-name.git
cd your-repo-name
```

2. Compila el proyecto con Maven:

```bash
mvn clean install
```

3. Ejecuta el programa cliente:

```bash
mvn exec:java -Dexec.mainClass="org.example.Client"
```

4. En una nueva terminal, ejecuta el programa de servicio:

```bash
mvn exec:java -Dexec.mainClass="org.example.Service"
```
[!IMPORTANT]
Por favor, asegúrate de reemplazar `yourusername` y `your-repo-name` con tu nombre de usuario de GitHub y el nombre de tu repositorio, respectivamente.

Además, recuerda que necesitarás tener acceso a una base de datos Redis y deberás configurar la dirección del host y el puerto en el archivo `Constantes.java`.