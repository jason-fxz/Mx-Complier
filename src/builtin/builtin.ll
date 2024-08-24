; ModuleID = 'src/IR/builtin/builtin.c'
source_filename = "src/IR/builtin/builtin.c"
target datalayout = "e-m:e-p:32:32-i64:64-n32-S128"
target triple = "riscv32-unknown-unknown-elf"

@.str = private unnamed_addr constant [3 x i8] c"%s\00", align 1
@.str.1 = private unnamed_addr constant [4 x i8] c"%s\0A\00", align 1
@.str.2 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.3 = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@.str.4 = private unnamed_addr constant [5 x i8] c"true\00", align 1
@.str.5 = private unnamed_addr constant [6 x i8] c"false\00", align 1

; Function Attrs: noinline nounwind optnone
define dso_local void @print(ptr noundef %s) #0 {
entry:
  %s.addr = alloca ptr, align 4
  store ptr %s, ptr %s.addr, align 4
  %0 = load ptr, ptr %s.addr, align 4
  %call = call i32 (ptr, ...) @printf(ptr noundef @.str, ptr noundef %0) #2
  ret void
}

declare dso_local i32 @printf(ptr noundef, ...) #1

; Function Attrs: noinline nounwind optnone
define dso_local void @println(ptr noundef %s) #0 {
entry:
  %s.addr = alloca ptr, align 4
  store ptr %s, ptr %s.addr, align 4
  %0 = load ptr, ptr %s.addr, align 4
  %call = call i32 (ptr, ...) @printf(ptr noundef @.str.1, ptr noundef %0) #2
  ret void
}

; Function Attrs: noinline nounwind optnone
define dso_local void @printInt(i32 noundef %n) #0 {
entry:
  %n.addr = alloca i32, align 4
  store i32 %n, ptr %n.addr, align 4
  %0 = load i32, ptr %n.addr, align 4
  %call = call i32 (ptr, ...) @printf(ptr noundef @.str.2, i32 noundef %0) #2
  ret void
}

; Function Attrs: noinline nounwind optnone
define dso_local void @printlnInt(i32 noundef %n) #0 {
entry:
  %n.addr = alloca i32, align 4
  store i32 %n, ptr %n.addr, align 4
  %0 = load i32, ptr %n.addr, align 4
  %call = call i32 (ptr, ...) @printf(ptr noundef @.str.3, i32 noundef %0) #2
  ret void
}

; Function Attrs: noinline nounwind optnone
define dso_local ptr @getString() #0 {
entry:
  %s = alloca ptr, align 4
  %call = call ptr @malloc(i32 noundef 1024) #2
  store ptr %call, ptr %s, align 4
  %0 = load ptr, ptr %s, align 4
  %call1 = call i32 (ptr, ...) @scanf(ptr noundef @.str, ptr noundef %0) #3
  %1 = load ptr, ptr %s, align 4
  ret ptr %1
}

declare dso_local ptr @malloc(i32 noundef) #1

declare dso_local i32 @scanf(ptr noundef, ...) #1

; Function Attrs: noinline nounwind optnone
define dso_local i32 @getInt() #0 {
entry:
  %n = alloca i32, align 4
  %call = call i32 (ptr, ...) @scanf(ptr noundef @.str.2, ptr noundef %n) #3
  %0 = load i32, ptr %n, align 4
  ret i32 %0
}

; Function Attrs: noinline nounwind optnone
define dso_local ptr @toString(i32 noundef %i) #0 {
entry:
  %i.addr = alloca i32, align 4
  %s = alloca ptr, align 4
  store i32 %i, ptr %i.addr, align 4
  %call = call ptr @malloc(i32 noundef 12) #2
  store ptr %call, ptr %s, align 4
  %0 = load ptr, ptr %s, align 4
  %1 = load i32, ptr %i.addr, align 4
  %call1 = call i32 (ptr, ptr, ...) @sprintf(ptr noundef %0, ptr noundef @.str.2, i32 noundef %1) #3
  %2 = load ptr, ptr %s, align 4
  ret ptr %2
}

declare dso_local i32 @sprintf(ptr noundef, ptr noundef, ...) #1

; Function Attrs: noinline nounwind optnone
define dso_local ptr @__mx_allocate(i32 noundef %size) #0 {
entry:
  %size.addr = alloca i32, align 4
  store i32 %size, ptr %size.addr, align 4
  %0 = load i32, ptr %size.addr, align 4
  %call = call ptr @malloc(i32 noundef %0) #2
  ret ptr %call
}

