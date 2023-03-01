package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class jalRtest extends FreeSpec with ChiselScalatestTester{
    "controller" in { test (new jalR()){c=>
    c.io.rs1.poke(1.S)
    c.io.imm.poke(1.S)
    c.clock.step(10)
    c.io.out.expect(2.S)
    }
    }
}