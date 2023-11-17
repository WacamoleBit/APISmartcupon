# Estándar de códificación

## Índice

- [Lenguaje de programación](#lenguaje-de-programación)
- [Estándar](#estándar)
    - [Clases](#clases)

## Lenguaje de programación

El lenguaje de programación utilizado es __Java 8__

## Estándar general

- No usar ñ, ni acentos.
- Reemplazar __ñ__ por __ni__ o __n__ segun sea el caso. Ejemplo: `cumpleanios, tamanio, anadir, senalar, etc`.


### Clases

- Utilizar __pascal case__.
- Los nombres de las clases deben ser describir claramente su utilidad.

Ejemplos:

~~~ java
public class ServicioWS {
    // atributos
    // métodos
}

public class Mensaje {
    // atributos
    // métodos
}

public class AutenticacionDAO {
    // atributos
    // métodos
}
~~~

### Variables

- Utilizar __camel case__.
- Los nombres de las variables deben describir su funcionalidad, sin abreviar. 
- Una clase puede dar lugar a variables con nombre diferente segun el contexto.
- De preferencia usar __clases en lugar de tipos primitivos__
- En caso de no iniciar las variables para su uso, asignarles __`null`__

~~~ java
private Mensaje mensaje = new Mensaje();

private Mensaje respuesta = null;

private Integer numeroFilasAfectadas = null; // clase

private int idUsuario; //primitivo
~~~

### Funciones

- Utilizar __camel case__.
- Los nombres de las funciones deben describir su funcionalidad.

~~~ java
public Mensaje iniciarSesion(String usuario, String contrasenia) {}

public Mensaje registrarCliente(String json) {}

public Mensaje modificarUsuario(String json, Integer idUsuario) {}
~~~

### Web Services Paths

- Los URIs de los recursos deben ser sustantivos.
- Los recursos deben seguir una jerarquía.
- Usar guines medios en lugar de guiones bajos.
- Utilizar nombres intuitivos

~~~ java
${baseURL}/empresas/registrar

${baseURL}/usuarios/{id}

${baseURL}/usuarios/{id}/domicilio

${baseURL}/promociones/{id}
~~~