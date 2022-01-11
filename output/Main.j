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
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 1
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 2
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 4
		new List
		dup
		aload 4
		invokespecial List/<init>(Ljava/util/ArrayList;)V
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
		new Order
		dup
		invokespecial Order/<init>()V
		astore 5
		new ProductCatalog
		dup
		invokespecial ProductCatalog/<init>()V
		astore 6
		new ProductCatalog
		dup
		invokespecial ProductCatalog/<init>()V
		astore 7
		new ProductCatalog
		dup
		invokespecial ProductCatalog/<init>()V
		astore 8
		new ProductCatalog
		dup
		invokespecial ProductCatalog/<init>()V
		astore 9
		aload 6
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 6
		
		ldc 0
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 7
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 7
		
		ldc 1
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 8
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 8
		
		ldc 2
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 9
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 9
		
		ldc 3
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 3
		aload 6
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 7
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 8
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 9
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		
		ldc 0
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 3
		
		ldc 0
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		ldc 5000
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		aload 3
		
		ldc 1
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 3
		
		ldc 1
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		ldc 4000
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		aload 3
		
		ldc 2
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 3
		
		ldc 2
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		ldc 2000
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		aload 3
		
		ldc 3
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 3
		
		ldc 3
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		ldc 8000
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		ldc 0
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 1
		pop
	Label_0:
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 4
		if_icmpge Label_2
		iconst_1
		goto Label_3
		Label_2:
		iconst_0
		Label_3:
		ifeq Label_1
		new Fptr
		dup
		aload_0
		ldc "createOrder"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 10
		aload 10
		aload 3
		
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 10
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 10
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Order
		
		dup
		astore 5
		pop
		aload 4
		aload 5
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 1
		pop
		goto Label_0
	Label_1:
		iconst_0
		pop
		new Fptr
		dup
		aload_0
		ldc "getSum"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 10
		aload 10
		new List
		dup
		aload 4
		invokespecial List/<init>(LList;)V
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 10
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 2
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 10
		new Fptr
		dup
		aload_0
		ldc "k"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 11
		aload 11
		ldc 155
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 11
		ldc 0
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 11
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 11
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 11
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 12
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 13
		ldc 8
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 13
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 12
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 11
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 12
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 13
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		new Fptr
		dup
		aload_0
		ldc "k"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 14
		aload 14
		ldc 155
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 14
		ldc 1
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 14
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 14
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 14
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 15
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 14
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 15
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 6
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 15
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 14
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 14
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 15
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 1
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 10
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 10
		invokevirtual java/lang/Integer/intValue()I
		ldc 2
		ldc 85
		imul
		iadd
		invokevirtual java/io/PrintStream/println(I)V
		return
.end method
.method public createOrder(LProductCatalog;Ljava/lang/Integer;)LOrder;
		.limit stack 128
		.limit locals 128
		new Order
		dup
		invokespecial Order/<init>()V
		astore 3
		aload 3
		
		getfield Order/quantity Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 3
		
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		putfield Order/quantity Ljava/lang/Integer;
		pop
		aload 3
		
		getfield Order/product LProductCatalog;
		aload 3
		
		aload 1
		
		putfield Order/product LProductCatalog;
		pop
		aload 3
		areturn
