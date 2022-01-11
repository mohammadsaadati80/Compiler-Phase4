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
import main.symbolTable.items.StructSymbolTableItem;
import main.visitor.Visitor;
import main.visitor.type.ExpressionTypeChecker;

import java.io.*;
import java.util.*;

public class CodeGenerator extends Visitor<String> {
    private final ExpressionTypeChecker expressionTypeChecker = new ExpressionTypeChecker();
    private String outputPath;
    private FileWriter currentFile;
    private int labelCnt = 0;
    private int tmpVarCnt = 0;
    private boolean isMain = false;
    private boolean isStruct = false;
    private FunctionDeclaration currentFunction;
    private StructDeclaration currentStruct;
    private final ArrayList<String> localVars = new ArrayList<>();


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

    private int slotOf(String identifier) {
        int count = 1;
        if(currentFunction == null) //todo not sure
            return tmpVarCnt++;
        for (int i = 0; i < localVars.size(); i++) {
            if (localVars.get(i).equals(identifier)) return count;
            count++;
        }
        return count + tmpVarCnt++; //todo or return count; ??
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
        try{
            String structKey = StructSymbolTableItem.START_KEY + structDeclaration.getStructName().getName();
            StructSymbolTableItem structSymbolTableItem = (StructSymbolTableItem)SymbolTable.root.getItem(structKey);
            SymbolTable.push(structSymbolTableItem.getStructSymbolTable());
        }catch (ItemNotFoundException e){//unreachable
        }
        createFile(structDeclaration.getStructName().getName());

        currentStruct = structDeclaration;
        isStruct = true;

        addCommand(".class public " + structDeclaration.getStructName().getName());
        addCommand(".super java/lang/Object");



        addCommand(".method public <init>()V");
        addCommand(".limit stack 128");
        addCommand(".limit locals 128");

        addCommand("aload_0");
        addCommand("invokespecial " + structDeclaration.getStructName().getName() + "/<init>()V");

        //todo chera moteghayyer ha visit nashodan?

        addCommand("return");
        addCommand(".end method");

        isStruct = false;
        currentStruct = null;

        SymbolTable.pop();
        return null;
    }

    @Override
    public String visit(FunctionDeclaration functionDeclaration) {
        try{
            String functionKey = FunctionSymbolTableItem.START_KEY + functionDeclaration.getFunctionName().getName();
            FunctionSymbolTableItem functionSymbolTableItem = (FunctionSymbolTableItem)SymbolTable.root.getItem(functionKey);
            SymbolTable.push(functionSymbolTableItem.getFunctionSymbolTable());
        }catch (ItemNotFoundException e){//unreachable
        }

        currentFunction = functionDeclaration;
        String funcName = functionDeclaration.getFunctionName().getName();


        for (VariableDeclaration variableDeclaration : functionDeclaration.getArgs())
            localVars.add(variableDeclaration.getVarName().getName()); //todo nabayad ghable for localVar Clear she?

        String command = "";
        command += ".method public " + funcName;

        FunctionSymbolTableItem fsti = null;
        try {
            fsti = (FunctionSymbolTableItem) SymbolTable.root.getItem("Function_" + funcName);
        } catch (ItemNotFoundException exception) {
            exception.printStackTrace();
        }

        Type returnType = fsti.getReturnType();
        ArrayList<Type> argTypes = fsti.getArgTypes();

        StringBuilder argList = new StringBuilder("(");
        for (Type t : argTypes) {
                argList.append(getTypeString(t));
        }
        argList.append(")");
        argList.append(getTypeString(returnType));
        command += argList.toString() + "\n";

        command += ".limit stack 128\n";
        command += ".limit locals 128\n";
        addCommand(command);

        functionDeclaration.getBody().accept(this);

        addCommand(".end method\n");
        currentFunction = null;
        localVars.clear();
        SymbolTable.pop();
        return null;
    }

