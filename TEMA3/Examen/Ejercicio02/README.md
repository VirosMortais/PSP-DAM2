# Trabajando con la Aplicación de Chat

![Logo](https://github.com/VirosMs/PSP-DAM2/assets/94723454/2a91d2f8-bc3b-47f3-99ca-c0c93a6b6495)

## Descripción del Proyecto

Este proyecto es una aplicación de Java que permite a los usuarios introducir URLs, genera una cadena aleatoria para cada URL y las agrega a una lista observable. La aplicación también agrega un observador a la lista para realizar acciones cuando se agrega una URL.

La aplicación consta de cuatro clases principales:

- `Utils`: Esta clase proporciona métodos de utilidad para la aplicación, como la generación de una cadena aleatoria.

- `Chat`: Esta clase es responsable de interactuar con el usuario y administrar la lista de URLs.

- `DownloaderAndZipper`: Esta clase implementa la interfaz ListChangeListener y actúa como un observador para la lista de objetos UrlEntry.

- `UrlEntry`: Esta clase representa una entrada de URL en la lista. Contiene una URL y una cadena aleatoria.

## Configuración

Para ejecutar este proyecto, necesitarás tener instalado IntelliJ IDEA y el JDK de Java en tu máquina.

## Dependencias

Este proyecto depende de la biblioteca estándar de Java y no requiere ninguna dependencia adicional.

## Ejecución

Para ejecutar este proyecto, abre el proyecto en IntelliJ IDEA y haz clic en el botón "Run" en la barra de herramientas. Asegúrate de tener configurado el JDK de Java para ejecutar la aplicación.

## Estructura del Proyecto

El proyecto consta de varios archivos principales:

- `Utils.java`: Esta clase proporciona métodos de utilidad para la aplicación, como la generación de una cadena aleatoria.
- `Chat.java`: Esta clase es responsable de interactuar con el usuario y administrar la lista de URLs.
- `DownloaderAndZipper.java`: Esta clase implementa la interfaz ListChangeListener y actúa como un observador para la lista de objetos UrlEntry.
- `UrlEntry.java`: Esta clase representa una entrada de URL en la lista. Contiene una URL y una cadena aleatoria.


## Licencia

Este proyecto está licenciado bajo los términos de la licencia GNU GENERAL PUBLIC LICENSE. Para más detalles, consulta el archivo [GPL-3.0 license](https://github.com/VirosMortais/PersecucionVirosMs?tab=GPL-3.0-1-ov-file) en el directorio raíz del proyecto.

## Instalación

Para instalar este proyecto, necesitarás tener IntelliJ IDEA y el JDK de Java instalados en tu sistema. Aquí están los pasos para instalar y ejecutar el proyecto:

1. Clona el repositorio en tu máquina local usando Git:

```bash
git clone https://github.com/VirosMs/PSP-DAM2.git
cd /TEMA3/Examen/Ejercicio02
```

2. Abre el proyecto en IntelliJ IDEA.
3. Ejecuta el proyecto haciendo clic en el botón "Run" en la barra de herramientas de IntelliJ IDEA.

## Acknowledgements

- [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
- [Awesome README](https://github.com/matiassingers/awesome-readme)
- [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)

## Authors

- [@VirosMs](https://github.com/VirosMs)