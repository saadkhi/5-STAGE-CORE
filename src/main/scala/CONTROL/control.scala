package CONTROL

import chisel3._
import chisel3.util._
import chisel3.experimental.ChiselEnum
 
class controller extends Module{
    val io = IO(new Bundle{
        val opcode = Input(UInt(7.W))
        val memwrite = Output(Bool())
        val branch = Output(Bool())
        val memread = Output(Bool())
        val regwrite = Output(Bool())
        val memtoreg = Output(Bool())
        val aluop = Output(UInt(3.W))
        val operand_A = Output(UInt(2.W))
        val operand_B = Output(Bool())
        val extend = Output(UInt(2.W))
        val next_PC_select = Output(UInt(2.W))
    })

            io.memwrite := 0.B
            io.branch := 0.B
            io.memread := 0.B
            io.regwrite := 0.B
            io.memtoreg := 0.B
            io.aluop := 7.U
            io.operand_A := 0.U
            io.operand_B := 0.B
            io.extend := 0.U
            io.next_PC_select := 0.U

     switch (io.opcode){
        is ("b0110011".U){      //r-type
            io.memwrite := 0.B
            io.branch := 0.B
            io.memread := 0.B
            io.regwrite := 1.B
            io.memtoreg := 0.B
            io.operand_A := "b00".U
            io.operand_B := 0.B
            io.extend := 0.U
            io.next_PC_select := "b00".U
            io.aluop := "b000".U}
        is ("b0010011".U){     //i-type
            io. memwrite := 0.B
            io. branch := 0.B
            io. memread := 0.B 
            io. regwrite := 1.B
            io. memtoreg := 0.B
            io.next_PC_select := "b00".U
            io. operand_A := "b00".U
            io. operand_B := 1.B
            io. extend := 0.U
            io.aluop := "b001".U}
        is ("b0100011".U){ //s-type
            io. memwrite := 1.B
            io. branch := 0.B
            io. memread := 0.B 
            io. regwrite := 1.B
            io. memtoreg := 0.B
            io. operand_A := "b00".U
            io. operand_B := 1.B
            io. extend := "b01".U
            io.aluop := "b101".U}
         is ("b0000011".U){  //load-type
            io. memwrite := 0.B
            io. branch := 0.B
            io. memread := 1.B 
            io. regwrite := 1.B
            io. memtoreg := 1.B
            io. operand_A := "b00".U
            io. operand_B := 1.B
            io.next_PC_select := "b00".U
            io. extend := "b00".U
            io.aluop := "b100".U}
         is ("b1100011".U){    //sb-type
            io. memwrite := 0.B
            io. branch := 1.B
            io. memread := 0.B 
            io. regwrite := 0.B
            io. memtoreg := 0.B
            io. operand_A := "b00".U
            io. operand_B := 0.B
            io. extend := "b00".U
            io.next_PC_select := "b01".U
            io.aluop := "b010".U}
         is ("b1101111".U){        //jal-type
            io. memwrite := 0.B
            io. branch := 0.B
            io. memread := 0.B 
            io. regwrite := 1.B
            io. memtoreg := 0.B
            io. operand_A := "b01".U
            io. operand_B := 0.B
            io. extend := "b00".U
            io.next_PC_select := "b10".U
            io.aluop := "b011".U}
         is ("b1100111".U){   //jalr-type
            io. memwrite := 0.B
            io. branch := 0.B
            io. memread := 0.B 
            io. regwrite := 1.B
            io. memtoreg := 0.B
            io. operand_A := "b01".U
            io. operand_B := 1.B
            io. extend := "b00".U
            io.next_PC_select := "b11".U
            io.aluop := "b011".U}
        is ("b0110111".U){   //lui-type
            io. memwrite := 0.B
            io. branch := 0.B
            io. memread := 0.B 
            io. regwrite := 1.B
            io. memtoreg := 0.B
            io. operand_A := "b10".U
            io. operand_B := 1.B
            io. extend := "b10".U
            io.next_PC_select := "b00".U
            io.aluop := "b110".U
}}}


//     switch (io.opcode){
// // R-type
//         is("b0110011".U){
//             io.memwrite := 0.B
//             io.branch := 0.B
//             io.memread := 0.B
//             io.regwrite := 1.B
//             io.memtoreg := 0.B
//             io.aluop := "b000".U
//             io.operand_A := "b00".U
//             io.operand_B := 0.B
//             io.extend := "b00".U
//             io.next_PC_select := "b00".U
//         }
// //lw- typr
//         is("b0000011".U){
//             io.memwrite := 0.B
//             io.branch := 0.B
//             io.memread := 1.B
//             io.regwrite := 1.B
//             io.memtoreg := 1.B
//             io.aluop := "b100".U        
//             io.operand_A := "b00".U
//             io.operand_B := 1.B
//             io.extend := "b00".U
//             io.next_PC_select := "b00".U        
//             }
// //s-type
//         is("b0100011".U){
//             io.memwrite := 1.B
//             io.branch := 0.B
//             io.memread := 0.B
//             io.regwrite := 0.B
//             io.memtoreg := 0.B
//             io.aluop := "b101".U
//             io.operand_A := "b00".U
//             io.operand_B := 1.B
//             io.extend := "b01".U
//             io.next_PC_select := "b00".U
//             }
// //sb-type
//             is("b1100011".U){
//             io.memwrite := 0.B
//             io.branch := 1.B
//             io.memread := 0.B
//             io.regwrite := 0.B
//             io.memtoreg := 0.B
//             io.aluop := "b010".U         
//             io.operand_A := "b00".U
//             io.operand_B := 0.B
//             io.extend := "b00".U
//             io.next_PC_select := "b01".U        
//             }
// //i-type
//             is("b0010011".U){
//             io.memwrite := 0.B
//             io.branch := 0.B
//             io.memread := 0.B
//             io.regwrite := 1.B
//             io.memtoreg := 0.B
//             io.aluop := "b001".U            
//             io.operand_A := "b00".U
//             io.operand_B := 1.B
//             io.extend := 0.U
//             io.next_PC_select := "b00".U       
//             }
// //jalr-type
//             is("b1100111".U){
//             io.memwrite := 0.B
//             io.branch := 0.B
//             io.memread := 0.B
//             io.regwrite := 1.B
//             io.memtoreg := 0.B
//             io.aluop := "b011".U            
//             io.operand_A := "b01".U
//             io.operand_B := 1.B
//             io.extend := "b00".U
//             io.next_PC_select := "b11".U            
//             }
// //jal-type
//             is("b1101111".U){
//             io.memwrite := 0.B
//             io.branch := 0.B
//             io.memread := 0.B
//             io.regwrite := 1.B
//             io.memtoreg := 0.B
//             io.aluop := "b011".U            
//             io.operand_A := "b01".U
//             io.operand_B := 0.B
//             io.extend := "b00".U
//             io.next_PC_select := "b10".U            
//             }
//             is("b0110111".U){
//             io.memwrite := 0.B
//             io.branch := 0.B
//             io.memread := 0.B
//             io.regwrite := 1.B
//             io.memtoreg := 0.B
//             io.aluop := "b110".U         
//             io.operand_A := "b10".U
//             io.operand_B := 1.B
//             io.extend := "b10".U
//             io.next_PC_select := "b00".U        
//             }}}