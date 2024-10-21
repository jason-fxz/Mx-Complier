; ModuleID = './src/builtin/builtin.c'
source_filename = "./src/builtin/builtin.c"
target datalayout = "e-m:e-p:32:32-i64:64-n32-S128"
target triple = "riscv32-unknown-unknown-elf"

@.str = private unnamed_addr constant [3 x i8] c"%s\00", align 1
@.str.1 = private unnamed_addr constant [4 x i8] c"%s\0A\00", align 1
@.str.2 = private unnamed_addr constant [3 x i8] c"%d\00", align 1
@.str.3 = private unnamed_addr constant [4 x i8] c"%d\0A\00", align 1
@.str.4 = private unnamed_addr constant [5 x i8] c"true\00", align 1
@.str.5 = private unnamed_addr constant [6 x i8] c"false\00", align 1

; Function Attrs: nounwind
define dso_local void @print(ptr noundef %s) local_unnamed_addr #0 {
entry:
  %call = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str, ptr noundef %s) #8
  ret void
}

declare dso_local i32 @printf(ptr noundef, ...) local_unnamed_addr #1

; Function Attrs: nounwind
define dso_local void @println(ptr noundef %s) local_unnamed_addr #0 {
entry:
  %call = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str.1, ptr noundef %s) #8
  ret void
}

; Function Attrs: nounwind
define dso_local void @printInt(i32 noundef %n) local_unnamed_addr #0 {
entry:
  %call = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str.2, i32 noundef %n) #8
  ret void
}

; Function Attrs: nounwind
define dso_local void @printlnInt(i32 noundef %n) local_unnamed_addr #0 {
entry:
  %call = tail call i32 (ptr, ...) @printf(ptr noundef nonnull @.str.3, i32 noundef %n) #8
  ret void
}

; Function Attrs: nounwind
define dso_local ptr @getString() local_unnamed_addr #0 {
entry:
  %call = tail call ptr @malloc(i32 noundef 1024) #8
  %call1 = tail call i32 (ptr, ...) @scanf(ptr noundef nonnull @.str, ptr noundef %call) #9
  ret ptr %call
}

; Function Attrs: mustprogress nocallback nofree nosync nounwind willreturn memory(argmem: readwrite)
declare void @llvm.lifetime.start.p0(i64 immarg, ptr nocapture) #2

declare dso_local ptr @malloc(i32 noundef) local_unnamed_addr #1

; Function Attrs: nofree nounwind
declare dso_local noundef i32 @scanf(ptr nocapture noundef readonly, ...) local_unnamed_addr #3

; Function Attrs: mustprogress nocallback nofree nosync nounwind willreturn memory(argmem: readwrite)
declare void @llvm.lifetime.end.p0(i64 immarg, ptr nocapture) #2

; Function Attrs: nofree nounwind
define dso_local i32 @getInt() local_unnamed_addr #3 {
entry:
  %n = alloca i32, align 4
  call void @llvm.lifetime.start.p0(i64 4, ptr nonnull %n) #10
  %call = call i32 (ptr, ...) @scanf(ptr noundef nonnull @.str.2, ptr noundef nonnull %n) #9
  %0 = load i32, ptr %n, align 4, !tbaa !4
  call void @llvm.lifetime.end.p0(i64 4, ptr nonnull %n) #10
  ret i32 %0
}

; Function Attrs: nounwind
define dso_local ptr @toString(i32 noundef %i) local_unnamed_addr #0 {
entry:
  %call = tail call ptr @malloc(i32 noundef 12) #8
  %call1 = tail call i32 (ptr, ptr, ...) @sprintf(ptr noundef nonnull dereferenceable(1) %call, ptr noundef nonnull dereferenceable(1) @.str.2, i32 noundef %i) #9
  ret ptr %call
}

; Function Attrs: nofree nounwind
declare dso_local noundef i32 @sprintf(ptr noalias nocapture noundef writeonly, ptr nocapture noundef readonly, ...) local_unnamed_addr #3

