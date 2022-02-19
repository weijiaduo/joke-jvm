package com.wjd;

import com.wjd.instructions.InstructionFactory;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Thread;
import com.wjd.rtda.stack.Frame;

/**
 * @since 2022/1/29
 */
public class Interpreter {

    public static void interpret(Thread thread, boolean logInst) {
        loop(thread, logInst);
    }

    private static void loop(Thread thread, boolean logInst) {
        ByteCodeReader reader = new ByteCodeReader();
        while (!thread.isStackEmpty()) {
            // 栈顶，当前栈帧
            Frame frame = thread.currentFrame();

            // 程序计数器地址
            int pc = frame.getNextPc();
            thread.setPc(pc);

            // 设置当前指令地址
            reader.reset(frame.getMethod().getCodes(), pc);

            // 编译识别指令
            int opcode = reader.readUint8().value();
            Instruction instruction = InstructionFactory.newInstance(opcode);
            if (instruction == null) {
                System.out.println("Unsupported instruction opcode: " + opcode);
                break;
            }

            // 获取指定操作数
            instruction.fetchOperands(reader);

            // 设置下一条指令地址
            frame.setNextPc(reader.getPosition());

            // 打印指令
            if (logInst) {
                System.out.println(instruction);
            }

            // 执行指令
            instruction.execute(frame);
        }
    }

}
