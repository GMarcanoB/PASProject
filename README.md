# PASProject

Este proyecto se desarrollo en **Kotlin**.
Se desarrolla en **Android Studio** y posee las siguientes **funcionalidades**:

- Autenticación de Usuarios, mediante email/password, mediate Firebase Authentication.
- Conexión a un servicio API externo de datos abiertos (webservice) para extraer información (por ejemplo: el tiempo mi localización, el nivel de polución para alérgicos, nivel de alerta por polución, entre otros. Se utilizó apis de datos abiertos y las librerías Volley o Retrofit para recoger y listar los datos en la aplicación. Los datos podrán persistir en BD local (sqlite o room)
- Recoger datos de los sensores, la aplicación recoge telemetrías (datos o datasets) de los sensores utilizando las librerías de sensores de Android (p.ej. acelerómetro, giróscopo, luz, orientación), o accediendo a componentes hardware específicos del teléfono (p.ej. GPS, microfono, cámara, bluetooth).
- Mapa, para geolocalizar los datos recogidos por los sensores.
- Persistir los datos en la nube, utilizando Firebease realtime database, storage, y/o firestore