; Function Attrs: noinline nounwind optnone
define dso_local ptr @__mx_allocate_array(i32 noundef %size, i32 noundef %length) #0 {
entry:
  %size.addr = alloca i32, align 4
  %length.addr = alloca i32, align 4
  %a = alloca ptr, align 4
  store i32 %size, ptr %size.addr, align 4
  store i32 %length, ptr %length.addr, align 4
  %0 = load i32, ptr %size.addr, align 4
  %1 = load i32, ptr %length.addr, align 4
  %mul = mul nsw i32 %0, %1
  %add = add nsw i32 %mul, 4
  %call = call ptr @malloc(i32 noundef %add) #2
  store ptr %call, ptr %a, align 4
  %2 = load i32, ptr %length.addr, align 4
  %3 = load ptr, ptr %a, align 4
  %arrayidx = getelementptr inbounds i32, ptr %3, i32 0
  store i32 %2, ptr %arrayidx, align 4
  %4 = load ptr, ptr %a, align 4
  %add.ptr = getelementptr inbounds i32, ptr %4, i32 1
  ret ptr %add.ptr
}

; Function Attrs: noinline nounwind optnone
define dso_local i32 @__mx_array_size(ptr noundef %array) #0 {
entry:
  %array.addr = alloca ptr, align 4
  store ptr %array, ptr %array.addr, align 4
  %0 = load ptr, ptr %array.addr, align 4
  %arrayidx = getelementptr inbounds i32, ptr %0, i32 -1
  %1 = load i32, ptr %arrayidx, align 4
  ret i32 %1
}

; Function Attrs: noinline nounwind optnone
define dso_local ptr @__mx_bool_to_string(i1 noundef zeroext %b) #0 {
entry:
  %b.addr = alloca i8, align 1
  %frombool = zext i1 %b to i8
  store i8 %frombool, ptr %b.addr, align 1
  %0 = load i8, ptr %b.addr, align 1
  %tobool = trunc i8 %0 to i1
  %1 = zext i1 %tobool to i64
  %cond = select i1 %tobool, ptr @.str.4, ptr @.str.5
  ret ptr %cond
}

; Function Attrs: noinline nounwind optnone
define dso_local i32 @string.length(ptr noundef %s) #0 {
entry:
  %s.addr = alloca ptr, align 4
  %i = alloca i32, align 4
  store ptr %s, ptr %s.addr, align 4
  store i32 0, ptr %i, align 4
  br label %while.cond

while.cond:                                       ; preds = %while.body, %entry
  %0 = load ptr, ptr %s.addr, align 4
  %1 = load i32, ptr %i, align 4
  %arrayidx = getelementptr inbounds i8, ptr %0, i32 %1
  %2 = load i8, ptr %arrayidx, align 1
  %conv = zext i8 %2 to i32
  %cmp = icmp ne i32 %conv, 0
  br i1 %cmp, label %while.body, label %while.end

while.body:                                       ; preds = %while.cond
  %3 = load i32, ptr %i, align 4
  %inc = add nsw i32 %3, 1
  store i32 %inc, ptr %i, align 4
  br label %while.cond, !llvm.loop !5

while.end:                                        ; preds = %while.cond
  %4 = load i32, ptr %i, align 4
  ret i32 %4
}

; Function Attrs: noinline nounwind optnone
define dso_local ptr @string.substring(ptr noundef %s, i32 noundef %left, i32 noundef %right) #0 {
entry:
  %s.addr = alloca ptr, align 4
  %left.addr = alloca i32, align 4
  %right.addr = alloca i32, align 4
  %len = alloca i32, align 4
  %result = alloca ptr, align 4
  %i = alloca i32, align 4
  store ptr %s, ptr %s.addr, align 4
  store i32 %left, ptr %left.addr, align 4
  store i32 %right, ptr %right.addr, align 4
  %0 = load i32, ptr %right.addr, align 4
  %1 = load i32, ptr %left.addr, align 4
  %sub = sub nsw i32 %0, %1
  store i32 %sub, ptr %len, align 4
  %2 = load i32, ptr %len, align 4
  %add = add nsw i32 %2, 1
  %mul = mul i32 1, %add
  %call = call ptr @malloc(i32 noundef %mul) #2
  store ptr %call, ptr %result, align 4
  store i32 0, ptr %i, align 4
  br label %for.cond

for.cond:                                         ; preds = %for.inc, %entry
  %3 = load i32, ptr %i, align 4
  %4 = load i32, ptr %len, align 4
  %cmp = icmp slt i32 %3, %4
  br i1 %cmp, label %for.body, label %for.end

