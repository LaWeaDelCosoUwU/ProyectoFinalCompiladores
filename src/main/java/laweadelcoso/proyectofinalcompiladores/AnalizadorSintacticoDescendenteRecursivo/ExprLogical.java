package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object resolver(TablaSimbolos tablasimbolos) {

        //Porque puede que alguno de los lados sean otras expresiones no literales
        Object leftResuelto = left.resolver(tablasimbolos);
        Object rightResuelto = right.resolver(tablasimbolos);

        return switch (operator.getLexema()) {
//            case "==" -> esIgual(leftResuelto, rightResuelto); Esto era Binary, no logical
//            case "!=" -> !esIgual(leftResuelto, rightResuelto); Lo mismo para aca. Me confundí, porque regresan o true o false :c
            case "and" -> hacerAND(leftResuelto, rightResuelto);
            case "or" -> hacerOR(leftResuelto, rightResuelto);
            default -> null;
        };

    }

//    private boolean esIgual(Object leftResuelto, Object rightResuelto) {
//        if(leftResuelto instanceof Number && rightResuelto instanceof Number){
//            return ((Number) leftResuelto).doubleValue() == ((Number) rightResuelto).doubleValue();
//        }else if (leftResuelto instanceof String && rightResuelto instanceof String){
//            return leftResuelto.equals(rightResuelto);
//        }else if (leftResuelto instanceof Boolean && rightResuelto instanceof Boolean){
//            return (Boolean) leftResuelto == (Boolean) rightResuelto;
//        }else{
//            throw new RuntimeException();//Creo que esto es mejor, de ahora en adelante esto voy a usar XD
//        }
//    }

    private boolean hacerAND(Object leftResuelto, Object rightResuelto) {
        if (leftResuelto instanceof Boolean && rightResuelto instanceof Boolean){
            return (Boolean) leftResuelto && (Boolean) rightResuelto;
        }else{
            System.err.println("No se puede");
            System.exit(1);
            return false;
        }
    }

    private boolean hacerOR(Object leftResuelto, Object rightResuelto) {
        if (leftResuelto instanceof Boolean && rightResuelto instanceof Boolean){
            return (Boolean) leftResuelto || (Boolean) rightResuelto;
        }else{
            System.err.println("No se puede");
            System.exit(1);
            return false;
        }
    }

}

