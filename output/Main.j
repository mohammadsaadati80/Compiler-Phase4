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
		astore_1
		new Fptr
		dup
		aload_1
		ldc "k"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 0
		aload 0
		ldc 155
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 0
		ldc 0
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 0
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 0
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		return
.end method
.method public r()LFptr;
		.limit stack 128
		.limit locals 128
		new Fptr
		dup
		aload_0
		ldc "m"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
.end method
.method public m()Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		getstatic java/lang/System/out Ljava/io/PrintStream;
		ldc 1400
		invokevirtual java/io/PrintStream/println(I)V
		ldc 15
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public k(Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		aload 2
		invokevirtual java/lang/Boolean/booleanValue()Z
		ifeq Label_0
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
		goto Label_1
	Label_0:
		iconst_0
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		ldc 6
		invokevirtual java/io/PrintStream/println(I)V
	Label_1:
		iconst_0
		pop
		new Fptr
		dup
		aload_0
		ldc "r"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 4
		aload 4
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 4
		aload 4
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