for.body:                                         ; preds = %for.cond
  %5 = load ptr, ptr %s.addr, align 4
  %6 = load i32, ptr %left.addr, align 4
  %7 = load i32, ptr %i, align 4
  %add1 = add nsw i32 %6, %7
  %arrayidx = getelementptr inbounds i8, ptr %5, i32 %add1
  %8 = load i8, ptr %arrayidx, align 1
  %9 = load ptr, ptr %result, align 4
  %10 = load i32, ptr %i, align 4
  %arrayidx2 = getelementptr inbounds i8, ptr %9, i32 %10
  store i8 %8, ptr %arrayidx2, align 1
  br label %for.inc

for.inc:                                          ; preds = %for.body
  %11 = load i32, ptr %i, align 4
  %inc = add nsw i32 %11, 1
  store i32 %inc, ptr %i, align 4
  br label %for.cond, !llvm.loop !7

for.end:                                          ; preds = %for.cond
  %12 = load ptr, ptr %result, align 4
  %13 = load i32, ptr %len, align 4
  %arrayidx3 = getelementptr inbounds i8, ptr %12, i32 %13
  store i8 0, ptr %arrayidx3, align 1
  %14 = load ptr, ptr %result, align 4
  ret ptr %14
}

; Function Attrs: noinline nounwind optnone
define dso_local i32 @string.parseInt(ptr noundef %s) #0 {
entry:
  %s.addr = alloca ptr, align 4
  %result = alloca i32, align 4
  %fg = alloca i32, align 4
  store ptr %s, ptr %s.addr, align 4
  store i32 0, ptr %result, align 4
  store i32 0, ptr %fg, align 4
  %0 = load ptr, ptr %s.addr, align 4
  %1 = load i8, ptr %0, align 1
  %conv = zext i8 %1 to i32
  %cmp = icmp eq i32 %conv, 45
  br i1 %cmp, label %if.then, label %if.end

if.then:                                          ; preds = %entry
  store i32 1, ptr %fg, align 4
  br label %if.end

if.end:                                           ; preds = %if.then, %entry
  br label %while.cond

while.cond:                                       ; preds = %while.body, %if.end
  %2 = load ptr, ptr %s.addr, align 4
  %3 = load i8, ptr %2, align 1
  %conv2 = zext i8 %3 to i32
  %cmp3 = icmp ne i32 %conv2, 0
  br i1 %cmp3, label %while.body, label %while.end

while.body:                                       ; preds = %while.cond
  %4 = load i32, ptr %result, align 4
  %mul = mul nsw i32 %4, 10
  %5 = load ptr, ptr %s.addr, align 4
  %6 = load i8, ptr %5, align 1
  %conv5 = zext i8 %6 to i32
  %add = add nsw i32 %mul, %conv5
  %sub = sub nsw i32 %add, 48
  store i32 %sub, ptr %result, align 4
  %7 = load ptr, ptr %s.addr, align 4
  %incdec.ptr = getelementptr inbounds i8, ptr %7, i32 1
  store ptr %incdec.ptr, ptr %s.addr, align 4
  br label %while.cond, !llvm.loop !8

while.end:                                        ; preds = %while.cond
  %8 = load i32, ptr %fg, align 4
  %tobool = icmp ne i32 %8, 0
  br i1 %tobool, label %cond.true, label %cond.false

cond.true:                                        ; preds = %while.end
  %9 = load i32, ptr %result, align 4
  %sub6 = sub nsw i32 0, %9
  br label %cond.end

cond.false:                                       ; preds = %while.end
  %10 = load i32, ptr %result, align 4
  br label %cond.end

cond.end:                                         ; preds = %cond.false, %cond.true
  %cond = phi i32 [ %sub6, %cond.true ], [ %10, %cond.false ]
  ret i32 %cond
}

; Function Attrs: noinline nounwind optnone
define dso_local i32 @string.ord(ptr noundef %s, i32 noundef %i) #0 {
entry:
  %s.addr = alloca ptr, align 4
  %i.addr = alloca i32, align 4
  store ptr %s, ptr %s.addr, align 4
  store i32 %i, ptr %i.addr, align 4
  %0 = load ptr, ptr %s.addr, align 4
  %1 = load i32, ptr %i.addr, align 4
  %arrayidx = getelementptr inbounds i8, ptr %0, i32 %1
  %2 = load i8, ptr %arrayidx, align 1
  %conv = zext i8 %2 to i32
  ret i32 %conv
}

