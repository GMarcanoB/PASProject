# PASProject
Este proyecto se desarrollo en Kotlin aborda todos los conocimientos adquiridos durante la asignatura.
Para ello se pide realizar una aplicación en la cual el alumno debe utilizar estos conocimientos y su capacidad como ingeniero para realizar una aplicación en Android con las siguientes funcionalidades:

  a. Autenticación de Usuarios, mediante email/password, mediate Firebase Authentication.
  b. Conexión a un servicio API externo de datos abiertos (webservice) para extraer información (por ejemplo: el tiempo mi localización, el nivel de polución para alérgicos, nivel de alerta por polución, entre otros. Se utilizó apis de datos abiertos y las librerías Volley o Retrofit para recoger y listar los datos en la
aplicación. Los datos se podrán persistir en BD local (sqlite 2, room3)
  c. Recoger datos de los sensores. La aplicación podrá recoger telemetrías (datos o datasets) de los sensores utilizando las librerías de sensores de Android4 (p.ej. acelerómetro, giróscopo, luz, orientación), o accediendo a componentes hardware específicos del teléfono (p.ej. GPS, microfono, cámara, bluetooth).
  d. Mapas. La aplicación utilizará mapas para geolocalizar los datos recogidos por los sensores.
  e. Persistir los datos en la nube. La aplicación persistirá los datos de los sensores en la nube utilizando Firebease realtime database5, storage6, y/o firestore7
