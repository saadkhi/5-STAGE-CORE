package CONTROL

import chisel3._
import chisel3.util._

class id_exe extends Module{
	val io = IO(new Bundle{
		val pc = Input(UInt(32.W))
		val pc4 = Input(UInt(32.W))
		val memwrite = Input(UInt(1.W))
		val branch = Input(UInt(1.W))
		val memread = Input(UInt(1.W))
		val regwrite = Input(UInt(1.W))
		val memtoreg = Input(UInt(1.W))
		val alucontrol = Input(UInt(3.W))
		val operand_A = Input(UInt(2.W))
		val operand_B = Input(UInt(1.W))
		val next_PC_select = Input(UInt(2.W))
		val imm = Input(SInt(32.W))
		val func3 = Input(UInt(3.W))
		val func7 = Input(UInt(1.W))
		val val1 = Input(SInt(32.W))
		val val2 = Input(SInt(32.W))
		val rs1s = Input(UInt(5.W))
		val rs2s = Input(UInt(5.W))
		val rd = Input(UInt(5.W))
		// val strData_in = Input(SInt(32.W))
		// val hazard_in = Input(UInt(1.W))

		val pc_out = Output(UInt(32.W))
		val pc4_out = Output(UInt(32.W))
		val memWrite_out = Output(UInt(1.W))
		val branch_out = Output(UInt(1.W))
		val memRead_out = Output(UInt(1.W))
		val regWrite_out = Output(UInt(1.W))
		val memToReg_out = Output(UInt(1.W))
		val aluCtrl_out = Output(UInt(3.W))
		val operandAsel_out = Output(UInt(2.W))
		val operandBsel_out = Output(UInt(1.W))
		val next_PC_sel_out = Output(UInt(2.W))
		val imm_out = Output(SInt(32.W))
		val func3_out = Output(UInt(3.W))
		val func7_out = Output(UInt(1.W))
		val operandA_out = Output(SInt(32.W))
		val operandB_out = Output(SInt(32.W))
		val rs1Ins_out = Output(UInt(5.W))
		val rs2Ins_out = Output(UInt(5.W))
		val rd_out = Output(UInt(5.W))
		// val strData_out = Output(SInt(32.W))
	    //val hazard_out = Output(UInt(1.W))
	})


	val reg_pc = RegInit(0.U(32.W))
	val reg_pc4 = RegInit(0.U(32.W))
	val reg_memWrite = RegInit(0.U(1.W))
	val reg_branch = RegInit(0.U(1.W))
	val reg_memRead = RegInit(0.U(1.W))
	val reg_regWrite = RegInit(0.U(1.W))
	val reg_memToReg = RegInit(0.U(1.W))
	val reg_aluCtrl = RegInit(0.U(3.W))
	val reg_operandAsel = RegInit(0.U(2.W))
	val reg_operandBsel = RegInit(0.U(1.W))
	val reg_next_pc = RegInit(0.U(2.W))
	val reg_imm = RegInit(0.S(32.W))
	val reg_fun3 = RegInit(0.U(3.W))
	val reg_fun7 = RegInit(0.U(1.W))
	val reg_operandA = RegInit(0.S(32.W))
	val reg_operandB = RegInit(0.S(32.W))
	val reg_rs1Ins = RegInit(0.U(5.W))
	val reg_rs2Ins = RegInit(0.U(5.W))
	val reg_rd = RegInit(0.U(5.W))
	// val reg_strData = RegInit(0.S(32.W))
	// val reg_hazard = RegInit(0.U(1.W))

	reg_pc := io.pc
	reg_pc4 := io.pc4
	reg_memWrite := io.memwrite
	reg_branch := io.branch
	reg_memRead := io.memread
	reg_regWrite := io.regwrite
	reg_memToReg := io.memtoreg
	reg_aluCtrl := io.alucontrol
	reg_operandAsel := io.operand_A
	reg_operandBsel := io.operand_B
	reg_next_pc := io.next_PC_select
	reg_imm := io.imm
	reg_fun3 := io.func3
	reg_fun7 := io.func7
	reg_operandA := io.val1
	reg_operandB := io.val2
	reg_rs1Ins := io.rs1s
	reg_rs2Ins := io.rs2s
	reg_rd := io.rd
	// reg_strData := io.strData_in
	// reg_hazard := io.hazard_in

	io.pc_out := reg_pc
	io.pc4_out := reg_pc4
	io.memWrite_out := reg_memWrite
	io.branch_out := reg_branch
	io.memRead_out := reg_memRead
	io.regWrite_out := reg_regWrite
	io.memToReg_out := reg_memToReg
	io.aluCtrl_out := reg_aluCtrl
	io.operandAsel_out := reg_operandAsel
	io.operandBsel_out := reg_operandBsel
	io.next_PC_sel_out := reg_next_pc
	io.imm_out := reg_imm
	io.func3_out := reg_fun3
	io.func7_out := reg_fun7
	io.operandA_out := reg_operandA
	io.operandB_out := reg_operandB
	io.rs1Ins_out := reg_rs1Ins
	io.rs2Ins_out := reg_rs2Ins
	io.rd_out := reg_rd
	// io.strData_out := reg_strData
	// io.hazard_out := reg_hazard
}
