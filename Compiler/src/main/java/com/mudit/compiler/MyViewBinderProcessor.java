package com.mudit.compiler;


import com.mudit.customanno.MyViewBinder;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.mudit.customanno.MyViewBinder")
public final class MyViewBinderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        final String newLine = "\n";
        final String tab = "\t";

        HashMap<Element,List<VariableElement>> hashMap = new HashMap<>();

        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(MyViewBinder.class);
        for (Element annotatedElement : annotatedElements) {
            if (annotatedElement.getKind() == ElementKind.FIELD) {
                VariableElement element = (VariableElement) annotatedElement;
                Element k = element.getEnclosingElement();
                if(hashMap.containsKey(k)){
                    hashMap.get(k).add(element);
                }else{
                    List<VariableElement> variableElements = new ArrayList<>();
                    variableElements.add(element);
                    hashMap.put(element.getEnclosingElement(),variableElements);
                }
            }
        }


        for (Map.Entry<Element,List<VariableElement>> entry : hashMap.entrySet()){
            printMessage("Key = " + entry.getKey().getSimpleName());
            try {
                TypeElement typeElement = (TypeElement) entry.getKey();
                PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
                String className = typeElement.getSimpleName()+"_MBinder";
                JavaFileObject fileObject = processingEnv.getFiler().createSourceFile(packageElement.getQualifiedName()+"."+className);
                try(Writer writer = fileObject.openWriter()){
                    writer.append("package ").append(String.valueOf(packageElement.getQualifiedName())).append(";");
                    writer.append(newLine+newLine);
                    writer.append("import com.mudit.customanno.BaseBinder;");
                    writer.append(newLine+newLine);
                    writer.append("public class ").append(className).append(" implements BaseBinder{"+newLine);
                    writer.append(tab).append("public ").append(className).append("(").append(String.valueOf(typeElement.getSimpleName())).append(" activity){"+newLine);
                    for(VariableElement var: entry.getValue()){
                        writer.append(tab+tab);
                        writer.append("activity.").append(String.valueOf(var.getSimpleName())).append(" = ").append("activity.getWindow().getDecorView().findViewById(").append(String.valueOf(var.getAnnotation(MyViewBinder.class).value())).append(");");
                        writer.append(newLine);
                    }
                    writer.append(newLine+tab).append("}");
                    writer.append(newLine+"}");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        /*for(Element element: roundEnv.getElementsAnnotatedWith(AutoParcel.class)){
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
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,executableElement.getSimpleName());
                            writer.append("private ").append(String.valueOf(executableElement.asType())).append(" ").append(String.valueOf(executableElement.getSimpleName())).append(";"+newLine+newLine);
                            writer.append("public ").append(String.valueOf(executableElement.asType())).append(" get").append(executableElement.getSimpleName());
                            writer.append("(){"+newLine);
                            writer.append(tab + "return ").append(executableElement.getSimpleName()).append(";");
                            writer.append(newLine+"}"+newLine);
                        }
                        *//*for (Field field : fields) {



                        }*//*

                        writer.append(newLine+newLine);
                        writer.append("}");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING,"Writer Done");
                    }
                }catch (IOException ex){
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,ex.getMessage());
                }
            }
        }*/
        return true;
    }



    private String getName(VariableElement variableElement){
        return variableElement.getEnclosingElement().getSimpleName().toString();
    }


    private void printMessage(String msg){
        processingEnv.getMessager().printMessage(Diagnostic.Kind.MANDATORY_WARNING, msg);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
