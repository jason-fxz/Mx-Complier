	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p1"
	.file	"builtin.c"
	.globl	print                           # -- Begin function print
	.p2align	1
	.type	print,@function
print:                                  # @print
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	printf
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end0:
	.size	print, .Lfunc_end0-print
                                        # -- End function
	.globl	println                         # -- Begin function println
	.p2align	1
	.type	println,@function
println:                                # @println
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.1)
	addi	a0, a0, %lo(.L.str.1)
	call	printf
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end1:
	.size	println, .Lfunc_end1-println
                                        # -- End function
	.globl	printInt                        # -- Begin function printInt
	.p2align	1
	.type	printInt,@function
printInt:                               # @printInt
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	call	printf
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end2:
	.size	printInt, .Lfunc_end2-printInt
                                        # -- End function
	.globl	printlnInt                      # -- Begin function printlnInt
	.p2align	1
	.type	printlnInt,@function
printlnInt:                             # @printlnInt
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	call	printf
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
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
	addi	s0, sp, 16
	li	a0, 1024
	call	malloc
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	scanf
	lw	a0, -12(s0)
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
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, s0, -12
	call	scanf
	lw	a0, -12(s0)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
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
	addi	s0, sp, 16
	sw	a0, -12(s0)
	li	a0, 12
	call	malloc
	sw	a0, -16(s0)
	lw	a0, -16(s0)
	lw	a2, -12(s0)
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	call	sprintf
	lw	a0, -16(s0)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
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
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a0, -12(s0)
	call	malloc
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end7:
	.size	__mx_allocate, .Lfunc_end7-__mx_allocate
                                        # -- End function
	.globl	__mx_allocate_array             # -- Begin function __mx_allocate_array
	.p2align	1
	.type	__mx_allocate_array,@function
__mx_allocate_array:                    # @__mx_allocate_array
# %bb.0:                                # %entry
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	addi	s0, sp, 32
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	mul	a0, a0, a1
	addi	a0, a0, 4
	call	malloc
	sw	a0, -20(s0)
	lw	a0, -16(s0)
	lw	a1, -20(s0)
	sw	a0, 0(a1)
	lw	a0, -20(s0)
	addi	a0, a0, 4
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end8:
	.size	__mx_allocate_array, .Lfunc_end8-__mx_allocate_array
                                        # -- End function
	.globl	__mx_array_size                 # -- Begin function __mx_array_size
	.p2align	1
	.type	__mx_array_size,@function
__mx_array_size:                        # @__mx_array_size
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	lw	a0, -12(s0)
	lw	a0, -4(a0)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end9:
	.size	__mx_array_size, .Lfunc_end9-__mx_array_size
                                        # -- End function
	.globl	__mx_bool_to_string             # -- Begin function __mx_bool_to_string
	.p2align	1
	.type	__mx_bool_to_string,@function
__mx_bool_to_string:                    # @__mx_bool_to_string
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sb	a0, -9(s0)
	lbu	a0, -9(s0)
	andi	a2, a0, 1
	lui	a0, %hi(.L.str.5)
	addi	a1, a0, %lo(.L.str.5)
	lui	a0, %hi(.L.str.4)
	addi	a0, a0, %lo(.L.str.4)
	bnez	a2, .LBB10_2
# %bb.1:                                # %entry
	mv	a0, a1
.LBB10_2:                               # %entry
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end10:
	.size	__mx_bool_to_string, .Lfunc_end10-__mx_bool_to_string
                                        # -- End function
	.globl	string.length                   # -- Begin function string.length
	.p2align	1
	.type	string.length,@function
string.length:                          # @string.length
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	zero, -16(s0)
	j	.LBB11_1
.LBB11_1:                               # %while.cond
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	beqz	a0, .LBB11_3
	j	.LBB11_2
.LBB11_2:                               # %while.body
                                        #   in Loop: Header=BB11_1 Depth=1
	lw	a0, -16(s0)
	addi	a0, a0, 1
	sw	a0, -16(s0)
	j	.LBB11_1
.LBB11_3:                               # %while.end
	lw	a0, -16(s0)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end11:
	.size	string.length, .Lfunc_end11-string.length
                                        # -- End function
	.globl	string.substring                # -- Begin function string.substring
	.p2align	1
	.type	string.substring,@function
