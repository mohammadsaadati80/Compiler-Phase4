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
    private int labelCnt = 0;
    private String outputPath;
    private int tmpVarCnt = 0;
    private FileWriter currentFile;
    private FunctionDeclaration currentFunction;
    private final ArrayList<String> localVars = new ArrayList<>();
    private final ExpressionTypeChecker expressionTypeChecker = new ExpressionTypeChecker();

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
        if (identifier.equals(""))
            return localVars.size() - 1 + this.tmpVarCnt;

        for (int i = 0; i < localVars.size(); i++)
            if (localVars.get(i).equals(identifier)) return i;

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

        addCommand(".class public " + structDeclaration.getStructName().getName());
        addCommand(".super java/lang/Object");



        addCommand(".method public <init>()V");
        addCommand(".limit stack 128");
        addCommand(".limit locals 128");

        addCommand("aload_0");
        addCommand("invokespecial " + structDeclaration.getStructName().getName() + "/<init>()V");



        addCommand("return");
        addCommand(".end method");

        return null;
    }

    @Override
    public String visit(FunctionDeclaration functionDeclaration) {

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

        command += functionDeclaration.getBody().accept(this);

        addCommand(".end method\n");
        currentFunction = null;
        return command;
    }

    public String getTypeString(Type t) {
        if (t instanceof IntType)
            return "Ljava/lang/Integer;";
        if (t instanceof BoolType)
            return "Ljava/lang/Boolean;";
        if (t instanceof ListType)
            return "LList;";
        if (t instanceof FptrType)
            return "LFptr;";
        if (t instanceof VoidType)
            return "V";
        //  TODO StructType Nemidonam Object bayad bahse ya khode class
        if (t instanceof StructType)
            return "Ljava/lang/Object;";
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
        if (currentFunction != null) localVars.add(variableDeclaration.getVarName().getName());
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
        if (returnStmt.getReturnedExpr() == null){
            addCommand("return\n");
            return null;
        }
        String command = returnStmt.getReturnedExpr().accept(this);
        // TODO Ehtemalan kht paeen moshkel dare
        Type expr_type = returnStmt.getReturnedExpr().accept(expressionTypeChecker);
        if (expr_type instanceof VoidType) command += "return\n";
        else command += "areturn\n";
        addCommand(command);

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
        return listAppendStmt.getListAppendExpr().accept(this);
    }

    @Override
    public String visit(ListSizeStmt listSizeStmt) {
        //todo
        return null;
    }

    private String getNewLabel() {
        return "Label_" + labelCnt++;
    }

    private String underlineOrSpace(int slot) {
        if (0 <= slot && slot <= 3) return "_";
        else return " ";
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

            String nTrue = getNewLabel();
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();
            if (operator == BinaryOperator.gt)
                commands += "if_icmpgt " + nTrue + "\n";
            else
                commands += "if_icmplt " + nTrue + "\n";
            commands += nFalse + ":\n";
            commands += "ldc 0\n";
            commands += "goto " + nAfter + "\n";
            commands += nTrue + ":\n";
            commands += "ldc 1\n";
            commands += nAfter + ":\n";
        } else if (operator == BinaryOperator.eq) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);

            String nTrue = getNewLabel();
            String nFalse = getNewLabel();
            String nAfter = getNewLabel();

            Type operandsType = binaryExpression.getFirstOperand().accept(this.expressionTypeChecker);

            if ((operandsType instanceof IntType) || (operandsType instanceof BoolType)) {
                commands += "if_icmpeq " + nTrue + "\n";
                commands += nFalse + ":\n";
                commands += "ldc 0\n";
                commands += "goto " + nAfter + "\n";
                commands += nTrue + ":\n";
                commands += "ldc 1\n";
                commands += nAfter + ":\n";
            }
            /*else if (operandsType instanceof ListType) {
                ListType firstOperand = (ListType) binaryExpression.getFirstOperand().accept(this.expressionTypeChecker);
                ListType secondOperand = (ListType) binaryExpression.getSecondOperand().accept(this.expressionTypeChecker);
                boolean listsAreEqual = areListsEqual(firstOperand, secondOperand);
                if (listsAreEqual)
                    commands += "ldc 1\n";
                else
                    commands += "ldc 0\n";
            }*/
            else {
                commands += "if_acmpeq " + nTrue + "\n";
                commands += nFalse + ":\n";
                commands += "ldc 0\n";
                commands += "goto " + nAfter + "\n";
                commands += nTrue + ":\n";
                commands += "ldc 1\n";
                commands += nAfter + ":\n";
            }
        } else if (operator == BinaryOperator.and) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "iand\n";
        } else if (operator == BinaryOperator.or) {
            commands += binaryExpression.getFirstOperand().accept(this);
            commands += binaryExpression.getSecondOperand().accept(this);
            commands += "ior\n";
        } else if (operator == BinaryOperator.assign) {
            Type firstType = binaryExpression.getFirstOperand().accept(expressionTypeChecker);
            String secondOperandCommands = binaryExpression.getSecondOperand().accept(this);
            if (firstType instanceof ListType) {
                secondOperandCommands = "new List\n" + "dup\n" + secondOperandCommands + "invokespecial List/<init>(LList;)V\n";
            }
            if (binaryExpression.getFirstOperand() instanceof Identifier) {
                Type secondType = binaryExpression.getSecondOperand().accept(this.expressionTypeChecker);
                int slot = slotOf(((Identifier) binaryExpression.getFirstOperand()).getName());

                if (secondType instanceof IntType) {
                    commands += "new java/lang/Integer\n";
                    commands += "dup\n";
                    commands += secondOperandCommands;
                    commands += "invokespecial java/lang/Integer/<init>(I)V\n";
                    commands += "dup\n";
                    commands += "astore" + underlineOrSpace(slot) + slot + "\n";
                    commands += "invokevirtual java/lang/Integer/intValue()I\n";
                } else if (secondType instanceof BoolType) {
                    commands += "new java/lang/Boolean\n";
                    commands += "dup\n";
                    commands += secondOperandCommands;
                    commands += "invokespecial java/lang/Boolean/<init>(Z)V\n";
                    commands += "dup\n";
                    commands += "astore" + underlineOrSpace(slot) + slot + "\n";
                    commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
                } else {
                    commands += secondOperandCommands;
                    commands += "dup\n";
                    commands += "astore" + underlineOrSpace(slot) + slot + "\n";
                }
            } else if (binaryExpression.getFirstOperand() instanceof ListAccessByIndex) {
                Type secondType = binaryExpression.getSecondOperand().accept(this.expressionTypeChecker);
                ListAccessByIndex firstOperandListAccess = (ListAccessByIndex) binaryExpression.getFirstOperand();
                this.tmpVarCnt++;
                int tempSlot = slotOf("");
                if (secondType instanceof IntType) {
                    commands += "new java/lang/Integer\n";
                    commands += "dup\n";
                    commands += secondOperandCommands;
                    commands += "invokespecial java/lang/Integer/<init>(I)V\n";
                    commands += "astore" + underlineOrSpace(tempSlot) + tempSlot + "\n";

                    commands += firstOperandListAccess.getInstance().accept(this);
                    commands += firstOperandListAccess.getIndex().accept(this);
                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
                    commands += "invokevirtual List/setElement(ILjava/lang/Object;)V\n";

                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
                    commands += "invokevirtual java/lang/Integer/intValue()I\n";
                } else if (secondType instanceof BoolType) {
                    commands += "new java/lang/Boolean\n";
                    commands += "dup\n";
                    commands += secondOperandCommands;
                    commands += "invokespecial java/lang/Boolean/<init>(Z)V\n";
                    commands += "astore" + underlineOrSpace(tempSlot) + tempSlot + "\n";

                    commands += firstOperandListAccess.getInstance().accept(this);
                    commands += firstOperandListAccess.getIndex().accept(this);
                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
                    commands += "invokevirtual List/setElement(ILjava/lang/Object;)V\n";

                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
                    commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
                } else {
                    commands += secondOperandCommands;
                    commands += "astore" + underlineOrSpace(tempSlot) + tempSlot + "\n";

                    commands += firstOperandListAccess.getInstance().accept(this);
                    commands += firstOperandListAccess.getIndex().accept(this);
                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
                    commands += "invokevirtual List/setElement(ILjava/lang/Object;)V\n";

                    commands += "aload" + underlineOrSpace(tempSlot) + tempSlot + "\n";
                }
                this.tmpVarCnt--;
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
        String commands = "";
        Type t = identifier.accept(this.expressionTypeChecker);
        int slot = slotOf(identifier.getName());
        commands += "aload " + slot + "\n";
        if (t instanceof BoolType)
            commands += "invokevirtual java/lang/Boolean/booleanValue()Z\n";
        else if (t instanceof IntType)
            commands += "invokevirtual java/lang/Integer/intValue()I\n";
        return commands;
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
        StringBuilder commands = new StringBuilder();
        commands.append(functionCall.getInstance().accept(this));
        commands.append("new java/util/ArrayList\ndup\ninvokespecial java/util/ArrayList/<init>()V\n");
        for (Expression e : functionCall.getArgs()) {
            commands.append("dup\n");
            commands.append(e.accept(this));
            Type t = e.accept(this.expressionTypeChecker);
            if (t instanceof IntType)
                commands.append("invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;\n");
            if (t instanceof BoolType)
                commands.append("invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;\n");
            commands.append("invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z\npop\n");
        }
        commands.append("invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;\n");
        Type t = functionCall.accept(expressionTypeChecker);
        commands.append(getTypeString(t));
        if (t instanceof IntType)
            commands.append("invokevirtual java/lang/Integer/intValue()I\n");
        else if (t instanceof BoolType)
            commands.append("invokevirtual java/lang/Boolean/booleanValue()Z\n");
        return commands.toString();
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
