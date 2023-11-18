# Estándar de códificación

## Índice

- [Lenguaje de programación](#lenguaje-de-programación)
- [Estándar](#estándar-general)
    - [Clases](#clases)
    - [Variables](#variables)
    - [Constantes](#constantes)
    - [Métodos](#métodos)
    - [Web Services](#web-services)
    - [Web Services Paths](#web-services-paths)
    - [Identificar Mappers](#identificar-mappers)


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
public class ClienteWS {
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

### Constantes

- Deben escribirse en mayúsculas, para los epacios usar guión bajo ( _ )

### Métodos

- Utilizar __camel case__.
- Los nombres de las funciones deben describir su funcionalidad.
- Deben ser verbo, sustantivo y de ser necesario especificar filtros de busqueda.

~~~ java
public Mensaje iniciarSesion(String usuario, String contrasenia) {}

public Mensaje registrarCliente(String json) {}

public Mensaje modificarUsuario(String json, Integer idUsuario) {}

public void obtenerMunicipiosPorEstado(Integer estado) {}
~~~

### Web Services 

- Deben consumir `application/json`
- Deben producir `application/json`
- En caso de que problemas con validaciones regresar BAD REQUEST

### Web Services Paths

- Los URIs deben seguir el siguiente orden pertenencia/metodo/id
- Utilizar nombres intuitivos

~~~ java
${baseURL}/usuarios/obtenerDomicilio/{id}

${baseURL}/empresas/registrar

${baseURL}/clientes/obtenerPorId/{id}

${baseURL}/clientes/obtenerPorEmpresa/{id}
~~~

### Identificar Mappers

- El nombre de los mappers debe definirse {NombreTabla}Mapper.xml
- El namespace debe definirse en minusculas como un pojo.
- Los id de las consultas deben seguir el estándar para definir los métodos.

~~~ xml
<mapper namespace="cliente">
    <select id="obtenerClientePorId" resultType="modelo.pojo.Cliente" parameterType="int">
        SELECT * 
        FROM cliente 
        WHERE idCliente = #{idCliente}
    </select>

    <select id="modificarCliente" parameterType="modelo.pojo.Client">
        UPDATE cliente 
        SET column1 = ${column1}
        WHERE idCliente = #{idCliente}
    </select>
<mapper/>
~~~
