package di;

import annotations.Qualifier;
import errors.DependencyExistException;
import errors.QualifierException;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/*
    What it does?
 */
public class DependencySupplier {

    private Map<String,Class> classesForQualifier;
    private static final int CLASS =6;


    public void printAll (){
        classesForQualifier.forEach((k,v)-> System.out.println(k+":"+v));
    }

    public DependencySupplier(Map<String,Class> classesForQualifier){
        this.classesForQualifier = classesForQualifier;
    }

    public Class getDependencyForClass(String key){
        return classesForQualifier.get(key);
    }

    public void addDependencyForClass(Class impl){

        if(impl.isAnnotationPresent(Qualifier.class)){
            Qualifier qualifierClass = (Qualifier) impl.getAnnotation(Qualifier.class);
            String key = qualifierClass.value();

            if(!classesForQualifier.containsKey(key)){
                classesForQualifier.put(key,impl);
                return;
            }
            throw new DependencyExistException("Dependency for given key already exists");
        }


    }

    public void supplyDependencies(){
        List<String> classPaths = new ArrayList<>();

        List<URL> urls = Collections.singletonList(ClassLoader.getSystemResource(""));

        for(URL u : urls){
            String path;
            try{
                path = u.toURI().getPath();
            }
            catch (URISyntaxException e){
                path="";
            }
            classPaths.add(path);
        }

        try {
            findAndSupply(classPaths);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void findAndSupply(List<String> classPaths) throws ClassNotFoundException {
        List<String> resultingClassesName = new ArrayList<>();

        for(String path:classPaths){
            List<String> tmp = allClasses(path);
            resultingClassesName.addAll(tmp);
        }

        for(String cls:resultingClassesName){
            addDependencyForClass(Class.forName(cls));
        }
    }
    private List<String> allClasses(String root){
        File folder = new File(root);
        List<String> res = new ArrayList<>();

        for( File f:folder.listFiles()){
            getAllClassesFromFolder(root+File.separator+f.getName(),"",res);
        }
        return res;
    }
    private void getAllClassesFromFolder(String dir,String packageName,List<String> res){
        File file = new File(dir);
        if(file.isDirectory()){
            for(String s:file.list()){
                String pack = (packageName.isEmpty())? file.getName(): packageName+"."+file.getName();
                getAllClassesFromFolder(dir+File.separator+s,pack,res);
            }

        }
        else if(file.getName().endsWith(".class")){
            String name = file.getName().substring(0,file.getName().length() - CLASS);
            res.add(packageName+"."+name);
        }
    }
}
