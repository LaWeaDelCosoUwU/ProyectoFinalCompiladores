package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ExprAssign extends Expression{
    final Token name;
    final Expression value;

    ExprAssign(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public Token getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public Object resolver(TablaSimbolos tablasimbolos) {

        if (tablasimbolos.existeIdentificador(name.getLexema())){
            tablasimbolos.asignar(name.getLexema(), value.resolver(tablasimbolos));
        } else{
            throw new RuntimeException("Variable no declarada");
        }

        return null;
    }
}
