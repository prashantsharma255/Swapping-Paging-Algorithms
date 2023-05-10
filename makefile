JFLAGS = -g
JC = javac
JVM= java
RM = rm -f
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java
CLASSES = \
	Main.java \
	First_Come_First_Serve.java \
	Least_Frequently_Used.java \
	Least_Recently_Used.java \
	Most_Frequently_Used.java \
	Page.java \
	Process.java \
	Random_Pick.java

MAIN = Main

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

run:
	java Main