; Function Attrs: noinline nounwind optnone
define dso_local i32 @__mx_string_compare(ptr noundef %s1, ptr noundef %s2) #0 {
entry:
  %retval = alloca i32, align 4
  %s1.addr = alloca ptr, align 4
  %s2.addr = alloca ptr, align 4
  %i = alloca i32, align 4
  store ptr %s1, ptr %s1.addr, align 4
  store ptr %s2, ptr %s2.addr, align 4
  store i32 0, ptr %i, align 4
  br label %while.cond

while.cond:                                       ; preds = %if.end, %entry
  %0 = load ptr, ptr %s1.addr, align 4
  %1 = load i32, ptr %i, align 4
  %arrayidx = getelementptr inbounds i8, ptr %0, i32 %1
  %2 = load i8, ptr %arrayidx, align 1
  %conv = zext i8 %2 to i32
  %cmp = icmp ne i32 %conv, 0
  br i1 %cmp, label %land.rhs, label %land.end

land.rhs:                                         ; preds = %while.cond
  %3 = load ptr, ptr %s2.addr, align 4
  %4 = load i32, ptr %i, align 4
  %arrayidx2 = getelementptr inbounds i8, ptr %3, i32 %4
  %5 = load i8, ptr %arrayidx2, align 1
  %conv3 = zext i8 %5 to i32
  %cmp4 = icmp ne i32 %conv3, 0
  br label %land.end

land.end:                                         ; preds = %land.rhs, %while.cond
  %6 = phi i1 [ false, %while.cond ], [ %cmp4, %land.rhs ]
  br i1 %6, label %while.body, label %while.end

while.body:                                       ; preds = %land.end
  %7 = load ptr, ptr %s1.addr, align 4
  %8 = load i32, ptr %i, align 4
  %arrayidx6 = getelementptr inbounds i8, ptr %7, i32 %8
  %9 = load i8, ptr %arrayidx6, align 1
  %conv7 = zext i8 %9 to i32
  %10 = load ptr, ptr %s2.addr, align 4
  %11 = load i32, ptr %i, align 4
  %arrayidx8 = getelementptr inbounds i8, ptr %10, i32 %11
  %12 = load i8, ptr %arrayidx8, align 1
  %conv9 = zext i8 %12 to i32
  %cmp10 = icmp ne i32 %conv7, %conv9
  br i1 %cmp10, label %if.then, label %if.end

if.then:                                          ; preds = %while.body
  %13 = load ptr, ptr %s1.addr, align 4
  %14 = load i32, ptr %i, align 4
  %arrayidx12 = getelementptr inbounds i8, ptr %13, i32 %14
  %15 = load i8, ptr %arrayidx12, align 1
  %conv13 = zext i8 %15 to i32
  %16 = load ptr, ptr %s2.addr, align 4
  %17 = load i32, ptr %i, align 4
  %arrayidx14 = getelementptr inbounds i8, ptr %16, i32 %17
  %18 = load i8, ptr %arrayidx14, align 1
  %conv15 = zext i8 %18 to i32
  %sub = sub nsw i32 %conv13, %conv15
  store i32 %sub, ptr %retval, align 4
  br label %return

if.end:                                           ; preds = %while.body
  %19 = load i32, ptr %i, align 4
  %inc = add nsw i32 %19, 1
  store i32 %inc, ptr %i, align 4
  br label %while.cond, !llvm.loop !9

while.end:                                        ; preds = %land.end
  %20 = load ptr, ptr %s1.addr, align 4
  %21 = load i32, ptr %i, align 4
  %arrayidx16 = getelementptr inbounds i8, ptr %20, i32 %21
  %22 = load i8, ptr %arrayidx16, align 1
  %conv17 = zext i8 %22 to i32
  %23 = load ptr, ptr %s2.addr, align 4
  %24 = load i32, ptr %i, align 4
  %arrayidx18 = getelementptr inbounds i8, ptr %23, i32 %24
  %25 = load i8, ptr %arrayidx18, align 1
  %conv19 = zext i8 %25 to i32
  %sub20 = sub nsw i32 %conv17, %conv19
  store i32 %sub20, ptr %retval, align 4
  br label %return

return:                                           ; preds = %while.end, %if.then
  %26 = load i32, ptr %retval, align 4
  ret i32 %26
}

