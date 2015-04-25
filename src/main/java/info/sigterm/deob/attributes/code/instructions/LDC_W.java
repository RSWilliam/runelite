package info.sigterm.deob.attributes.code.instructions;

import info.sigterm.deob.ClassFile;
import info.sigterm.deob.attributes.code.Instruction;
import info.sigterm.deob.attributes.code.InstructionType;
import info.sigterm.deob.attributes.code.Instructions;
import info.sigterm.deob.execution.Frame;
import info.sigterm.deob.pool.PoolEntry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LDC_W extends Instruction
{
	private int index;

	public LDC_W(Instructions instructions, InstructionType type, int pc) throws IOException
	{
		super(instructions, type, pc);

		DataInputStream is = instructions.getCode().getAttributes().getStream();
		index = is.readUnsignedShort();
		length += 2;
	}
	
	@Override
	public void write(DataOutputStream out, int pc) throws IOException
	{
		super.write(out, pc);
		out.writeShort(index);
	}

	@Override
	public void execute(Frame frame)
	{
		ClassFile thisClass = this.getInstructions().getCode().getAttributes().getClassFile();
		PoolEntry entry = thisClass.getPool().getEntry(index);
		frame.getStack().push(this, entry.getObject());
	}
	
	
	@Override
	public String getDesc(Frame frame)
	{
		ClassFile thisClass = this.getInstructions().getCode().getAttributes().getClassFile();
		PoolEntry entry = thisClass.getPool().getEntry(index);
		
		return "ldc_w " + entry.getObject();
	}
}