; Function Attrs: nounwind
define dso_local ptr @__mx_allocate(i32 noundef %size) local_unnamed_addr #0 {
entry:
  %call = tail call ptr @malloc(i32 noundef %size) #8
  ret ptr %call
}

; Function Attrs: nounwind
define dso_local nonnull ptr @__mx_allocate_array(i32 noundef %length) local_unnamed_addr #0 {
entry:
  %mul = shl nsw i32 %length, 2
  %add = add nsw i32 %mul, 4
  %call = tail call ptr @malloc(i32 noundef %add) #8
  store i32 %length, ptr %call, align 4, !tbaa !4
  %add.ptr = getelementptr inbounds i32, ptr %call, i32 1
  ret ptr %add.ptr
}

; Function Attrs: mustprogress nofree norecurse nosync nounwind willreturn memory(argmem: read)
define dso_local i32 @__mx_array_size(ptr nocapture noundef readonly %array) local_unnamed_addr #4 {
entry:
  %arrayidx = getelementptr inbounds i32, ptr %array, i32 -1
  %0 = load i32, ptr %arrayidx, align 4, !tbaa !4
  ret i32 %0
}

; Function Attrs: mustprogress nofree norecurse nosync nounwind willreturn memory(none)
define dso_local nonnull ptr @__mx_bool_to_string(i1 noundef zeroext %b) local_unnamed_addr #5 {
entry:
  %cond = select i1 %b, ptr @.str.4, ptr @.str.5
  ret ptr %cond
}

; Function Attrs: nofree norecurse nosync nounwind memory(argmem: read)
define dso_local i32 @string.length(ptr nocapture noundef readonly %s) local_unnamed_addr #6 {
entry:
  br label %while.cond

while.cond:                                       ; preds = %while.cond, %entry
  %i.0 = phi i32 [ 0, %entry ], [ %inc, %while.cond ]
  %arrayidx = getelementptr inbounds i8, ptr %s, i32 %i.0
  %0 = load i8, ptr %arrayidx, align 1, !tbaa !8
  %cmp.not = icmp eq i8 %0, 0
  %inc = add nuw nsw i32 %i.0, 1
  br i1 %cmp.not, label %while.end, label %while.cond, !llvm.loop !9

while.end:                                        ; preds = %while.cond
  ret i32 %i.0
}

; Function Attrs: nounwind
define dso_local ptr @string.substring(ptr nocapture noundef readonly %s, i32 noundef %left, i32 noundef %right) local_unnamed_addr #0 {
entry:
  %sub = sub nsw i32 %right, %left
  %add = add nsw i32 %sub, 1
  %call = tail call ptr @malloc(i32 noundef %add) #8
  %cmp12 = icmp sgt i32 %sub, 0
  br i1 %cmp12, label %for.body, label %for.cond.cleanup

for.cond.cleanup:                                 ; preds = %for.body, %entry
  %arrayidx3 = getelementptr inbounds i8, ptr %call, i32 %sub
  store i8 0, ptr %arrayidx3, align 1, !tbaa !8
  ret ptr %call

for.body:                                         ; preds = %entry, %for.body
  %i.013 = phi i32 [ %inc, %for.body ], [ 0, %entry ]
  %add1 = add nsw i32 %i.013, %left
  %arrayidx = getelementptr inbounds i8, ptr %s, i32 %add1
  %0 = load i8, ptr %arrayidx, align 1, !tbaa !8
  %arrayidx2 = getelementptr inbounds i8, ptr %call, i32 %i.013
  store i8 %0, ptr %arrayidx2, align 1, !tbaa !8
  %inc = add nuw nsw i32 %i.013, 1
  %exitcond.not = icmp eq i32 %inc, %sub
  br i1 %exitcond.not, label %for.cond.cleanup, label %for.body, !llvm.loop !11
}

; Function Attrs: nofree norecurse nosync nounwind memory(read, inaccessiblemem: none)
define dso_local i32 @string.parseInt(ptr nocapture noundef readonly %s) local_unnamed_addr #7 {
entry:
  %0 = load i8, ptr %s, align 1, !tbaa !8
  %cmp3.not12 = icmp eq i8 %0, 0
  br i1 %cmp3.not12, label %while.end, label %while.body

