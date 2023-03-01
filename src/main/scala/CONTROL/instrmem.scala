package CONTROL

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile
import scala.io.Source
 
class instrmem extends Module{
    val io = IO(new Bundle{
        val writeaddr = Input(UInt(10.W))
        val readdata = Output(UInt(32.W))
        })
    val mem = Mem(1024,UInt(32.W)) 
    loadMemoryFromFile(mem,"C:/Users/DELL/Downloads/Scala-Chisel-Learning-Journey-main/src/main/scala/CONTROL/instfetch.txt") 
    io.readdata := mem.read(io.writeaddr)
}
// D:\VS code\Scala-Chisel-Learning-Journey-main\src\main\scala\CONTROL