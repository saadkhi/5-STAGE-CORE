package CONTROL

import chisel3._
import chisel3.util._

class Top5 extends Module{
    val io=IO(new Bundle{
        val out = Output(UInt(32.W))
         val addr = Output ( UInt ( 10 . W ) )
    })

// var temp = 0.U
val Pccount_Module = Module(new pccount())
val Alucomp_Module = Module(new Alu())
val Alucontrol_Module = Module(new alucontrol())
// val Brcntrl_Module = Module (new branchControl_())
val Controller_Module = Module(new controller())
val Datamem_Module = Module(new datamem())
val Immedgen_Module = Module(new immedgen())
val Instrmem_Module = Module(new instrmem())
val JalR_Module = Module(new jalR())
val Regwrite_Module = Module(new regwrite())
val If_id_Module = Module(new if_id())
val Id_exe_Module = Module(new id_exe())
val Exe_mem_Module = Module(new exe_mem())
val Mem_wb_Module = Module(new mem_wr())
val FU_Module = Module(new ForwardUnit())
val HD_Module = Module(new HazardDetection())
val BFU_Module = Module(new DecodeForwardUnit())
val BLU_Module = Module(new BranchLogic())
val SDU_Module = Module(new StructuralDetector())
io.addr := Instrmem_Module.io.writeaddr

// Forward Unit
FU_Module.io.EX_MEM_rd := Exe_mem_Module.io.rd_out
FU_Module.io.ID_EX_rs1_s := Id_exe_Module.io.rs1Ins_out
FU_Module.io.ID_EX_rs2_s := Id_exe_Module.io.rs2Ins_out
FU_Module.io.EX_MEM_RegWrite := Exe_mem_Module.io.regWrite_out
FU_Module.io.MEM_WB_rd := Mem_wb_Module.io.rd_out
FU_Module.io.MEM_WB_RegWrite := Mem_wb_Module.io.regWrite_out

// PC Data in connect with PC4
Pccount_Module.io.cin := Pccount_Module.io.pc4

// Instruction in instruction memory
Instrmem_Module.io.writeaddr := Pccount_Module.io.pc(11,2)

// IF_ID Wiring
If_id_Module.io.pc := Pccount_Module.io.pc
If_id_Module.io.pc4 := Pccount_Module.io.pc4
If_id_Module.io.readdata := Instrmem_Module.io.readdata

// Instruction breaking for opcode of Control decode
Controller_Module.io.opcode := If_id_Module.io.readdata_out(6,0)

//regfile
Regwrite_Module.io.rs1s:= If_id_Module.io.readdata_out(19,15)
Regwrite_Module.io.rs2s := If_id_Module.io.readdata_out(24,20)

//immegen
Immedgen_Module.io.instruction := If_id_Module.io.readdata_out
Immedgen_Module.io.pc := If_id_Module.io.pc_out

//ALU Control
Alucontrol_Module.io.aluop := Id_exe_Module.io.aluCtrl_out
Alucontrol_Module.io.func3 := Id_exe_Module.io.func3_out
Alucontrol_Module.io.func7 := Id_exe_Module.io.func7_out

// SDU wiring
SDU_Module.io.rs1_sel := If_id_Module.io.readdata_out(19, 15)
SDU_Module.io.rs2_sel := If_id_Module.io.readdata_out(24, 20)
SDU_Module.io.MEM_WB_REGRD := Mem_wb_Module.io.rd_out
SDU_Module.io.MEM_WB_regWr := Mem_wb_Module.io.regWrite_out

// FOR RS1
Id_exe_Module.io.val1 := MuxCase(0.S, Array(
  (SDU_Module.io.fwd_rs1 === 1.U) -> Regwrite_Module.io.writedat,
  (SDU_Module.io.fwd_rs1 === 1.U) -> Regwrite_Module.io.rs1
))
// FOR RS2
Id_exe_Module.io.val2 := MuxCase(0.S, Array(
  (SDU_Module.io.fwd_rs2 === 1.U) -> Regwrite_Module.io.writedat,
  (SDU_Module.io.fwd_rs2 === 1.U) -> Regwrite_Module.io.rs2
))

// DECODE STAGE
when(HD_Module.io.ctrl_forward === "b1".U) {
    Id_exe_Module.io.memwrite := 0.U
    Id_exe_Module.io.memread := 0.U
    Id_exe_Module.io.branch := 0.U
    Id_exe_Module.io.regwrite := 0.U
    Id_exe_Module.io.memtoreg := 0.U
    Id_exe_Module.io.alucontrol := 0.U
    Id_exe_Module.io.operand_A  := 0.U
    Id_exe_Module.io.operand_B  := 0.U
    Id_exe_Module.io.next_PC_select := 0.U
} .otherwise {
    Id_exe_Module.io.memwrite := Controller_Module.io.memwrite
    Id_exe_Module.io.memread := Controller_Module.io.memread
    Id_exe_Module.io.branch := Controller_Module.io.branch
    Id_exe_Module.io.regwrite  := Controller_Module.io.regwrite
    Id_exe_Module.io.memtoreg := Controller_Module.io.memtoreg
    Id_exe_Module.io.alucontrol := Controller_Module.io.aluop
    Id_exe_Module.io.operand_A := Controller_Module.io.operand_A
    Id_exe_Module.io.operand_B := Controller_Module.io.operand_B
    Id_exe_Module.io.next_PC_select := Controller_Module.io.next_PC_select
}
// BFU Wiring
BFU_Module.io.ID_EX_REGRD := Id_exe_Module.io.rd_out
BFU_Module.io.ID_EX_MEMRD := Id_exe_Module.io.memRead_out
BFU_Module.io.EX_MEM_REGRD := Exe_mem_Module.io.rd_out
BFU_Module.io.MEM_WB_REGRD := Mem_wb_Module.io.rd_out
BFU_Module.io.EX_MEM_MEMRD := Exe_mem_Module.io.memRead_out
BFU_Module.io.MEM_WB_MEMRD := Mem_wb_Module.io.memRead_out
BFU_Module.io.rs1_sel := If_id_Module.io.readdata_out(19,15)
BFU_Module.io.rs2_sel := If_id_Module.io.readdata_out(24, 20)
BFU_Module.io.ctrl_branch := Controller_Module.io.branch

// BLU Wiring
BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1
BLU_Module.io.in_rs2 := Regwrite_Module.io.rs2
BLU_Module.io.in_func3 := If_id_Module.io.readdata_out(14,12)

// JalR Wiring
JalR_Module.io.rs1 := Regwrite_Module.io.rs1
JalR_Module.io.imm := Immedgen_Module.io.i_imm

when(BFU_Module.io.forward_rs1 === "b0000".U) {
  // No hazard just use register file data
  BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1
  JalR_Module.io.rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b0001".U) {
  // hazard in alu stage forward data from alu output
  BLU_Module.io.in_rs1 := Alucomp_Module.io.out
  JalR_Module.io.rs1:= Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b0010".U) {
  // hazard in EX/MEM stage forward data from EX/MEM.alu_output
  BLU_Module.io.in_rs1 := Exe_mem_Module.io.aluOutput_out
  JalR_Module.io.rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b0011".U) {
  // hazard in MEM/WB stage forward data from reg_file write data which will have correct data from the MEM/WB mux
  BLU_Module.io.in_rs1 := Regwrite_Module.io.writedat
  JalR_Module.io.rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b0100".U) {
  // hazard in EX/MEM stage and load type instruction so forwarding from data memory data output instead of EX/MEM.alu_output
  BLU_Module.io.in_rs1 := Datamem_Module.io.memout
  JalR_Module.io.rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b0101".U) {
  // hazard in MEM/WB stage and load type instruction so forwarding from register file write data which will have the correct output from the mux
  BLU_Module.io.in_rs1:= Regwrite_Module.io.writedat
  JalR_Module.io.rs1 := Regwrite_Module.io.rs1
}.elsewhen(BFU_Module.io.forward_rs1 === "b0110".U) {
    // hazard in alu stage forward data from alu output
    JalR_Module.io.rs1 := Alucomp_Module.io.out
    BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b0111".U) {
    // hazard in EX/MEM stage forward data from EX/MEM.alu_output
    JalR_Module.io.rs1 := Exe_mem_Module.io.aluOutput_out
    BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b1000".U) {
    // hazard in MEM/WB stage forward data from register file write data which will have correct data from the MEM/WB mux
    JalR_Module.io.rs1 := Regwrite_Module.io.writedat
    BLU_Module.io.in_rs1:= Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b1001".U) {
    // hazard in EX/MEM stage and load type instruction so forwarding from data memory data output instead of EX/MEM.alu_output
    JalR_Module.io.rs1 := Datamem_Module.io.memout
    BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1
} .elsewhen(BFU_Module.io.forward_rs1 === "b1010".U) {
    // hazard in MEM/WB stage and load type instruction so forwarding from register file write data which will have the correct output from the mux
    JalR_Module.io.rs1 := Regwrite_Module.io.writedat
    BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1}
  .otherwise {
    BLU_Module.io.in_rs1 := Regwrite_Module.io.rs1
    JalR_Module.io.rs1 := Regwrite_Module.io.rs1
}

// ID_EXE Pipelining
Id_exe_Module.io.pc := If_id_Module.io.pc_out
Id_exe_Module.io.pc4 := If_id_Module.io.pc4_out
Id_exe_Module.io.memwrite := Controller_Module.io.memwrite
Id_exe_Module.io.branch := Controller_Module.io.branch
Id_exe_Module.io.memread := Controller_Module.io.memread
Id_exe_Module.io.regwrite := Controller_Module.io.regwrite
Id_exe_Module.io.memtoreg := Controller_Module.io.memtoreg
Id_exe_Module.io.alucontrol := Controller_Module.io.aluop
Id_exe_Module.io.operand_A := Controller_Module.io.operand_A
Id_exe_Module.io.operand_B := Controller_Module.io.operand_B
Id_exe_Module.io.next_PC_select := Controller_Module.io.next_PC_select
Id_exe_Module.io.func3 := If_id_Module.io.readdata_out(14,12)
Id_exe_Module.io.func7 := If_id_Module.io.readdata_out(30)
Id_exe_Module.io.val1 := Regwrite_Module.io.rs1
Id_exe_Module.io.val2 := Regwrite_Module.io.rs2
Id_exe_Module.io.rs1s := If_id_Module.io.readdata_out(19, 15)
Id_exe_Module.io.rs2s := If_id_Module.io.readdata_out(24, 20)
Id_exe_Module.io.rd := If_id_Module.io.readdata_out(11,7)

// ID EXE IMMEDIATE WIRING
Id_exe_Module.io.imm := MuxCase(0.S, Array(
  (Controller_Module.io.extend === "b00".U) -> Immedgen_Module.io.i_imm,
  (Controller_Module.io.extend === "b01".U) -> Immedgen_Module.io.s_imm,
  (Controller_Module.io.extend === "b10".U) -> Immedgen_Module.io.u_imm
))

// Hazards Wiring
HD_Module.io.IF_ID_INST := If_id_Module.io.readdata_out
HD_Module.io.ID_EX_MEMREAD := Id_exe_Module.io.memRead_out
HD_Module.io.ID_EX_REGRD := Id_exe_Module.io.rd_out
HD_Module.io.pc_in := If_id_Module.io.pc4_out
HD_Module.io.current_pc := If_id_Module.io.pc_out

// IF_ID and HAZARDS Wiring
when(HD_Module.io.inst_forward === "b1".U) {
    If_id_Module.io.readdata := HD_Module.io.inst_out.asUInt
    If_id_Module.io.pc := HD_Module.io.current_pc_out.asUInt}
.otherwise {
  If_id_Module.io.readdata := Instrmem_Module.io.readdata
  }

// JAL TO HD CONNECTIONS 
when(HD_Module.io.pc_forward === "b1".U) {
  Pccount_Module.io.cin := HD_Module.io.pc_out
} .otherwise {
    when(Controller_Module.io.next_PC_select === "b01".U) {
      when(BLU_Module.io.output === 1.U && Controller_Module.io.branch === 1.B) {
        Pccount_Module.io.cin := Immedgen_Module.io.sb_imm.asUInt
        If_id_Module.io.pc := 0.U
        If_id_Module.io.pc4 := 0.U
        If_id_Module.io.readdata := 0.U
      } .otherwise {
        Pccount_Module.io.cin := Pccount_Module.io.pc4
      }
}.elsewhen(Controller_Module.io.next_PC_select === "b10".U) {
      Pccount_Module.io.cin := Immedgen_Module.io.uj_imm.asUInt
      If_id_Module.io.pc := 0.U
      If_id_Module.io.pc4 := 0.U
      If_id_Module.io.readdata := 0.U
    }.elsewhen(Controller_Module.io.next_PC_select === "b11".U) {
      Pccount_Module.io.cin := (JalR_Module.io.out).asUInt
      If_id_Module.io.pc := 0.U
      If_id_Module.io.pc4 := 0.U
      If_id_Module.io.readdata := 0.U
    }.otherwise {Pccount_Module.io.cin := Pccount_Module.io.pc4
}}

// EXE MEMORY CONNECTIONS
Exe_mem_Module.io.regwrite := Id_exe_Module.io.regWrite_out
Exe_mem_Module.io.memtoreg := Id_exe_Module.io.memToReg_out
Exe_mem_Module.io.rs2sel_in := Id_exe_Module.io.operandB_out
Exe_mem_Module.io.out := Alucomp_Module.io.out
Exe_mem_Module.io.rd := Id_exe_Module.io.rd_out

// ID EXE AND ALU MODULE CONNECTIONS
when (Id_exe_Module.io.operandAsel_out === "b10".U) {
    Alucomp_Module.io.in1 := Id_exe_Module.io.pc4_out.asSInt
  } .otherwise{
when(FU_Module.io.forward_a === "b00".U) {
  Alucomp_Module.io.in1  := Id_exe_Module.io.operandA_out
} .elsewhen(FU_Module.io.forward_a === "b01".U) {
  Alucomp_Module.io.in1  := Exe_mem_Module.io.aluOutput_out
} .elsewhen(FU_Module.io.forward_a === "b10".U) {
  Alucomp_Module.io.in1 := Regwrite_Module.io.writedat
} .otherwise {
  Alucomp_Module.io.in1  := Id_exe_Module.io.operandA_out
}}

// CONTROL AND IMMEDIATE GENERATOR CONNECTION
when (Controller_Module.io.extend=== "b00".U){
		Id_exe_Module.io.imm :=Immedgen_Module.io.i_imm}
	.elsewhen (Controller_Module.io.extend === "b01".U){
		Id_exe_Module.io.imm :=Immedgen_Module.io.s_imm}
	.elsewhen (Controller_Module.io.extend === "b10".U){
		Id_exe_Module.io.imm := Immedgen_Module.io.u_imm}
	.otherwise {Id_exe_Module.io.imm := 0.S}

// FOR REGISTER RS2 in BRANCH LOGIC UNIT
BLU_Module.io.in_rs2 := MuxCase(Regwrite_Module.io.rs2, Array(
  (BFU_Module.io.forward_rs2 === "b000".U) -> Regwrite_Module.io.rs2, // No hazard just use register file data
  (BFU_Module.io.forward_rs2 === "b001".U) -> Alucomp_Module.io.out, // hazard in alu stage forward data from alu output
  (BFU_Module.io.forward_rs2 === "b010".U) -> Exe_mem_Module.io.aluOutput_out, // hazard in EX/MEM stage forward data from EX/MEM.alu_output
  (BFU_Module.io.forward_rs2 === "b011".U) -> Regwrite_Module.io.writedat, // hazard in MEM/WB stage forward data from register file write data which will have correct data from the MEM/WB mux
  (BFU_Module.io.forward_rs2 === "b100".U) -> Datamem_Module.io.memout, // hazard in EX/MEM stage and load type instruction so forwarding from data memory data output instead of EX/MEM.alu_output
  (BFU_Module.io.forward_rs2 === "b101".U) -> Regwrite_Module.io.writedat, // hazard in MEM/WB stage and load type instruction so forwarding from register file write data which will have the correct output from the mux
))
// Id_exe_Module.io.val1 := MuxCase(0.S, Array(
//   (SDU_Module.io.fwd_rs1 === 1.U) -> Regwrite_Module.io.writedat,
//   (SDU_Module.io.fwd_rs1 === 1.U) -> Regwrite_Module.io.rs1
// ))

// Id_exe_Module.io.val1 := MuxCase(0.S, Array(
//   (SDU_Module.io.fwd_rs1 === 1.U) -> Regwrite_Module.io.writedat,
//   (SDU_Module.io.fwd_rs1 === 1.U) -> Regwrite_Module.io.rs1
// ))

Alucomp_Module.io.alucnt := Alucontrol_Module.io.out
when(Id_exe_Module.io.operandBsel_out === 1.U){
		Alucomp_Module.io.in2 := Id_exe_Module.io.imm_out

	when (FU_Module.io.forward_b === "b00".U){Exe_mem_Module.io.rs2sel_in := Id_exe_Module.io.operandB_out}
		.elsewhen ( FU_Module.io.forward_b === "b01".U  ){Exe_mem_Module.io.rs2sel_in := Exe_mem_Module.io.aluOutput_out}
		.elsewhen (FU_Module.io.forward_b === "b10".U){Exe_mem_Module.io.rs2sel_in := Regwrite_Module.io.writedat}
    .otherwise {
		Exe_mem_Module.io.rs2sel_in := Id_exe_Module.io.operandB_out
		}
	}
	.otherwise{
		when(FU_Module.io.forward_b === "b00".U) {
    Alucomp_Module.io.in2 := Id_exe_Module.io.operandB_out
    Exe_mem_Module.io.rs2sel_in:= Id_exe_Module.io.operandB_out
  } .elsewhen(FU_Module.io.forward_b === "b01".U) {
    Alucomp_Module.io.in2 := Exe_mem_Module.io.aluOutput_out
    Exe_mem_Module.io.rs2sel_in := Exe_mem_Module.io.aluOutput_out
  } .elsewhen(FU_Module.io.forward_b === "b10".U) {
    Alucomp_Module.io.in2 := Regwrite_Module.io.writedat
    Exe_mem_Module.io.rs2sel_in := Regwrite_Module.io.writedat
  } .otherwise {
    Alucomp_Module.io.in2 := Id_exe_Module.io.operandB_out
    Exe_mem_Module.io.rs2sel_in := Id_exe_Module.io.operandB_out
	}}

// EXE MEM REST CONNECTION
Exe_mem_Module.io.memwrite := Id_exe_Module.io.memWrite_out
Exe_mem_Module.io.memread := Id_exe_Module.io.memRead_out

// DATA MEMORY CONNECTIONS
Datamem_Module.io.addr := (Exe_mem_Module.io.aluOutput_out).asUInt
Datamem_Module.io.memdata := Exe_mem_Module.io.rs2Sel_out
Datamem_Module.io.memwrite := Exe_mem_Module.io.memWrite_out
Datamem_Module.io.memread := Exe_mem_Module.io.memRead_out

//  MEMORY WRITE BACK CONNECTION
Mem_wb_Module.io.regwrite := Exe_mem_Module.io.regWrite_out
Mem_wb_Module.io.memtoreg := Exe_mem_Module.io.memToReg_out
Mem_wb_Module.io.memread := Exe_mem_Module.io.memRead_out
Mem_wb_Module.io.memwrite := Exe_mem_Module.io.memWrite_out
Mem_wb_Module.io.memdatain := Datamem_Module.io.memout
Mem_wb_Module.io.out := Exe_mem_Module.io.aluOutput_out
Mem_wb_Module.io.rd := Exe_mem_Module.io.rd_out

// REGISTER WRITE AND MEMORY WRITE BACK CONNECTION
Regwrite_Module.io.writedat := MuxCase ( 0.S , Array (
(Mem_wb_Module.io.memToReg_out === 0.B ) -> Mem_wb_Module.io.aluOutput_out ,
(Mem_wb_Module.io.memToReg_out === 1.B ) -> Mem_wb_Module.io.dataOut_out))
Regwrite_Module.io.regwrite := Mem_wb_Module.io.regWrite_out
Regwrite_Module.io.rd := Mem_wb_Module.io.rd_out
io.out := 0.U
}