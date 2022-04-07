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
* Finale Generation
11: ST 5,0(5)	Push Old Frame Pointer
12: LDA 5,0(5)	Push frame
13: LDA 0,1(7)	Load AC with return pointer
