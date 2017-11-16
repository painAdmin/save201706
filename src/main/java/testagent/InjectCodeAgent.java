package testagent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

class InjectCodeClassWriter extends ClassWriter{

	private static final String oldClass="A";
	private static final String newClass="B";
	public InjectCodeClassWriter(int flags) {
		super(flags);
	}

	@Override
	public int newUTF8(final String value) {
        //将APP.class中 常量池（constant pool） 中类  A的的字符串 改为 类B的字符串
		if(value.equals(oldClass)){
			return super.newClass(newClass);
		}
		return super.newUTF8(value);
	}
}

class InjectCodeTransformer implements ClassFileTransformer{
	private static final String appClass="App";

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if(className.equals(appClass)){
			ClassWriter classWriter=new InjectCodeClassWriter(0);
			ClassReader classReader=new ClassReader(classfileBuffer);
			classReader.accept(classWriter, 0);
			return classWriter.toByteArray();
		}
		return null;
	}
}

public class InjectCodeAgent{
	public static void premain(String args,Instrumentation inst){
		inst.addTransformer(new InjectCodeTransformer());
		
	}
	
	
}


















