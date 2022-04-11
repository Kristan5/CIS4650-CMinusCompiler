* Standard Prelude
0: LD 6,0(0)	Load GP with max address
1: LDA 5,0(6)	Copy GP to FP
2: ST 0,0(0)	Clear value at location 0
* Jump around I/O functions
* Code for Input Routine
4: ST 0,-1(5)	Store return
5: IN 0,0,0	Input
6: LD 7,-1(5)	Return caller
* Code for Output Routine
7: ST 0,-1(5)	Store return
8: LD 0,-2(5)	Load output value
9: OUT 0,0,0	Output
10: LD 7,-1(5)	Return caller
3: LDA 7,7(7)	Jump around I/O code
* -> FunctionDec
* Processing Function: main
* Jump around function body here
12: ST 0,-1(5)	Store Return
* -> Compound Statement
* Processing local variable : x
* Processing local variable : fac
* -> OP
* -> ID
* Looking up ID: x
13: LDA 0,0(5)	Load ID Address
* <- ID
14: ST 0,-4(5)	OP: Push Left
* -> Call
* Call of Function: input
15: ST 5,-5(5)	Push OFP
16: LDA 5,-5(5)	Push Frame
17: LDA 0,1(7)	Load AC with Ret Pointer
18: LDA 7,-19(7)	Jump to Function Location
19: LD 5,0(5)	Pop Frame
* <- Call
20: LD 1,-4(5)	OP: Load Left
21: ST 0,0(1)	Assign: Store Value
* <- OP
* -> OP
* -> ID
* Looking up ID: fac
22: LDA 0,0(5)	Load ID Address
* <- ID
23: ST 0,-4(5)	OP: Push Left
* -> Constant
24: LDC 0,1(0)	Load Constant
* <- Constant
25: LD 1,-4(5)	OP: Load Left
26: ST 0,0(1)	Assign: Store Value
* <- OP
* -> WHILE
* While: Jump After Body Comes Back Here
* -> OP
* -> ID
* Looking up ID: x
27: LD 0,0(5)	Load ID Value
* <- ID
28: ST 0,-4(5)	OP: Push Left
* -> Constant
29: LDC 0,1(0)	Load Constant
* <- Constant
30: LD 1,-4(5)	OP: Load Left
31: SUB 0,1,0	OP >
32: JGT 0,2(7)	 
33: LDC 0,0(0)	False Case
34: LDA 7,1(7)	Unconditional Jump
35: LDC 0,1(0)	True Case
* <- OP
* -> Compound Statement
* -> OP
* -> ID
* Looking up ID: fac
37: LDA 0,0(5)	Load ID Address
* <- ID
38: ST 0,-4(5)	OP: Push Left
* -> OP
* -> ID
* Looking up ID: fac
39: LD 0,0(5)	Load ID Value
* <- ID
40: ST 0,-5(5)	OP: Push Left
* -> ID
* Looking up ID: x
41: LD 0,0(5)	Load ID Value
* <- ID
42: LD 1,-5(5)	OP: Load Left
43: MUL 0,1,0	OP *
* <- OP
44: LD 1,-4(5)	OP: Load Left
45: ST 0,0(1)	Assign: Store Value
* <- OP
* -> OP
* -> ID
* Looking up ID: x
46: LDA 0,0(5)	Load ID Address
* <- ID
47: ST 0,-4(5)	OP: Push Left
* -> OP
* -> ID
* Looking up ID: x
48: LD 0,0(5)	Load ID Value
* <- ID
49: ST 0,-5(5)	OP: Push Left
* -> Constant
50: LDC 0,1(0)	Load Constant
* <- Constant
51: LD 1,-5(5)	OP: Load Left
52: SUB 0,1,0	OP -
* <- OP
53: LD 1,-4(5)	OP: Load Left
54: ST 0,0(1)	Assign: Store Value
* <- OP
* <- Compound Statement
55: LDA 7,-29(7)	While: Absolute Jump to Test
36: JEQ 0,19(7)	While: Jump to End
* <- While
* -> Call
* Call of Function: output
* -> ID
* Looking up ID: fac
37: LD 0,0(5)	Load ID Value
* <- ID
38: ST 0,-6(5)	OP: Push Left
39: ST 5,-4(5)	Push OFP
40: LDA 5,-4(5)	Push Frame
41: LDA 0,1(7)	Load AC with Ret Pointer
42: LDA 7,-43(7)	Jump to Function Location
43: LD 5,0(5)	Pop Frame
* <- Call
* <- Compound Statement
44: LD 7,-1(5)	Return Caller
11: LDA 7,33(7)	Jump around function body
* <- FunctionDec
* Finale Generation
56: ST 5,0(5)	Push Old Frame Pointer
57: LDA 5,0(5)	Push frame
58: LDA 0,1(7)	Load AC with return pointer
59: LDA 7,-48(7)	Jump to main location
60: LD 5,0(5)	Pop frame
61: HALT 0,0,0	HALT
