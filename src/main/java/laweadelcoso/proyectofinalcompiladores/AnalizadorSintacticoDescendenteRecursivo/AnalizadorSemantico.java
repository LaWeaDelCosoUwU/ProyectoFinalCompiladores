package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import java.util.ArrayList;
import java.util.List;
import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class AnalizadorSemantico{

    private final TablaSimbolos tablaSimbolosPrincipal = new TablaSimbolos();
    //private boolean hayErroresSemanticos = false; iba a haber un metodo para lo de los errores, pero pues ya no hubo

    public AnalizadorSemantico(List<Statement> arbol) {
        analizar(arbol);
    }

    private void analizar(List<Statement> statements) {
        for (Statement statement : statements)
            statement.execute(tablaSimbolosPrincipal);
    }

}

    //Estaba haciendo esto hasta que me acordé que justo estas cosas deben de ir en en cada clase según corresponda, y porque me estaba quedando muy largo y redundante esto
    //El codigo que me fue util fue implementado en esas clases
    /*private void analizarStatement(Statement statement) {
        if (statement instanceof StmtVar) {
            analizarVarDeclaration((StmtVar) statement);
        } else if (statement instanceof StmtFunction) {
            analizarFunctionDeclaration((StmtFunction) statement);
        } else if (statement instanceof StmtBlock) {
            analizarBlock((StmtBlock) statement);
        } else if (statement instanceof StmtExpression) {
            analizarExpressionStatement((StmtExpression) statement);
        } else if (statement instanceof StmtIf) {
            analizarIfStatement((StmtIf) statement);
        } else if (statement instanceof StmtLoop) {
            analizarLoopStatement((StmtLoop) statement);
        } else if (statement instanceof StmtPrint) {
            analizarPrintStatement((StmtPrint) statement);
        } else if (statement instanceof StmtReturn) {
            analizarReturnStatement((StmtReturn) statement);
        }else{
            System.out.println("What?");
        }
    }

    private void analizarVarDeclaration(StmtVar statement) {

        if (tablaSimbolosPrincipal.existeIdentificador(statement.getName().getLexema())) {
            System.out.println("Duplicidad en varibale");
        } else {
            tablaSimbolosPrincipal.asignar(statement.getName().getLexema(),statement.getInitializer());
        }

    }

    private void analizarFunctionDeclaration(StmtFunction statement) {
        //Se ve muy feo de hacer así que se queda así
    }

    private void analizarBlock(StmtBlock statements) {

        for (Statement statement : statements.getStatements()) {
            analizarStatement(statement);
        }

    }

    private void analizarExpressionStatement(StmtExpression statement) {
    }

    private void analizarIfStatement(StmtIf statement) {
        
    }

    private void analizarLoopStatement(StmtLoop statement) {
    }

    private void analizarPrintStatement(StmtPrint statement) {

        if(statement.getExpression() instanceof ExprLiteral){
            System.out.println(statement.getExpression().toString().substring(1, statement.getExpression().toString().length()-1));
        }else if(statement.getExpression() instanceof ExprVariable){
            System.out.println(""+tablaSimbolosPrincipal.obtener(((ExprVariable) statement.getExpression()).getName().getLexema()));
        }else{
            System.err.println("La cadena QUIZA TIENE CONCATENACIONES, o alguna cosa que no considere y por lo tanto es automaticamente un error");
        }

    }

    private void analizarReturnStatement(StmtReturn statement) {
    }

    private void analizarStatementBlock(Statement statement) {
        if (statement instanceof StmtVar) {
            analizarVarDeclaration((StmtVar) statement);
        } else if (statement instanceof StmtFunction) {
            analizarFunctionDeclaration((StmtFunction) statement);
        } else if (statement instanceof StmtBlock) {
            analizarBlock((StmtBlock) statement);
        } else if (statement instanceof StmtExpression) {
            analizarExpressionStatement((StmtExpression) statement);
        } else if (statement instanceof StmtIf) {
            analizarIfStatement((StmtIf) statement);
        } else if (statement instanceof StmtLoop) {
            analizarLoopStatement((StmtLoop) statement);
        } else if (statement instanceof StmtPrint) {
            analizarPrintStatement((StmtPrint) statement);
        } else if (statement instanceof StmtReturn) {
            analizarReturnStatement((StmtReturn) statement);
        }else{
            System.out.println("What?");
        }
    }

    private void analizarExpression(Expression expression) {
        if (expression instanceof ExprAssign) {
            analizarExpressionAssignment((ExprAssign) expression);
        } else if (expression instanceof ExprBinary) {
            analizarExpressionBinary((ExprBinary) expression);
        } else if (expression instanceof ExprCallFunction) {
            analizarCallFunction((ExprCallFunction) expression);
        } else if (expression instanceof ExprGrouping) {
            analizarExpressionGrouping(((ExprGrouping) expression).getExpression());
        } else if (expression instanceof ExprLiteral) {
            // No se requiere un análisis adicional para literales
        } else if (expression instanceof ExprUnary) {
            analizarExpressionUnary((ExprUnary) expression);
        } else if (expression instanceof ExprVariable) {
            analizarVariableExpression((ExprVariable) expression);
        } else if (expression instanceof ExprLogical) {
            analizarExpressionLogical((ExprLogical) expression);
        }
    }

    private void analizarExpressionAssignment(ExprAssign expression) {
    }

    private void analizarExpressionBinary(ExprBinary expression) {
    }

    private void analizarCallFunction(ExprCallFunction expression) {
    }

    private void analizarExpressionGrouping(Expression expression) {
    }

    private void analizarExpressionUnary(ExprUnary expression) {
    }

    private void analizarVariableExpression(ExprVariable expression) {
    }

    private void analizarExpressionLogical(ExprLogical expression) {
    }

}*/