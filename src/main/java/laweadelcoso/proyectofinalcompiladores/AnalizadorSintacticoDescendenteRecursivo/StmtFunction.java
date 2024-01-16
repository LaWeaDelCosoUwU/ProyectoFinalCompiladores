package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

import java.util.List;

public class StmtFunction extends Statement {
    private final Token name;
    private final List<Token> params;
    private final StmtBlock body;

    StmtFunction(Token name, List<Token> params, StmtBlock body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    public Token getName() {
        return name;
    }

    public List<Token> getParams() {
        return params;
    }

    public StmtBlock getBody() {
        return body;
    }

    @Override
    public void execute(TablaSimbolos tablasimbolos) {
//      Se ve feo esto, a ver si me da tiempo al rato, prefiero que quede el resto mejor, a que las cosas queden medio feas por meterme con funciones
    }

}
