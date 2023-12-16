# El Radar de Velocidad de VirosMs

## Descripción del Proyecto

Este proyecto simula un sistema de radar de velocidad que envía multas al momento. Consiste en tres aplicaciones diferentes que utilizan MQTT y Redis para comunicarse:

1. `CarSimulator.java`: Simula el comportamiento de un coche, generando datos de velocidad aleatorios y publicándolos en un broker MQTT.
2. `Radar.java`: Simula un radar móvil que detecta la velocidad de los vehículos y publica mensajes en un broker MQTT si la velocidad es superior a 80.
3. `PoliceStation.java`: Simula una estación de policía que procesa las multas de velocidad. Cuando se detecta una velocidad excesiva, se envía una multa por MQTT y se añade la matrícula del vehículo a un grupo en Redis.

## Configuración

Para configurar el proyecto, necesitarás cambiar las variables `url` y `mqttUrl` en el archivo `Main.java`:

```java
String url = "34.228.162.124"; // Cambia esto a la URL de tu servidor Redis
String mqttUrl = String.format("tcp://%s:1883", url); // Cambia esto a la URL de tu broker MQTT
```

- `url`: Esta es la URL de tu servidor Redis. Deberías cambiarla a la URL de tu propio servidor Redis.
- `mqttUrl`: Esta es la URL de tu broker MQTT. Deberías cambiarla a la URL de tu propio broker MQTT.

## Ejecución

Para ejecutar el proyecto, simplemente ejecuta el método `main` en la clase `Main.java`. Esto iniciará la simulación, creando instancias de `CarSimulator`, `Radar` y `PoliceStation` y ejecutándolas en hilos separados.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue para discutir lo que te gustaría cambiar.

## Licencia

[MIT](https://choosealicense.com/licenses/mit/)