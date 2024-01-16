package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TablaSimbolos {

    private final Map<String, Object> values = new HashMap<>();

    boolean existeIdentificador(String identificador){
        return values.containsKey(identificador);
    }

    Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        throw new RuntimeException("Variable no definida '" + identificador + "'.");
    }

    void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }

    //Jaja esto ya no me sirve sdjhgjklsdfhgjklfdshj
    //Antes de enlazar la tabla, no queria más que utilizar solo una, contar las variables creadas en un solo bloque (if, loop, function,...)
    // Y despues iterar esta funcion n veces
    //Pero pues no XD
    void eliminarUltimaFila() {
        if (!values.isEmpty()) {

            String ultimaClave = null;

            Set<String> keys = values.keySet();
            Iterator<String> iterator = keys.iterator();

            // Iterar para encontrar la última clave
            while (iterator.hasNext()) {
                ultimaClave = iterator.next();
            }

            values.remove(ultimaClave);
        }
    }

}