; Function Attrs: noinline nounwind optnone
define dso_local ptr @__mx_string_concat(ptr noundef %s1, ptr noundef %s2) #0 {
entry:
  %s1.addr = alloca ptr, align 4
  %s2.addr = alloca ptr, align 4
  %len1 = alloca i32, align 4
  %len2 = alloca i32, align 4
  %result = alloca ptr, align 4
  %i = alloca i32, align 4
  %i5 = alloca i32, align 4
  store ptr %s1, ptr %s1.addr, align 4
  store ptr %s2, ptr %s2.addr, align 4
  %0 = load ptr, ptr %s1.addr, align 4
  %call = call i32 @string.length(ptr noundef %0) #3
  store i32 %call, ptr %len1, align 4
  %1 = load ptr, ptr %s2.addr, align 4
  %call1 = call i32 @string.length(ptr noundef %1) #3
  store i32 %call1, ptr %len2, align 4
  %2 = load i32, ptr %len1, align 4
  %3 = load i32, ptr %len2, align 4
  %add = add nsw i32 %2, %3
  %add2 = add nsw i32 %add, 1
  %mul = mul i32 1, %add2
  %call3 = call ptr @malloc(i32 noundef %mul) #2
  store ptr %call3, ptr %result, align 4
  store i32 0, ptr %i, align 4
  br label %for.cond

for.cond:                                         ; preds = %for.inc, %entry
  %4 = load i32, ptr %i, align 4
  %5 = load i32, ptr %len1, align 4
  %cmp = icmp slt i32 %4, %5
  br i1 %cmp, label %for.body, label %for.end

for.body:                                         ; preds = %for.cond
  %6 = load ptr, ptr %s1.addr, align 4
  %7 = load i32, ptr %i, align 4
  %arrayidx = getelementptr inbounds i8, ptr %6, i32 %7
  %8 = load i8, ptr %arrayidx, align 1
  %9 = load ptr, ptr %result, align 4
  %10 = load i32, ptr %i, align 4
  %arrayidx4 = getelementptr inbounds i8, ptr %9, i32 %10
  store i8 %8, ptr %arrayidx4, align 1
  br label %for.inc

for.inc:                                          ; preds = %for.body
  %11 = load i32, ptr %i, align 4
  %inc = add nsw i32 %11, 1
  store i32 %inc, ptr %i, align 4
  br label %for.cond, !llvm.loop !10

for.end:                                          ; preds = %for.cond
  store i32 0, ptr %i5, align 4
  br label %for.cond6

for.cond6:                                        ; preds = %for.inc12, %for.end
  %12 = load i32, ptr %i5, align 4
  %13 = load i32, ptr %len2, align 4
  %cmp7 = icmp slt i32 %12, %13
  br i1 %cmp7, label %for.body8, label %for.end14

for.body8:                                        ; preds = %for.cond6
  %14 = load ptr, ptr %s2.addr, align 4
  %15 = load i32, ptr %i5, align 4
  %arrayidx9 = getelementptr inbounds i8, ptr %14, i32 %15
  %16 = load i8, ptr %arrayidx9, align 1
  %17 = load ptr, ptr %result, align 4
  %18 = load i32, ptr %len1, align 4
  %19 = load i32, ptr %i5, align 4
  %add10 = add nsw i32 %18, %19
  %arrayidx11 = getelementptr inbounds i8, ptr %17, i32 %add10
  store i8 %16, ptr %arrayidx11, align 1
  br label %for.inc12

for.inc12:                                        ; preds = %for.body8
  %20 = load i32, ptr %i5, align 4
  %inc13 = add nsw i32 %20, 1
  store i32 %inc13, ptr %i5, align 4
  br label %for.cond6, !llvm.loop !11

for.end14:                                        ; preds = %for.cond6
  %21 = load ptr, ptr %result, align 4
  %22 = load i32, ptr %len1, align 4
  %23 = load i32, ptr %len2, align 4
  %add15 = add nsw i32 %22, %23
  %arrayidx16 = getelementptr inbounds i8, ptr %21, i32 %add15
  store i8 0, ptr %arrayidx16, align 1
  %24 = load ptr, ptr %result, align 4
  ret ptr %24
}

attributes #0 = { noinline nounwind optnone "frame-pointer"="all" "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #1 = { "frame-pointer"="all" "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #2 = { nobuiltin "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" }
attributes #3 = { "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" }

!llvm.module.flags = !{!0, !1, !2, !3}
!llvm.ident = !{!4}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 1, !"target-abi", !"ilp32"}
!2 = !{i32 7, !"frame-pointer", i32 2}
!3 = !{i32 8, !"SmallDataLimit", i32 8}
!4 = !{!"Ubuntu clang version 17.0.6 (++20231209124227+6009708b4367-1~exp1~20231209124336.77)"}
!5 = distinct !{!5, !6}
!6 = !{!"llvm.loop.mustprogress"}
!7 = distinct !{!7, !6}
!8 = distinct !{!8, !6}
!9 = distinct !{!9, !6}
!10 = distinct !{!10, !6}
!11 = distinct !{!11, !6}
