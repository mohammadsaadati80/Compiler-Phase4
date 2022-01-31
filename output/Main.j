.class public Main
.super java/lang/Object
.method public <init>()V
.limit stack 128
.limit locals 128
		aload_0
		invokespecial java/lang/Object/<init>()V
		return
.end method
.method public static main([Ljava/lang/String;)V
.limit locals 128
.limit stack 128
		new Main
		dup
		invokespecial Main/<init>()V
		astore_0
		getstatic java/lang/System/out Ljava/io/PrintStream;
		new Fptr
		dup
		aload_0
		ldc "f"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 1
		aload 1
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Boolean
		invokevirtual java/lang/Boolean/booleanValue()Z
		invokevirtual java/io/PrintStream/println(Z)V
		return
.end method
.method public f()Ljava/lang/Boolean;
		.limit stack 128
		.limit locals 128
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 1
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 2
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 3
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 5
		new List
		dup
		aload 5
		invokespecial List/<init>(Ljava/util/ArrayList;)V
		astore 4
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 5
		new Fptr
		dup
		aload_0
		ldc "g"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 9
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 2
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 3
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 3
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		pop
		new Fptr
		dup
		aload_0
		ldc "h"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 7
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 8
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 11
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast 
		pop
		new Fptr
		dup
		aload_0
		ldc "h"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast 
		pop
		new Fptr
		dup
		aload_0
		ldc "c1"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		new Fptr
		dup
		aload_0
		ldc "c2"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		new Fptr
		dup
		aload_0
		ldc "c3"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		new Fptr
		dup
		aload_0
		ldc "selector"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 5
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 3
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 5
		ldc 3
		if_icmple Label_0
		iconst_1
		goto Label_1
		Label_0:
		iconst_0
		Label_1:
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 1
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 10
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		new Fptr
		dup
		aload_0
		ldc "selector"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 3
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 5
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 3
		ldc 5
		if_icmple Label_2
		iconst_1
		goto Label_3
		Label_2:
		iconst_0
		Label_3:
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 1
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 10
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		new Fptr
		dup
		aload_0
		ldc "selector"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 2
		ldc 2
		if_icmple Label_4
		iconst_1
		goto Label_5
		Label_4:
		iconst_0
		Label_5:
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		ldc 1
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 10
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
	Label_6:
		aload 5
		invokevirtual java/lang/Integer/intValue()I
		ldc 8
		if_icmpge Label_8
		iconst_1
		goto Label_9
		Label_8:
		iconst_0
		Label_9:
		ifeq Label_7
		aload 4
		aload 5
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 5
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 5
		pop
		goto Label_6
	Label_7:
		iconst_0
		pop
		new Fptr
		dup
		aload_0
		ldc "x"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 6
		aload 6
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		new List
		dup
		aload 4
		invokespecial List/<init>(LList;)V
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 6
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		pop
		ldc 1
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		areturn
.end method
.method public g(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V
		.limit stack 128
		.limit locals 128
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		imul
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		iadd
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		isub
		invokevirtual java/io/PrintStream/println(I)V
		return
.end method
.method public h()LFptr;
		.limit stack 128
		.limit locals 128
		new Fptr
		dup
		aload_0
		ldc "g"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
.end method
.method public c1(Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		getstatic java/lang/System/out Ljava/io/PrintStream;
		ldc 1
		invokevirtual java/io/PrintStream/println(I)V
		ldc 2
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		imul
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public c2(Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		getstatic java/lang/System/out Ljava/io/PrintStream;
		ldc 2
		invokevirtual java/io/PrintStream/println(I)V
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 24
		imul
		ldc 2
		idiv
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public c3(Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		getstatic java/lang/System/out Ljava/io/PrintStream;
		ldc 3
		invokevirtual java/io/PrintStream/println(I)V
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 1000
		isub
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public selector(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Boolean;)LFptr;
		.limit stack 128
		.limit locals 128
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		if_icmple Label_16
		iconst_1
		goto Label_17
		Label_16:
		iconst_0
		Label_17:
		ifeq Label_14
		aload 3
		invokevirtual java/lang/Boolean/booleanValue()Z
		ifeq Label_14
		iconst_1
		goto Label_15
		Label_14:
		iconst_0
		Label_15:
		ifne Label_12
		aload 4
		invokevirtual java/lang/Boolean/booleanValue()Z
		ifne Label_12
		iconst_0
		goto Label_13
		Label_12:
		iconst_1
		Label_13:
		ifeq Label_10
		new Fptr
		dup
		aload_0
		ldc "c1"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
		goto Label_11
	Label_10:
		iconst_0
		pop
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		iadd
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		imul
		if_icmpne Label_20
		iconst_1
		goto Label_21
		Label_20:
		iconst_0
		Label_21:
		ifeq Label_18
		new Fptr
		dup
		aload_0
		ldc "c2"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
		goto Label_19
	Label_18:
		iconst_0
		pop
		new Fptr
		dup
		aload_0
		ldc "c3"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
	Label_19:
		iconst_0
		pop
	Label_11:
		iconst_0
		pop
		new Fptr
		dup
		aload_0
		ldc "c1"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
.end method
.method public x(Ljava/lang/Integer;LList;)V
		.limit stack 128
		.limit locals 128
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		invokevirtual List/getSize()I
		if_icmpne Label_24
		iconst_1
		goto Label_25
		Label_24:
		iconst_0
		Label_25:
		ifeq Label_22
		return
		goto Label_23
	Label_22:
		iconst_0
		pop
	Label_23:
		iconst_0
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 2
		
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		new Fptr
		dup
		aload_0
		ldc "x"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 3
		aload 3
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 3
		new List
		dup
		aload 2
		invokespecial List/<init>(LList;)V
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 3
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		pop
		return
.end method
