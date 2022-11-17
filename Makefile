# BJRAZ nov 2022
#
# Added this Makefile
# Testet on macOS 10.13

all:	
	-mkdir classes
	javac src/org/dyndns/tfud/TestApps/Test3D.java src/org/dyndns/tfud/Point.java src/org/dyndns/tfud/Frame3D.java -d classes/
run:	all
	java -cp classes/ org.dyndns.tfud.TestApps.Test3D 1 1 1
runold:
	java -cp old_classes org.dyndns.tfud.TestApps.Test3D 1 1 1 
clean:
	rm -rf ./classes

