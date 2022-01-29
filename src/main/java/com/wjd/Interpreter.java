package com.wjd;

import com.wjd.classfile.attr.CodeAttributeInfo;
import com.wjd.classfile.member.MethodInfo;
import com.wjd.classfile.type.Uint16;
import com.wjd.instructions.InstructionFactory;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Frame;
import com.wjd.rtda.Thread;

/**
 * @since 2022/1/29
 */
public class Interpreter {

    public void interpret(MethodInfo methodInfo) {
        CodeAttributeInfo codeAttr = methodInfo.getCodeAttributeInfo();
        Uint16 maxLocals = codeAttr.getMaxLocals();
        Uint16 maxStack = codeAttr.getMaxStack();
        byte[] codes = codeAttr.getCodes();

        Thread thread = new Thread();
        Frame frame = thread.newFrame(maxLocals.value(), maxStack.value());
        thread.pushFrame(frame);

        loop(thread, codes);
    }

    private void loop(Thread thread, byte[] codes) {
        Frame frame = thread.popFrame();
        ByteCodeReader reader = new ByteCodeReader(codes);
        try {
            while (true) {
                // 程序计数器地址
                int pc = frame.getNextPc();
                thread.setPc(pc);
                reader.setPosition(pc);

                // 编译识别指令
                int opcode = reader.readUint8().value();
                Instruction instruction = InstructionFactory.newInstance(opcode);
                if (instruction == null) {
                    break;
                }

                // 获取指定操作数
                instruction.fetchOperands(reader);
                frame.setNextPc(reader.getPosition());

                // 执行指令
                instruction.execute(frame);
            }
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            System.out.println("Frame: " + frame );
        }

    }

}