while.body:                                       ; preds = %entry, %while.body
  %result.014 = phi i32 [ %sub, %while.body ], [ 0, %entry ]
  %s.addr.013 = phi ptr [ %incdec.ptr, %while.body ], [ %s, %entry ]
  %1 = phi i8 [ %.pr, %while.body ], [ %0, %entry ]
  %conv2 = zext i8 %1 to i32
  %mul = mul nsw i32 %result.014, 10
  %add = add nsw i32 %conv2, -48
  %sub = add i32 %add, %mul
  %incdec.ptr = getelementptr inbounds i8, ptr %s.addr.013, i32 1
  %.pr = load i8, ptr %incdec.ptr, align 1, !tbaa !8
  %cmp3.not = icmp eq i8 %.pr, 0
  br i1 %cmp3.not, label %while.end, label %while.body, !llvm.loop !12

while.end:                                        ; preds = %while.body, %entry
  %result.0.lcssa = phi i32 [ 0, %entry ], [ %sub, %while.body ]
  %cmp = icmp eq i8 %0, 45
  %sub6 = sub nsw i32 0, %result.0.lcssa
  %cond = select i1 %cmp, i32 %sub6, i32 %result.0.lcssa
  ret i32 %cond
}

; Function Attrs: mustprogress nofree norecurse nosync nounwind willreturn memory(argmem: read)
define dso_local i32 @string.ord(ptr nocapture noundef readonly %s, i32 noundef %i) local_unnamed_addr #4 {
entry:
  %arrayidx = getelementptr inbounds i8, ptr %s, i32 %i
  %0 = load i8, ptr %arrayidx, align 1, !tbaa !8
  %conv = zext i8 %0 to i32
  ret i32 %conv
}

; Function Attrs: nofree norecurse nosync nounwind memory(argmem: read)
define dso_local i32 @__mx_string_compare(ptr nocapture noundef readonly %s1, ptr nocapture noundef readonly %s2) local_unnamed_addr #6 {
entry:
  %0 = load i8, ptr %s1, align 1, !tbaa !8
  %cmp.not38 = icmp eq i8 %0, 0
  br i1 %cmp.not38, label %while.end, label %land.rhs

land.rhs:                                         ; preds = %entry, %if.end
  %1 = phi i8 [ %3, %if.end ], [ %0, %entry ]
  %i.039 = phi i32 [ %inc, %if.end ], [ 0, %entry ]
  %arrayidx2 = getelementptr inbounds i8, ptr %s2, i32 %i.039
  %2 = load i8, ptr %arrayidx2, align 1, !tbaa !8
  %cmp4.not = icmp eq i8 %2, 0
  br i1 %cmp4.not, label %while.end, label %while.body

while.body:                                       ; preds = %land.rhs
  %cmp10.not = icmp eq i8 %1, %2
  br i1 %cmp10.not, label %if.end, label %cleanup

if.end:                                           ; preds = %while.body
  %inc = add nuw nsw i32 %i.039, 1
  %arrayidx = getelementptr inbounds i8, ptr %s1, i32 %inc
  %3 = load i8, ptr %arrayidx, align 1, !tbaa !8
  %cmp.not = icmp eq i8 %3, 0
  br i1 %cmp.not, label %while.end, label %land.rhs, !llvm.loop !13

while.end:                                        ; preds = %land.rhs, %if.end, %entry
  %i.0.lcssa = phi i32 [ 0, %entry ], [ %inc, %if.end ], [ %i.039, %land.rhs ]
  %.lcssa = phi i8 [ 0, %entry ], [ 0, %if.end ], [ %1, %land.rhs ]
  %arrayidx18 = getelementptr inbounds i8, ptr %s2, i32 %i.0.lcssa
  %4 = load i8, ptr %arrayidx18, align 1, !tbaa !8
  br label %cleanup

