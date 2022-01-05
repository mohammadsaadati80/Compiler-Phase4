package main.visitor.codeGenerator;

import main.ast.nodes.*;
import main.ast.nodes.declaration.*;
import main.ast.nodes.declaration.struct.*;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.operators.*;
import main.ast.nodes.expression.values.*;
import main.ast.nodes.expression.values.primitive.*;
import main.ast.nodes.statement.*;
import main.ast.types.*;
import main.ast.types.primitives.*;
import main.symbolTable.*;
import main.symbolTable.exceptions.*;
import main.symbolTable.items.FunctionSymbolTableItem;
import main.visitor.Visitor;
import main.visitor.type.ExpressionTypeChecker;

import java.io.*;
import java.util.*;

public class CodeGenerator extends Visitor<String> {
    private int lastSlot = 0;
    private String outputPath;
    private FileWriter currentFile;
    private FunctionDeclaration currentFunction;
    ExpressionTypeChecker expressionTypeChecker = new ExpressionTypeChecker();

    private void copyFile(String toBeCopied, String toBePasted) {
        try {
            File readingFile = new File(toBeCopied);
            File writingFile = new File(toBePasted);
            InputStream readingFileStream = new FileInputStream(readingFile);
            OutputStream writingFileStream = new FileOutputStream(writingFile);
            byte[] buffer = new byte[1024];
            int readLength;
            while ((readLength = readingFileStream.read(buffer)) > 0)
                writingFileStream.write(buffer, 0, readLength);
            readingFileStream.close();
            writingFileStream.close();
        } catch (IOException e) {//unreachable
        }
    }

    private void prepareOutputFolder() {
        this.outputPath = "output/";
        String jasminPath = "utilities/jarFiles/jasmin.jar";
        String listClassPath = "utilities/codeGenerationUtilityClasses/List.j";
        String fptrClassPath = "utilities/codeGenerationUtilityClasses/Fptr.j";
        try {
            File directory = new File(this.outputPath);
            File[] files = directory.listFiles();
            if (files != null)
                for (File file : files)
                    file.delete();
            directory.mkdir();
        } catch (SecurityException e) {//unreachable

        }
        copyFile(jasminPath, this.outputPath + "jasmin.jar");
        copyFile(listClassPath, this.outputPath + "List.j");
        copyFile(fptrClassPath, this.outputPath + "Fptr.j");
    }

    private void createFile(String name) {
        try {
            String path = this.outputPath + name + ".j";
            File file = new File(path);
            file.createNewFile();
            this.currentFile = new FileWriter(path);
        } catch (IOException e) {//never reached
        }
    }

    private void addCommand(String command) {
        try {
            command = String.join("\n\t\t", command.split("\n"));
            if (command.startsWith("Label_"))
                this.currentFile.write("\t" + command + "\n");
            else if (command.startsWith("."))
                this.currentFile.write(command + "\n");
            else
                this.currentFile.write("\t\t" + command + "\n");
            this.currentFile.flush();
        } catch (IOException e) {//unreachable

        }
    }

    private void addStaticMainMethod() {
        addCommand(".method public static main([Ljava/lang/String;)V");
        addCommand(".limit stack 128");
        addCommand(".limit locals 128");
        addCommand("new Main");
        addCommand("invokespecial Main/<init>()V");
        addCommand("return");
        addCommand(".end method");
    }

    private int slotOf(String identifier) throws ItemNotFoundException {
        //TODO
        /*int slot = 1;
        for(VariableDeclaration var: currentFunction.getArgs()){
            if(var.getVarName().getName().equals(identifier)) return slot;
            slot++;
        }
        for(VariableDeclaration var: ((FunctionSymbolTableItem) SymbolTable.root.getItem(identifier)).get){
            if(var.getVarName().getName().equals(identifier))
                return i;
            i++;
        }
        if(identifier.equals("")){
            if(last_slot == 0)
                last_slot = i;
            else
                last_slot++;
        }
        return last_slot;*/
        return 0;
    }

    @Override
    public String visit(Program program) {
        prepareOutputFolder();

        for (StructDeclaration structDeclaration : program.getStructs()) {
            structDeclaration.accept(this);
        }

        createFile("Main");

        program.getMain().accept(this);

        for (FunctionDeclaration functionDeclaration : program.getFunctions()) {
            functionDeclaration.accept(this);
        }
        return null;
    }

    @Override
    public String visit(StructDeclaration structDeclaration) {
        createFile(structDeclaration.getStructName().getName());
        //todo
        return null;
    }

    @Override
    public String visit(FunctionDeclaration functionDeclaration) {



        return null;
    }