    public String getTypeString(Type t) {
//        if (t instanceof IntType)
//            return "Ljava/lang/Integer;";
//        if (t instanceof BoolType)
//            return "Ljava/lang/Boolean;";
//        if (t instanceof ListType)
//            return "LList;";
//        if (t instanceof FptrType)
//            return "LFptr;";
//        if (t instanceof VoidType)
//            return "V";
//        //  TODO StructType Nemidonam Object bayad bahse ya khode class
////        if (t instanceof StructType)
////            return "Ljava/lang/Object;";
//        if (t instanceof StructType)
//            return "L" + ((StructType) t).getStructName().getName() + ";";
//        return null;
        if(t instanceof VoidType)
            return "V";
        else
            return "L" + getClass(t) + ";";
    }

    private String getClass(Type t) {
        if(t instanceof IntType){
            return "java/lang/Integer";
        }
        else if(t instanceof BoolType){
            return "java/lang/Boolean";
        }
        else if(t instanceof ListType){
            return "List";
        }
        else if(t instanceof FptrType){
            return "Fptr";
        }
        else if(t instanceof StructType){
            return ((StructType) t).getStructName().getName();
        }
        return "";
    }

    @Override
    public String visit(MainDeclaration mainDeclaration) {
        try{
            String functionKey = FunctionSymbolTableItem.START_KEY + "main";
            FunctionSymbolTableItem functionSymbolTableItem = (FunctionSymbolTableItem)SymbolTable.root.getItem(functionKey);
            SymbolTable.push(functionSymbolTableItem.getFunctionSymbolTable());
        }catch (ItemNotFoundException e){//unreachable
        }

//        addCommand(".class public Main\n");
//        addCommand(".super java/lang/Object\n");
//        addCommand(".method public static main([Ljava/lang/String;)V\n");
//        addCommand(".limit locals 128\n");
//        addCommand(".limit stack 128\n");
//
//        mainDeclaration.getBody().accept(this);
//
//        addCommand("return\n");
//        addCommand(".end method\n");

        isMain = true;
        addCommand(".class public Main\n");
        addCommand(".super java/lang/Object\n");

        addCommand(".method public <init>()V\n");
        addCommand(".limit stack 128\n");
        addCommand(".limit locals 128\n");
        addCommand("aload_0\n");
        addCommand("invokespecial java/lang/Object/<init>()V\n");
        addCommand("return\n");
        addCommand(".end method\n");

        addCommand(".method public static main([Ljava/lang/String;)V\n");
        addCommand(".limit locals 128\n");
        addCommand(".limit stack 128\n");

        addCommand("new Main\n");
        addCommand("dup\n");
        addCommand("invokespecial Main/<init>()V\n");
        addCommand("astore_1\n");
        mainDeclaration.getBody().accept(this);

        addCommand("return\n");
        addCommand(".end method\n");
        isMain = false;

        SymbolTable.pop();
        return null;
    }

    private void initializeVar(VariableDeclaration varDeclaration, boolean isField) {
        Type type = varDeclaration.getVarType();
        String name = varDeclaration.getVarName().getName();
        String structName = "";
        if(isField)
            structName = currentStruct.getStructName().getName();
        if(isField)
            addCommand("aload 0");
        addCommand(this.generateValue(true, null, type, name));
        if(type instanceof IntType)
            addCommand("invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;");
        else if(type instanceof BoolType)
            addCommand("invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;");
        if(isField)
            addCommand("putfield " + structName + "/" + name + " " + getTypeString(type));
        else
            addCommand("astore " + slotOf(varDeclaration.getVarName().getName()));
    }

