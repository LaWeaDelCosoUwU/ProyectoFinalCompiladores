package laweadelcoso.proyectofinalcompiladores.AnalizadorSintacticoDescendenteRecursivo;

public abstract class Statement {

    //Tuve que meter la tabla cómo argumento porque se me olvidó
    public abstract void execute(TablaSimbolos tablasimbolos);

}