.end method
.method public getSum(LList;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 2
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 3
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 4
	Label_4:
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		aload 1
		invokevirtual List/getSize()I
		if_icmpge Label_6
		iconst_1
		goto Label_7
		Label_6:
		iconst_0
		Label_7:
		ifeq Label_5
		aload 1
		
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast Order
		
		getfield Order/product LProductCatalog;
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		aload 1
		
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast Order
		
		getfield Order/quantity Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		imul
		aload 1
		
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast Order
		
		getfield Order/quantity Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		ldc 100
		imul
		iadd
		aload 1
		
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast Order
		
		getfield Order/product LProductCatalog;
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		
		invokevirtual java/lang/Integer/intValue()I
		ldc 100
		idiv
		isub
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 4
		pop
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		aload 4
		invokevirtual java/lang/Integer/intValue()I
		iadd
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 3
		pop
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 2
		pop
		goto Label_4
	Label_5:
		iconst_0
		pop
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public f(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 4
		new List
		dup
		aload 4
		invokespecial List/<init>(Ljava/util/ArrayList;)V
		astore 3
		aload 3
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 3
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual List/addElement(Ljava/lang/Object;)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 3
		
		aload 2
		invokevirtual java/lang/Integer/intValue()I
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		ldc 2
		imul
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 3
		invokevirtual List/getSize()I
		aload 3
		invokevirtual List/getSize()I
		imul
		invokevirtual java/io/PrintStream/println(I)V
		ldc 13
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public g()LFptr;
		.limit stack 128
		.limit locals 128
		new Fptr
		dup
		aload_0
		ldc "f"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		areturn
.end method
.method public h()V
		.limit stack 128
		.limit locals 128
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
.method public j(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		getstatic java/lang/System/out Ljava/io/PrintStream;
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
		ldc 20
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 3
		ldc 1
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 3
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 456546
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
.method public k(Ljava/lang/Integer;Ljava/lang/Boolean;Ljava/lang/Integer;)Ljava/lang/Integer;
		.limit stack 128
		.limit locals 128
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 4
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 4
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 99
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 4
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 4
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 5
	Label_8:
		aload 5
		invokevirtual java/lang/Integer/intValue()I
		ldc 4
		if_icmpge Label_10
		iconst_1
		goto Label_11
		Label_10:
		iconst_0
		Label_11:
		ifeq Label_9
		aload 5
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 5
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 5
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		goto Label_8
	Label_9:
		iconst_0
		pop
		ldc 5
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 6
	Label_12:
		aload 6
		invokevirtual java/lang/Integer/intValue()I
		ldc 1
		iadd
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 6
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 6
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		aload 6
		invokevirtual java/lang/Integer/intValue()I
		ldc 6
		if_icmpge Label_14
		iconst_1
		goto Label_15
		Label_14:
		iconst_0
		Label_15:
		ifeq Label_13
		goto Label_12
	Label_13:
		iconst_0
		pop
		ldc 8888
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 7
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 7
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 99
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 7
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 7
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 1
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		astore 8
	Label_16:
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 8
		invokevirtual java/lang/Boolean/booleanValue()Z
		invokevirtual java/io/PrintStream/println(Z)V
		ldc 0
		
		dup
		invokestatic java/lang/Boolean/valueOf(Z)Ljava/lang/Boolean;
		astore 8
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 8
		invokevirtual java/lang/Boolean/booleanValue()Z
		invokevirtual java/io/PrintStream/println(Z)V
		aload 8
		invokevirtual java/lang/Boolean/booleanValue()Z
		ifeq Label_17
		goto Label_16
	Label_17:
		iconst_0
		pop
		new Fptr
		dup
		aload_0
		ldc "h"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 9
		aload 9
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		pop
		new Fptr
		dup
		aload_0
		ldc "g"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 9
		aload 9
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 9
		aload 9
		aload 1
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 9
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 9
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 9
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 9
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		ldc 99
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 9
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		aload 9
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
		aload 2
		invokevirtual java/lang/Boolean/booleanValue()Z
		ifeq Label_18
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
		goto Label_19
	Label_18:
		iconst_0
		pop
		getstatic java/lang/System/out Ljava/io/PrintStream;
		new Fptr
		dup
		aload_0
		ldc "j"
		invokespecial Fptr/<init>(Ljava/lang/Object;Ljava/lang/String;)V
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 10
		aload 10
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 10
		aload 3
		invokevirtual java/lang/Integer/intValue()I
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		invokevirtual java/util/ArrayList/add(Ljava/lang/Object;)Z
		pop
		aload 10
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		invokevirtual java/io/PrintStream/println(I)V
	Label_19:
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
		astore 10
		aload 10
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast Fptr
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 10
		aload 10
		invokevirtual Fptr/invoke(Ljava/util/ArrayList;)Ljava/lang/Object;
		checkcast java/lang/Integer
		invokevirtual java/lang/Integer/intValue()I
		pop
		ldc 2
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		areturn
.end method