    private String generateValue(boolean isInitialization, Expression expr, Type type, String name) {
        if(type instanceof IntType) {
            if(isInitialization)
                return this.visit(new IntValue(0));
            else
                return expr.accept(this);
        }
        else if(type instanceof BoolType) {
            if(isInitialization)
                return this.visit(new BoolValue(false));
            else
                return expr.accept(this);
        }
        else if(type instanceof StructType || type instanceof FptrType) {
            if(isInitialization)
                return "aconst_null";
            else
                return expr.accept(this);
        }
        else if(type instanceof ListType) {
            String commands = "";
            commands += "new java/util/ArrayList\n";
            commands += "dup\n";
            commands += "invokespecial java/util/ArrayList/<init>()V\n";
            int tempVar = slotOf("");
            commands += "astore " + tempVar + "\n";
//            if(isInitialization) {
//                commands += "aload " + tempVar + "\n";
//                commands += this.generateValue(true, null, ((ListType) type).getType(),name) + "\n";
//                if(((ListType) type).getType() instanceof IntType)
//                    commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
//                else if(((ListType) type).getType() instanceof BoolType)
//                    commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
//                commands += "invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z\n";
//                commands += "pop\n";
//            }

            commands += "new List\n";
            commands += "dup\n";
            commands += "aload " + tempVar + "\n";
            commands += "invokespecial List/<init>(Ljava/util/ArrayList;)V";
            --(this.tmpVarCnt);
            return commands;
        }
        return null;
    }

    @Override
    public String visit(VariableDeclaration variableDeclaration) {
//        if (currentFunction != null) localVars.add(variableDeclaration.getVarName().getName()); //todo halte dar struct boodan koo?
//        String command = "";
//        Type type = variableDeclaration.getVarType();
//        if (type instanceof IntType) {
//            command += "ldc 0\n";
//            command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
//        } else if (type instanceof BoolType) {
//            command += "ldc 0\n";
//            command += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
//        } else if (type instanceof FptrType)
//            command += "aconst_null\n";
//        return command;

        this.initializeVar(variableDeclaration, isStruct);
        if (currentFunction != null) localVars.add(variableDeclaration.getVarName().getName());
        return null;
    }

    @Override
    public String visit(SetGetVarDeclaration setGetVarDeclaration) {
        return null;
    }

    @Override
    public String visit(AssignmentStmt assignmentStmt) {
        String commands = this.visit(new BinaryExpression(assignmentStmt.getLValue(), assignmentStmt.getRValue(), BinaryOperator.assign));
        addCommand(commands);
        addCommand("pop");
        return null;
    }

    @Override
    public String visit(BlockStmt blockStmt) {
        for (Statement statement : blockStmt.getStatements()) statement.accept(this);
        return null;
    }

    @Override
    public String visit(ConditionalStmt conditionalStmt) {
        String elseLabel = getNewLabel();
        String exitLabel = getNewLabel();
        addCommand(conditionalStmt.getCondition().accept(this));
       // addCommand("invokevirtual java/lang/Boolean/booleanValue()Z\n");
        addCommand("ifeq " + elseLabel + "\n");
        conditionalStmt.getThenBody().accept(this);
        addCommand("goto " + exitLabel + "\n");
        addCommand(elseLabel + ":\n");
        addCommand("iconst_0\n");
        addCommand("pop\n");
        if (conditionalStmt.getElseBody() != null)
            conditionalStmt.getElseBody().accept(this);
        addCommand(exitLabel + ":\n");
        addCommand("iconst_0\n");
        addCommand("pop\n");
        return null;
//        String command = (conditionalStmt.getCondition().accept(this));
//        command += ("invokevirtual java/lang/Boolean/booleanValue()Z\n");
//        command += ("ifeq " + elseLabel + "\n");
//        command += (conditionalStmt.getThenBody().accept(this));
//        command += ("goto " + exitLabel + "\n");
//        command += (elseLabel + ":\n");
//        command += ("iconst_0\n");
//        command += ("pop\n");
//        if (conditionalStmt.getElseBody() != null)
//            command += (conditionalStmt.getElseBody().accept(this));
//        command += (exitLabel + ":\n");
//        command += ("iconst_0\n");
//        command += ("pop\n");
//        return command;
    }

