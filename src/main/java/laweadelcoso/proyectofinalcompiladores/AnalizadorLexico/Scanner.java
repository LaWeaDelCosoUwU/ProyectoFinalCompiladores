package laweadelcoso.proyectofinalcompiladores.AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Interprete.error;

/*

Operadores de comparación (>, <, ==, !=):

    Estados: 0, 1, 4, 7, 10, 2, 3, 5, 6, 8, 9, 11, 12
    Transiciones: Se reconocen los operadores >, <, ==, != y se generan los tokens correspondientes.


Identificadores y palabras clave:

    Estados: 13, 14
    Transiciones: Se reconocen identificadores y se comprueba si son palabras clave.


Números (enteros y decimales):

    Estados: 15, 16, 17, 18, 19, 20, 21, 22, 23
    Transiciones: Se reconocen números enteros y decimales, incluyendo notación científica.

Cadenas:

    Estados: 24, 25
    Transiciones: Se reconocen cadenas delimitadas por comillas, y se extraen los literales de las cadenas.


Comentarios (de línea y de bloque):

    Estados: 26, 27, 28, 30, 32*/
    //Transiciones: Se reconocen comentarios de línea (//) y de bloque (/* ... */).

/*
Operadores aritméticos (+, -, *, /):

    Estados: 0, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42
    Transiciones: Se reconocen los operadores aritméticos y se generan los tokens correspondientes.

Símbolos de puntuación ({, }, (, ), ,, ., ;):

    Estados: 0, 36, 37, 38, 39, 40, 41, 42
    Transiciones: Se reconocen los símbolos de puntuación y se generan los tokens correspondientes.

*/

public class Scanner {

    // Mapa que asocia palabras reservadas con sus tipos de tokens correspondientes
    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and", TipoToken.AND);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("fun", TipoToken.FUN);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("or", TipoToken.OR);
        palabrasReservadas.put("print", TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("while", TipoToken.WHILE);
    }

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    public Scanner(String source) {
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {

        int estado = 0, linea = 0;
        String lexema = "";
        char c;

        for (int i = 0; i < source.length(); i++) {
            
            c = source.charAt(i);
            
            //Tiene que estár por fuera del switch porque sino llevaría mal la cuenta de las lineas
            if (c == '\n')
                linea++;

            switch (estado) {
                
                // Caso inicial
                case 0:
                    if (c == '>') {
                        estado = 1;
                        lexema += c;
                    } else if (c == '<') {
                        estado = 4;
                        lexema += c;
                    } else if (c == '=') {
                        estado = 7;
                        lexema += c;
                    } else if (c == '!') {
                        estado = 10;
                        lexema += c;
                    } else if (Character.isLetter(c)) {
                        estado = 13;
                        lexema += c;
                    } else if (Character.isDigit(c)) {
                        estado = 15;
                        lexema += c;
                    } else if (c == '"') {
                        estado = 24;
                        lexema += c;
                    } else if (c == '/') {
                        estado = 26;
                        lexema += c;
                    } else if (c == '+') {
                        lexema += c;
                        Token t = new Token(TipoToken.PLUS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '-') {
                        lexema += c;
                        Token t = new Token(TipoToken.MINUS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '*') {
                        lexema += c;
                        Token t = new Token(TipoToken.STAR, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '{') {
                        lexema += c;
                        Token t = new Token(TipoToken.LEFT_BRACE, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '}') {
                        lexema += c;
                        Token t = new Token(TipoToken.RIGHT_BRACE, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '(') {
                        lexema += c;
                        Token t = new Token(TipoToken.LEFT_PAREN, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == ')') {
                        lexema += c;
                        Token t = new Token(TipoToken.RIGHT_PAREN, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == ';') {
                        lexema += c;
                        Token t = new Token(TipoToken.SEMICOLON, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == ',') {
                        lexema += c;
                        Token t = new Token(TipoToken.COMMA, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '.') {
                        lexema += c;
                        Token t = new Token(TipoToken.DOT, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (!Character.isWhitespace(c)) {
                        // Caracter desconocido
                        error(linea + 1, "Caracter no perteneciente al lenguaje");
                    }
                    break;

                // Estados para operadores y asignación
                case 1:
                    if (c == '=') {
                        lexema += c;
                        Token t = new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else {
                        Token t = new Token(TipoToken.GREATER, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 4:
                    if (c == '=') {
                        lexema += c;
                        Token t = new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else {
                        Token t = new Token(TipoToken.LESS, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 7:
                    if (c == '=') {
                        lexema += c;
                        Token t = new Token(TipoToken.EQUAL_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else {
                        Token t = new Token(TipoToken.EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 10:
                    if (c == '=') {
                        lexema += c;
                        Token t = new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else {
                        Token t = new Token(TipoToken.BANG, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                // Estado para identificadores y palabras reservadas
                case 13:
                    if (Character.isLetterOrDigit(c)) {
                        estado = 13;
                        lexema += c;
                    } else {
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if (tt == null) {
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        } else {
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;

                    }
                    break;

                // Estados para números
                case 15:
                    if (Character.isDigit(c)) {
                        estado = 15;
                        lexema += c;
                    } else if (c == '.') {
                        estado = 16;
                        lexema += c;
                    } else if (c == 'E') {
                        estado = 18;
                        lexema += c;
                    } else {
                        Token t = new Token(TipoToken.NUMBER, lexema, Integer.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 16:
                    if (Character.isDigit(c)) {
                        estado = 17;
                        lexema += c;
                    }
                    break;

                case 17:
                    if (Character.isDigit(c)) {
                        estado = 17;
                        lexema += c;
                    } else if (c == 'E') {
                        estado = 18;
                        lexema += c;
                    } else {
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 18:
                    if (Character.isDigit(c)) {
                        estado = 20;
                        lexema += c;
                    } else if (c == '+' || c == '-') {
                        estado = 19;
                        lexema += c;
                    }
                    break;

                case 19:
                    if (Character.isDigit(c)) {
                        estado = 20;
                        lexema += c;
                    }
                    break;

                case 20:
                    if (Character.isDigit(c)) {
                        estado = 20;
                        lexema += c;
                    } else {
                        Token t = new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                // Estados para cadenas
                case 24:
                    if (c == '"') {
                        lexema += c;
                        Token t = new Token(TipoToken.STRING, lexema, lexema.substring(1, lexema.length() - 1));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                    } else if (c == '\n') {
                        error(linea + 1, "No se han cerrado las comillas");
                        i = source.length();
                    } else if (i == source.length() - 1) {
                        error(linea + 1, "No se han cerrado las comillas");
                    } else {
                        estado = 24;
                        lexema += c;
                    }
                    break;

                // Estados para comentarios
                case 26:
                    if (c == '*') {
                        estado = 27;
                        lexema += c;
                    } else if (c == '/') {
                        estado = 30;
                        lexema += c;
                    } else {
                        Token t = new Token(TipoToken.SLASH, lexema);
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;

                case 27:
                    if (c == '*') {
                        estado = 28;
                        lexema += c;
                    } else {
                        estado = 27;
                        lexema += c;
                    }
                    break;

                case 28:
                    if (c == '*') {
                        estado = 28;
                        lexema += c;
                    } else if (c == '/') {
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 27;
                        lexema += c;
                    }
                    break;

                case 30:
                    if (c == '\n') {
                        estado = 0;
                        lexema = "";
                    } else {
                        estado = 30;
                        lexema += c;
                    }
                    break;
            }
        }

        return tokens;
    }
}