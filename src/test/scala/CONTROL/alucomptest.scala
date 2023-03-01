package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class ALUcomptest extends FreeSpec with ChiselScalatestTester{
    "ALU" in { test (new Alu()){c=>
    c.io.in1.poke(4.S)
    c.io.in2.poke(6.S)
    c.io.alucnt.poke("b00010".U)
    c.clock.step(10)
    c.io.out.expect(1.S)
    // c.io.branch_tk.expect(0.U)
    }
    }
}