    @Override
    public String visit(FunctionCallStmt functionCallStmt) {
        expressionTypeChecker.setInFunctionCallStmt(true);
        //addCommand(functionCallStmt.getFunctionCall().accept(this) + "pop\n"); //todo agar void bood ham pop konim?

        addCommand(functionCallStmt.getFunctionCall().accept(this));
        Type t = functionCallStmt.getFunctionCall().accept(expressionTypeChecker);
        if (!(t instanceof VoidType))
            addCommand("pop\n");

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
        if (returnStmt.getReturnedExpr() == null){
            addCommand("return\n");
            return null;
        }
        Type type = returnStmt.getReturnedExpr().accept(expressionTypeChecker);
        if(type instanceof VoidType) {
            if(currentFunction != null)
                if(!(currentFunction.getReturnType() instanceof VoidType)) {
                    addCommand(returnStmt.getReturnedExpr().accept(this));
                    addCommand("areturn\n");
                }
            addCommand("return\n");
        }
        else {
            addCommand(returnStmt.getReturnedExpr().accept(this));
            if(type instanceof IntType)
                addCommand("invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n");
            else if(type instanceof BoolType)
                addCommand("invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n");
            addCommand("areturn\n");
        }

//        if (returnStmt.getReturnedExpr() == null){
//            addCommand("return\n");
//            return null;
//        }
//        String command = returnStmt.getReturnedExpr().accept(this);
//        // TODO Ehtemalan kht paeen moshkel dare
//        Type expr_type = returnStmt.getReturnedExpr().accept(expressionTypeChecker);
//        if (expr_type instanceof VoidType) command += "return\n";
//        else command += "areturn\n";
//        addCommand(command);

        /*if (returnStmt.getReturnedExpr() == null){
            addCommand("return\n");
            return null;
        }
        Type retType = returnStmt.getReturnedExpr().accept(expressionTypeChecker);

        if (retType instanceof IntType) {
            addCommand("new java/lang/Integer");
            addCommand("dup");
            addCommand(returnStmt.getReturnedExpr().accept(this));
            addCommand("invokespecial java/lang/Integer/<init>(I)V");
            addCommand("areturn");
        }
        else if (retType instanceof BoolType) {
            addCommand("new java/lang/Boolean");
            addCommand("dup");
            addCommand(returnStmt.getReturnedExpr().accept(this));
            addCommand("invokespecial java/lang/Boolean/<init>(Z)V");
            addCommand("areturn");
        }
        else {
            addCommand(returnStmt.getReturnedExpr().accept(this));
            addCommand("areturn");
        }

        return null;*/

        return null;
    }

    @Override
    public String visit(LoopStmt loopStmt) {
        //todo
        return null;
    }

    @Override
    public String visit(VarDecStmt varDecStmt) {
        for (VariableDeclaration variableDeclaration : varDecStmt.getVars())
            variableDeclaration.accept(this);
        return null;
    }

    @Override
    public String visit(ListAppendStmt listAppendStmt) {
        expressionTypeChecker.setInFunctionCallStmt(true);
        addCommand(listAppendStmt.getListAppendExpr().accept(this));
        expressionTypeChecker.setInFunctionCallStmt(false);
        return null;
    }

    @Override
    public String visit(ListSizeStmt listSizeStmt) {
        addCommand(listSizeStmt.getListSizeExpr().accept(this));
        addCommand("pop");
        return null;
    }

    private String getNewLabel() {
        return "Label_" + labelCnt++;
    }

    private String underlineOrSpace(int slot) {
//        if (0 <= slot && slot <= 3) return "_";
//        else return " ";
        return " ";
    }

    /*private boolean areListsEqual(ListType firstOperand, ListType secondOperand) {
        if (firstOperand.getElementsTypes().size() != secondOperand.getElementsTypes().size())
            return false;
        ArrayList<ListNameType> firstOperandElementTypes = firstOperand.getElementsTypes();
        ArrayList<ListNameType> secondOperandElementTypes = secondOperand.getElementsTypes();

        for (int i = 0; i < firstOperandElementTypes.size(); i++) {
            if (firstOperandElementTypes.get(i).getType() instanceof ListType) {
                if (!(secondOperandElementTypes.get(i).getType() instanceof ListType))
                    return false;
                else if (!areListsEqual((ListType)firstOperandElementTypes.get(i).getType(),
                        (ListType)secondOperandElementTypes.get(i).getType()))
                    return false;
            }
            else {
                if (!firstOperandElementTypes.get(i).getType().toString()
                        .equals(secondOperandElementTypes.get(i).getType().toString()))
                    return false;
            }
        }

        return true;
    }*/

