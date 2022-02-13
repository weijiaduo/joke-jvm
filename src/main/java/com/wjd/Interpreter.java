package com.wjd;

import com.wjd.instructions.InstructionFactory;
import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.stack.Frame;
import com.wjd.rtda.Thread;
import com.wjd.rtda.meta.ClassMetaLoader;
import com.wjd.rtda.heap.HeapObject;
import com.wjd.rtda.meta.StringPool;
import com.wjd.rtda.meta.MethodMeta;
import com.wjd.rtda.meta.ClassMeta;

/**
 * @since 2022/1/29
 */
public class Interpreter {

    public void interpret(MethodMeta methodMeta, boolean logInst, String[] args) {
        Thread thread = new Thread();
        Frame frame = thread.newFrame(methodMeta);
        thread.pushFrame(frame);
        HeapObject argsObj = createArgsArray(methodMeta.getClazz().getLoader(), args);
        frame.getLocalVars().setRef(0, argsObj);
        loop(thread, logInst);
    }

    private HeapObject createArgsArray(ClassMetaLoader loader, String[] args) {
        ClassMeta stringClassMeta = loader.loadClass("java/lang/String");
        HeapObject argsArray = stringClassMeta.getArrayClass().newArray(args.length);
        HeapObject[] refs = argsArray.getRefs();
        for (int i = 0; i < args.length; i++) {
            refs[i] = StringPool.getObjString(loader, args[i]);
        }
        return argsArray;
    }

    private void loop(Thread thread, boolean logInst) {
        ByteCodeReader reader = new ByteCodeReader();
        try {
            while (true) {
                Frame frame = thread.currentFrame();
                // 程序计数器地址
                int pc = frame.getNextPc();
                thread.setPc(pc);

                // 设置指令地址
                reader.reset(frame.getMethod().getCodes(), pc);

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
                if (logInst) {
                    System.out.println(instruction);
                }
                instruction.execute(frame);

                // 执行结束
                if (thread.isStackEmpty()) {
                    break;
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