cleanup:                                          ; preds = %while.body, %while.end
  %5 = phi i8 [ %.lcssa, %while.end ], [ %1, %while.body ]
  %conv3.pn.in = phi i8 [ %4, %while.end ], [ %2, %while.body ]
  %conv = zext i8 %5 to i32
  %conv3.pn = zext i8 %conv3.pn.in to i32
  %retval.0 = sub nsw i32 %conv, %conv3.pn
  ret i32 %retval.0
}

; Function Attrs: nounwind
define dso_local ptr @__mx_string_concat(ptr nocapture noundef readonly %s1, ptr nocapture noundef readonly %s2) local_unnamed_addr #0 {
entry:
  br label %while.cond.i

while.cond.i:                                     ; preds = %while.cond.i, %entry
  %i.0.i = phi i32 [ 0, %entry ], [ %inc.i, %while.cond.i ]
  %arrayidx.i = getelementptr inbounds i8, ptr %s1, i32 %i.0.i
  %0 = load i8, ptr %arrayidx.i, align 1, !tbaa !8
  %cmp.not.i = icmp eq i8 %0, 0
  %inc.i = add nuw nsw i32 %i.0.i, 1
  br i1 %cmp.not.i, label %while.cond.i34, label %while.cond.i, !llvm.loop !9

while.cond.i34:                                   ; preds = %while.cond.i, %while.cond.i34
  %i.0.i35 = phi i32 [ %inc.i38, %while.cond.i34 ], [ 0, %while.cond.i ]
  %arrayidx.i36 = getelementptr inbounds i8, ptr %s2, i32 %i.0.i35
  %1 = load i8, ptr %arrayidx.i36, align 1, !tbaa !8
  %cmp.not.i37 = icmp eq i8 %1, 0
  %inc.i38 = add nuw nsw i32 %i.0.i35, 1
  br i1 %cmp.not.i37, label %string.length.exit39, label %while.cond.i34, !llvm.loop !9

string.length.exit39:                             ; preds = %while.cond.i34
  %add = add nuw nsw i32 %i.0.i35, %i.0.i
  %add2 = add nuw nsw i32 %add, 1
  %call3 = tail call ptr @malloc(i32 noundef %add2) #8
  %cmp40.not = icmp eq i32 %i.0.i, 0
  br i1 %cmp40.not, label %for.cond6.preheader, label %for.body

for.cond6.preheader:                              ; preds = %for.body, %string.length.exit39
  %cmp742.not = icmp eq i32 %i.0.i35, 0
  br i1 %cmp742.not, label %for.cond.cleanup8, label %for.body9

for.body:                                         ; preds = %string.length.exit39, %for.body
  %i.041 = phi i32 [ %inc, %for.body ], [ 0, %string.length.exit39 ]
  %arrayidx = getelementptr inbounds i8, ptr %s1, i32 %i.041
  %2 = load i8, ptr %arrayidx, align 1, !tbaa !8
  %arrayidx4 = getelementptr inbounds i8, ptr %call3, i32 %i.041
  store i8 %2, ptr %arrayidx4, align 1, !tbaa !8
  %inc = add nuw nsw i32 %i.041, 1
  %exitcond.not = icmp eq i32 %inc, %i.0.i
  br i1 %exitcond.not, label %for.cond6.preheader, label %for.body, !llvm.loop !14

for.cond.cleanup8:                                ; preds = %for.body9, %for.cond6.preheader
  %arrayidx17 = getelementptr inbounds i8, ptr %call3, i32 %add
  store i8 0, ptr %arrayidx17, align 1, !tbaa !8
  ret ptr %call3

for.body9:                                        ; preds = %for.cond6.preheader, %for.body9
  %i5.043 = phi i32 [ %inc14, %for.body9 ], [ 0, %for.cond6.preheader ]
  %arrayidx10 = getelementptr inbounds i8, ptr %s2, i32 %i5.043
  %3 = load i8, ptr %arrayidx10, align 1, !tbaa !8
  %add11 = add nuw nsw i32 %i5.043, %i.0.i
  %arrayidx12 = getelementptr inbounds i8, ptr %call3, i32 %add11
  store i8 %3, ptr %arrayidx12, align 1, !tbaa !8
  %inc14 = add nuw nsw i32 %i5.043, 1
  %exitcond46.not = icmp eq i32 %inc14, %i.0.i35
  br i1 %exitcond46.not, label %for.cond.cleanup8, label %for.body9, !llvm.loop !15
}

