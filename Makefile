# BJRAZ nov 2022
#
# Added this Makefile
# Testet on macOS 10.13

all:	
	javac src/Test3D.java src/Test3DFrame.java src/Point.java -d classes/
run:	all
	java -cp classes/ Test3D 1 1 1
runold:
	java -cp old_classes Test3D 1 1 1 
clean:
	rm -rf classes/*.class

