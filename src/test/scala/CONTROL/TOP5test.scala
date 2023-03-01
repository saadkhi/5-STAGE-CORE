package CONTROL

import chisel3._
import org.scalatest._
import chiseltest._

class top5test extends FreeSpec with ChiselScalatestTester{
    "top teat12" in { test(new Top5()){ c=>
        c.clock.step(500)
        }
    }
}