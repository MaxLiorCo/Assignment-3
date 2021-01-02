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

string encdec::encode(std::string &line) {
    int nextSpace = line.find(" ");
    string command = line.substr(0, nextSpace); //Command
    string result = "";
    char shortBytes[2];
    //TODO do not forget to transfer to short first otherfwise input like "365" will be 3 bytes
    if(command == "ADMINREG"){
        shortToBytes(1,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        int currSpace = nextSpace;
        nextSpace = line.find(" ",nextSpace + 1);
        result.append(line.substr(currSpace+1,nextSpace-currSpace-1)); // append Username
        result.append("\0",1);
        result.append(line.substr(nextSpace + 1)); // append Password
        result.append("\0",1);
    }
    else if(command == "STUDENTREG"){
        shortToBytes(2,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        int currSpace = nextSpace;
        nextSpace = line.find(" ",nextSpace + 1);
        result.append(line.substr(currSpace+1,nextSpace-currSpace-1)); // append Username
        result.append("\0",1);
        result.append(line.substr(nextSpace + 1)); // append Password
        result.append("\0",1);
    }
    else if(command == "LOGIN"){
        shortToBytes(3,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        int currSpace = nextSpace;
        nextSpace = line.find(" ",nextSpace + 1);
        result.append(line.substr(currSpace+1,nextSpace-currSpace-1)); // append Username
        result.append("\0",1);
        result.append(line.substr(nextSpace + 1)); // append Password
        result.append("\0",1);
    }
    else if(command == "LOGOUT"){
        shortToBytes(4,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
    }
    else if(command == "COURSEREG"){
        shortToBytes(5,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        string course = line.substr(nextSpace+1);
        shortToBytes(atoi( course.c_str() ),shortBytes);
        result.append(shortBytes, 2); // course number encoded in 2 bytes

    }
    else if(command == "KDAMCHECK"){
        shortToBytes(6,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        string course = line.substr(nextSpace+1);
        shortToBytes(atoi( course.c_str() ),shortBytes);
        result.append(shortBytes, 2); // course number encoded in 2 bytes
    }
    else if(command == "COURSESTAT"){
        shortToBytes(7,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        string course = line.substr(nextSpace+1);
        shortToBytes(atoi( course.c_str() ),shortBytes);
        result.append(shortBytes, 2); // course number encoded in 2 bytes
    }
    else if(command == "STUDENTSTAT"){
        shortToBytes(8,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        result.append(line.substr(nextSpace+1)); // append Username
        result.append("\0",1);
    }
    else if(command == "ISREGISTERED"){
        shortToBytes(9,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        string course = line.substr(nextSpace+1);
        shortToBytes(atoi( course.c_str() ),shortBytes);
        result.append(shortBytes, 2); // course number encoded in 2 bytes
    }
    else if(command == "UNREGISTER"){
        shortToBytes(10,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
        string course = line.substr(nextSpace+1);
        shortToBytes(atoi( course.c_str() ),shortBytes);
        result.append(shortBytes, 2); // course number encoded in 2 bytes
    }
    else if(command == "MYCOURSES"){
        shortToBytes(11,shortBytes);
        result.append(shortBytes, 2); //opCode to make sure it takes 2 bytes in string
    }
    else
        cerr << "invalid command" << endl;

    //NOTICE: debugger doesnt show last "\0" but it is included in the string length
    return result;
}

short encdec::decodeTwoBytes(char replyOpCode[]) {
    return bytesToShort(replyOpCode);
}

//we assume a '\0' delimiter
bool encdec::decodeString(ConnectionHandler &ch, string &result){
    char character;
    // Stop when we encounter the null character.
    // Notice that the null character is not appended to the frame string.
    try {
        do{
            if(!ch.getBytes(&character, 1))
            {
                return false;
            }
            if(character!='\0')
                result.append(1, character);
        }while ('\0' != character);
    } catch (std::exception& e) {
        std::cerr << "recv failed2 (Error: " << e.what() << ')' << std::endl;
        return false;
    }
    return true;
}

