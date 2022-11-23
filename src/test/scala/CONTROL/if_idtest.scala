package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class IF_ID_Tests extends FreeSpec with ChiselScalatestTester{
    "IF_ID_Test" in { test (new if_id()){c=>
    c.io.readdata.poke(0.U)
    c.io.pc.poke(4.U)
    c.io.pc4.poke(0.U)
    c.clock.step(20)
    c.io.pc_out.expect(4.U)
    c.io.readdata_out.expect(0.U)
    c.io.pc4_out.expect(0.U)
    }
    }
}
