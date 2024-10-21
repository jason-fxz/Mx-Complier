	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p1"
	.file	"builtin.c"
	.globl	print                           # -- Begin function print
	.p2align	1
	.type	print,@function
print:                                  # @print
# %bb.0:                                # %entry
	lui	a1, %hi(.L.str)
	addi	a1, a1, %lo(.L.str)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end0:
	.size	print, .Lfunc_end0-print
                                        # -- End function
	.globl	println                         # -- Begin function println
	.p2align	1
	.type	println,@function
println:                                # @println
# %bb.0:                                # %entry
	lui	a1, %hi(.L.str.1)
	addi	a1, a1, %lo(.L.str.1)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end1:
	.size	println, .Lfunc_end1-println
                                        # -- End function
	.globl	printInt                        # -- Begin function printInt
	.p2align	1
	.type	printInt,@function
printInt:                               # @printInt
# %bb.0:                                # %entry
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end2:
	.size	printInt, .Lfunc_end2-printInt
                                        # -- End function
	.globl	printlnInt                      # -- Begin function printlnInt
	.p2align	1
	.type	printlnInt,@function
printlnInt:                             # @printlnInt
# %bb.0:                                # %entry
	lui	a1, %hi(.L.str.3)
	addi	a1, a1, %lo(.L.str.3)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end3:
	.size	printlnInt, .Lfunc_end3-printlnInt
                                        # -- End function
	.globl	getString                       # -- Begin function getString
	.p2align	1
	.type	getString,@function
getString:                              # @getString
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	li	a0, 1024
	call	malloc
	mv	s0, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	mv	a1, s0
	call	scanf
	mv	a0, s0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end4:
	.size	getString, .Lfunc_end4-getString
                                        # -- End function
	.globl	getInt                          # -- Begin function getInt
	.p2align	1
	.type	getInt,@function
getInt:                                 # @getInt
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end5:
	.size	getInt, .Lfunc_end5-getInt
                                        # -- End function
	.globl	toString                        # -- Begin function toString
	.p2align	1
	.type	toString,@function
toString:                               # @toString
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	mv	s0, a0
	li	a0, 12
	call	malloc
	mv	s1, a0
	lui	a0, %hi(.L.str.2)
	addi	a1, a0, %lo(.L.str.2)
	mv	a0, s1
	mv	a2, s0
	call	sprintf
	mv	a0, s1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end6:
	.size	toString, .Lfunc_end6-toString
                                        # -- End function
	.globl	__mx_allocate                   # -- Begin function __mx_allocate
	.p2align	1
	.type	__mx_allocate,@function
__mx_allocate:                          # @__mx_allocate
# %bb.0:                                # %entry
	tail	malloc
.Lfunc_end7:
	.size	__mx_allocate, .Lfunc_end7-__mx_allocate
                                        # -- End function
	.globl	__mx_allocate_array             # -- Begin function __mx_allocate_array
	.p2align	1
	.type	__mx_allocate_array,@function
__mx_allocate_array:                    # @__mx_allocate_array
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	mv	s0, a0
	slli	a0, a0, 2
	addi	a0, a0, 4
	call	malloc
	addi	a1, a0, 4
	sw	s0, 0(a0)
	mv	a0, a1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end8:
	.size	__mx_allocate_array, .Lfunc_end8-__mx_allocate_array
                                        # -- End function
	.globl	__mx_array_size                 # -- Begin function __mx_array_size
	.p2align	1
	.type	__mx_array_size,@function
__mx_array_size:                        # @__mx_array_size
# %bb.0:                                # %entry
	lw	a0, -4(a0)
	ret
.Lfunc_end9:
	.size	__mx_array_size, .Lfunc_end9-__mx_array_size
                                        # -- End function
	.globl	__mx_bool_to_string             # -- Begin function __mx_bool_to_string
	.p2align	1
	.type	__mx_bool_to_string,@function
__mx_bool_to_string:                    # @__mx_bool_to_string
# %bb.0:                                # %entry
	bnez	a0, .LBB10_2
# %bb.1:                                # %entry
	lui	a0, %hi(.L.str.5)
	addi	a0, a0, %lo(.L.str.5)
	ret
.LBB10_2:
	lui	a0, %hi(.L.str.4)
	addi	a0, a0, %lo(.L.str.4)
	ret