    @Override
    public String visit(MainDeclaration mainDeclaration) {
        addCommand(".class public Main\n");
        addCommand(".super java/lang/Object\n");
        addCommand(".method public static main([Ljava/lang/String;)V\n");
        addCommand(".limit locals 128\n");
        addCommand(".limit stack 128\n");

        mainDeclaration.getBody().accept(this);

        addCommand("return\n");
        addCommand(".end method\n");

        return null;
    }

    @Override
    public String visit(VariableDeclaration variableDeclaration) {
        String command = "";
        Type type = variableDeclaration.getVarType();
        if (type instanceof IntType) {
            command += "ldc 0\n";
            command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        } else if (type instanceof BoolType) {
            command += "ldc 0\n";
            command += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
        } else if (type instanceof FptrType)
            command += "aconst_null\n";
        return command;
    }

    @Override
    public String visit(SetGetVarDeclaration setGetVarDeclaration) {
        return null;
    }

    @Override
    public String visit(AssignmentStmt assignmentStmt) {
        //todo
        return null;
    }

    @Override
    public String visit(BlockStmt blockStmt) {
        for (Statement statement : blockStmt.getStatements()) statement.accept(this);
        return null;
    }

    @Override
    public String visit(ConditionalStmt conditionalStmt) {
        //todo
        return null;
    }

    @Override
    public String visit(FunctionCallStmt functionCallStmt) {
        expressionTypeChecker.setInFunctionCallStmt(true);
        addCommand(functionCallStmt.getFunctionCall().accept(this) + "pop\n");
        expressionTypeChecker.setInFunctionCallStmt(false);
        return null;
    }

    @Override
    public String visit(DisplayStmt displayStmt) {
        addCommand("getstatic java/lang/System/out Ljava/io/PrintStream;");
        Type argType = displayStmt.getArg().accept(expressionTypeChecker);
        String commandsOfArg = displayStmt.getArg().accept(this);

        addCommand(commandsOfArg);
        if (argType instanceof IntType)
            addCommand("invokevirtual java/io/PrintStream/println(I)V");
        if (argType instanceof BoolType)
            addCommand("invokevirtual java/io/PrintStream/println(Z)V");

        return null;
    }

    @Override
    public String visit(ReturnStmt returnStmt) {
        String command = returnStmt.getReturnedExpr().accept(this);
        Type expr_type = returnStmt.getReturnedExpr().accept(expressionTypeChecker);
        if(expr_type instanceof VoidType) command += "return\n";
        else command += "areturn\n";
        addCommand(command);
        return null;
    }

    @Override
    public String visit(LoopStmt loopStmt) {
        //todo
        return null;
    }

    @Override
    public String visit(VarDecStmt varDecStmt) {
        //todo
        return null;
    }

    @Override
    public String visit(ListAppendStmt listAppendStmt) {
        return listAppendStmt.getListAppendExpr().accept(this);
    }

    @Override
    public String visit(ListSizeStmt listSizeStmt) {
        //todo
        return null;
    }

    @Override
    public String visit(BinaryExpression binaryExpression) {
        //todo
        return null;
    }

    @Override
    public String visit(UnaryExpression unaryExpression) {
        return null;
    }

    @Override
    public String visit(StructAccess structAccess) {
        //todo
        return null;
    }

    @Override
    public String visit(Identifier identifier) {
        //todo
        return null;
    }

    @Override
    public String visit(ListAccessByIndex listAccessByIndex) {
        String command = listAccessByIndex.getInstance().accept(this);
        command += listAccessByIndex.getIndex().accept(this);
        command += "invokevirtual java/lang/Integer/intValue()I\n";
        command += "invokevirtual List/getElement(I)Ljava/lang/Object;\n";
        command += "checkcast java/lang/Integer\n";
        return command;
    }

    @Override
    public String visit(FunctionCall functionCall) {
        //todo
        return null;
    }

    @Override
    public String visit(ListSize listSize) {
        String command = listSize.accept(this);
        command += "invokevirtual List/getSize()I\n";
        command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        return command;
    }

    @Override
    public String visit(ListAppend listAppend) {
        //todo
        return null;
    }

    @Override
    public String visit(IntValue intValue) {
        String command = "ldc " + intValue.getConstant() + "\n";
        command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        return command;
    }

    @Override
    public String visit(BoolValue boolValue) {
        String command = "ldc " + (boolValue.getConstant() ? 1 : 0) + "\n";
        command += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
        return command;
    }

    @Override
    public String visit(ExprInPar exprInPar) {
        return exprInPar.getInputs().get(0).accept(this);
    }
}
