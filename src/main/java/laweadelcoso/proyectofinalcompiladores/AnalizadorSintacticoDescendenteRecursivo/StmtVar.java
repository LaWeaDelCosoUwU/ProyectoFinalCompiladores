package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

import laweadelcoso.proyectofinalcompiladores.AnalizadorLexico.Token;

public class StmtVar extends Statement {
    private final Token name;
    private final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    public Token getName() {
        return name;
    }

    public Expression getInitializer() {
        return initializer;
    }


    @Override
    public void execute(TablaSimbolos tablasimbolos) {

        if(tablasimbolos.existeIdentificador(name.getLexema())){
            throw new RuntimeException();
        }else if(initializer == null){
            tablasimbolos.asignar(name.getLexema(),null);
        }else{
            tablasimbolos.asignar(name.getLexema(),initializer.resolver(tablasimbolos));
        }

    }

}
