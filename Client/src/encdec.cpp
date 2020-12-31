//
// Created by spl211 on 31/12/2020.
//
#include "encdec.h"

using namespace std;

void encdec::shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

short encdec::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}

string encdec::encode(std::string &line, int len) {
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
        cerr << "invalid command" << endl;

    return result;
}


