package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class FUtest extends FreeSpec with ChiselScalatestTester{
    "FU test" in {test(new ForwardUnit()){ c =>
        c.io.EX_MEM_rd.poke(1.U)
        c.io.ID_EX_rs2_s.poke(1.U)
        c.io.ID_EX_rs1_s.poke(1.U)
        c.io.EX_MEM_RegWrite.poke(1.B)
        c.io.MEM_WB_rd.poke(1.U)
        c.io.MEM_WB_RegWrite.poke(1.B)
        c.clock.step(20)
        c.io.forward_a.expect(1.U)
        c.io.forward_b.expect(1.U)
        }
    }
}