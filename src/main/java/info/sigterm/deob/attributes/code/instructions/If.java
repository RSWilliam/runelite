package info.sigterm.deob.attributes.code.instructions;

import info.sigterm.deob.attributes.code.Instruction;
import info.sigterm.deob.attributes.code.InstructionType;
import info.sigterm.deob.attributes.code.Instructions;
import info.sigterm.deob.execution.Frame;
import info.sigterm.deob.execution.Path;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class If extends Instruction
{
	private short offset;

	public If(Instructions instructions, InstructionType type, int pc) throws IOException
	{
		super(instructions, type, pc);

		DataInputStream is = instructions.getCode().getAttributes().getStream();
		offset = is.readShort();
		length += 2;
	}
	
	@Override
	public void write(DataOutputStream out, int pc) throws IOException
	{
		super.write(out, pc);
		out.writeShort(offset);
	}

	@Override
	public void buildJumpGraph()
	{
		this.addJump(offset);
	}
	
	@Override
	public void execute(Frame e)
	{
		e.getStack().pop();
		e.getStack().pop();
		
		Path other = e.getPath().dup();
		Frame frame = other.getCurrentFrame();
		frame.jump(offset);
	}
}
