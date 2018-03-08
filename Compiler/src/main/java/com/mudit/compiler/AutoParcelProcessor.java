package com.mudit.compiler;


import com.mudit.customanno.AutoParcel;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.mudit.customanno.AutoParcel")
public final class AutoParcelProcessor extends AbstractProcessor{

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        final String newLine ="\n";
        final String tab = "\t";
        for (TypeElement annotation : annotations) {
        //    processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,annotation.getQualifiedName());
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                if (annotatedElement.getKind() == ElementKind.CLASS) {
                    TypeElement element = (TypeElement) annotatedElement;
        //            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,element.getSimpleName());
                }
            }
        }
        for(Element element: roundEnv.getElementsAnnotatedWith(AutoParcel.class)){
            if(element instanceof TypeElement){
                TypeElement typeElement = (TypeElement) element;
                PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
                try{
                    String className = typeElement.getSimpleName()+"_AutoPraser";
                    JavaFileObject fileObject = processingEnv.getFiler().createSourceFile(packageElement.getQualifiedName()+"."+className);

                    try(Writer writer = fileObject.openWriter()){
                        writer.append("package ").append(String.valueOf(packageElement.getQualifiedName())).append(";");
                        writer.append(newLine+newLine);
                        writer.append("public class ").append(className).append("{");
                        writer.append(newLine+newLine);
                        if(typeElement.getSimpleName().toString().equalsIgnoreCase("Foo")){
                            writer.append(tab+"private static final int COUNT = 10;\n");
                        }else{
                            writer.append(tab+"private static final int COUNT = 20;\n");
                        }

                        for(VariableElement executableElement: ElementFilter.fieldsIn(element.getEnclosedElements())){
                        //    processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,executableElement.getSimpleName());
                            writer.append("private ").append(String.valueOf(executableElement.asType())).append(" ").append(String.valueOf(executableElement.getSimpleName())).append(";"+newLine+newLine);
                            writer.append("public ").append(String.valueOf(executableElement.asType())).append(" get").append(executableElement.getSimpleName());
                            writer.append("(){"+newLine);
                            writer.append(tab + "return ").append(executableElement.getSimpleName()).append(";");
                            writer.append(newLine+"}"+newLine);
                        }
                        /*for (Field field : fields) {



                        }*/

                        writer.append(newLine+newLine);
                        writer.append("}");
                     //   processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,"Writer Done");
                    }
                }catch (IOException ex){
            //        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,ex.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