string.substring:                       # @string.substring
# %bb.0:                                # %entry
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	addi	s0, sp, 32
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	sw	a2, -20(s0)
	lw	a0, -20(s0)
	lw	a1, -16(s0)
	sub	a0, a0, a1
	sw	a0, -24(s0)
	lw	a0, -24(s0)
	addi	a0, a0, 1
	call	malloc
	sw	a0, -28(s0)
	sw	zero, -32(s0)
	j	.LBB12_1
.LBB12_1:                               # %for.cond
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, -32(s0)
	lw	a1, -24(s0)
	bge	a0, a1, .LBB12_4
	j	.LBB12_2
.LBB12_2:                               # %for.body
                                        #   in Loop: Header=BB12_1 Depth=1
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	lw	a2, -32(s0)
	add	a1, a1, a2
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	a1, -28(s0)
	add	a1, a1, a2
	sb	a0, 0(a1)
	j	.LBB12_3
.LBB12_3:                               # %for.inc
                                        #   in Loop: Header=BB12_1 Depth=1
	lw	a0, -32(s0)
	addi	a0, a0, 1
	sw	a0, -32(s0)
	j	.LBB12_1
.LBB12_4:                               # %for.end
	lw	a0, -28(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	sb	zero, 0(a0)
	lw	a0, -28(s0)
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end12:
	.size	string.substring, .Lfunc_end12-string.substring
                                        # -- End function
	.globl	string.parseInt                 # -- Begin function string.parseInt
	.p2align	1
	.type	string.parseInt,@function
string.parseInt:                        # @string.parseInt
# %bb.0:                                # %entry
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	addi	s0, sp, 32
	sw	a0, -12(s0)
	sw	zero, -16(s0)
	sw	zero, -20(s0)
	lw	a0, -12(s0)
	lbu	a0, 0(a0)
	li	a1, 45
	bne	a0, a1, .LBB13_2
	j	.LBB13_1
.LBB13_1:                               # %if.then
	li	a0, 1
	sw	a0, -20(s0)
	j	.LBB13_2
.LBB13_2:                               # %if.end
	j	.LBB13_3
.LBB13_3:                               # %while.cond
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, -12(s0)
	lbu	a0, 0(a0)
	beqz	a0, .LBB13_5
	j	.LBB13_4
.LBB13_4:                               # %while.body
                                        #   in Loop: Header=BB13_3 Depth=1
	lw	a0, -16(s0)
	li	a1, 10
	mul	a0, a0, a1
	lw	a1, -12(s0)
	lbu	a1, 0(a1)
	add	a0, a0, a1
	addi	a0, a0, -48
	sw	a0, -16(s0)
	lw	a0, -12(s0)
	addi	a0, a0, 1
	sw	a0, -12(s0)
	j	.LBB13_3
.LBB13_5:                               # %while.end
	lw	a0, -20(s0)
	beqz	a0, .LBB13_7
	j	.LBB13_6
.LBB13_6:                               # %cond.true
	lw	a0, -16(s0)
	neg	a0, a0
	j	.LBB13_8
.LBB13_7:                               # %cond.false
	lw	a0, -16(s0)
	j	.LBB13_8
.LBB13_8:                               # %cond.end
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end13:
	.size	string.parseInt, .Lfunc_end13-string.parseInt
                                        # -- End function
	.globl	string.ord                      # -- Begin function string.ord
	.p2align	1
	.type	string.ord,@function
string.ord:                             # @string.ord
# %bb.0:                                # %entry
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	addi	s0, sp, 16
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	lw	a1, -16(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end14:
	.size	string.ord, .Lfunc_end14-string.ord
                                        # -- End function
	.globl	__mx_string_compare             # -- Begin function __mx_string_compare
	.p2align	1
	.type	__mx_string_compare,@function
__mx_string_compare:                    # @__mx_string_compare
# %bb.0:                                # %entry
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	addi	s0, sp, 32
	sw	a0, -16(s0)
	sw	a1, -20(s0)
	sw	zero, -24(s0)
	j	.LBB15_1
.LBB15_1:                               # %while.cond
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	lbu	a1, 0(a0)
	li	a0, 0
	beqz	a1, .LBB15_3
	j	.LBB15_2
.LBB15_2:                               # %land.rhs
                                        #   in Loop: Header=BB15_1 Depth=1
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	snez	a0, a0
	j	.LBB15_3
.LBB15_3:                               # %land.end
                                        #   in Loop: Header=BB15_1 Depth=1
	andi	a0, a0, 1
	beqz	a0, .LBB15_7
	j	.LBB15_4
.LBB15_4:                               # %while.body
                                        #   in Loop: Header=BB15_1 Depth=1
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	a2, -20(s0)
	add	a1, a1, a2
	lbu	a1, 0(a1)
	beq	a0, a1, .LBB15_6
	j	.LBB15_5
.LBB15_5:                               # %if.then
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	a2, -20(s0)
	add	a1, a1, a2
	lbu	a1, 0(a1)
	sub	a0, a0, a1
	sw	a0, -12(s0)
	j	.LBB15_8
.LBB15_6:                               # %if.end
                                        #   in Loop: Header=BB15_1 Depth=1
	lw	a0, -24(s0)
	addi	a0, a0, 1
	sw	a0, -24(s0)
	j	.LBB15_1
.LBB15_7:                               # %while.end
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	a2, -20(s0)
	add	a1, a1, a2
	lbu	a1, 0(a1)
	sub	a0, a0, a1
	sw	a0, -12(s0)
	j	.LBB15_8
.LBB15_8:                               # %return
	lw	a0, -12(s0)
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end15:
	.size	__mx_string_compare, .Lfunc_end15-__mx_string_compare
                                        # -- End function
	.globl	__mx_string_concat              # -- Begin function __mx_string_concat
	.p2align	1
	.type	__mx_string_concat,@function
__mx_string_concat:                     # @__mx_string_concat
# %bb.0:                                # %entry
	addi	sp, sp, -48
	sw	ra, 44(sp)                      # 4-byte Folded Spill
	sw	s0, 40(sp)                      # 4-byte Folded Spill
	addi	s0, sp, 48
	sw	a0, -12(s0)
	sw	a1, -16(s0)
	lw	a0, -12(s0)
	call	string.length
	sw	a0, -20(s0)
	lw	a0, -16(s0)
	call	string.length
	sw	a0, -24(s0)
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	add	a0, a0, a1
	addi	a0, a0, 1
	call	malloc
	sw	a0, -28(s0)
	sw	zero, -32(s0)
	j	.LBB16_1
.LBB16_1:                               # %for.cond
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, -32(s0)
	lw	a1, -20(s0)
	bge	a0, a1, .LBB16_4
	j	.LBB16_2
.LBB16_2:                               # %for.body
                                        #   in Loop: Header=BB16_1 Depth=1
	lw	a0, -12(s0)
	lw	a1, -32(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	a2, -28(s0)
	add	a1, a1, a2
	sb	a0, 0(a1)
	j	.LBB16_3
.LBB16_3:                               # %for.inc
                                        #   in Loop: Header=BB16_1 Depth=1
	lw	a0, -32(s0)
	addi	a0, a0, 1
	sw	a0, -32(s0)
	j	.LBB16_1
.LBB16_4:                               # %for.end
	sw	zero, -36(s0)
	j	.LBB16_5
.LBB16_5:                               # %for.cond6
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, -36(s0)
	lw	a1, -24(s0)
	bge	a0, a1, .LBB16_8
	j	.LBB16_6
.LBB16_6:                               # %for.body8
                                        #   in Loop: Header=BB16_5 Depth=1
	lw	a0, -16(s0)
	lw	a1, -36(s0)
	add	a0, a0, a1
	lbu	a0, 0(a0)
	lw	a2, -28(s0)
	lw	a3, -20(s0)
	add	a1, a1, a3
	add	a1, a1, a2
	sb	a0, 0(a1)
	j	.LBB16_7
.LBB16_7:                               # %for.inc12
                                        #   in Loop: Header=BB16_5 Depth=1
	lw	a0, -36(s0)
	addi	a0, a0, 1
	sw	a0, -36(s0)
	j	.LBB16_5
.LBB16_8:                               # %for.end14
	lw	a0, -28(s0)
	lw	a1, -20(s0)
	lw	a2, -24(s0)
	add	a1, a1, a2
	add	a0, a0, a1
	sb	zero, 0(a0)
	lw	a0, -28(s0)
	lw	ra, 44(sp)                      # 4-byte Folded Reload
	lw	s0, 40(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 48
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
