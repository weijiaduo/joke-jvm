package com.wjd.instructions;

import com.wjd.instructions.base.Instruction;
import com.wjd.instructions.comparisons.*;
import com.wjd.instructions.constants.*;
import com.wjd.instructions.control.Goto;
import com.wjd.instructions.control.LookupSwitch;
import com.wjd.instructions.control.TableSwitch;
import com.wjd.instructions.conversions.*;
import com.wjd.instructions.extended.*;
import com.wjd.instructions.loads.*;
import com.wjd.instructions.math.*;
import com.wjd.instructions.stack.*;
import com.wjd.instructions.stores.*;

/**
 * @since 2022/1/29
 */
public class InstructionFactory {

    public static Instruction newInstance(int opcode) {
        switch (opcode) {
            case 0x00:
                return new NoOperandsInstruction();
            case 0x01:
                return new AConstNull();
            case 0x02:
                return new IConstM1();
            case 0x03:
                return new IConst0();
            case 0x04:
                return new IConst1();
            case 0x05:
                return new IConst2();
            case 0x06:
                return new IConst3();
            case 0x07:
                return new IConst4();
            case 0x08:
                return new IConst5();
            case 0x09:
                return new LConst0();
            case 0x0a:
                return new LConst1();
            case 0x0b:
                return new FConst0();
            case 0x0c:
                return new FConst1();
            case 0x0d:
                return new FConst2();
            case 0x0e:
                return new DConst0();
            case 0x0f:
                return new DConst1();
            case 0x10:
                return new BIPush();
            case 0x11:
                return new SIPush();
            case 0x12:
                return null;
            case 0x13:
                return null;
            case 0x14:
                return null;
            case 0x15:
                return new WILoad();
            case 0x16:
                return new WLLoad();
            case 0x17:
                return new WFLoad();
            case 0x18:
                return new WDLoad();
            case 0x19:
                return new WALoad();
            case 0x1a:
                return new ILoad0();
            case 0x1b:
                return new ILoad1();
            case 0x1c:
                return new ILoad2();
            case 0x1d:
                return new ILoad3();
            case 0x1e:
                return new LLoad0();
            case 0x1f:
                return new LLoad1();
            case 0x20:
                return new LLoad2();
            case 0x21:
                return new LLoad3();
            case 0x22:
                return new FLoad0();
            case 0x23:
                return new FLoad1();
            case 0x24:
                return new FLoad2();
            case 0x25:
                return new FLoad3();
            case 0x26:
                return new DLoad0();
            case 0x27:
                return new DLoad1();
            case 0x28:
                return new DLoad2();
            case 0x29:
                return new DLoad3();
            case 0x2a:
                return new ALoad0();
            case 0x2b:
                return new ALoad1();
            case 0x2c:
                return new ALoad2();
            case 0x2d:
                return new ALoad3();
            case 0x2e:
                return new ILoad();
            case 0x2f:
                return new LLoad();
            case 0x30:
                return new FLoad();
            case 0x31:
                return new DLoad();
            case 0x32:
                return null;
            case 0x33:
                return null;
            case 0x34:
                return null;
            case 0x35:
                return null;
            case 0x36:
                return new WIStore();
            case 0x37:
                return new WLStore();
            case 0x38:
                return new WFStore();
            case 0x39:
                return new WDStore();
            case 0x3a:
                return new WAStore();
            case 0x3b:
                return new IStore0();
            case 0x3c:
                return new IStore1();
            case 0x3d:
                return new IStore2();
            case 0x3e:
                return new IStore3();
            case 0x3f:
                return new LStore0();
            case 0x40:
                return new LStore1();
            case 0x41:
                return new LStore2();
            case 0x42:
                return new LStore3();
            case 0x43:
                return new FStore0();
            case 0x44:
                return new FStore1();
            case 0x45:
                return new FStore2();
            case 0x46:
                return new FStore3();
            case 0x47:
                return new DStore0();
            case 0x48:
                return new DStore1();
            case 0x49:
                return new DStore2();
            case 0x4a:
                return new DStore3();
            case 0x4b:
                return new AStore0();
            case 0x4c:
                return new AStore1();
            case 0x4d:
                return new AStore2();
            case 0x4e:
                return new AStore3();
            case 0x4f:
                return null;
            case 0x50:
                return null;
            case 0x51:
                return null;
            case 0x52:
                return null;
            case 0x53:
                return null;
            case 0x54:
                return null;
            case 0x55:
                return null;
            case 0x56:
                return null;
            case 0x57:
                return new Pop();
            case 0x58:
                return new Pop2();
            case 0x59:
                return new Dup();
            case 0x5a:
                return new DupX1();
            case 0x5b:
                return new DupX2();
            case 0x5c:
                return new Dup2();
            case 0x5d:
                return new Dup2X1();
            case 0x5e:
                return new Dup2X2();
            case 0x5f:
                return new Swap();
            case 0x60:
                return new IAdd();
            case 0x61:
                return new LAdd();
            case 0x62:
                return new FAdd();
            case 0x63:
                return new DAdd();
            case 0x64:
                return new ISub();
            case 0x65:
                return new LSub();
            case 0x66:
                return new FSub();
            case 0x67:
                return new DSub();
            case 0x68:
                return new IMul();
            case 0x69:
                return new LMul();
            case 0x6a:
                return new FMul();
            case 0x6b:
                return new DMul();
            case 0x6c:
                return new IDiv();
            case 0x6d:
                return new LDiv();
            case 0x6e:
                return new FDiv();
            case 0x6f:
                return new DDiv();
            case 0x70:
                return new IRem();
            case 0x71:
                return new LRem();
            case 0x72:
                return new FRem();
            case 0x73:
                return new DRem();
            case 0x74:
                return new INeg();
            case 0x75:
                return new LNeg();
            case 0x76:
                return new FNeg();
            case 0x77:
                return new DNeg();
            case 0x78:
                return new IShl();
            case 0x79:
                return new LShl();
            case 0x7a:
                return new IShr();
            case 0x7b:
                return new LShr();
            case 0x7c:
                return new IUShr();
            case 0x7d:
                return new LUShr();
            case 0x7e:
                return new IAnd();
            case 0x7f:
                return new LAnd();
            case 0x80:
                return new IOr();
            case 0x81:
                return new LOr();
            case 0x82:
                return new IXor();
            case 0x83:
                return new LXor();
            case 0x84:
                return new IInc();
            case 0x85:
                return new I2L();
            case 0x86:
                return new I2F();
            case 0x87:
                return new I2D();
            case 0x88:
                return new L2I();
            case 0x89:
                return new L2F();
            case 0x8a:
                return new L2D();
            case 0x8b:
                return new F2I();
            case 0x8c:
                return new F2L();
            case 0x8d:
                return new F2D();
            case 0x8e:
                return new D2I();
            case 0x8f:
                return new D2L();
            case 0x90:
                return new D2F();
            case 0x91:
                return new I2B();
            case 0x92:
                return new I2C();
            case 0x93:
                return new I2S();
            case 0x94:
                return new LCmp();
            case 0x95:
                return new FCmpl();
            case 0x96:
                return new FCmpg();
            case 0x97:
                return new DCmpl();
            case 0x98:
                return new DCmpg();
            case 0x99:
                return new IfEq();
            case 0x9a:
                return new IfNe();
            case 0x9b:
                return new IfLt();
            case 0x9c:
                return new IfGe();
            case 0x9d:
                return new IfGt();
            case 0x9e:
                return new IfLe();
            case 0x9f:
                return new IfICmpEq();
            case 0xa0:
                return new IfICmpNe();
            case 0xa1:
                return new IfICmpLt();
            case 0xa2:
                return new IfICmpGe();
            case 0xa3:
                return new IfICmpGt();
            case 0xa4:
                return new IfICmpLe();
            case 0xa5:
                return new IfACmpEq();
            case 0xa6:
                return new IfACmpNe();
            case 0xa7:
                return new Goto();
            case 0xa8:
                return null;
            case 0xa9:
                return null;
            case 0xaa:
                return new TableSwitch();
            case 0xab:
                return new LookupSwitch();
            case 0xac:
                return null;
            case 0xad:
                return null;
            case 0xae:
                return null;
            case 0xaf:
                return null;
            case 0xb0:
                return null;
            case 0xb1:
                return null;
            case 0xb2:
                return null;
            case 0xb3:
                return null;
            case 0xb4:
                return null;
            case 0xb5:
                return null;
            case 0xb6:
                return null;
            case 0xb7:
                return null;
            case 0xb8:
                return null;
            case 0xb9:
                return null;
            case 0xba:
                return null;
            case 0xbb:
                return null;
            case 0xbc:
                return null;
            case 0xbd:
                return null;
            case 0xbe:
                return null;
            case 0xbf:
                return null;
            case 0xc0:
                return null;
            case 0xc1:
                return null;
            case 0xc2:
                return null;
            case 0xc3:
                return null;
            case 0xc4:
                return new Wide();
            case 0xc5:
                return null;
            case 0xc6:
                return new IfNull();
            case 0xc7:
                return new IfNonNull();
            case 0xc8:
                return new WGoto();
            case 0xc9:
                return null;
            case 0xfe:
                return null;
            case 0xff:
                return null;
            default:
                System.out.println("Invalid opcode: " + opcode);
        }
        return null;
    }

}
