package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Object resolver(TablaSimbolos tablasimbolos) {

        Object rightResuelto = right.resolver(tablasimbolos);

        return switch (operator.getLexema()) {
            case "!" -> hacerNegacionLogica(rightResuelto);
            case "-" -> hacerNegacionNumerica(rightResuelto);
            default -> throw new RuntimeException();
        };
        
    }

    private boolean hacerNegacionLogica(Object operador) {
        if (operador instanceof Boolean) {
            return !(Boolean) operador;
        } else {
            throw new RuntimeException();
        }
    }

    private Number hacerNegacionNumerica(Object operador) {
        if (operador instanceof Number) {
            return -1 * ((Number) operador).doubleValue();
        } else {
            throw new RuntimeException();
        }
    }

}