attributes #0 = { nounwind "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #1 = { "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #2 = { mustprogress nocallback nofree nosync nounwind willreturn memory(argmem: readwrite) }
attributes #3 = { nofree nounwind "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #4 = { mustprogress nofree norecurse nosync nounwind willreturn memory(argmem: read) "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #5 = { mustprogress nofree norecurse nosync nounwind willreturn memory(none) "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #6 = { nofree norecurse nosync nounwind memory(argmem: read) "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #7 = { nofree norecurse nosync nounwind memory(read, inaccessiblemem: none) "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="generic-rv32" "target-features"="+32bit,+a,+c,+m,+relax,-d,-e,-experimental-smaia,-experimental-ssaia,-experimental-zacas,-experimental-zfa,-experimental-zfbfmin,-experimental-zicond,-experimental-zihintntl,-experimental-ztso,-experimental-zvbb,-experimental-zvbc,-experimental-zvfbfmin,-experimental-zvfbfwma,-experimental-zvkg,-experimental-zvkn,-experimental-zvknc,-experimental-zvkned,-experimental-zvkng,-experimental-zvknha,-experimental-zvknhb,-experimental-zvks,-experimental-zvksc,-experimental-zvksed,-experimental-zvksg,-experimental-zvksh,-experimental-zvkt,-f,-h,-save-restore,-svinval,-svnapot,-svpbmt,-v,-xcvbitmanip,-xcvmac,-xsfcie,-xsfvcp,-xtheadba,-xtheadbb,-xtheadbs,-xtheadcmo,-xtheadcondmov,-xtheadfmemidx,-xtheadmac,-xtheadmemidx,-xtheadmempair,-xtheadsync,-xtheadvdot,-xventanacondops,-zawrs,-zba,-zbb,-zbc,-zbkb,-zbkc,-zbkx,-zbs,-zca,-zcb,-zcd,-zce,-zcf,-zcmp,-zcmt,-zdinx,-zfh,-zfhmin,-zfinx,-zhinx,-zhinxmin,-zicbom,-zicbop,-zicboz,-zicntr,-zicsr,-zifencei,-zihintpause,-zihpm,-zk,-zkn,-zknd,-zkne,-zknh,-zkr,-zks,-zksed,-zksh,-zkt,-zmmul,-zve32f,-zve32x,-zve64d,-zve64f,-zve64x,-zvfh,-zvl1024b,-zvl128b,-zvl16384b,-zvl2048b,-zvl256b,-zvl32768b,-zvl32b,-zvl4096b,-zvl512b,-zvl64b,-zvl65536b,-zvl8192b" }
attributes #8 = { nobuiltin nounwind "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" }
attributes #9 = { "no-builtin-malloc" "no-builtin-memcpy" "no-builtin-printf" "no-builtin-strlen" }
attributes #10 = { nounwind }

!llvm.module.flags = !{!0, !1, !2}
!llvm.ident = !{!3}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 1, !"target-abi", !"ilp32"}
!2 = !{i32 8, !"SmallDataLimit", i32 8}
!3 = !{!"Ubuntu clang version 17.0.6 (++20231209124227+6009708b4367-1~exp1~20231209124336.77)"}
!4 = !{!5, !5, i64 0}
!5 = !{!"int", !6, i64 0}
!6 = !{!"omnipotent char", !7, i64 0}
!7 = !{!"Simple C/C++ TBAA"}
!8 = !{!6, !6, i64 0}
!9 = distinct !{!9, !10}
!10 = !{!"llvm.loop.mustprogress"}
!11 = distinct !{!11, !10}
!12 = distinct !{!12, !10}
!13 = distinct !{!13, !10}
!14 = distinct !{!14, !10}
!15 = distinct !{!15, !10}
