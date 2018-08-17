package com.example.bean_compiler;

import com.example.bean_annotion.Filed;
import com.example.bean_annotion.Name;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * Created by Administrator on 2017/3/1 0001.
 */
@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {
	
	private Elements elementUtils;
	private Filer filer;
	private TypeName viewClass;
	private String className;
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
		elementUtils = processingEnvironment.getElementUtils();
		filer = processingEnvironment.getFiler();
	}
	
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> types = new LinkedHashSet<>();
		types.add(Filed.class.getCanonicalName());
		return types;
	}
	
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
		Map<TypeElement, List<mField>> targetMap = new HashMap<>();
		
		for (Element element : roundEnvironment.getElementsAnnotatedWith(Name.class)) {
			className = element.getAnnotation(Name.class).value();
		}
		
		for (Element element : roundEnvironment.getElementsAnnotatedWith(Filed.class)) {
			
			FileUtils.print("element:" + element.getSimpleName().toString()+"\r\n");
			
			TypeElement enClosingElement = (TypeElement) element.getEnclosingElement();
			
			List<mField> list = targetMap.get(enClosingElement);
			if (list == null) {
				list = new ArrayList<>();
				targetMap.put(enClosingElement, list);
			}
			
			String value = element.getAnnotation(Filed.class).value();
			String fieldName = element.getSimpleName().toString();
			TypeMirror typeMirror = element.asType();
			mField mField = new mField(fieldName, typeMirror, value);
			list.add(mField);
		}
		for (Map.Entry<TypeElement, List<mField>> item : targetMap.entrySet()) {
			List<mField> list = item.getValue();
			
			if (list == null || list.size() == 0) {
				continue;
			}
			TypeElement enClosingElement = item.getKey();
			String packageName = getPackageName(enClosingElement);
			String compile = getClassName(enClosingElement, packageName);
			
			TypeSpec.Builder result = TypeSpec.classBuilder(className)
					.addModifiers(Modifier.PUBLIC);
			
			for (int i = 0; i < list.size(); i++) {
				mField mField = list.get(i);
				String packageNameString = mField.getType().toString();
				
				viewClass = bestGuess(packageNameString);
				
				MethodSpec.Builder getMethodBuilder = MethodSpec.methodBuilder("get" +
						mField.getValue())
						.addModifiers(Modifier.PUBLIC)
						.addStatement("$N","return "+ mField.getName())
						.returns(viewClass);
				result.addMethod(getMethodBuilder.build());

				MethodSpec.Builder setMethodBuilder = MethodSpec.methodBuilder("set" +
						mField.getValue())
						.addModifiers(Modifier.PUBLIC)
						.returns(TypeName.VOID)
						.addParameter(viewClass, mField.getName())
						.addStatement("this.$N = $N", mField.getName(), mField.getName());
				
				result.addField(viewClass, mField.getName(),Modifier.PRIVATE);
				result.addMethod(setMethodBuilder.build());
			}
			
			try {
				JavaFile.builder(packageName, result.build())
						.addFileComment("auto create make")
						.build().writeTo(filer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		return false;
	}
	
	private String getClassName(TypeElement enClosingElement, String packageName) {
		int packageLength = packageName.length() + 1;
		return enClosingElement.getQualifiedName().toString().substring(packageLength).replace("" +
				".", "$");
	}
	
	private String getPackageName(TypeElement enClosingElement) {
		return elementUtils.getPackageOf(enClosingElement).getQualifiedName().toString();
	}
	
	private static TypeName bestGuess(String type) {
		switch (type) {
			case "void": return TypeName.VOID;
			case "boolean": return TypeName.BOOLEAN;
			case "byte": return TypeName.BYTE;
			case "char": return TypeName.CHAR;
			case "double": return TypeName.DOUBLE;
			case "float": return TypeName.FLOAT;
			case "int": return TypeName.INT;
			case "long": return TypeName.LONG;
			case "short": return TypeName.SHORT;
			default:
				int left = type.indexOf('<');
				if (left != -1) {
					ClassName typeClassName = ClassName.bestGuess(type.substring(0, left));
					List<TypeName> typeArguments = new ArrayList<>();
					do {
						typeArguments.add(WildcardTypeName.subtypeOf(Object.class));
						left = type.indexOf('<', left + 1);
					} while (left != -1);
					return ParameterizedTypeName.get(typeClassName,
							typeArguments.toArray(new TypeName[typeArguments.size()]));
				}
				return ClassName.bestGuess(type);
		}
	}
}
