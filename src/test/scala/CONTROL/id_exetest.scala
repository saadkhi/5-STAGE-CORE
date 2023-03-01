package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class ID_EXE_Tests extends FreeSpec with ChiselScalatestTester{
    "ID_EXE_Test" in { test (new id_exe()){c=>
        c.io.pc.poke(7.U)
        c.io.pc4.poke(7.U)
        c.io.memwrite.poke(1.U)
        c.io.branch.poke(1.U)
        c.io.memread.poke(1.U)
        c.io.regwrite.poke(1.U)
        c.io.memtoreg.poke(1.U)
        c.io.alucontrol.poke(7.U)
        c.io.operand_A.poke(7.U)
        c.io.operand_B.poke(1.U)
        c.io.next_PC_select.poke(7.U)
        c.io.imm.poke(7.S)
        c.io.func3.poke(7.U)
        c.io.func7.poke(7.U)
        c.io.val1.poke(7.S)
        c.io.val2.poke(7.S)
        c.io.rs1s.poke(7.U)
        c.io.rs2s.poke(7.U)
        c.io.rd.poke(7.U)
        
        c.clock.step(20)

        c.io.pc_out.expect(7.U)
        c.io.pc4_out.expect(7.U)
        c.io.memWrite_out.expect(1.U)
        c.io.branch_out.expect(1.U)
        c.io.memRead_out.expect(1.U)
        c.io.regWrite_out.expect(1.U)
        c.io.memToReg_out.expect(1.U)
        c.io.aluCtrl_out.expect(7.U)
        c.io.operandAsel_out.expect(3.U)
        c.io.operandBsel_out.expect(1.U)
        c.io.next_PC_sel_out.expect(3.U)
        c.io.imm_out.expect(-1.S)
        c.io.func3_out.expect(7.U)
        c.io.func7_out.expect(1.U)
        c.io.operandA_out.expect(7.S)
        c.io.operandB_out.expect(7.S)
        c.io.rs1Ins_out.expect(7.U)
        c.io.rs2Ins_out.expect(7.U)
        c.io.rd_out.expect(7.U)
       
        }
    }
}
    