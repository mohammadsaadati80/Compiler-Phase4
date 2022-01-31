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
		new A
		dup
		invokespecial A/<init>()V
		astore 1
		new Fptr
		dup
		aload_0
		ldc "start"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 2
		aload 2
		ldc 1252
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 2
		aload 1
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 2
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast A
		
		dup
		astore 1
		pop
		new Fptr
		dup
		aload_0
		ldc "f"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 2
		aload 2
		ldc 3
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 2
		aload 1
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 2
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		pop
		return
.end method
.method public start(Ljava/lang/Integer;LA;)LA;
		.limit stack 128
		.limit locals 128
		aload 2
		
		getfield A/x Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield A/x Ljava/lang/Integer;
		pop
		aload 2
		areturn
.end method
.method public f(Ljava/lang/Integer;LA;)V
		.limit stack 128
		.limit locals 128
		aload 2
		
		getfield A/x Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		if_icmpne Label_4
		iconst_1
		goto Label_5
		Label_4:
		iconst_0
		Label_5:
		ifne Label_2
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		if_icmpge Label_6
		iconst_1
		goto Label_7
		Label_6:
		iconst_0
		Label_7:
		ifne Label_2
		iconst_0
		goto Label_3
		Label_2:
		iconst_1
		Label_3:
		ifeq Label_0
		return
		goto Label_1
	Label_0:
		iconst_0
		pop
	Label_1:
		iconst_0
		pop
		aload 2
		
		getfield A/x Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 2
		
		aload 2
		
		getfield A/x Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		idiv
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield A/x Ljava/lang/Integer;
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 2
		
		getfield A/x Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		new Fptr
		dup
		aload_0
		ldc "f"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 3
		aload 3
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 3
		aload 2
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 3
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		pop
.end method
