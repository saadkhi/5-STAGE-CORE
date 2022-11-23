package CONTROL
import chisel3._
class ForwardUnit extends Module {
    val io = IO(new Bundle {
        val EX_MEM_rd = Input(UInt(5.W))
        val ID_EX_rs1_s = Input(UInt(5.W))
        val ID_EX_rs2_s = Input(UInt(5.W))
        val EX_MEM_RegWrite = Input(Bool())
        val MEM_WB_rd = Input(UInt(5.W))
        val MEM_WB_RegWrite = Input(Bool())
        val forward_a = Output(UInt(2.W))
        val forward_b = Output(UInt(2.W))})
    io.forward_a := "b00".U
    io.forward_b := "b00".U
// EX HAZARD
when(io.EX_MEM_RegWrite === 1.B && io.EX_MEM_rd =/= "b00000".U && (io.EX_MEM_rd=== io.ID_EX_rs1_s) && (io.EX_MEM_rd === io.ID_EX_rs2_s)) {
  io.forward_a := "b01".U
	io.forward_b := "b01".U} 
.elsewhen(io.EX_MEM_RegWrite === 1.B && io.EX_MEM_rd =/= "b00000".U && (io.EX_MEM_rd === io.ID_EX_rs2_s)) {
	io.forward_b := "b01".U} 
.elsewhen(io.EX_MEM_RegWrite === 1.B && io.EX_MEM_rd =/= "b00000".U && (io.EX_MEM_rd === io.ID_EX_rs1_s)) {
	io.forward_a := "b01".U}
// MEM HAZARD
when(io.MEM_WB_RegWrite === 1.B && io.MEM_WB_rd =/= "b00000".U && ~((io.EX_MEM_RegWrite === "b1".U) && (io.EX_MEM_rd =/= "b00000".U) && 
  (io.EX_MEM_rd === io.ID_EX_rs1_s) && (io.EX_MEM_rd === io.ID_EX_rs2_s)) && (io.MEM_WB_rd=== io.ID_EX_rs1_s) && 
  (io.MEM_WB_rd === io.ID_EX_rs2_s)) {
    io.forward_a := "b10".U
    io.forward_b := "b10".U} 
.elsewhen(io.MEM_WB_RegWrite === 1.B && io.MEM_WB_rd =/= "b00000".U && ~((io.EX_MEM_RegWrite === 1.B) && (io.EX_MEM_rd =/= "b00000".U) && 
  (io.EX_MEM_rd === io.ID_EX_rs2_s)) && (io.MEM_WB_rd === io.ID_EX_rs2_s)) {
    io.forward_b := "b10".U} 
.elsewhen(io.MEM_WB_RegWrite === 1.B && io.MEM_WB_rd =/= "b00000".U && ~((io.EX_MEM_RegWrite ===1.B) && (io.EX_MEM_rd =/= "b00000".U) && 
  (io.EX_MEM_rd === io.ID_EX_rs2_s))  &&  (io.MEM_WB_rd === io.ID_EX_rs1_s)) {
	io.forward_a := "b10".U}}
