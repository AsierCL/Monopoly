# Comandos esenciales de Git:

git status

Verifica el estado del repositorio (muestra qué archivos han cambiado).

git add .

Añade todos los archivos de la ruta actual al "staging area" para prepararlos para el commit.

git commit -m "Mensaje del commit"

Guarda los cambios localmente con un mensaje que describa lo que se ha hecho.

git pull

Descarga y fusiona los cambios del repositorio remoto a tu rama actual.

git push

Sube los cambios locales al repositorio remoto (GitHub).

git branch

Muestra las ramas del proyecto. Añade -a para ver todas (locales y remotas).

git checkout -b <nombre-de-la-rama>

Crea y cambia a una nueva rama. Útil para trabajar en nuevas características sin alterar la rama principal.

git checkout <nombre-de-la-rama>

Cambia a la rama que quieras

git merge <nombre-de-la-rama>

Fusiona una rama a tu rama actual.

git log

Muestra el historial de commits.

### Orden a seguir

#### Empezar a trabajar en una nueva funcionalidad:

git checkout -b <nombre-funcionalidad>

creamos una rama separada de main para trabajar

#### Guardar cambios

Cada vez que queramos guardar cambios en esta rama,usamos:

git add .

git commit -m "<cambios-hechos>" 

Intentad que sean commits en el siguiente formato: "Nombre de quien hace el commit- Descripción breve y concisa de lo que se hace." Intentad en la medida de lo posible, no hacer commits de código que no compile.

git pull

git push

##### Actualizar mi rama de trabajo

Si otro compañero ha hecho cambios en el main, y quieres tener en tu rama los nuevos cambios, debes ejecutar

git checkout main

git pull

git checkout <nombre-funcionalidad>

git merge main

#### Juntar ramas

Una vez terminemos la funcionalidad, guardemos los cambios, y verificamos que todo funciona correctamente (preferiblemente esperar por otro miembro del equipo), juntamos nuestra rama separada al main.

git checkout main

git merge <nombre-funcionalidad>

git push

#### Ver estado de código

Para ver los cambios que hemos hecho, podemos usar "git status". Con ello, vemos los archivos modificados

Para ver el historial de commits, podemos usar "git log".