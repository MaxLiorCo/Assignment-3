CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lboost_thread -pthread

all: BGRSclient
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/clientMain.o bin/encdec.o $(LDFLAGS)

BGRSclient: bin/connectionHandler.o bin/clientMain.o bin/connectionHandler.o bin/encdec.o
	
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

#bin/echoClient.o: src/echoClient.cpp
#	g++ $(CFLAGS) -o bin/echoClient.o src/echoClient.cpp

bin/clientMain.o: src/clientMain.cpp
	g++ $(CFLAGS) -o bin/clientMain.o src/clientMain.cpp

bin/encdec.o: src/encdec.cpp
	g++ $(CFLAGS) -o bin/encdec.o src/encdec.cpp
	
.PHONY: clean
clean:
	rm -f bin/*
