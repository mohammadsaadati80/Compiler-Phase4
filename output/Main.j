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
		new Order
		dup
		invokespecial Order/<init>()V
		astore 1
		new Order
		dup
		invokespecial Order/<init>()V
		astore 2
		aload 1
		
		getfield Order/quantity Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 1
		
		ldc 1
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield Order/quantity Ljava/lang/Integer;
		pop
		aload 2
		
		getfield Order/quantity Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		
		ldc 1
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield Order/quantity Ljava/lang/Integer;
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 1
		aload 2
		if_acmpne Label_0
		iconst_1
		goto Label_1
		Label_0:
		iconst_0
		Label_1:
		invokevirtual java/io/PrintStream/println(Z)V
		aload 2
		
		dup
		astore 1
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 1
		aload 2
		if_acmpne Label_2
		iconst_1
		goto Label_3
		Label_2:
		iconst_0
		Label_3:
		invokevirtual java/io/PrintStream/println(Z)V
		return
.end method
