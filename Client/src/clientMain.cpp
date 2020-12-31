#include <iostream>
#include <connectionHandler.h>
#include "encdec.h"

using namespace std;

int main(int argc, char *argv[]) {

    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }


    //From here we will see the rest of the BGRS client implementation:
    while (1) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        int len=line.length();


        string toBytes = encdec::encode(line , len);
        //
        //TODO convert line to required byte array and length
        if (!connectionHandler.sendBytes( toBytes.c_str() , toBytes.length())) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        // connectionHandler.sendBytes()
        std::cout << "Sent " <<  toBytes.length() << " bytes to server" << std::endl;



        char answer[2];
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
        if (!connectionHandler.getBytes(answer, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
/*
        len=answer.length();
        // A C string must end with a 0 char delimiter.  When we filled the answer buffer from the socket
        // we filled up to the \n char - we must make sure now that a 0 char is also present. So we truncate last character.
        answer.resize(len-1);
        std::cout << "Reply: " << answer << " " << len << " bytes " << std::endl << std::endl;
        if (answer == "bye") {
            std::cout << "Exiting...\n" << std::endl;
            break;
        }*/
    }
    cout << "got out of loop" <<endl;

    return 0;
}


/*

string encode(std::string linee , int len){
    int nextSpace = line.find(" ");
    string command = line.substr(0, nextSpace); //Command
    string result = "";
    char shortBytes[2];
    //TODO do not forget to transfer to short first otherfwise input like "365" will be 3 bytes
    if(command.compare("ADMINREG")){
        shortToBytes(1,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
        int currSpace = nextSpace;
        nextSpace = line.find(" ",nextSpace + 1);
        result.append(line.substr(currSpace+1,nextSpace)); // append Username
        command.append("\0");
        result.append(line.substr(nextSpace + 1)); // append Password
        command.append("\0");
    }
    else if(command.compare("STUDENTREG")){
        shortToBytes(2,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
        int currSpace = nextSpace;
        nextSpace = line.find(" ",nextSpace + 1);
        result.append(line.substr(currSpace+1,nextSpace)); // append Username
        command.append("\0");
        result.append(line.substr(nextSpace + 1)); // append Password
        command.append("\0");
    }
    else if(command.compare("LOGIN")){
        shortToBytes(3,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
        int currSpace = nextSpace;
        nextSpace = line.find(" ",nextSpace + 1);
        result.append(line.substr(currSpace+1,nextSpace)); // append Username
        command.append("\0");
        result.append(line.substr(nextSpace + 1)); // append Password
        command.append("\0");
    }
    else if(command.compare("LOGOUT")){
        shortToBytes(4,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else if(command.compare("COURSEREG")){
        shortToBytes(5,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else if(command.compare("KDAMCHECK")){
        shortToBytes(6,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else if(command.compare("COURSESTAT")){
        shortToBytes(7,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else if(command.compare("STUDENTSTAT")){
        shortToBytes(8,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
        result.append(line.substr(nextSpace+1)); // append Username
        command.append("\0");
    }
    else if(command.compare("ISREGISTERED")){
        shortToBytes(9,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else if(command.compare("UNREGISTER")){
        shortToBytes(10,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else if(command.compare("MYCOURSES")){
        shortToBytes(11,shortBytes);
        result.append(shortBytes); //opCode to make sure it takes 2 bytes in string
    }
    else
        std::cerr << "invalid command" << std::endl;

    return result;
}

void shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}
*/
