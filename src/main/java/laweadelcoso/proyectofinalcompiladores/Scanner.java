package laweadelcoso.proyectofinalcompiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static laweadelcoso.proyectofinalcompiladores.Interprete.error;

public class Scanner {

    // Mapa que asocia palabras reservadas con sus respectivos tipos de token
    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;  // Código fuente a analizar
    private final List<Token> tokens = new ArrayList<>();  // Lista de tokens generados durante el análisis

    public Scanner(String source){
        // Se agrega un espacio al final del código fuente para facilitar el análisis
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        
        int estado = 0;  // Estado actual del automata
        String lexema = "";  // Almacena el lexema actual
        char c;  // Carácter actual

        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);

            switch (estado){
                case 0:
                    // Estado inicial
                    if(Character.isLetter(c)){
                        // Comienza a reconocer un identificador o palabra clave
                        estado = 13;
                        lexema += c;
                    }
                    else if(Character.isDigit(c)){
                        // Comienza a reconocer un número
                        estado = 15;
                        lexema += c;
                    }
                    break;

                case 13:
                    // Reconociendo identificador o palabra clave
                    if(Character.isLetterOrDigit(c)){
                        
                        estado = 13;
                        lexema += c;
                    }
                    else{
                        
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                            
                        }else{
                            
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                            
                        }

                        // Reinicia el estado y el lexema
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 15:
                    // Reconociendo número
                    if(Character.isDigit(c)){
                        
                        estado = 15;
                        lexema += c;
                        
                    }
                    else if(c == '.'){
                        // Punto decimal encontrado
                        estado = 16;
                        lexema += c;
                    }
                    else if(c == 'E'){
                        // Notación científica encontrada
                        estado = 18;
                        lexema += c;
                    }
                    else{
                        // Número reconocido como entero
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);

                        // Reinicia el estado y el lexema
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
            }
        }

        return tokens;
    }
}