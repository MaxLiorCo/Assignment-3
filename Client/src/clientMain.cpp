#include <iostream>
#include <connectionHandler.h>
#include <encdec.h>
#include <bitset>

using namespace std;


/*class KeyboardListener {
private:
    int _id;
    std::mutex &_mutex;
public:
    KeyboardListener(int id, std::mutex &mutex) : _id(id), _mutex(mutex) {}

    void run() {
        std::cin.getline(buf, bufsize);
    }
};*/

void test();
int main(int argc, char *argv[]) {

    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    test();

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    else
        std::cout << "Connection established to: " << host << ":" << port << std::endl;

    bool session = true;
    //From here we will see the rest of the BGRS client implementation:
    while (session) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        int len=line.length();


        std::string toBytes = encdec::encode(line);
        //
        //TODO convert line to required byte array and length
        if (!connectionHandler.sendBytes( toBytes.c_str() , toBytes.length())) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        // connectionHandler.sendBytes()
        std::cout << "Sent " <<  toBytes.length() << " bytes to server" << std::endl;



        char opCodeArr[2];
        char messageOpCodeArr[2];
        // Get back an answer: by using the expected number of bytes (len bytes + newline delimiter)
        // We could also use: connectionHandler.getline(answer) and then get the answer without the newline char at the end
        if (!connectionHandler.getBytes(opCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        if (!connectionHandler.getBytes(messageOpCodeArr, 2)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        short opCode = encdec::decodeTwoBytes(opCodeArr);
        short opCodeMessage = encdec::decodeTwoBytes(messageOpCodeArr);
        if(opCode == 12){
            cout << "ACK " << opCodeMessage << endl;
            char buffer[bufsize];
            std::string str(buffer);
            if(opCodeMessage == 4) //Logout
                session = false;
            else if(true){ //TODO decide which opCodeMessages return additional strings (if required to do so)
                if (!connectionHandler.getFrameAscii(str, '\0')) {
                    std::cout << "Disconnected. Exiting...\n" << std::endl;
                    break;
                }
                cout << str << endl;
            }
        }
        else if(opCode == 13){
            cout << "ERR " << opCodeMessage << endl;
        }
        else
            cout << "Incorrect OpCode returned" << endl;



    }
    std::cout << "got out of loop" << std::endl;

    return 0;
}

void test(){
    std::string command = "UNREGISTER 400";
    std::string s = encdec::encode(command);
    const char* c = s.c_str();
    std::bitset<8> x(c[3]);
    cout << x << endl;  //prints bits of char/byte in string in place 3
    int len = s.length();
}

/*

string encode(std::string linee , int len){
    int nextSpace = line.find(" ");
    string command = line.substr(0, nextSpace); //Command
    string result = "";
    char shortBytes[2];

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