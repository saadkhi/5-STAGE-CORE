package CONTROL

import chisel3._
import chisel3.util._

class mem_wr extends Module{

	val io = IO(new Bundle{
		
		val regwrite = Input(UInt(1.W))
		val memtoreg = Input(UInt(1.W))
		val memread = Input(UInt(1.W))
		val memwrite = Input(UInt(1.W))
		val memdatain = Input(SInt(32.W))
		val out = Input(SInt(32.W))
		val rd = Input(UInt(5.W))
	//	val rs2s = Input(UInt(5.W))
	//	val baseReg_in = Input(SInt(32.W))
	//	val offSet_in = Input(SInt(32.W))
	
	    val regWrite_out = Output(UInt(1.W))
		val memToReg_out = Output(UInt(1.W))
		val memRead_out = Output(UInt(1.W))
		val memWrite_out = Output(UInt(1.W))
		val dataOut_out = Output(SInt(32.W))
		val aluOutput_out = Output(SInt(32.W))
		val rd_out = Output(UInt(5.W))
	//	val rs2Sel_out = Output(UInt(5.W))
	//	val baseReg_out = Output(SInt(32.W))
	//	val offSet_out = Output(SInt(32.W))
	})
	
	val reg_regWrite = RegInit(0.U(1.W))
	val reg_memToReg = RegInit(0.U(1.W))
	val reg_memRead = RegInit(0.U(1.W))
	val reg_memWrite = RegInit(0.U(1.W))
	val reg_dataOut = RegInit(0.S(32.W))
	val reg_aluOutput = RegInit(0.S(32.W))
	val reg_rd = RegInit(0.U(5.W))
//	val reg_rs2Sel = RegInit(0.U(5.W))
//	val reg_baseReg = RegInit(0.S(32.W))
//	val reg_offSet = RegInit(0.S(32.W))

	reg_regWrite := io.regwrite
	reg_memToReg := io.memtoreg
	reg_memRead := io.memread
	reg_memWrite := io.memwrite
	reg_dataOut := io.memdatain
	reg_aluOutput := io.out
	reg_rd := io.rd
//	reg_rs2Sel := io.rs2s
//	reg_baseReg := io.baseReg_in
//	reg_offSet := io.offSet_in
	
	io.regWrite_out := reg_regWrite
	io.memToReg_out := reg_memToReg
	io.memRead_out := reg_memRead
	io.memWrite_out := reg_memWrite
	io.dataOut_out := reg_dataOut
	io.aluOutput_out := reg_aluOutput
	io.rd_out := reg_rd
//	io.rs2Sel_out := reg_rs2Sel
//	io.baseReg_out := reg_baseReg
//	io.offSet_out := reg_offSet
}


// val memtoreg = Input(UInt(1.W))
// 		val rd = Input(UInt(5.W))
// 		val memout = Input(SInt(32.W)) ////
// 		val out = Input(SInt(32.W))
// 		val regwrite = Input(UInt(1.W))
// 		val rs2s = Input(UInt(5.W))
// 	//	val baseReg_in = Input(SInt(32.W))
// 	//	val offSet_in = Input(SInt(32.W))
// 		val memread = Input(UInt(1.W))
// 		val memwrite = Input(UInt(1.W))

// 		val memToReg_out = Output(UInt(1.W))
// 		val rd_out = Output(UInt(5.W))
// 		val dataOut_out = Output(SInt(32.W))
// 		val aluOutput_out = Output(SInt(32.W))
// 		val regWrite_out = Output(UInt(1.W))
// 		val rs2Sel_out = Output(UInt(5.W))
// 	//	val baseReg_out = Output(SInt(32.W))
// 	//	val offSet_out = Output(SInt(32.W))
// 		val MemRead_out = Output(UInt(1.W))
// 		val memWrite_out = Output(UInt(1.W))
// 	})


// 	val reg_memToReg = RegInit(0.U(1.W))
// 	val reg_rd = RegInit(0.U(5.W))
// 	val reg_dataOut = RegInit(0.S(32.W))
// 	val reg_aluOutput = RegInit(0.S(32.W))
// 	val reg_regWrite = RegInit(0.U(1.W))
// 	val reg_rs2Sel = RegInit(0.U(5.W))
// //	val reg_baseReg = RegInit(0.S(32.W))
// //	val reg_offSet = RegInit(0.S(32.W))
// 	val reg_memRead = RegInit(0.U(1.W))
// 	val reg_memWrite = RegInit(0.U(1.W))

	
// 	reg_memToReg := io.memtoreg
// 	reg_rd := io.rd
// 	reg_dataOut := io.memout
// 	reg_aluOutput := io.out
// 	reg_regWrite := io.regwrite
// 	reg_rs2Sel := io.rs2s
// //	reg_baseReg := io.baseReg_in
// //	reg_offSet := io.offSet_in
// 	reg_memRead := io.memread
// 	reg_memWrite := io.memwrite


// 	io.memToReg_out := reg_memToReg
// 	io.rd_out := reg_rd
// 	io.dataOut_out := reg_dataOut
// 	io.aluOutput_out := reg_aluOutput
// 	io.regWrite_out := reg_regWrite
// 	io.rs2Sel_out := reg_rs2Sel
// //	io.baseReg_out := reg_baseReg
// //	io.offSet_out := reg_offSet
// 	io.MemRead_out := reg_memRead
// 	io.memWrite_out := reg_memWrite
// }