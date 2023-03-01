package CONTROL

import chisel3._
import chisel3.util._
 
class jalR extends Module{
    val io = IO(new Bundle{
        val rs1 = Input(SInt(32.W))
        val imm = Input(SInt(32.W))
        val out = Output(SInt(32.W))
        })
        val adder = io.rs1 + io.imm
        io.out := adder & 4284967294L.S
}