    @Override
    public String visit(BinaryExpression binaryExpression) {
        BinaryOperator operator = binaryExpression.getBinaryOperator();
        String commands = "";
        Type tl = binaryExpression.getFirstOperand().accept(expressionTypeChecker);
        Type tr = binaryExpression.getSecondOperand().accept(expressionTypeChecker);
        if (operator == BinaryOperator.add) {
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += "iadd\n";
            //commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        } else if (operator == BinaryOperator.sub) {
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += "isub\n";
            //commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        } else if (operator == BinaryOperator.mult) {
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += "imul\n";
            //commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        } else if (operator == BinaryOperator.div) {
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += "idiv\n";
            //commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        } else if ((operator == BinaryOperator.gt) || (operator == BinaryOperator.lt)) {
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Integer/intValue()I\n";

            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            if (operator == BinaryOperator.gt)
                commands += "if_icmple " + nFalse + "\n";
            else
                commands += "if_icmpge " + nFalse + "\n";
            commands += "iconst_1\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_0\n";
            commands += nAfter + ":\n";
            //commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
        } else if (operator == BinaryOperator.eq) {
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            if (tl instanceof IntType) {
                commands += binaryExpression.getFirstOperand().accept(this);
                //commands += "invokevirtual java/lang/Integer/intValue()I\n";
                commands += binaryExpression.getSecondOperand().accept(this);
                //commands += "invokevirtual java/lang/Integer/intValue()I\n";
                commands += "if_icmpne " + nFalse + "\n";
            }
            if (tl instanceof BoolType) {
                commands += binaryExpression.getFirstOperand().accept(this);
                //commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
                commands += binaryExpression.getSecondOperand().accept(this);
                //commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
                commands += "if_icmpne " + nFalse + "\n";
            }
            if (tl instanceof ListType || tl instanceof FptrType) {
                commands += binaryExpression.getFirstOperand().accept(this);
                commands += binaryExpression.getSecondOperand().accept(this);
                commands += "if_acmpne " + nFalse + "\n";
            }
            commands += "iconst_1\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_0\n";
            commands += nAfter + ":\n";
            //commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
            /*else if (operandsType instanceof ListType) {
                ListType firstOperand = (ListType) binaryExpression.getFirstOperand().accept(this.expressionTypeChecker);
                ListType secondOperand = (ListType) binaryExpression.getSecondOperand().accept(this.expressionTypeChecker);
                boolean listsAreEqual = areListsEqual(firstOperand, secondOperand);
                if (listsAreEqual)
                    commands += "ldc 1\n";
                else
                    commands += "ldc 0\n";
            }*/
        } else if (operator == BinaryOperator.and) {
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
            commands += "ifeq " + nFalse + "\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
            commands += "ifeq " + nFalse + "\n";
            commands += "iconst_1" + "\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_0" + "\n";
            commands += nAfter + ":\n";
            //commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
        } else if (operator == BinaryOperator.or) {
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            commands += binaryExpression.getFirstOperand().accept(this);
            //commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
            commands += "ifne " + nFalse + "\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            //commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
            commands += "ifne " + nFalse + "\n";
            commands += "iconst_0" + "\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_1" + "\n";
            commands += nAfter + ":\n";
            //commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n"; // todo comment another like this?
        } else if (operator == BinaryOperator.assign) {
            Type firstType = binaryExpression.getFirstOperand().accept(expressionTypeChecker);
            String secondOperandCommands = binaryExpression.getSecondOperand().accept(this);
            if (firstType instanceof ListType) {
                secondOperandCommands = "new List\n" + "dup\n" + binaryExpression.getSecondOperand().accept(this)
                        + "\n" + "invokespecial List/<init>(LList;)V\n";
            }
            if (binaryExpression.getFirstOperand() instanceof Identifier) { //todo not sure
                commands += secondOperandCommands + "\n";
                commands += "dup\n";
                if(firstType instanceof IntType)
                    commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
                else if(firstType instanceof BoolType)
                    commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
                int varSlot = slotOf(((Identifier) binaryExpression.getFirstOperand()).getName());
                commands += "astore" + underlineOrSpace(varSlot) + varSlot + "\n";

//                Type secondType = binaryExpression.getSecondOperand().accept(this.expressionTypeChecker);
//                int slot = slotOf(((Identifier) binaryExpression.getFirstOperand()).getName());
//
//                if (secondType instanceof IntType) {
//                    commands += "new java/lang/Integer\n";
//                    commands += "dup\n";
//                    commands += secondOperandCommands;
//                    commands += "invokespecial java/lang/Integer/<init>(I)V\n";
//                    commands += "dup\n";
//                    commands += "astore" + underlineOrSpace(slot) + slot + "\n";
//                    commands += "invokevirtual java/lang/Integer/intValue()I\n";
//                } else if (secondType instanceof BoolType) {
//                    commands += "new java/lang/Boolean\n";
//                    commands += "dup\n";
//                    commands += secondOperandCommands;
//                    commands += "invokespecial java/lang/Boolean/<init>(Z)V\n";
//                    commands += "dup\n";
//                    commands += "astore" + underlineOrSpace(slot) + slot + "\n";
//                    commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
//                } else {
//                    commands += secondOperandCommands;
//                    commands += "dup\n";
//                    commands += "astore" + underlineOrSpace(slot) + slot + "\n";
//                }
            } else if (binaryExpression.getFirstOperand() instanceof ListAccessByIndex) { //todo not sure
                commands += ((ListAccessByIndex) binaryExpression.getFirstOperand()).getInstance().accept(this) + "\n";
                commands += ((ListAccessByIndex) binaryExpression.getFirstOperand()).getIndex().accept(this) + "\n";
                commands += secondOperandCommands + "\n";
                commands += "dup_x2\n";
                if(firstType instanceof IntType)
                    commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
                else if(firstType instanceof BoolType)
                    commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
                commands += "invokevirtual List/setElement(ILjava/lang/Object;)V";
//                Type secondType = binaryExpression.getSecondOperand().accept(this.expressionTypeChecker);
//                ListAccessByIndex firstOperandListAccess = (ListAccessByIndex) binaryExpression.getFirstOperand();
//                this.tmpVarCnt++;
//                int tempSlot = slotOf("");
//                if (secondType instanceof IntType) {
//                    commands += "new java/lang/Integer\n";
//                    commands += "dup\n";
//                    commands += secondOperandCommands;
//                    commands += "invokespecial java/lang/Integer/<init>(I)V\n";
//                    commands += "astore" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//
//                    commands += firstOperandListAccess.getInstance().accept(this);
//                    commands += firstOperandListAccess.getIndex().accept(this);
//                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//                    commands += "invokevirtual List/setElement(ILjava/lang/Object;)V\n";
//
//                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//                    commands += "invokevirtual java/lang/Integer/intValue()I\n";
//                } else if (secondType instanceof BoolType) {
//                    commands += "new java/lang/Boolean\n";
//                    commands += "dup\n";
//                    commands += secondOperandCommands;
//                    commands += "invokespecial java/lang/Boolean/<init>(Z)V\n";
//                    commands += "astore" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//
//                    commands += firstOperandListAccess.getInstance().accept(this);
//                    commands += firstOperandListAccess.getIndex().accept(this);
//                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//                    commands += "invokevirtual List/setElement(ILjava/lang/Object;)V\n";
//
//                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//                    commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
//                } else {
//                    commands += secondOperandCommands;
//                    commands += "astore" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//
//                    commands += firstOperandListAccess.getInstance().accept(this);
//                    commands += firstOperandListAccess.getIndex().accept(this);
//                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//                    commands += "invokevirtual List/setElement(ILjava/lang/Object;)V\n";
//
//                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
//                }
//                this.tmpVarCnt--;
            } else if (binaryExpression.getFirstOperand() instanceof StructAccess) {
                //todo
            }
        }
        return commands;
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
        FunctionSymbolTableItem fsti = null;
        try {
            fsti = (FunctionSymbolTableItem) SymbolTable.root.getItem("Function_" + identifier.getName());
        } catch (ItemNotFoundException exception) {
        }
        String command = "";
        if (fsti == null) {
            Type type = identifier.accept(expressionTypeChecker);
            command += "aload " + slotOf(identifier.getName())+ "\n";
            if(type instanceof IntType)
                command += "invokevirtual java/lang/Integer/intValue()I\n";
            else if(type instanceof  BoolType)
                command += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
        }
        else {
            command += "new Fptr\n" +
                    "dup\n" +
                    (isMain ? "aload_1\n" : "aload_0\n") +
                    "ldc \"" + identifier.getName() + "\"\n" +
                    "invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V\n";
        }
        return command;
    }

