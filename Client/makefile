CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system

all: BGRSclient
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/clientMain.o $(LDFLAGS)

BGRSclient: bin/connectionHandler.o bin/clientMain.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

#bin/echoClient.o: src/echoClient.cpp
#	g++ $(CFLAGS) -o bin/echoClient.o src/echoClient.cpp
bin/clientMain.o: src/clientMain.cpp
	g++ $(CFLAGS) -o bin/clientMain.o src/clientMain.cpp
	
.PHONY: clean
clean:
	rm -f bin/*
