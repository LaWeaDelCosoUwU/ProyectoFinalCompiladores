package laweadelcoso.proyectofinalcompiladores.AnalizadorLexico;

import laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo.ASDR;
import laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo.AnalizadorSemantico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//Bien, veamos si esto se acaba en un día.
//Al final son sólo programar algunos autómatas... Creo


public class Interprete {

    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        
        // Verifica si recibe más de un argumento
        if (args.length > 1) {
            
            System.out.println("Uso correcto: interprete [archivo.txt]");
            System.exit(64);
            
        } else if (args.length == 1) {
            
            // Si se proporciona un archivo como argumento, ejecuta el archivo
            ejecutarArchivo(args[0]);
            
        } else {
            
            // Si no se proporcionan argumentos deja que el usuario ingrese un prompt por si mismo
            ejecutarPrompt();
            
        }
    }

    private static void ejecutarArchivo(String path) throws IOException {
        
        // Lee el contenido del archivo y lo convierte a una cadena
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()));

        // Por si hay errores 
        if (existenErrores) 
            System.exit(65);
        
    }

    private static void ejecutarPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for(;;){
            System.out.print(">>> ");
            String linea = reader.readLine();
            if(linea == null) break; // Presionar Ctrl + D
            ejecutar(linea + " ");
            existenErrores = false;
        }
    }

    private static void ejecutar(String source) {
        
        try {
            
            // Escanea la cadena de entrada y obtiene una lista de tokens
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scan();

            /* Imprime cada token en la consola
            for (Token token : tokens) {
                System.out.println(token);
            }*/

            //El analizador sintactico
            ASDR asdr = new ASDR(tokens);

            //Analisis sintactico
            if(asdr.parse()){
                new AnalizadorSemantico(asdr.getArbol());
            }

            
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprime la traza de la excepción en caso de error
        }
        
    }

    /*
    El método error se puede usar desde las distintas clases
    para reportar los errores
     */
    static void error(int linea, String mensaje) {
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String posicion, String mensaje) {
        
        // Imprime un mensaje de error en la salida de error estándar
        System.err.println(
                "[linea " + linea + "] Error " + posicion + ": " + mensaje
        );
        existenErrores = true; // Establece la bandera de errores a true
        
    }
    
}
