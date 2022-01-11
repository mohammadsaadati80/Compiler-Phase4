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
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 0
		ldc 0
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 1
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 2
		new List
		dup
		aload 2
		invokespecial List/<init>(Ljava/util/ArrayList;)V
		astore 2
		new java/util/ArrayList
		dup
		invokespecial java/util/ArrayList/<init>()V
		astore 3
		new List
		dup
		aload 3
		invokespecial List/<init>(Ljava/util/ArrayList;)V
		astore 3
		aconst_null
		astore 4
		aconst_null
		astore 5
		aconst_null
		astore 6
		aconst_null
		astore 7
		aconst_null
		astore 8
		aload 9
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 10
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 11
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 12
		
		getfield ProductCatalog/id Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/id Ljava/lang/Integer;
		pop
		aload 13
		aload 14
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 15
		aload 16
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 17
		aload 18
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 19
		aload 20
		invokevirtual List/addElement(Ljava/lang/Object;)V
		aload 21
		
		ldc 0
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		aload 22
		
		ldc 1
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		aload 23
		
		ldc 2
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		aload 24
		
		ldc 3
		
		invokevirtual List/getElement(I)Ljava/lang/Object;
		checkcast ProductCatalog
		
		getfield ProductCatalog/price Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield ProductCatalog/price Ljava/lang/Integer;
		pop
		ldc 0
		
		dup
		invokestatic java/lang/Integer/valueOf(I)Ljava/lang/Integer;
		astore 25
		pop
		return
.end method
.method public createOrder(LProductCatalog;Ljava/lang/Integer;)LOrder;
		.limit stack 128
		.limit locals 128
		aconst_null
		astore 29
		aload 3
		
		getfield Order/quantity Ljava/lang/Integer;
		invokevirtual java/lang/Integer/intValue()I
		putfield Order/quantity Ljava/lang/Integer;
		pop
		aload 3
		
		getfield Order/product LProductCatalog;
		putfield Order/product LProductCatalog;
		pop
		aload 3
		areturn
.end method
