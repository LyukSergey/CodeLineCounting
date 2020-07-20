# counting-code-line

Shows folder structure with count of no commented lines in Java classes. Files with not .java extension will be skipped. 

## to work locally:
run command below in the terminal:
```console
mvn clean install
java -jar [JAR-NAME] ["Absolute path to folder ot file"]
```
or if you run app without arguments:

```console
mvn clean install
java -jar [JAR-NAME]
```
you will be able to type absolute path to folder or file in the console.

with this command counting-code-line application shows you:
1) If a single file is provided as an input, the result has to be in the form of: filename : number of lines. 
E.g. "App.java : 42"
2) If a directory name is provided as an input result should include aggregated values as well, e.g. 

root : 331
   
   subfolder1 : 140 Class1.java : 65 Class2.java : 75
   
   subfolder2 : 161 Class3.java : 102 Class4.java : 59