.Lfunc_end10:
	.size	__mx_bool_to_string, .Lfunc_end10-__mx_bool_to_string
                                        # -- End function
	.globl	string.length                   # -- Begin function string.length
	.p2align	1
	.type	string.length,@function
string.length:                          # @string.length
# %bb.0:                                # %entry
	li	a1, 0
.LBB11_1:                               # %while.cond
                                        # =>This Inner Loop Header: Depth=1
	add	a2, a0, a1
	lbu	a2, 0(a2)
	addi	a1, a1, 1
	bnez	a2, .LBB11_1
# %bb.2:                                # %while.end
	addi	a0, a1, -1
	ret
.Lfunc_end11:
	.size	string.length, .Lfunc_end11-string.length
                                        # -- End function
	.globl	string.substring                # -- Begin function string.substring
	.p2align	1
	.type	string.substring,@function
string.substring:                       # @string.substring
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	sw	s2, 0(sp)                       # 4-byte Folded Spill
	mv	s0, a1
	mv	s2, a0
	sub	s1, a2, a1
	addi	a0, s1, 1
	call	malloc
	blez	s1, .LBB12_3
# %bb.1:                                # %for.body.preheader
	add	s0, s0, s2
	mv	a1, a0
	mv	a2, s1
.LBB12_2:                               # %for.body
                                        # =>This Inner Loop Header: Depth=1
	lbu	a3, 0(s0)
	sb	a3, 0(a1)
	addi	a2, a2, -1
	addi	a1, a1, 1
	addi	s0, s0, 1
	bnez	a2, .LBB12_2
