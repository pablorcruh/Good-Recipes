# TRABAJO FINAL DE MASTER

## GOOD RECIPES

| Figura 1 									        | Figura 2 | Figura 3 |
| -------------------------------------------------------------------------------------:| 
| ![login](https://github.com/pablorcruh/Good-Recipes/blob/master/repo_assets/login.png)| ![home](https://github.com/pablorcruh/Good-Recipes/blob/master/repo_assets/home.png) | ![new_recipe](https://github.com/pablorcruh/Good-Recipes/blob/master/repo_assets/add_new.png) |

### Descripción

El siguiente proyecto tiene como objetivo compartir información sobre recetas de comida saludable.
De esta manera puedes subir información sobre los ingredientes y el proceso de preparación de la receta,
adjuntar una imagen con la receta y compartirla dentro de la comunidad de usuarios.

Para poder acceder a la aplicación es necesario que crees una cuenta con una dirección de correo electrónico,
para de esta manera poder indetificar a los usuarios dentro de la aplicación de manera única

Es necesario crear un proyecto dentro de la console de firebase para poder descargar el archivo **google-services.json**
que permite la configuración de firebase dentro del proyecto android

La aplicación usa varios servicios de Firebase:

* Autenticación.
* Storage.
* Cloud Functions.
* Firestore.

El proyecto hace uso de dos colecciones, la coleccion de usuarios y la colección de recetas para el almacenamiento
de la información.

#### Coleccion Usuarios

```
{ 
   "email":"string",
   "followers":[],
   "token":"string",
   "username":"string"
}

```

#### Colección Recetas

```
{ 
   "author":"string",
   "creationDate":"timestamp",
   "description":"string",
   "ingredients":[],
   "name":"string",
   "recipeImageUrl":"string",
   "steps":[]
}

```

### Agregar Fastlane dentro del Proyecto

Para poder automatizar los despliegues dentro de Google Play Console es necesario el uso de una 
herramienta, esta herramienta permite que por medio de la ejecución de un comando se desplieguen 
las diferentes versiones de nuestra aplicación.

Para ello es necesario haber creado todo lo necesario dentro de Google Play Console como son la
ficha de descripción, la clasificación de contenido, el precio y la distribución.

Dentro de nuestro ambiente de desarrrollo es necesario la instalación de las diferentes dependecias
que la herramienta requiera. Fastlane es desarrollado en Ruby por lo que es necesario tener instalado
ruby y su administrador de gemas.

Para instalar Fastlane ejecutamos:
 ```
    sudo gem install fastlane -NV
 ```
 
 Para poder incorporar fastlane dentro del proyecto en particular es necesario ubicarnos en la raíz
 del proyecto android y ejecutar:
 
 ``` 
    fastlane init
 ```
 Este comando creará una estructura de carpetas necesarias para la gestión de la herramienta.
 
 Para que fastlane pueda interactucar con Google Play Console es necesario la generación de una llave,
 para lo que adjuntamos una guia en el siguiente [enlace](https://www.yudiz.com/fastlane-an-automated-app-deployment-tool-part-1/)
 
 Los archivos dentro de la carpeta fastlane son:
 
 * Appfile.- Contiene la ubicación de la llave y el nombre del paquete.
 * Fastfile.- Contiene la definición de las tareas a realizar cuando se hace un despliegue.
 
 Una vez generada la llave en formato json, modificamos el archivo Appfile y especificamos la ruta
 en donde almacenamos la llave descargada desde la consola de google
 
 Se recomienda establecer dos variables de entorno para poder construir y subir el ensamble del artefacto
 
 ```
    export LC_ALL=en_US.UTF-8
    export LANG=en_US.UTF-8
 ```
 
 Se debe configurar las llaves usadas para la generación de la aplicación firmada para que pueda
 generar nuevas aplicaciones en cada despliegue realizado.
 
 Luego ejecutamos el comando supply para que se pueda descargar toda la metadata desde Google Play
 Console
 
 ```
    fastlane supply init
 ```
  
 Ejecutamos  el siguiente comando para poder incrementar el número de versión del apk 
 
 ```
    fastlane add_plugin increment_version_code
 ```
 
 Por último podemos invocar a las diferentes etapas de despliegue que consideremos necesarias y que se
 encuentran descritas dentro del archivo Fastfile
 
 ### Referencia
 
 * [Automatización de Despliegues Usando Fastlane](https://www.yudiz.com/fastlane-an-automated-app-deployment-tool-part-2/). Shubham Sejpal
 * [Documentaci'on Fastlane](https://fastlane.tools/)
