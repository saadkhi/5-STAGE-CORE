package CONTROL

import chisel3._
import chisel3.util._
import chisel3.experimental.ChiselEnum

class if_id extends Module{
	val io = IO(new Bundle{
		val pc = Input(UInt(32.W))
		val pc4 = Input(UInt(32.W))
		val readdata = Input(UInt(32.W))

		val pc_out = Output(UInt(32.W))
		val pc4_out = Output(UInt(32.W))
		val readdata_out = Output(UInt(32.W))
	})
	
	val reg_pc = RegInit(0.U(32.W))
	val reg_pc4 = RegInit(0.U(32.W))
	val reg_readdata = RegInit(0.U(32.W))

	reg_pc := io.pc
	reg_pc4 := io.pc4
	reg_readdata := io.readdata

	io.pc_out := reg_pc
	io.pc4_out := reg_pc4
	io.readdata_out := reg_readdata
}