package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }


    public Expression getLeft() {
        return left;
    }

    public Token getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Object resolver(TablaSimbolos tablasimbolos) {

        //Porque puede que alguno de los lados sean otras expresiones no literales
        Object leftResuelto = left.resolver(tablasimbolos);
        Object rightResuelto = right.resolver(tablasimbolos);

        return switch (operator.getLexema()) {
            case "+" -> hacerOperacionAritmetica(leftResuelto, rightResuelto, "suma");
            case "-" -> hacerOperacionAritmetica(leftResuelto, rightResuelto, "resta");
            case "==" -> esIgual(leftResuelto, rightResuelto);
            case "!=" -> !esIgual(leftResuelto, rightResuelto);
            case "*" -> hacerOperacionAritmetica(leftResuelto, rightResuelto, "multiplicación");
            case "/" -> hacerOperacionDivision(leftResuelto, rightResuelto);
            case "<", ">", "<=", ">=" -> hacerComparasion(leftResuelto, rightResuelto, operator.getLexema());
            default -> throw new RuntimeException();
        };
    }

    private boolean esIgual(Object leftResuelto, Object rightResuelto) {
        if(leftResuelto instanceof Number && rightResuelto instanceof Number){
            return ((Number) leftResuelto).doubleValue() == ((Number) rightResuelto).doubleValue();
        }else if (leftResuelto instanceof String && rightResuelto instanceof String){
            return leftResuelto.equals(rightResuelto);
        }else if (leftResuelto instanceof Boolean && rightResuelto instanceof Boolean){
            return (Boolean) leftResuelto == (Boolean) rightResuelto;
        }else{
            throw new RuntimeException();
        }
    }

    private Number hacerOperacionDivision(Object leftResuelto, Object rightResuelto) {
        if (leftResuelto instanceof Number && rightResuelto instanceof Number) {
            double divisor = ((Number) rightResuelto).doubleValue();
            if (divisor != 0.0) {
                return ((Number) leftResuelto).doubleValue() / divisor;
            } else {
                throw new RuntimeException();
            }
        } else {
            throw new RuntimeException();
        }
    }

    private Number hacerOperacionAritmetica(Object leftResuelto, Object rightResuelto, String operacion) {

        double resultado = 0.0;

        if (leftResuelto instanceof Number && rightResuelto instanceof Number) {
            resultado = switch (operacion) {
                case "suma" -> ((Number) leftResuelto).doubleValue() + ((Number) rightResuelto).doubleValue();
                case "resta" -> ((Number) leftResuelto).doubleValue() - ((Number) rightResuelto).doubleValue();
                case "multiplicación" -> ((Number) leftResuelto).doubleValue() * ((Number) rightResuelto).doubleValue();
                default -> resultado;
            };
            return resultado;
        } else {
            throw new RuntimeException();
        }
    }

    //Está generalizada para todo aquello que se pueda comparar, no se si funcione bien
    //jaja se pinta de verde arriba porque puse "todo"
    private boolean hacerComparasion(Object leftResuelto, Object rightResuelto, String operador) {
        if (left instanceof Comparable<?> && right instanceof Comparable<?>) {
            @SuppressWarnings("unchecked")
            Comparable<Object> comparableLeftResuelto = (Comparable<Object>) leftResuelto;
            return switch (operador) {
                case "<" -> comparableLeftResuelto.compareTo(rightResuelto) < 0;
                case ">" -> comparableLeftResuelto.compareTo(rightResuelto) > 0;
                case "<=" -> comparableLeftResuelto.compareTo(rightResuelto) <= 0;
                case ">=" -> comparableLeftResuelto.compareTo(rightResuelto) >= 0;
                default -> false;
            };
        } else {
            throw new RuntimeException();
        }
    }

}
