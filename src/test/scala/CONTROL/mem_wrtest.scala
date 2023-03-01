package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class MEM_WR_Tests extends FreeSpec with ChiselScalatestTester{
    "MEM_WR_Test" in { test (new mem_wr()){c=>
        c.io.regwrite.poke(1.U)
        c.io.memtoreg.poke(1.U)
        c.io.memread.poke(1.U)
        c.io.memwrite.poke(1.U)
        c.io.memdatain.poke(86.S)
        c.io.out.poke(86.S)
        c.io.rd.poke(86.U)
        c.clock.step(20)
        c.io.regWrite_out.expect(1.U)
        c.io.memToReg_out.expect(1.U)
        c.io.memRead_out.expect(1.U)
        c.io.memWrite_out.expect(1.U)
        c.io.dataOut_out.expect(86.S)
        c.io.aluOutput_out.expect(86.S)
        c.io.rd_out.expect(22.U)
    }
    }
}