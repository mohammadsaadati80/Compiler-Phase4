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
		aload 0
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		dup
		ldc 155
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		dup
		ldc 0
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		dup
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		Ljava/lang/Integer;invokevirtual java/lang/Integer/intValue()I
		pop
		return
.end method
.method public f(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 5
		aload 5
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		new List
		dup
		aload 5
		invokespecial List/<init>(Ljava/util/ArrayList;)V
		astore 3
		aload 3
		dup
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		dup
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		dup
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		dup
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		dup
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		dup
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/addElement(Ljava/lang/Object;)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 3
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		imul
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/io/PrintStream/println(I)V
		ldc 13
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public g()LFptr;
		.limit stack 128
		.limit locals 128
		aload 2
		areturn
.end method
.method public h()V
		.limit stack 128
		.limit locals 128