    @Override
    public String visit(ListAccessByIndex listAccessByIndex) {
        String commands = "";
        commands += listAccessByIndex.getInstance().accept(this) + "\n";
        commands += listAccessByIndex.getIndex().accept(this) + "\n";
        Type type = listAccessByIndex.accept(expressionTypeChecker);
        commands += "invokevirtual List/getElement(I)Ljava/lang/Object;\n";
        commands += "checkcast " + getClass(type) + "\n";
        if(type instanceof IntType)
            commands += "invokevirtual java/lang/Integer/intValue()I\n";
        else if(type instanceof BoolType)
            commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
        return commands;
    }

    @Override
    public String visit(FunctionCall functionCall) {
        String commands = "";
        commands += functionCall.getInstance().accept(this);
        commands += "new java/util/ArrayList\n";
        commands += "dup\n";
        commands += "invokespecial java/util/ArrayList/<init>()V\n";
        int tempVar = slotOf("");
        commands += "astore " + tempVar + "\n";
        for(Expression arg : functionCall.getArgs()) {
            commands += "aload " + tempVar + "\n";
            Type argType = arg.accept(expressionTypeChecker);
            if(argType instanceof ListType) {
                commands += "new List\n";
                commands += "dup\n";
            }
            commands += arg.accept(this);
            if(argType instanceof IntType)
                commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
            else if(argType instanceof BoolType)
                commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
            else if(argType instanceof ListType) {
                commands += "invokespecial List/<init>(LList;)V\n";
            }
            commands += "invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z\n";
            commands += "pop\n";
        }
        commands += "aload " + tempVar + "\n";
        commands += "invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;\n";
        Type type = functionCall.accept(expressionTypeChecker);
        if(!(type instanceof VoidType))
            commands += "checkcast " + getClass(type) + "\n";
        if(type instanceof IntType)
            commands += "invokevirtual java/lang/Integer/intValue()I" + "\n";
        else if(type instanceof  BoolType)
            commands += "invokevirtual java/lang/Boolean/booleanValue()Z" + "\n";
        --(this.tmpVarCnt); //todo not sure need check why?
        return commands;
    }

    @Override
    public String visit(ListSize listSize) {
        String command = listSize.getArg().accept(this);
        command += "invokevirtual List/getSize()I\n";
        command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n"; //todo comment? like Int & Bool visitor
        return command;
    }

    @Override
    public String visit(ListAppend listAppend) { // todo not sure
        String command = listAppend.getListArg().accept(this);
        command += "dup\n";
        command += listAppend.getElementArg().accept(this);
        command += "invokevirtual List/addElement(Ljava/lang/Object;)V\n";
        return command;
    }

    @Override
    public String visit(IntValue intValue) {
        String command = "ldc " + intValue.getConstant() + "\n";
        //command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        return command;
    }

    @Override
    public String visit(BoolValue boolValue) {
        String command = "ldc " + (boolValue.getConstant() ? 1 : 0) + "\n";
        //command += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
        return command;
    }

    @Override
    public String visit(ExprInPar exprInPar) {
        return exprInPar.getInputs().get(0).accept(this);
    }
}
