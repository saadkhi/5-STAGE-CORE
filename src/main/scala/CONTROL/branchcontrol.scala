package CONTROL

import chisel3._
import chisel3.util._
class LM_IO_Interface_BranchControl extends Bundle {
    val aluc = Input ( UInt (5.W ) )
    val branch = Input ( Bool () )
    val arg_x = Input ( SInt (32.W ) )
    val arg_y = Input ( SInt (32.W ) )
    val br_taken = Output ( Bool () )
}
class branchControl_ extends Module {
    val io = IO (new LM_IO_Interface_BranchControl )
    var xy=Wire(Bool())
    xy:=0.B
// Start Coding here
switch(io.aluc){
    is("b10000".U){xy:=io.arg_x === io.arg_y} //equal
    is("b10011".U){xy:=io.arg_x =/= io.arg_y} //not equal
    is("b10100".U){xy:=io.arg_x<io.arg_y} //lesser equal
    is("b10101".U){xy:=io.arg_x>io.arg_y }// greater equal
    is("b10110".U){xy:=io.arg_x<io.arg_y} //lesser than unsign
    is("b10111".U){xy:=io.arg_x>=io.arg_y} //greater than unsign
}
io.br_taken:=xy & io.branch
// End your code here
// Well , you can actually write classes too . So , technically you have no limit ; )
}

// import chisel3._
// import chisel3.util._
// import chisel3.stage.ChiselStage

// class brcntrl extends Bundle {
// val aluc = Input ( UInt (5. W ) )
// val branch = Input ( Bool () )
// val arg_x = Input ( SInt (32. W ) )
// val arg_y = Input ( SInt (32. W ) )
// val br_taken = Output ( Bool () )
// }
// class branchControl_ extends Module {
// val io = IO (new brcntrl )
// // Start Coding here
// object ALUC {
// val beq = "b10000".U
// val bne = "b10011".U
// val blt = "b10100".U
// val bge = "b10101".U
// val bltu = "b10110".U
// val bgeu = "b10111".U}


// import ALUC._
// //val temp = RegInit(0.U)
// io.br_taken := 0.B
// switch ( io.aluc ) {
// is (beq ) {
// when (io.arg_x === io.arg_y){
//     io.br_taken := 1.B & io.branch
// }.otherwise{ 
// io.br_taken := 0.B & io.branch
// }}
// is (bne) {
// when (io.arg_x =/= io.arg_y){
//     io.br_taken := 1.B & io.branch
// }.otherwise{ 
// io.br_taken := 0.B & io.branch}
// }
// is (blt) {
// when (io.arg_x < io.arg_y){
//     io.br_taken := 1.B & io.branch
// }.otherwise{ 
// io.br_taken := 0.B & io.branch}
// }
// is (bge) {
// when (io.arg_x >= io.arg_y){
//     io.br_taken := 1.B & io.branch
// }.otherwise{ 
// io.br_taken := 0.B& io.branch }
// }
// is (blt ) {
// when (io.arg_x <= io.arg_y){
//     io.br_taken := 1.B & io.branch
// }.otherwise{ 
// io.br_taken := 0.B & io.branch }
// }
// is (bgeu ) {
// when (io.arg_x >= io.arg_y){
//     io.br_taken := 1.B & io.branch
// }.otherwise{ 
// io.br_taken := 0.B & io.branch }
// }
// }}