.LBB12_3:                               # %for.cond.cleanup
	add	s1, s1, a0
	sb	zero, 0(s1)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	lw	s2, 0(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end12:
	.size	string.substring, .Lfunc_end12-string.substring
                                        # -- End function
	.globl	string.parseInt                 # -- Begin function string.parseInt
	.p2align	1
	.type	string.parseInt,@function
string.parseInt:                        # @string.parseInt
# %bb.0:                                # %entry
	lbu	a2, 0(a0)
	beqz	a2, .LBB13_6
# %bb.1:                                # %while.body.preheader
	mv	a1, a0
	li	a0, 0
	addi	a1, a1, 1
	li	a3, 10
	mv	a4, a2
.LBB13_2:                               # %while.body
                                        # =>This Inner Loop Header: Depth=1
	andi	a5, a4, 255
	lbu	a4, 0(a1)
	mul	a0, a0, a3
	add	a0, a0, a5
	addi	a0, a0, -48
	addi	a1, a1, 1
	bnez	a4, .LBB13_2
# %bb.3:                                # %while.end
	li	a1, 45
	bne	a2, a1, .LBB13_5
.LBB13_4:
	neg	a0, a0
.LBB13_5:                               # %while.end
	ret
.LBB13_6:
	li	a0, 0
	li	a1, 45
	beq	a2, a1, .LBB13_4
	j	.LBB13_5
.Lfunc_end13:
	.size	string.parseInt, .Lfunc_end13-string.parseInt
                                        # -- End function
	.globl	string.ord                      # -- Begin function string.ord
	.p2align	1
	.type	string.ord,@function
string.ord:                             # @string.ord
# %bb.0:                                # %entry
	add	a0, a0, a1
	lbu	a0, 0(a0)
	ret
.Lfunc_end14:
	.size	string.ord, .Lfunc_end14-string.ord
                                        # -- End function
	.globl	__mx_string_compare             # -- Begin function __mx_string_compare
	.p2align	1
	.type	__mx_string_compare,@function
__mx_string_compare:                    # @__mx_string_compare
# %bb.0:                                # %entry
	lbu	a2, 0(a0)
	beqz	a2, .LBB15_5
# %bb.1:                                # %land.rhs.preheader
	li	a3, 0
	addi	a0, a0, 1
.LBB15_2:                               # %land.rhs
                                        # =>This Inner Loop Header: Depth=1
	add	a4, a1, a3
	lbu	a4, 0(a4)
	beqz	a4, .LBB15_6
# %bb.3:                                # %while.body
                                        #   in Loop: Header=BB15_2 Depth=1
	andi	a5, a2, 255
	bne	a5, a4, .LBB15_8
# %bb.4:                                # %if.end
                                        #   in Loop: Header=BB15_2 Depth=1
	add	a2, a0, a3
	lbu	a2, 0(a2)
	addi	a4, a3, 1
	mv	a3, a4
	bnez	a2, .LBB15_2
	j	.LBB15_7
.LBB15_5:
	li	a4, 0
	j	.LBB15_7
.LBB15_6:
	mv	a4, a3
.LBB15_7:                               # %while.end
	add	a1, a1, a4
	lbu	a4, 0(a1)
.LBB15_8:                               # %cleanup
	andi	a0, a2, 255
	sub	a0, a0, a4
	ret
.Lfunc_end15:
	.size	__mx_string_compare, .Lfunc_end15-__mx_string_compare
                                        # -- End function
	.globl	__mx_string_concat              # -- Begin function __mx_string_concat
	.p2align	1
	.type	__mx_string_concat,@function
__mx_string_concat:                     # @__mx_string_concat
# %bb.0:                                # %entry
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	sw	s1, 20(sp)                      # 4-byte Folded Spill
	sw	s2, 16(sp)                      # 4-byte Folded Spill
	sw	s3, 12(sp)                      # 4-byte Folded Spill
	sw	s4, 8(sp)                       # 4-byte Folded Spill
	mv	s2, a1
	mv	s4, a0
	li	a0, 0
	li	a1, -1
.LBB16_1:                               # %while.cond.i
                                        # =>This Inner Loop Header: Depth=1
	mv	s3, a0
	add	a0, a0, s4
	lbu	a2, 0(a0)
	mv	s0, a1
	addi	a0, s3, 1
	addi	a1, a1, 1
	bnez	a2, .LBB16_1
# %bb.2:                                # %while.cond.i34.preheader
	li	s1, 1
	mv	a1, s2
.LBB16_3:                               # %while.cond.i34
                                        # =>This Inner Loop Header: Depth=1
	lbu	a2, 0(a1)
	addi	s1, s1, -1
	addi	s0, s0, 1
	addi	a1, a1, 1
	bnez	a2, .LBB16_3
# %bb.4:                                # %string.length.exit39
	sub	a0, a0, s1
	call	malloc
	beqz	s3, .LBB16_7
# %bb.5:
	mv	a1, a0
	mv	a2, s3
.LBB16_6:                               # %for.body
                                        # =>This Inner Loop Header: Depth=1
	lbu	a3, 0(s4)
	sb	a3, 0(a1)
	addi	a2, a2, -1
	addi	a1, a1, 1
	addi	s4, s4, 1
	bnez	a2, .LBB16_6
.LBB16_7:                               # %for.cond6.preheader
	beqz	s1, .LBB16_10
# %bb.8:                                # %for.body9.preheader
	neg	a1, s1
	add	s3, s3, a0
.LBB16_9:                               # %for.body9
                                        # =>This Inner Loop Header: Depth=1
	lbu	a2, 0(s2)
	sb	a2, 0(s3)
	addi	a1, a1, -1
	addi	s3, s3, 1
	addi	s2, s2, 1
	bnez	a1, .LBB16_9
.LBB16_10:                              # %for.cond.cleanup8
	add	s0, s0, a0
	sb	zero, 0(s0)
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	lw	s1, 20(sp)                      # 4-byte Folded Reload
	lw	s2, 16(sp)                      # 4-byte Folded Reload
	lw	s3, 12(sp)                      # 4-byte Folded Reload
	lw	s4, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end16:
	.size	__mx_string_concat, .Lfunc_end16-__mx_string_concat
                                        # -- End function
	.type	.L.str,@object                  # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object                # @.str.1
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

	.type	.L.str.2,@object                # @.str.2
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object                # @.str.3
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.type	.L.str.4,@object                # @.str.4
.L.str.4:
	.asciz	"true"
	.size	.L.str.4, 5

	.type	.L.str.5,@object                # @.str.5
.L.str.5:
	.asciz	"false"
	.size	.L.str.5, 6

	.ident	"Ubuntu clang version 17.0.6 (++20231209124227+6009708b4367-1~exp1~20231209124336.77)"
	.section	".note.GNU-stack","",@progbits
