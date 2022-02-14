package main.visitor.codeGenerator;

import main.ast.nodes.*;
import main.ast.nodes.declaration.*;
import main.ast.nodes.declaration.struct.*;
import main.ast.nodes.expression.*;
import main.ast.nodes.expression.operators.*;
import main.ast.nodes.expression.values.primitive.*;
import main.ast.nodes.statement.*;
import main.ast.types.*;
import main.ast.types.primitives.*;
import main.symbolTable.*;
import main.symbolTable.exceptions.*;
import main.symbolTable.items.FunctionSymbolTableItem;
import main.symbolTable.items.StructSymbolTableItem;
import main.symbolTable.items.VariableSymbolTableItem;
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
    private boolean structFiled;
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
        if (currentFunction == null)
            return tmpVarCnt++;
        for (String localVar : localVars) {
            if (localVar.equals(identifier)) return count;
            count++;
        }
        return count + tmpVarCnt++;
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
        try {
            String structKey = StructSymbolTableItem.START_KEY + structDeclaration.getStructName().getName();
            StructSymbolTableItem structSymbolTableItem = (StructSymbolTableItem) SymbolTable.root.getItem(structKey);
            SymbolTable.push(structSymbolTableItem.getStructSymbolTable());
        } catch (ItemNotFoundException e) {//unreachable
        }
        createFile(structDeclaration.getStructName().getName());

        currentStruct = structDeclaration;
        isStruct = true;

        addCommand(".class public " + structDeclaration.getStructName().getName());
        addCommand(".super java/lang/Object");

        structFiled = true;
        structDeclaration.getBody().accept(this);
        structFiled = false;

        addCommand(".method public <init>()V");
        addCommand(".limit stack 128");
        addCommand(".limit locals 128");

        addCommand("aload_0");
        addCommand("invokespecial java/lang/Object/<init>()V");

        structDeclaration.getBody().accept(this);

        addCommand("return");
        addCommand(".end method");

        isStruct = false;
        currentStruct = null;

        SymbolTable.pop();
        return null;
    }

    @Override
    public String visit(FunctionDeclaration functionDeclaration) {
        try {
            String functionKey = FunctionSymbolTableItem.START_KEY + functionDeclaration.getFunctionName().getName();
            FunctionSymbolTableItem functionSymbolTableItem = (FunctionSymbolTableItem) SymbolTable.root.getItem(functionKey);
            SymbolTable.push(functionSymbolTableItem.getFunctionSymbolTable());
        } catch (ItemNotFoundException e) {//unreachable
        }

        currentFunction = functionDeclaration;
        String funcName = functionDeclaration.getFunctionName().getName();


        for (VariableDeclaration variableDeclaration : functionDeclaration.getArgs())
            localVars.add(variableDeclaration.getVarName().getName());

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

        if (functionDeclaration.getReturnType() instanceof VoidType){
            if (functionDeclaration.getBody() instanceof BlockStmt){
                BlockStmt blockStmt = (BlockStmt) functionDeclaration.getBody();
                if (blockStmt.getStatements().size() == 0) addCommand("return\n");
                else if(!(blockStmt.getStatements().get(blockStmt.getStatements().size() - 1) instanceof ReturnStmt))
                        addCommand("return\n");
            }
        }

        addCommand(".end method\n");
        currentFunction = null;
        localVars.clear();
        tmpVarCnt = 0;
        SymbolTable.pop();
        return null;
    }

    public String getTypeString(Type t) {
        if (t instanceof VoidType) return "V";
        else return "L" + getClass(t) + ";";
    }

    private String getClass(Type t) {
        if (t instanceof IntType) return "java/lang/Integer";
        else if (t instanceof BoolType) return "java/lang/Boolean";
        else if (t instanceof ListType) return "List";
        else if (t instanceof FptrType) return "Fptr";
        else if (t instanceof StructType) return ((StructType) t).getStructName().getName();
        return "";
    }

    @Override
    public String visit(MainDeclaration mainDeclaration) {
        try {
            String functionKey = FunctionSymbolTableItem.START_KEY + "main";
            FunctionSymbolTableItem functionSymbolTableItem = (FunctionSymbolTableItem) SymbolTable.root.getItem(functionKey);
            SymbolTable.push(functionSymbolTableItem.getFunctionSymbolTable());
        } catch (ItemNotFoundException e) {//unreachable
        }

        currentFunction = new FunctionDeclaration();
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
        addCommand("astore_0\n");

        mainDeclaration.getBody().accept(this);

        addCommand("return\n");
        addCommand(".end method\n");

        localVars.clear();
        tmpVarCnt = 0;

        SymbolTable.pop();
        return null;
    }

    private String generateValue(boolean haveNotDefaultValue, Expression expr, Type type) {
        if (type instanceof BoolType) {
            if (haveNotDefaultValue) return this.visit(new BoolValue(false));
            else return expr.accept(this);
        } else if (type instanceof IntType) {
            if (haveNotDefaultValue) return this.visit(new IntValue(0));
            else return expr.accept(this);
        } else if (type instanceof FptrType) {
            if (haveNotDefaultValue) return "aconst_null";
            else return expr.accept(this);
        } else if (type instanceof ListType) {
            String commands = "";
            commands += "new java/util/ArrayList\n";
            commands += "dup\n";
            commands += "invokespecial java/util/ArrayList/<init>()V\n";
            int tempVar = slotOf("");
            commands += "astore " + tempVar + "\n";
            commands += "new List\n";
            commands += "dup\n";
            commands += "aload " + tempVar + "\n";
            commands += "invokespecial List/<init>(Ljava/util/ArrayList;)V";
            tmpVarCnt--;
            return commands;
        }
        return null;
    }

    @Override
    public String visit(VariableDeclaration variableDeclaration) {
        if (currentFunction != null) localVars.add(variableDeclaration.getVarName().getName());
        Type type = variableDeclaration.getVarType();
        String name = variableDeclaration.getVarName().getName();
        if (structFiled) {
            addCommand(".field public " + name + " " + getTypeString(type));
            return null;
        }
        String structName = "";
        if (isStruct) {
            structName = currentStruct.getStructName().getName();
            addCommand("aload 0");
        }

        if (!(type instanceof StructType))
            addCommand(this.generateValue((variableDeclaration.getDefaultValue() == null),
                    variableDeclaration.getDefaultValue(), type));

        if (type instanceof IntType) addCommand("invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;");
        else if (type instanceof BoolType) addCommand("invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;");
        else if (type instanceof StructType) {
            addCommand("new " + ((StructType) type).getStructName().getName() + "\n");
            addCommand("dup\n");
            addCommand("invokespecial " + getClass(type) + "/<init>()V\n");
        }

        if (isStruct) addCommand("putfield " + structName + "/" + name + " " + getTypeString(type));
        else addCommand("astore " + slotOf(variableDeclaration.getVarName().getName()));
        return null;
    }

    @Override
    public String visit(SetGetVarDeclaration setGetVarDeclaration) {
        return null;
    }

    @Override
    public String visit(AssignmentStmt assignmentStmt) {
        String commands = this.visit(new BinaryExpression(assignmentStmt.getLValue(),
                assignmentStmt.getRValue(), BinaryOperator.assign));
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
    }

    @Override
    public String visit(FunctionCallStmt functionCallStmt) {
        expressionTypeChecker.setInFunctionCallStmt(true);
        addCommand(functionCallStmt.getFunctionCall().accept(this));
        Type t = functionCallStmt.getFunctionCall().accept(expressionTypeChecker);
        if (!(t instanceof VoidType)) addCommand("pop\n");
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
        if (returnStmt.getReturnedExpr() == null) {
            addCommand("return\n");
            return null;
        }
        Type type = returnStmt.getReturnedExpr().accept(expressionTypeChecker);
        if (!(type instanceof VoidType)) {
            addCommand(returnStmt.getReturnedExpr().accept(this));
            if (type instanceof IntType)
                addCommand("invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n");
            else if (type instanceof BoolType)
                addCommand("invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n");
            addCommand("areturn\n");
        } else {
            if (currentFunction != null)
                if (!(currentFunction.getReturnType() instanceof VoidType)) {
                    addCommand(returnStmt.getReturnedExpr().accept(this));
                    addCommand("areturn\n");
                }
            addCommand("return\n");
        }
        return null;
    }

    @Override
    public String visit(LoopStmt loopStmt) {
        String startLabel = getNewLabel();
        String exitLabel = getNewLabel();
        addCommand(startLabel + ":\n");
        if (loopStmt.getIsDoWhile()) {
            loopStmt.getBody().accept(this);
            addCommand(loopStmt.getCondition().accept(this));
            addCommand("ifeq " + exitLabel + "\n");
        } else {
            addCommand(loopStmt.getCondition().accept(this));
            addCommand("ifeq " + exitLabel + "\n");
            loopStmt.getBody().accept(this);
        }
        addCommand("goto " + startLabel + "\n");
        addCommand(exitLabel + ":\n");
        addCommand("iconst_0\n");
        addCommand("pop\n");
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

    @Override
    public String visit(BinaryExpression binaryExpression) {
        BinaryOperator operator = binaryExpression.getBinaryOperator();
        String commands = "";
        Type tl = binaryExpression.getFirstOperand().accept(expressionTypeChecker);
        if (operator == BinaryOperator.add) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "iadd\n";
        } else if (operator == BinaryOperator.sub) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "isub\n";
        } else if (operator == BinaryOperator.mult) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "imul\n";
        } else if (operator == BinaryOperator.div) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "idiv\n";
        } else if ((operator == BinaryOperator.gt) || (operator == BinaryOperator.lt)) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);

            String nFalse = getNewLabel();
            String nAfter = getNewLabel();

            if (operator == BinaryOperator.gt) commands += "if_icmple " + nFalse + "\n";
            else commands += "if_icmpge " + nFalse + "\n";

            commands += "iconst_1\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_0\n";
            commands += nAfter + ":\n";
        } else if (operator == BinaryOperator.eq) {
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            if (tl instanceof IntType) {
                commands += binaryExpression.getFirstOperand().accept(this);
                commands += binaryExpression.getSecondOperand().accept(this);
                commands += "if_icmpne " + nFalse + "\n";
            }
            if (tl instanceof BoolType) {
                commands += binaryExpression.getFirstOperand().accept(this);
                commands += binaryExpression.getSecondOperand().accept(this);
                commands += "if_icmpne " + nFalse + "\n";
            }
            if ((tl instanceof ListType) || (tl instanceof FptrType) || (tl instanceof StructType)) {
                commands += binaryExpression.getFirstOperand().accept(this);
                commands += binaryExpression.getSecondOperand().accept(this);
                commands += "if_acmpne " + nFalse + "\n";
            }
            commands += "iconst_1\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_0\n";
            commands += nAfter + ":\n";
        } else if (operator == BinaryOperator.and) {
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += "ifeq " + nFalse + "\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "ifeq " + nFalse + "\n";
            commands += "iconst_1" + "\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_0" + "\n";
            commands += nAfter + ":\n";
        } else if (operator == BinaryOperator.or) {
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += "ifne " + nFalse + "\n";
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "ifne " + nFalse + "\n";
            commands += "iconst_0" + "\n";
            commands += "goto " + nAfter + "\n";
            commands += nFalse + ":\n";
            commands += "iconst_1" + "\n";
            commands += nAfter + ":\n";
        } else if (operator == BinaryOperator.assign) {
            Type firstType = binaryExpression.getFirstOperand().accept(expressionTypeChecker);
            String secondOperandCommands = binaryExpression.getSecondOperand().accept(this);
            if (firstType instanceof ListType) {
                secondOperandCommands = "new List\n" + "dup\n" + binaryExpression.getSecondOperand().accept(this)
                        + "\n" + "invokespecial List/<init>(LList;)V\n";
            }
            if (binaryExpression.getFirstOperand() instanceof Identifier) {
                commands += secondOperandCommands + "\n";
                commands += "dup\n";
                if (firstType instanceof IntType)
                    commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
                else if (firstType instanceof BoolType)
                    commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
                int varSlot = slotOf(((Identifier) binaryExpression.getFirstOperand()).getName());
                commands += "astore" + underlineOrSpace(varSlot) + varSlot + "\n";
            } else if (binaryExpression.getFirstOperand() instanceof ListAccessByIndex) {
                commands += ((ListAccessByIndex) binaryExpression.getFirstOperand()).getInstance().accept(this) + "\n";
                commands += ((ListAccessByIndex) binaryExpression.getFirstOperand()).getIndex().accept(this) + "\n";
                commands += secondOperandCommands + "\n";
                commands += "dup_x2\n";
                if (firstType instanceof IntType)
                    commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
                else if (firstType instanceof BoolType)
                    commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
                commands += "invokevirtual List/setElement(ILjava/lang/Object;)V";
            } else if (binaryExpression.getFirstOperand() instanceof StructAccess) {
                Expression instance = ((StructAccess) binaryExpression.getFirstOperand()).getInstance();
                Type memberType = binaryExpression.getFirstOperand().accept(expressionTypeChecker);
                String memberName = ((StructAccess) binaryExpression.getFirstOperand()).getElement().getName();
                Type instanceType = instance.accept(expressionTypeChecker);

                commands += binaryExpression.getFirstOperand().accept(this);

                String className = ((StructType) instanceType).getStructName().getName();
                commands += instance.accept(this) + "\n";
                commands += binaryExpression.getSecondOperand().accept(this) + "\n";

                if (memberType instanceof IntType) {
                    commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
                } else if (memberType instanceof BoolType) {
                    commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
                }
                commands += "putfield " + className + "/" + memberName + " " + getTypeString(memberType) + "\n";
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
        Type memberType = structAccess.accept(expressionTypeChecker);
        Type instanceType = structAccess.getInstance().accept(expressionTypeChecker);
        String memberName = structAccess.getElement().getName();
        String commands = "";
        String className = ((StructType) instanceType).getStructName().getName();
        try {
            SymbolTable classSymbolTable = ((StructSymbolTableItem) SymbolTable.root.getItem
                    (StructSymbolTableItem.START_KEY + className)).getStructSymbolTable();
            try {
                classSymbolTable.getItem(VariableSymbolTableItem.START_KEY + memberName);
                commands += structAccess.getInstance().accept(this) + "\n";
                commands += "getfield " + className + "/" + memberName + " " + getTypeString(memberType) + "\n";
                if (memberType instanceof IntType)
                    commands += "\ninvokevirtual java/lang/Integer/intValue()I\n";
                else if (memberType instanceof BoolType)
                    commands += "\ninvokevirtual java/lang/Boolean/booleanValue()Z\n";
            } catch (ItemNotFoundException memberIsMethod) {
                commands += "new Fptr\n";
                commands += "dup\n";
                commands += structAccess.getInstance().accept(this) + "\n";
                commands += "ldc \"" + memberName + "\"\n";
                commands += "invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V\n";
            }
        } catch (ItemNotFoundException ignored) {
        }
        return commands;
    }

    @Override
    public String visit(Identifier identifier) {
        FunctionSymbolTableItem fsti = null;
        try {
            fsti = (FunctionSymbolTableItem) SymbolTable.root.getItem("Function_" + identifier.getName());
        } catch (ItemNotFoundException ignored) {
        }
        String command = "";
        if (fsti == null) {
            Type type = identifier.accept(expressionTypeChecker);
            command += "aload " + slotOf(identifier.getName()) + "\n";
            if (type instanceof IntType)
                command += "invokevirtual java/lang/Integer/intValue()I\n";
            else if (type instanceof BoolType)
                command += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
        } else {
            command += "new Fptr\n" + "dup\n" + "aload_0\n" + "ldc \"" + identifier.getName() + "\"\n" +
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
        if (!(type instanceof VoidType) && !(type instanceof NoType))
            commands += "checkcast " + getClass(type) + "\n";
        if (type instanceof IntType)
            commands += "invokevirtual java/lang/Integer/intValue()I\n";
        else if (type instanceof BoolType)
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
        for (Expression arg : functionCall.getArgs()) {
            commands += "aload " + tempVar + "\n";
            Type argType = arg.accept(expressionTypeChecker);
            if (argType instanceof ListType) {
                commands += "new List\n";
                commands += "dup\n";
            }
            commands += arg.accept(this);
            if (argType instanceof IntType)
                commands += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
            else if (argType instanceof BoolType)
                commands += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";
            else if (argType instanceof ListType) {
                commands += "invokespecial List/<init>(LList;)V\n";
            }
            commands += "invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z\n";
            commands += "pop\n";
        }
        commands += "aload " + tempVar + "\n";
        commands += "invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;\n";
        Type type = functionCall.accept(expressionTypeChecker);
        if (!(type instanceof VoidType) && !(type instanceof NoType))
            commands += "checkcast " + getClass(type) + "\n";
        if (type instanceof IntType)
            commands += "invokevirtual java/lang/Integer/intValue()I" + "\n";
        else if (type instanceof BoolType)
            commands += "invokevirtual java/lang/Boolean/booleanValue()Z" + "\n";
        tmpVarCnt--;
        return commands;
    }

    @Override
    public String visit(ListSize listSize) {
        String command = listSize.getArg().accept(this);
        command += "invokevirtual List/getSize()I\n";
        return command;
    }

    @Override
    public String visit(ListAppend listAppend) {
        Type elementType = listAppend.getElementArg().accept(expressionTypeChecker);
        String command = listAppend.getListArg().accept(this);
        command += listAppend.getElementArg().accept(this);

        if (elementType instanceof IntType)
            command += "invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n";
        if (elementType instanceof BoolType)
            command += "invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n";

        command += "invokevirtual List/addElement(Ljava/lang/Object;)V\n";
        return command;
    }

    @Override
    public String visit(IntValue intValue) {
        return "ldc " + intValue.getConstant() + "\n";
    }

    @Override
    public String visit(BoolValue boolValue) {
        return "ldc " + (boolValue.getConstant() ? 1 : 0) + "\n";
    }

    @Override
    public String visit(ExprInPar exprInPar) {
        return exprInPar.getInputs().get(0).accept(this);
    }
}
