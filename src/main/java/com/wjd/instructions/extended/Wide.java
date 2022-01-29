package com.wjd.instructions.extended;

import com.wjd.instructions.base.ByteCodeReader;
import com.wjd.instructions.base.Instruction;
import com.wjd.rtda.Frame;

/**
 * 扩展指令
 * @since 2022/1/29
 */
public class Wide implements Instruction {

    /** 实际指令 */
    private Instruction modifyInstruction;

    @Override
    public void execute(Frame frame) {
        modifyInstruction.execute(frame);
    }

    @Override
    public void fetchOperands(ByteCodeReader reader) {
        int opcode = reader.readUint8().value();
        switch (opcode) {
            case 0x15:
                modifyInstruction = new WILoad();
                break;
            case 0x16:
                modifyInstruction = new WLLoad();
                break;
            case 0x17:
                modifyInstruction = new WFLoad();
                break;
            case 0x18:
                modifyInstruction = new WDLoad();
                break;
            case 0x19:
                modifyInstruction = new WALoad();
                break;
            case 0x36:
                modifyInstruction = new WIStore();
                break;
            case 0x37:
                modifyInstruction = new WLStore();
                break;
            case 0x38:
                modifyInstruction = new WFStore();
                break;
            case 0x39:
                modifyInstruction = new WDStore();
                break;
            case 0x3a:
                modifyInstruction = new WAStore();
                break;
            case 0x84:
                modifyInstruction = new WIInc();
                break;
            case 0xa9:
                System.out.println("Unsupported opcode: 0xa9!");
                break;
        }
        modifyInstruction.fetchOperands(reader);
    }
}
