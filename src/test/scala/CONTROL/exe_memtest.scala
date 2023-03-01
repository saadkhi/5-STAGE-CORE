package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class EXE_MEM_Tests extends FreeSpec with ChiselScalatestTester{
    "EXE_MEM_Tests" in {test (new exe_mem()){c=>
    c.io.memwrite.poke(1.B)
    c.io.memread.poke(1.B)
    c.io.regwrite.poke(1.B)
    c.io.memtoreg.poke(1.B)
    c.io.out.poke(7.S)
    c.io.rs2sel_in.poke(7.S)
    c.io.rd.poke(7.U)
    c.clock.step(20)
    c.io.rs2Sel_out.expect(7.S)
    c.io.aluOutput_out.expect(7.S)
    c.io.rd_out.expect(7.U)
    c.io.memWrite_out.expect(1.B)
    c.io.memRead_out.expect(1.B)
    c.io.regWrite_out.expect(1.B)
    c.io.memToReg_out.expect(1.B)
    }
    }
}